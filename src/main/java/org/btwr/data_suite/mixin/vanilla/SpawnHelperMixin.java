package org.btwr.data_suite.mixin.vanilla;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SpawnHelper;
import org.btwr.data_suite.util.RepellingSoulTorch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {
    /**
     * Changes the world spawn 24-block restriction to 0, effectively
     * allowing hostile mobs to spawn right at world spawn.
     */
    @ModifyConstant(
            method = "isAcceptableSpawnPosition",
            constant = @Constant(doubleValue = 24.0)
    )
    private static double removeWorldSpawnRestriction(double original) {
        return 0.0; // No exclusion radius
    }

    //@Inject(method = "isValidSpawn", at = @At("HEAD"), cancellable = true)
    private static void blockSpawnsNearSoulTorch(
            ServerWorld world, MobEntity entity, double squaredDistance, CallbackInfoReturnable<Boolean> cir
    ) {
        if (RepellingSoulTorch.getInstance().isMobNearby(world, entity.getBlockPos())) {
            cir.setReturnValue(false); // cancel spawn
        }
    }
}