package org.ivangeevo.btwr_ds.mixin.entity;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor
{

    @Accessor("jumping")
    boolean isJumping();

    @Invoker("getMovementSpeed")
    float getMovementSpeed(float slipperiness);
}
