package org.btwr.data_suite.mixin.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {

    @Shadow @Final public ModelPart body;
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart rightArm;

    // TODO: Rework a bit to match retail BTW more closely
    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void modifyBodyBySaturation(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (!FabricLoader.getInstance().isModLoaded("granular_hunger")) return;
        if (entity instanceof PlayerEntity player) {
            float saturation = player.getHungerManager().getSaturationLevel();
            float zScaleFactor = 1.0f;
            float xScaleFactor = 1.0f;

            // Plump
            if (saturation >= 36 && saturation < 42) {
                zScaleFactor = 1.625f;

            }
            // Chubby
            else if (saturation >= 42 && saturation < 48) {
                zScaleFactor = 2.25f;
                xScaleFactor = zScaleFactor / 2;

            }
            // Fat
            else if (saturation >= 48 && saturation < 54) {
                zScaleFactor = 2.875f;
                xScaleFactor = zScaleFactor / 2;

            }
            // Obese
            else if (saturation >= 54 && saturation <= 60) {
                zScaleFactor = 3.5f;
                xScaleFactor = zScaleFactor / 2;
            }

            // Modify the Z and X scales
            this.body.zScale = zScaleFactor;
            this.body.xScale = xScaleFactor;

            // Offset the arms to the side to match the modified xScale
            float armOffset = 5.0f * (xScaleFactor - 1.0f);
            this.leftArm.pivotX = 5.0f + armOffset;
            this.rightArm.pivotX = -5.0f - armOffset;
        }
    }

}