package org.ivangeevo.btwr_ds.mixin.vanilla.entity;

import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    @Inject(method = "canSummonGolem", at = @At("HEAD"), cancellable = true)
    private void onCanSummonGolem(long time, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
