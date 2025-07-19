package org.ivangeevo.btwr_ds.mixin.block;

import com.bwt.blocks.SoilPlanterBlock;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.ivangeevo.vegehenna.block.blocks.SugarCaneRootsBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.state.property.Properties.MOISTURE;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @Inject(method = "randomTick", at = @At("HEAD"))
    private void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (((AbstractBlock)(Object)this) instanceof SoilPlanterBlock) {
            int i = state.get(MOISTURE);
            if (!isWaterNearby(world, pos) && !world.hasRain(pos.up())) {
                if (i > 0) {
                    world.setBlockState(pos, state.with(MOISTURE, i - 1), 2);
                }
            } else if (i < 7) {
                world.setBlockState(pos, state.with(MOISTURE, 7), 2);
            }
        }
    }

    @Inject(method = "onEntityCollision", at = @At("HEAD"))
    private void slowDownEntityMovement(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {

        if (/**entity.isAffectedByMovementModifiers() &&**/ entity.isOnGround()) {
            // Apply the velocity change only for PlantBlock and it's subclasses
            if (!(state.getBlock() instanceof PlantBlock) && !isMiscSlowingBlock(state)) return;

            // Don't apply to creative mode players
            if (entity instanceof PlayerEntity player && player.isCreative()) return;

            double velX = entity.getVelocity().getX();
            double velY = entity.getVelocity().getY();
            double velZ = entity.getVelocity().getZ();

            // 0.8D = 80 percent off the original speed
            entity.setVelocity(velX * 0.8D, velY, velZ * 0.8D);
        }
    }

    @Unique
    private static boolean isMiscSlowingBlock(BlockState state) {
        Block block = state.getBlock();
        return state.isIn(BlockTags.LEAVES)
                || block instanceof SugarCaneBlock
                || block instanceof SugarCaneRootsBlock;
    }

    @Unique
    private static boolean isWaterNearby(WorldView world, BlockPos pos) {
        // Original area check
        for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (world.getFluidState(blockPos).isIn(FluidTags.WATER) || world.getFluidState(blockPos).isOf(Fluids.WATER)) {
                return true;
            }
        }

        // Additional check: directly below
        BlockPos below = pos.down();
        return world.getFluidState(below).isIn(FluidTags.WATER) || world.getFluidState(below).isOf(Fluids.WATER);
    }

}
