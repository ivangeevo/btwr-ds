package org.btwr.data_suite.ai.goal;

import com.bwt.entities.GoToAndPickUpBreedingItemGoal;
import com.bwt.entities.WolfIsFedAccess;
import com.bwt.items.BwtItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WolfGoToAndPickupBreedingItemGoal extends GoToAndPickUpBreedingItemGoal {
    public WolfGoToAndPickupBreedingItemGoal(AnimalEntity animal, double searchRadius, double pickupRadius,
                                             double speed, Predicate<AnimalEntity> wantsFoodCondition, Consumer<ItemStack> foodConsumer) {
        super(animal, searchRadius, pickupRadius, speed, wantsFoodCondition, foodConsumer);
    }

    @Override
    protected @Nullable ItemEntity findClosestBreedingItem() {
        return animal.getWorld()
                .getEntitiesByClass(
                        ItemEntity.class,
                        animal.getBoundingBox().expand(searchRadius),
                        itemEntity -> isFeedingItem(itemEntity.getStack())
                )
                .stream()
                .min(Comparator.comparingDouble(animal::squaredDistanceTo))
                .filter(itemEntity -> animal.distanceTo(itemEntity) < searchRadius)
                .orElse(null);
    }

    @Override
    protected boolean wantsFood() {
        if (!super.wantsFood()) return false;

        // If the only reason we're running is healing (already fed),
        // rotten flesh and kibble don't count — they're feeding-only items
        if (animal instanceof WolfIsFedAccess wolf && wolf.bwt$isFed()) {
            if (targetBreedingItem != null) {
                ItemStack stack = targetBreedingItem.getStack();
                return !stack.isOf(Items.ROTTEN_FLESH) && !stack.isOf(BwtItems.kibbleItem); // fed wolf ignores rotten flesh and kibble on the ground
            }
        }
        return true;
    }

    @Override
    public void tick() {
        if (targetBreedingItem == null || !isTargetValid() || !wantsFood() || !animal.canMoveVoluntarily()) {
            return;
        }

        animal.getLookControl().lookAt(targetBreedingItem, animal.getMaxLookYawChange(), animal.getMaxLookPitchChange());

        if (!(animal instanceof TameableEntity tameable) || !tameable.isSitting()) {
            animal.getNavigation().startMovingTo(targetBreedingItem, speed);
        }

        if (animal.distanceTo(targetBreedingItem) <= pickupRadius) {
            animal.getNavigation().stop();
            routeItem(targetBreedingItem.getStack());
            targetBreedingItem.getStack().decrement(1);
        }
    }

    /**
     * Items the wolf will walk toward and pick up.
     * Covers both feeding-only and feeding+breeding items.
     */
    protected boolean isFeedingItem(ItemStack stack) {
        if (stack.isOf(Items.ROTTEN_FLESH)) return true;
        if (stack.isOf(BwtItems.kibbleItem)) return true;
        // WOLF_FOOD tag, but not wolf chops
        return stack.isIn(ItemTags.WOLF_FOOD)
                && !stack.isOf(BwtItems.wolfChopItem)
                && !stack.isOf(BwtItems.cookedWolfChopItem);
    }

    /**
     * Decides whether an item triggers breeding (love mode) on top of feeding.
     * Rotten flesh and kibble feed only — no love mode.
     */
    protected boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.WOLF_FOOD)
                && !stack.isOf(BwtItems.wolfChopItem)
                && !stack.isOf(BwtItems.cookedWolfChopItem);
    }

    /**
     * Routes a picked-up item to the correct system(s).
     * Feed system: always called for any item the wolf picks up.
     * Breed system: only called for WOLF_FOOD tag items (excl. wolf chops).
     */
    protected void routeItem(ItemStack stack) {
        if (foodConsumer != null) {
            foodConsumer.accept(stack.copyWithCount(1));
        }
        if (isBreedingItem(stack)) {
            animal.lovePlayer(null);
        }
    }
}