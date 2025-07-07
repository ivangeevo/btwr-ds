package org.ivangeevo.btwr_ds.mixin;

import btwr.core.item.BTWR_Items;
import com.bwt.items.BwtItems;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.ivangeevo.vegehenna.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

    // Add no damage to axe items when breaking replaceable blocks like short grass, fern, tall grass, etc.
    @Inject(method = "postMine", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)V"
    ), cancellable = true)
    private void skipDamageOnReplaceable(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof AxeItem && state.isReplaceable()) {
            cir.setReturnValue(true);  // Axe breaks replaceable block, no damage
        }
    }

    @Inject(method = "hasGlint", at = @At("HEAD"), cancellable = true)
    private void setItemHasGlint(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(Items.NETHERITE_INGOT)) {
            cir.setReturnValue(true);
        }
    }

}
