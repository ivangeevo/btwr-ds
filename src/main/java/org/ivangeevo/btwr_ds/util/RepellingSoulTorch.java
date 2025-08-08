package org.ivangeevo.btwr_ds.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class RepellingSoulTorch {

    private static final RepellingSoulTorch INSTANCE = new RepellingSoulTorch();

    private RepellingSoulTorch() {}

    public static RepellingSoulTorch getInstance() {
        return INSTANCE;
    }

    private static final int SPAWN_DISABLED_RADIUS = 27;

    private static final int REPEL_RADIUS = 6;

    /** Used to disable mob spawning around soul torches **/
    public boolean isMobNearby(WorldAccess world, BlockPos center) {
        for (BlockPos pos : BlockPos.iterate(
                center.add(-SPAWN_DISABLED_RADIUS, -2, -SPAWN_DISABLED_RADIUS),
                center.add(SPAWN_DISABLED_RADIUS, 2, SPAWN_DISABLED_RADIUS)
        )) {
            if (world.getBlockState(pos).isOf(Blocks.SOUL_TORCH) && pos.isWithinDistance(center, SPAWN_DISABLED_RADIUS)) {
                return true;
            }
        }
        return false;

    }

    /** Repel mobs close to a soul torch **/
    public void repelMobs(MobEntity mobEntity) {
        if (mobEntity.getWorld().isClient) return;

        // Only apply to hostile mobs
        if (!(mobEntity instanceof HostileEntity)) return;

        BlockPos mobPos = mobEntity.getBlockPos();

        BlockPos.stream(mobPos.add(-REPEL_RADIUS, -2, -REPEL_RADIUS), mobPos.add(REPEL_RADIUS, 2, REPEL_RADIUS))
                .filter(pos -> mobEntity.getWorld().getBlockState(pos).getBlock() == Blocks.SOUL_TORCH)
                .filter(pos -> pos.isWithinDistance(mobEntity.getPos(), REPEL_RADIUS))
                .findFirst()
                .ifPresent(pos -> {
                    Vec3d direction = mobEntity.getPos().subtract(Vec3d.ofCenter(pos)).normalize();
                    double strength = 0.15;
                    mobEntity.addVelocity(direction.multiply(strength));
                    mobEntity.velocityModified = true;
                });

    }

    public void displayHorizontalParticles(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.isOf(Blocks.SOUL_TORCH)) return;

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

    public void displayHorizontalWallParticles(BlockState state, World world, BlockPos pos, Random random) {
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
