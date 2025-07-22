package org.ivangeevo.btwr_ds.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {

    @Shadow protected abstract List<String> getLeftText();

    @Shadow protected abstract void drawText(DrawContext context, List<String> text, boolean left);

    @Shadow @Final private MinecraftClient client;

    // Cancels the left text to remove the debug charts text
    @Inject(method = "drawLeftText", at = @At("HEAD"), cancellable = true)
    private void onDrawLeftText(DrawContext context, CallbackInfo ci) {
        if (this.shouldHideDebug()) return;

        List<String> list = this.getLeftText();
        list.add("");
        this.drawText(context, list, true);
        ci.cancel();
    }

    // Filters the left text to only display specific text
    @Inject(method = "getLeftText", at = @At("RETURN"), cancellable = true)
    private void onGetLeftText(CallbackInfoReturnable<List<String>> cir) {
        if (this.shouldHideDebug()) return;

        List<String> original = cir.getReturnValue();
        List<String> filtered = new ArrayList<>();

        for (String line : original) {
            if (line.startsWith("Minecraft ")) {
                filtered.add(line);
            } else if (line.startsWith("FPS") || line.contains("fps")) {
                filtered.add(line);
            } else if (line.startsWith("Integrated server") || line.contains("server")) {
                filtered.add(line);
            }
        }

        cir.setReturnValue(filtered);
    }

    // Filters the right text to only display specific text
    @Inject(method = "getRightText", at = @At("RETURN"), cancellable = true)
    private void trimRightText(CallbackInfoReturnable<List<String>> cir) {
        if (this.shouldHideDebug()) return;

        List<String> original = cir.getReturnValue();
        int gpuIndex = -1;

        for (int i = 0; i < original.size(); i++) {
            if (original.get(i).startsWith("Display:")) {
                if (i + 2 < original.size()) {
                    gpuIndex = i + 2;
                }
            }
        }

        if (gpuIndex != -1) {
            cir.setReturnValue(original.subList(0, gpuIndex + 1));
        }
    }

    @Unique
    private boolean shouldHideDebug() {
        assert this.client.player != null;
        return this.client.player.isCreative() || this.client.player.isSpectator();
    }

}
