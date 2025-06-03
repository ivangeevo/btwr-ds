package org.ivangeevo.btwr_ds.mixin;

import com.bwt.items.BwtItems;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder, FabricItemStack
{

    @Shadow public abstract ComponentMap getComponents();

    @Shadow public abstract Item getItem();

    // sets items to stack up to 16 only
    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void setFoodMaxStackCount(CallbackInfoReturnable<Integer> cir) {
        if (shouldStackTo16((ItemStack)(Object)this)) {
            cir.setReturnValue(16);
        }
    }

    @Unique
    private boolean shouldStackTo16(ItemStack stack) {
        return stack.contains(DataComponentTypes.FOOD)
                || stack.isOf(Items.BONE)
                || stack.isOf(Items.ROTTEN_FLESH)
                || stack.isOf(Items.MELON_SLICE)
                || stack.isOf(Items.COCOA_BEANS)
                || stack.isOf(Items.EGG)
                || stack.isOf(BwtItems.soulUrnItem)
                || stack.isOf(SturdyTreesItems.STUMP_REMOVER)

                ;
    }

}
