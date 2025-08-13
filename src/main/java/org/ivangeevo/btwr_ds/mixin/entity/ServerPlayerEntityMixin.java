package org.ivangeevo.btwr_ds.mixin.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.ivangeevo.btwr_ds.attachment.MagneticPointAttachedData;
import org.ivangeevo.btwr_ds.attachment.ModAttachmentTypes;
import org.ivangeevo.btwr_ds.util.MagneticPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity
{

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        updateMagneticInfluences();
    }

    @Unique
    private void updateMagneticInfluences() {

        if ((this.getWorld().getTime() + this.getId()) % 40 != 0) {
            // Stagger these updates as they can be performance intensive
            return;
        }

        MagneticPoint strongestPoint = null;
        double strongestFieldStrength = 0.0D;

        if (this.getWorld().getDimensionEntry() == DimensionTypes.OVERWORLD) {

            BlockPos spawnPos = this.getWorld().getSpawnPos();

            strongestPoint = new MagneticPoint(spawnPos.getX(), 0, spawnPos.getZ(), 2);

            strongestFieldStrength = strongestPoint.getFieldStrengthRelativeToPosition(this.getX(), this.getZ());


            for (MagneticPoint tempPoint : this.getWorld().getMagneticPointList().magneticPoints) {
                double tempFieldStrength = tempPoint.getFieldStrengthRelativeToPosition(this.getX(), this.getZ());

                if (tempFieldStrength > strongestFieldStrength) {
                    strongestPoint = tempPoint;
                    strongestFieldStrength = tempFieldStrength;
                }
            }
        } else {

            for (MagneticPoint tempPoint : this.getWorld().getMagneticPointList().magneticPoints) {
                double dTempFieldStrength = tempPoint.getFieldStrengthRelativeToPositionWithBackgroundNoise(this.getX(), this.getZ());

                if (dTempFieldStrength > strongestFieldStrength) {
                    strongestPoint = tempPoint;
                    strongestFieldStrength = dTempFieldStrength;
                }
            }
        }

        MagneticPointAttachedData data = this.getWorld().getAttachedOrElse(
                ModAttachmentTypes.MAGNETIC_POINT, MagneticPointAttachedData.DEFAULT
        );

        if (strongestPoint != null) {
            data.setHasValid(true);
            data.setPosX(strongestPoint.posX);
            data.setPosZ(strongestPoint.posZ);
        } else {
            data.setHasValid(false);
        }

        this.getWorld().setAttached(ModAttachmentTypes.MAGNETIC_POINT, data);
    }

}
