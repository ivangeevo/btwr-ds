package org.ivangeevo.btwr_ds.mixin.client;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin
{
    //@Inject(method = "getSimulationDistance", at = @At("HEAD"), cancellable = true)
    private void lockSimDistance(CallbackInfoReturnable<Integer> cir) {
        // locks the simulation distance to the default 12 (the slider can still move)
        cir.setReturnValue(12);
    }
}
