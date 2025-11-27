package org.btwr.data_suite.mixin.vanilla.entity;

import net.minecraft.entity.mob.GhastEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GhastEntity.class)
public abstract class GhastEntityMixin {

    // Modifies the random that handles ghast spawning. This increases ghast spawn rate.
    @ModifyArg(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"))
    private static int modifyGhastSpawnRate(int bound)  {
        return 5;
    }

}