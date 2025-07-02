package org.ivangeevo.btwr_ds.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.ivangeevo.btwr_ds.util.PatchyMovement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements PatchyMovement
{

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // Add exhaustion when blocking with a shield for each successful hit
    @Inject(method = "damage", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V",
            shift = At.Shift.AFTER
    ))
    private void applyExhaustionFromShieldBlock(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;

        if (!self.getWorld().isClient && self instanceof PlayerEntity player) {
            if (player.blockedByShield(source)) {
                player.addExhaustion(1.0F);
            }
        }
    }

    // Changes shield "cone" in front of the player to be much smaller
    @Inject(method = "blockedByShield", at = @At("HEAD"), cancellable = true)
    private void modifyShieldCone(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = source.getSource();
        if (entity instanceof PersistentProjectileEntity projectile && projectile.getPierceLevel() > 0) {
            return; // piercing ignores shields anyway
        }

        if (!source.isIn(DamageTypeTags.BYPASSES_SHIELD)) {
            LivingEntity self = (LivingEntity) (Object) this;
            if (self.isBlocking()) {
                Vec3d srcPos = source.getPosition();
                if (srcPos != null) {
                    Vec3d facing = self.getRotationVector(0.0F, self.getHeadYaw());
                    Vec3d dir = srcPos.relativize(self.getPos());
                    dir = new Vec3d(dir.x, 0.0, dir.z).normalize();

                    double dot = dir.dotProduct(facing);

                    // 90° frontal cone (±45°)
                    if (dot < -0.707) {
                        cir.setReturnValue(true);
                        return;
                    }
                }
            }
        }

        cir.setReturnValue(false);
    }

}
