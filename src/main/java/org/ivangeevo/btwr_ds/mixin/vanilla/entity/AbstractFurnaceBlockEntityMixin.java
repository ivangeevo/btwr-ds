package org.ivangeevo.btwr_ds.mixin.vanilla.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import org.ivangeevo.btwr_ds.registry.BTWRCustomFuelRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Objects;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends LockableContainerBlockEntity {

    @Shadow @Nullable private static volatile Map<Item, Integer> fuelTimes;

    protected AbstractFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(method = "createFuelTimeMap", at = @At("HEAD"), cancellable = true)
    private static void overrideFuelMap(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        Map<Item, Integer> map = fuelTimes;
        cir.setReturnValue(Objects.requireNonNullElseGet(map, BTWRCustomFuelRegistry::getMap));
    }

}
