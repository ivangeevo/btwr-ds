package org.ivangeevo.btwr_ds.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.ivangeevo.btwr_ds.util.ArmorWeightManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "addExhaustion", at = @At("HEAD"), cancellable = true)
    private void addExhaustionWithArmorWeight(float originalAmount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        float weight = ArmorWeightManager.getInstance().getArmorWeight(player);
        float multiplier = 1.0f + (Math.min(weight, 44.0f) / 44.0f); // max 2x
        player.getHungerManager().addExhaustion(originalAmount * multiplier);
        ci.cancel();
    }

    @Inject(method = "travel", at = @At("HEAD"))
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

}
