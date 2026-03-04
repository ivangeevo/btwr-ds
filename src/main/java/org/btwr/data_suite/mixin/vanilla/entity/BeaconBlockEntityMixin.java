package org.btwr.data_suite.mixin.vanilla.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.btwr.data_suite.block.entity.BeaconBlockEntityMagneticTracker;
import org.btwr.data_suite.block.entity.interfaces.BeaconBlockEntityAdded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntityMixin extends BlockEntity implements BeaconBlockEntityAdded {

    @Unique private int oldLevel = 0;

    public BeaconBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public int getOldLevel() {
        return oldLevel;
    }

    @Override
    public void setOldLevel(int oldLevel) {
        this.oldLevel = oldLevel;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private static void saveOldLevel(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci) {
        ((BeaconBlockEntityAdded) blockEntity).setOldLevel(((BeaconBlockEntityAccessor) blockEntity).getLevel());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTime()J"))
    private static void onTickBeforeEffects(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci) {
        BeaconBlockEntityAdded added = (BeaconBlockEntityAdded) blockEntity;
        int oldLevel = added.getOldLevel();
        int newLevel = ((BeaconBlockEntityAccessor) blockEntity).getLevel();

        if (newLevel == oldLevel) return;

        if (!world.isClient) {
            BeaconBlockEntityMagneticTracker.getInstance().onPowerChange(newLevel, oldLevel, blockEntity);
        }
        added.setOldLevel(newLevel);
    }

}