package org.btwr.data_suite.mixin.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import org.btwr.data_suite.util.BlockReplacementRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Modifies world generation by replacing specific blocks based on the BlockReplacementRegistry.
 * Redirects block placement in ProtoChunk to apply replacements dynamically.
 */
@Mixin(ProtoChunk.class)
public abstract class OGProtoChunkMixin {
    /**
    @Redirect(method = "setBlockState", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/chunk/ChunkSection;setBlockState(IIILnet/minecraft/block/BlockState;)Lnet/minecraft/block/BlockState;")
    )
    private BlockState replaceBlockState(ChunkSection chunkSection, int x, int y, int z, BlockState state) {

        Block original = state.getBlock();
        Block replaced = BlockReplacementRegistry.getReplacementFor(original);

        //boolean shouldCopy = BlockReplacementRegistry.shouldCopyProperties(original);

        if (original != replaced) {
            if (!shouldCopy) {
                state = replaced.getDefaultState();
            } else {
                // Copy properties from the original state
                state = replaced.getStateWithProperties(state);
            }
        }

        return chunkSection.setBlockState(x, y, z, state);
    }
    **/
}