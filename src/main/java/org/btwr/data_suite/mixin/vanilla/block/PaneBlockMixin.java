package org.btwr.data_suite.mixin.vanilla.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.Blocks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(PaneBlock.class)
public abstract class PaneBlockMixin {
    // Define blocks that panes must NOT connect to
    @Unique
    private static final Set<Block> BLACKLISTED_CONNECTION_BLOCKS = Set.of(
        Blocks.PISTON_HEAD
        // Add more blocks here
    );

    @Inject(
        method = "connectsTo",
        at = @At("HEAD"),
        cancellable = true
    )
    private void injectedConnectsTo(BlockState state, boolean sideSolidFullSquare, CallbackInfoReturnable<Boolean> cir) {
        if (BLACKLISTED_CONNECTION_BLOCKS.contains(state.getBlock())) {
            cir.setReturnValue(false);
        }
    }
}