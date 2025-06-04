package org.ivangeevo.btwr_ds.mixin;

import btwr.core.item.BTWR_Items;
import com.bwt.entities.GoToAndPickUpBreedingItemGoal;
import com.bwt.entities.PickUpBreedingItemWhileSittingGoal;
import com.bwt.items.BwtItems;
import com.bwt.mixin.accessors.MobEntityAccessorMixin;
import com.bwt.sounds.BwtSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.ivangeevo.animageddon.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin {

    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    public void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (isMeatFoodItemForWolf(stack)) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private boolean isMeatFoodItemForWolf(ItemStack stack) {
        return stack.isOf(BTWR_Items.SANDWICH)
                || stack.isOf(BTWR_Items.HAM_AND_EGGS)
                || stack.isOf(BTWR_Items.STEAK_AND_POTATOES)
                || stack.isOf(BTWR_Items.RAW_KEBAB)
                || stack.isOf(BTWR_Items.COOKED_KEBAB)
                || stack.isOf(BTWR_Items.STEAK_DINNER)
                || stack.isOf(BTWR_Items.PORK_DINNER)
                || stack.isOf(BTWR_Items.WOLF_DINNER)
                || stack.isOf(BTWR_Items.BEAST_LIVER_RAW)
                || stack.isOf(BTWR_Items.BEAST_LIVER_COOKED)
                || stack.isOf(ModItems.BURNED_MEAT);
    }

}
