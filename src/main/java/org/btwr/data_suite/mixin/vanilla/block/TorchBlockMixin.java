package org.btwr.data_suite.mixin.vanilla.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.btwr.data_suite.util.RepellingSoulTorch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TorchBlock.class)
public abstract class TorchBlockMixin {

    // Add soul particle effects to soul torches to indicate of their repel and spawn blocking behavior
    //@Inject(method = "randomDisplayTick", at = @At("TAIL"))
    private void addHorizontalSoulParticles(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        RepellingSoulTorch.getInstance().displayHorizontalParticles(state, world, pos, random);
    }

}