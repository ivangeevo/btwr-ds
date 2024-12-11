package org.ivangeevo.btwr_ds.mixin;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemMixin {

    @Shadow public abstract ComponentMap getComponents();

    // set all foods to stack up to 16 only
    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void setFoodMaxStackCount(CallbackInfoReturnable<Integer> cir) {
        if (this.getComponents().get(DataComponentTypes.FOOD) != null) {
            cir.setReturnValue(16);
        }
    }
}
