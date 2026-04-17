package org.btwr.data_suite.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ShieldModificationManager {
    private static final ShieldModificationManager INSTANCE = new ShieldModificationManager();

    private ShieldModificationManager() {}

    public static ShieldModificationManager getInstance() {
        return INSTANCE;
    }

    /** Add exhaustion to the player when blocking with a shield for each successful hit**/
    public void applyExhaustionOnBlock(LivingEntity self, DamageSource source) {
        if (!self.getWorld().isClient && self instanceof PlayerEntity player) {
            if (player.blockedByShield(source)) {
                player.addExhaustion(1.0F);
            }
        }
    }

    /** Changes shield "cone" in front of the player to be much smaller **/
    public void modifyShieldCone(LivingEntity livingEntity, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = source.getSource();
        if (entity instanceof PersistentProjectileEntity projectile && projectile.getPierceLevel() > 0) {
            return; // piercing ignores shields anyway
        }

        if (!source.isIn(DamageTypeTags.BYPASSES_SHIELD)) {
            if (livingEntity.isBlocking()) {
                Vec3d srcPos = source.getPosition();
                if (srcPos != null) {
                    Vec3d facing = livingEntity.getRotationVector(0.0F, livingEntity.getHeadYaw());
                    Vec3d dir = srcPos.relativize(livingEntity.getPos());
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