package org.ivangeevo.btwr_ds.mixin.client;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Arrays;

@Mixin(CreateWorldScreen.GameTab.class)
public abstract class GameTabMixin {

    @ModifyArg(
        method = "<init>", // constructor of GameTab
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;values([Ljava/lang/Object;)Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;",
                ordinal = 0
        ),
        index = 0
    )
    private Object[] removeHardcoreOption(Object[] original) {
        return Arrays.stream(original)
            .filter(obj -> obj != WorldCreator.Mode.HARDCORE)
            .toArray();
    }

    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;values([Ljava/lang/Object;)Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;"
            ),
            index = 0,
            slice = @Slice( // disambiguate between the Mode and Difficulty button
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/Difficulty;values()[Lnet/minecraft/world/Difficulty;")
            )
    )
    private Object[] removePeacefulDifficulty(Object[] original) {
        return Arrays.stream(original)
                .filter(obj -> obj != Difficulty.PEACEFUL)
                .toArray();
    }
}
