package org.ivangeevo.btwr_ds.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

/**
@Mixin(EmiFuelRecipe.class)
public abstract class EmiFuelRecipeMixin {

    @Inject(method = "addWidgets", at = @At("TAIL"))
    private void modifyFuelText(WidgetHolder widgets, CallbackInfo ci) {
        EmiFuelRecipe self = (EmiFuelRecipe)(Object)this;
        int time = self.getFuelTime(); // you may need to expose this via an accessor
        String text = formatBurnTime(time);
        widgets.addText(Text.of(text), 38, 5, -1, true);
    }

    private String formatBurnTime(int ticks) {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return "Burns for " + (minutes > 0 ? minutes + "m " : "") + remainingSeconds + "s";
    }

}
 **/