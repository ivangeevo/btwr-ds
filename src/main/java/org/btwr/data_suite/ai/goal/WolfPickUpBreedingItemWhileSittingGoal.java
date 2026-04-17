package org.btwr.data_suite.ai.goal;

import com.bwt.entities.PickUpBreedingItemWhileSittingGoal;
import com.bwt.items.BwtItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import org.btwr.data_suite.data.ModDataAttachments;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class WolfPickUpBreedingItemWhileSittingGoal extends PickUpBreedingItemWhileSittingGoal {
    public WolfPickUpBreedingItemWhileSittingGoal(TameableEntity animal, double searchRadius, @Nullable Predicate<AnimalEntity> wantsFoodCondition, @Nullable Consumer<ItemStack> foodConsumer) {
        super(animal, searchRadius, wantsFoodCondition, foodConsumer);
    }

    @Override
    protected @Nullable ItemEntity findClosestBreedingItem() {
        var beastData = animal.getAttached(ModDataAttachments.BEAST_TIMER);
        boolean ateRottenFlesh = beastData != null && beastData.getAteRottenFlesh();

        return animal.getWorld()
                .getEntitiesByClass(
                        ItemEntity.class,
                        animal.getBoundingBox().expand(searchRadius),
                        itemEntity -> {
                            ItemStack stack = itemEntity.getStack();
                            if (stack.isOf(Items.ROTTEN_FLESH) && ateRottenFlesh) return false;
                            return stack.isIn(ItemTags.WOLF_FOOD) || stack.isOf(BwtItems.kibbleItem);
                        }
                )
                .stream()
                .min(Comparator.comparingDouble(animal::squaredDistanceTo))
                .filter(itemEntity -> animal.distanceTo(itemEntity) < searchRadius)
                .orElse(null);
    }
}