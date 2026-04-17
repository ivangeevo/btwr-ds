package org.btwr.data_suite.mixin.vanilla.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.btwr.data_suite.util.RepellingSoulTorch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WallTorchBlock.class)
public abstract class WallTorchBlockMixin {
    //@Inject(method = "randomDisplayTick", at = @At("TAIL"))
    private void addHorizontalSoulParticles(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        RepellingSoulTorch.getInstance().displayHorizontalWallParticles(state, world, pos, random);
    }
}