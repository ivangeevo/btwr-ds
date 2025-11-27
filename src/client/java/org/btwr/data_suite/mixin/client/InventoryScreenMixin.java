package org.btwr.data_suite.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {

    // Apply fat level player model modifications in the inventory rendered entity
    @Inject(
        method = "drawEntity(Lnet/minecraft/client/gui/DrawContext;FFFLorg/joml/Vector3f;Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;Lnet/minecraft/entity/LivingEntity;)V",
        at = @At("HEAD")
    )
    private static void modifyModelScaleForInventory(
        DrawContext context,
        float x, float y, float size,
        Vector3f offset,
        Quaternionf rotation1,
        Quaternionf rotation2,
        LivingEntity entity,
        CallbackInfo ci
    ) {
        if (!(entity instanceof PlayerEntity player)) return;

        // Fetch the active model from the EntityRenderDispatcher
        EntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        if (!(dispatcher.getRenderer(player) instanceof LivingEntityRenderer<?, ?> livingRenderer)) return;
        if (!(livingRenderer.getModel() instanceof BipedEntityModel<?> model)) return;

        float saturation = player.getHungerManager().getSaturationLevel();
        float zScale = 1.0f;
        float xScale = 1.0f;

        if (saturation >= 36 && saturation < 42) {
            zScale = 1.625f;
        } else if (saturation >= 42 && saturation < 48) {
            zScale = 2.25f;
            xScale = zScale / 2;
        } else if (saturation >= 48 && saturation < 54) {
            zScale = 2.875f;
            xScale = zScale / 2;
        } else if (saturation >= 54 && saturation <= 60) {
            zScale = 3.5f;
            xScale = zScale / 2;
        }

        // Apply scale and offsets
        model.body.xScale = xScale;
        model.body.zScale = zScale;
        float armOffset = 5.0f * (xScale - 1.0f);
        model.leftArm.pivotX = 5.0f + armOffset;
        model.rightArm.pivotX = -5.0f - armOffset;
    }

}