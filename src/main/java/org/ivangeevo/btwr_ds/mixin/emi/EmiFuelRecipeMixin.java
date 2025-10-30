package org.ivangeevo.btwr_ds.mixin.emi;

import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.recipe.EmiFuelRecipe;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EmiFuelRecipe.class)
public abstract class EmiFuelRecipeMixin {

    @Inject(method = "addWidgets", at = @At(
            value = "INVOKE",
            target = "Ldev/emi/emi/api/widget/WidgetHolder;addText(Lnet/minecraft/text/Text;IIIZ)Ldev/emi/emi/api/widget/TextWidget;"),
            cancellable = true
    )
    private void modifyFuelText(WidgetHolder widgets, CallbackInfo ci) {
        EmiFuelRecipe self = (EmiFuelRecipe)(Object)this;
        EmiFuelRecipeAccessor accessor = (EmiFuelRecipeAccessor)self;
        int time = accessor.getTime();
        String text = formatBurnTime(time);
        widgets.addText(Text.of(text), 38, 5, -1, true);
        ci.cancel();
    }

    @Unique
    private String formatBurnTime(int ticks) {
        int seconds = ticks / 20;
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return "Burns for " + (minutes > 0 ? minutes + "m " : "") + remainingSeconds + "s";
    }

}
