package org.ivangeevo.btwr_ds.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.ivangeevo.vegehenna.block.blocks.SugarCaneRootsBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "onEntityCollision", at = @At("HEAD"))
    private void slowDownEntityMovement(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {

        if (/**entity.isAffectedByMovementModifiers() &&**/ entity.isOnGround()) {
            // Apply the velocity change only for PlantBlock and it's subclasses
            if (!(state.getBlock() instanceof PlantBlock) && !isMiscSlowingBlock(state)) return;

            // Don't apply to creative mode players
            if (entity instanceof PlayerEntity player && player.isCreative()) return;

            double velocityX = entity.getVelocity().getX();
            double velocityY = entity.getVelocity().getY();
            double velocityZ = entity.getVelocity().getZ();

            // 0.8D = 80 percent off the original speed
            entity.setVelocity(velocityX * 0.8D, velocityY, velocityZ * 0.8D);
        }
    }

    @Unique
    private static boolean isMiscSlowingBlock(BlockState state) {
        Block block = state.getBlock();
        return state.isIn(BlockTags.LEAVES)
                || block instanceof SugarCaneBlock
                || block instanceof SugarCaneRootsBlock
                || block instanceof VineBlock;
    }

}
