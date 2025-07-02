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

    //@Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void setFoodMaxStackCount(CallbackInfoReturnable<Integer> cir) {
        if (shouldStackTo16((Item)(Object)this)) {
            cir.setReturnValue(16);
        }
    }

    @Unique
    private boolean shouldStackTo16(Item item) {
        return isFood(item) || isMiscItem(item);
    }

    @Unique
    private boolean isFood(Item item) {
        return isRawFood(item) /**|| isCookedFood(item) || isMiscFood(item)**/;
    }
    
    @Unique
    private boolean isRawFood(Item item) {
      return item == Items.APPLE
              || item == Items.MUSHROOM_STEW
              || item == Items.CARROT
              || item == Items.POTATO
              || item == Items.SWEET_BERRIES
              || item == Items.GLOW_BERRIES
              || item == Items.HONEY_BOTTLE


              || item == Items.PORKCHOP
              || item == Items.COD
              || item == Items.SALMON
              || item == Items.TROPICAL_FISH
              || item == Items.PUFFERFISH
              || item == Items.BEEF
              || item == Items.CHICKEN
              || item == Items.RABBIT
              || item == Items.MUTTON

              || item == BwtItems.rawEggItem
              || item == BwtItems.wolfChopItem



              ;
    }

    @Unique
    private boolean isCookedFood(Item item) {
        // vanilla non-meats first
        return item == Items.BREAD
                || item == Items.COOKIE
                || item == Items.BAKED_POTATO
                // item == Items.PUMPKIN_PIE
                || item == Items.GOLDEN_CARROT
                || item == Items.CHORUS_FRUIT
                || item == Items.BEETROOT
                || item == Items.BEETROOT_SOUP

                // vanilla meats
                || item == Items.COOKED_PORKCHOP
                || item == Items.COOKED_COD
                || item == Items.COOKED_SALMON
                || item == Items.COOKED_BEEF
                || item == Items.COOKED_CHICKEN
                || item == Items.COOKED_RABBIT
                || item == Items.RABBIT_STEW
                || item == Items.COOKED_MUTTON

                // btwr: core
                || item == BTWR_Items.EGG_SCRAMBLED_COOKED
                || item == BTWR_Items.MUSHROOM_OMELETTE_COOKED
                || item == BTWR_Items.SANDWICH
                || item == BTWR_Items.HAM_AND_EGGS
                || item == BTWR_Items.CHOWDER
                || item == BTWR_Items.STEAK_AND_POTATOES
                || item == BTWR_Items.COOKED_KEBAB
                || item == BTWR_Items.STEAK_DINNER
                || item == BTWR_Items.PORK_DINNER
                || item == BTWR_Items.WOLF_DINNER
                || item == BTWR_Items.CHICKEN_SOUP
                || item == BTWR_Items.HEARTY_STEW
                || item == BTWR_Items.BEAST_LIVER_COOKED

                // vegehenna
                || item == ModItems.BOILED_POTATO
                || item == ModItems.COOKED_CARROT

                // bwt
                || item == BwtItems.friedEggItem
                || item == BwtItems.poachedEggItem
                || item == BwtItems.cookedWolfChopItem
                || item == BwtItems.donutItem

                ;

    }

    @Unique
    private boolean isMiscFood(Item item) {
        return item == Items.MELON_SLICE
                || item == Items.COCOA_BEANS
                || item == Items.GOLDEN_APPLE
                || item == Items.ENCHANTED_GOLDEN_APPLE
                || item == Items.DRIED_KELP
                || item == Items.POISONOUS_POTATO
                || item == Items.BROWN_MUSHROOM
                || item == Items.RED_MUSHROOM

                || item == BTWR_Items.CREEPER_OYSTERS

                || item == ModItems.CHOCOLATE
                || item == ModItems.CHOCOLATE_MILK


                ;
    }

    @Unique
    private boolean isMiscItem(Item item) {
        return item == Items.BONE
                || item == Items.EGG
                || item == Items.ROTTEN_FLESH
                || item == Items.SPIDER_EYE
                || item == Items.SUSPICIOUS_STEW
                || item == Items.OMINOUS_BOTTLE

                || item == BwtItems.soulUrnItem
                || item == SturdyTreesItems.STUMP_REMOVER;
    }

}
