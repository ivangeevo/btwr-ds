package org.ivangeevo.btwr_ds.mixin.vanilla;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SpawnHelper;
import org.ivangeevo.btwr_ds.util.RepellingSoulTorch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {

    //@Inject(method = "isValidSpawn", at = @At("HEAD"), cancellable = true)
    private static void blockSpawnsNearSoulTorch(
            ServerWorld world, MobEntity entity, double squaredDistance, CallbackInfoReturnable<Boolean> cir
    ) {
        if (RepellingSoulTorch.getInstance().isMobNearby(world, entity.getBlockPos())) {
            cir.setReturnValue(false); // cancel spawn
        }
    }

}
