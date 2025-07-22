package org.ivangeevo.btwr_ds.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TorchBlock.class)
public abstract class TorchBlockMixin {

    // Add soul particle effects to soul torches to indicate of their repel and spawn blocking behavior
    @Inject(method = "randomDisplayTick", at = @At("TAIL"))
    private void addHorizontalSoulParticles(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {

        if (state.isOf(Blocks.SOUL_TORCH) || state.isOf(Blocks.SOUL_WALL_TORCH)) {
            if (random.nextFloat() > 0.3f) return; // Control spawn rate

            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.7;
            double z = pos.getZ() + 0.5;

            for (Direction dir : Direction.Type.HORIZONTAL) {
                double dx = x + dir.getOffsetX() * 0.4;
                double dz = z + dir.getOffsetZ() * 0.4;
                double vx = dir.getOffsetX() * 0.02;
                double vz = dir.getOffsetZ() * 0.02;

                world.addParticle(ParticleTypes.SOUL, dx, y, dz, vx, 0, vz);
            }
        }


    }
}
