package org.ivangeevo.btwr_ds.mixin.client;

import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin {

    @ModifyArg(
        method = "createDifficultyButtonWidget",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;values([Ljava/lang/Object;)Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;"
        ),
        index = 0
    )
    private static Object[] removePeacefulFromOptions(Object[] original) {
        return Arrays.stream(original)
            .filter(o -> o != Difficulty.PEACEFUL)
            .toArray();
    }
}
