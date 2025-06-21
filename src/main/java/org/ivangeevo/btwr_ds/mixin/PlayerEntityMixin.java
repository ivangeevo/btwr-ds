package org.ivangeevo.btwr_ds.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {


    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void modifyArmorExhaustion(float originalAmount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        float weight = getArmorWeight(player);

        float multiplier = 1.0f + (Math.min(weight, 44.0f) / 44.0f); // max 2x
        player.getHungerManager().addExhaustion(originalAmount * multiplier);
        ci.cancel();
    }

    @Inject(method = "travel", at = @At("HEAD"))
    private void sinkIfTooHeavy(Vec3d movementInput, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if (player.isTouchingWater() && !player.isClimbing() && !player.hasVehicle()) {
            float weight = getArmorWeight(player);

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

    @Unique
    private float getArmorWeight(PlayerEntity player) {
        float weight = 0.0f;
        for (ItemStack stack : player.getArmorItems()) {
            if (stack.isEmpty()) continue;

            if (stack.getItem() == Items.NETHERITE_HELMET) weight += 10;
            else if (stack.getItem() == Items.NETHERITE_CHESTPLATE) weight += 14;
            else if (stack.getItem() == Items.NETHERITE_LEGGINGS) weight += 12;
            else if (stack.getItem() == Items.NETHERITE_BOOTS) weight += 8;

            else if (stack.getItem() == Items.DIAMOND_HELMET) weight += 5;
            else if (stack.getItem() == Items.DIAMOND_CHESTPLATE) weight += 8;
            else if (stack.getItem() == Items.DIAMOND_LEGGINGS) weight += 7;
            else if (stack.getItem() == Items.DIAMOND_BOOTS) weight += 4;

            else if (stack.getItem() == Items.IRON_HELMET) weight += 5;
            else if (stack.getItem() == Items.IRON_CHESTPLATE) weight += 8;
            else if (stack.getItem() == Items.IRON_LEGGINGS) weight += 7;
            else if (stack.getItem() == Items.IRON_BOOTS) weight += 4;

            else if (stack.getItem() == Items.GOLDEN_HELMET) weight += 5;
            else if (stack.getItem() == Items.GOLDEN_CHESTPLATE) weight += 8;
            else if (stack.getItem() == Items.GOLDEN_LEGGINGS) weight += 7;
            else if (stack.getItem() == Items.GOLDEN_BOOTS) weight += 4;

            else if (stack.getItem() == Items.CHAINMAIL_HELMET) weight += 3;
            else if (stack.getItem() == Items.CHAINMAIL_CHESTPLATE) weight += 4;
            else if (stack.getItem() == Items.CHAINMAIL_LEGGINGS) weight += 4;
            else if (stack.getItem() == Items.CHAINMAIL_BOOTS) weight += 2;

            // Add custom materials as needed
        }
        return weight;
    }
}
