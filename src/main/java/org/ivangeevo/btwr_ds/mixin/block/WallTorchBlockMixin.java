package org.ivangeevo.btwr_ds.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WallTorchBlock.class)
public abstract class WallTorchBlockMixin {

    @Inject(method = "randomDisplayTick", at = @At("TAIL"))
    private void addHorizontalSoulParticles(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {

        if (!state.isOf(Blocks.SOUL_WALL_TORCH)) return;

        if (random.nextFloat() > 0.3f) return; // Control spawn rate

        Direction facing = state.get(WallTorchBlock.FACING);

        // Shift center away from wall side (offset magnitude ~0.27)
        double centerX = pos.getX() + 0.5 - facing.getOffsetX() * 0.27;
        double centerY = pos.getY() + 0.7;
        double centerZ = pos.getZ() + 0.5 - facing.getOffsetZ() * 0.27;

        for (Direction dir : Direction.Type.HORIZONTAL) {
            double dx = centerX + dir.getOffsetX() * 0.4;
            double dz = centerZ + dir.getOffsetZ() * 0.4;
            double vx = dir.getOffsetX() * 0.02;
            double vz = dir.getOffsetZ() * 0.02;

            world.addParticle(ParticleTypes.SOUL, dx, centerY, dz, vx, 0, vz);
        }
    }
}

