package org.ivangeevo.btwr_ds.mixin.entity;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.ivangeevo.btwr_ds.attachment.MagneticPointAttachedData;
import org.ivangeevo.btwr_ds.attachment.ModAttachmentTypes;
import org.ivangeevo.btwr_ds.data.ItemDespawnData;
import org.ivangeevo.btwr_ds.data.ModAttachments;
import org.ivangeevo.btwr_ds.util.ItemDespawnType;
import org.ivangeevo.btwr_ds.util.MagneticPoint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity
{

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    //@Inject(method = "dropItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private void onDropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir, @Local ItemEntity itemEntity) {
        ServerPlayerEntity self = (ServerPlayerEntity)(Object)this;

        // Always tag drop owner
        itemEntity.setAttached(ModAttachments.DROP_OWNER, self.getUuid());
    }

    //@Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("RETURN"))
    private void attachDespawnData(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        ItemEntity dropped = cir.getReturnValue();
        if (dropped == null) return;

        ServerPlayerEntity self = (ServerPlayerEntity)(Object)this;

        // Only tag death drops with special despawn logic
        if (self.isDead() || self.getHealth() <= 0.0f) {
            self.setAttached(ModAttachments.ITEM_DESPAWN,
                    new ItemDespawnData(ItemDespawnType.PERSIST_UNTIL_PLAYER_REDEATH));
        }
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
