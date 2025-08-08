package org.ivangeevo.btwr_ds.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.ivangeevo.btwr_ds.util.PatchyMovement;
import org.ivangeevo.btwr_ds.util.ShieldModificationManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

    // Makes the player resistant to poison and nausea if wearing full netherite armor
    @Inject(
            method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelPoisonAndNausea(StatusEffectInstance effect, @Nullable Entity source, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof PlayerEntity player) {
            boolean fullNetherite = true;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                    ItemStack stack = player.getEquippedStack(slot);
                    if (!(stack.getItem() instanceof ArmorItem armor) ||
                            armor.getMaterial() != ArmorMaterials.NETHERITE) {
                        fullNetherite = false;
                        break;
                    }
                }
            }
            if (fullNetherite &&
                    (effect.getEffectType().matches(StatusEffects.POISON) ||
                            effect.getEffectType().matches(StatusEffects.NAUSEA))) {
                cir.setReturnValue(false); // block the effect from being added
            }
        }
    }

}
