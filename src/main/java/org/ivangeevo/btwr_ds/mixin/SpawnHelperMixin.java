package org.ivangeevo.btwr_ds.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {

    @Inject(method = "isValidSpawn", at = @At("HEAD"), cancellable = true)
    private static void blockSpawnsNearSoulTorch(
            ServerWorld world, MobEntity entity, double squaredDistance, CallbackInfoReturnable<Boolean> cir
    ) {
        if (isNearSoulTorch(world, entity.getBlockPos())) {
            cir.setReturnValue(false); // cancel spawn
        }
    }

    @Unique
    private static boolean isNearSoulTorch(WorldAccess world, BlockPos center) {
        int radius = 27; // tweak as needed

        for (BlockPos pos : BlockPos.iterate(center.add(-radius, -2, -radius), center.add(radius, 2, radius))) {
            if (world.getBlockState(pos).isOf(Blocks.SOUL_TORCH) && pos.isWithinDistance(center, radius)) {
                return true;
            }
        }
        return false;
    }
}
