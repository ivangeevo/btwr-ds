package org.btwr.data_suite.ai.goal;

import com.bwt.entities.GoToAndPickUpBreedingItemGoal;
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
    public WolfGoToAndPickupBreedingItemGoal(AnimalEntity animal, double searchRadius, double pickupRadius, double speed, @Nullable Predicate<AnimalEntity> wantsFoodCondition, @Nullable Consumer<ItemStack> foodConsumer) {
        super(animal, searchRadius, pickupRadius, speed, wantsFoodCondition, foodConsumer);
    }

    @Override
    protected @Nullable ItemEntity findClosestBreedingItem() {
        return animal.getWorld()
                .getEntitiesByClass(
                        ItemEntity.class,
                        animal.getBoundingBox().expand(searchRadius),
                        itemEntity -> itemEntity.getStack().isIn(ItemTags.WOLF_FOOD)
                                || itemEntity.getStack().isOf(BwtItems.kibbleItem)
                )
                .stream()
                .min(Comparator.comparingDouble(animal::squaredDistanceTo))
                .filter(itemEntity -> animal.distanceTo(itemEntity) < searchRadius)
                .orElse(null);
    }

    @Override
    public void tick() {
        if (targetBreedingItem == null || !isTargetValid() || !wantsFood() || !animal.canMoveVoluntarily()) {
            return;
        }
        animal.getLookControl().lookAt(targetBreedingItem, animal.getMaxLookYawChange(), animal.getMaxLookPitchChange());
        if (!(animal instanceof TameableEntity tameableEntity) || !tameableEntity.isSitting()) {
            animal.getNavigation().startMovingTo(targetBreedingItem, speed);
        }
        if (animal.distanceTo(targetBreedingItem) <= pickupRadius) {
            animal.getNavigation().stop();

            ItemStack pickedUpStack = targetBreedingItem.getStack();
            boolean isRottenFlesh = pickedUpStack.isOf(Items.ROTTEN_FLESH);

            if (foodConsumer != null) {
                foodConsumer.accept(pickedUpStack.copyWithCount(1));
            }
            pickedUpStack.decrement(1);

            if (!isRottenFlesh) {
                animal.lovePlayer(null);
            }
        }
    }
}
