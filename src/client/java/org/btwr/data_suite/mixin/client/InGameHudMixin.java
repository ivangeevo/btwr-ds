package org.btwr.data_suite.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    // Force hardcore heart style
    @ModifyArg(
            method = "drawHeart",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud$HeartType;getTexture(ZZZ)Lnet/minecraft/util/Identifier;"),
            index = 0
    )
    private boolean alwaysHardcore(boolean original) {
        return true;
    }

    /**
    // Disables XYZ cursor rendering
    @Redirect(
            method = "renderCrosshair(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/DebugHud;shouldShowDebugHud()Z"
            )
    )
    private boolean disableDebugHudXYZCursor(DebugHud instance) {
        // Always return false to disable XYZ cursor rendering
        return false;
    }
    **/


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