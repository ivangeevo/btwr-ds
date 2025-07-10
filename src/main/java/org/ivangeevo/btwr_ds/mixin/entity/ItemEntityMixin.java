package org.ivangeevo.btwr_ds.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity
{

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // Changes item entity discard (despawn) timer from 5 min(6000ticks) to 20min(24000ticks)
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 6000))
    private int modifyDespawnTime(int constant) {
        return 24000;
    }



}
