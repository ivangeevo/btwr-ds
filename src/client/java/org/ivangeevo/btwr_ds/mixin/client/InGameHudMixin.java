package org.ivangeevo.btwr_ds.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {


    @ModifyArg(
            method = "drawHeart",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud$HeartType;getTexture(ZZZ)Lnet/minecraft/util/Identifier;"),
            index = 0
    )
    private boolean alwaysHardcore(boolean original) {
        return true; // force hardcore heart style
    }


    // Displays the saturation in the left top corner for debugging
    //@Inject(method = "render", at = @At("TAIL"))
    private void renderSaturationText(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        float saturation = client.player.getHungerManager().getSaturationLevel();
        String text = String.format("Saturation: %.2f", saturation);

        int x = 5;
        int y = 5;
        int color = 0xFFAA00;

        context.drawText(
                client.textRenderer,
                text,
                x,
                y,
                color,
                true
        );
    }
}
