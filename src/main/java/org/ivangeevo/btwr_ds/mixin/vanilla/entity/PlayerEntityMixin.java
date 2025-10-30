package org.ivangeevo.btwr_ds.mixin.vanilla.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.math.Vec3d;
import org.ivangeevo.btwr_ds.util.ArmorWeightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{

    // Add extra exhaustion to all actions when wearing armor
    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void addExhaustionWithArmorWeight(float originalAmount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        float weight = ArmorWeightManager.getInstance().getArmorWeight(player);
        float multiplier = 1.0f + (Math.min(weight, 44.0f) / 44.0f); // max 2x
        player.getHungerManager().addExhaustion(originalAmount * multiplier);
        ci.cancel();
    }

    // Make the player sink in water if the armor they are wearing is too heavy
    //@Inject(method = "travel", at = @At("HEAD"))
    private void sinkIfTooHeavy(Vec3d movementInput, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if (player.isTouchingWater() && !player.isClimbing() && !player.hasVehicle()) {
            float weight = ArmorWeightManager.getInstance().getArmorWeight(player);

            if (!(player instanceof LivingEntity livingEntity)) return;
            if (weight >= 11.0f) {
                Vec3d velocity = player.getVelocity();
                // Pull down harder if player tries to swim up
                if (((LivingEntityAccessor) livingEntity).isJumping()) {
                    player.setVelocity(velocity.x, -0.05, velocity.z);
                } else {
                    player.setVelocity(velocity.x, velocity.y - 0.02, velocity.z);
                }
            }
        }
    }

    // Makes arrows only able to work in the hotbar or the offhand
    @Inject(method = "getProjectileType", at = @At("HEAD"), cancellable = true)
    private void restrictProjectileToHotbarAndOffhand(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (!(stack.getItem() instanceof RangedWeaponItem rangedWeapon)) return;

        Predicate<ItemStack> predicate = rangedWeapon.getHeldProjectiles();

        PlayerEntity player = (PlayerEntity)(Object)this;

        // Check offhand
        ItemStack offhand = player.getOffHandStack();
        if (predicate.test(offhand)) {
            cir.setReturnValue(offhand);
            return;
        }

        // Check hotbar (slots 0-8)
        for (int i = 0; i < 9; i++) {
            ItemStack stack1 = player.getInventory().getStack(i);
            if (predicate.test(stack1)) {
                cir.setReturnValue(stack1);
                return;
            }
        }

        // No arrow found
        cir.setReturnValue(ItemStack.EMPTY);
    }

}
