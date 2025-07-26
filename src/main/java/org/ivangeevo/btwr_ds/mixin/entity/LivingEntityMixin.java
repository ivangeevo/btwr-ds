package org.ivangeevo.btwr_ds.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.ivangeevo.btwr_ds.util.PatchyMovement;
import org.ivangeevo.btwr_ds.util.ShieldModificationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements PatchyMovement
{

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V", shift = At.Shift.AFTER))
    private void onDamageShield(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ShieldModificationManager.getInstance().applyExhaustionOnBlock((LivingEntity)(Object)this, source);
    }

    @Inject(method = "blockedByShield", at = @At("HEAD"), cancellable = true)
    private void onBlockedByShield(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        ShieldModificationManager.getInstance().modifyShieldCone((LivingEntity)(Object)this, source, cir);
    }

}
