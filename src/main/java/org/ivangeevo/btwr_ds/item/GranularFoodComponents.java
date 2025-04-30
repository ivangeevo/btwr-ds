package org.ivangeevo.btwr_ds.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class GranularFoodComponents {

    // Granular food component entries
    public static final FoodComponent BROWN_MUSHROOM = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
    public static final FoodComponent RED_MUSHROOM = new FoodComponent.Builder().nutrition(0).saturationModifier(0).statusEffect(addPoisonEffect(100, 0), 1f).build();
    public static final FoodComponent PUMPKIN_SEEDS = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
    public static final FoodComponent COCOA_BEANS = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
    public static final FoodComponent MELON_SLICE = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();

    private static StatusEffectInstance addPoisonEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.POISON, dur, amp, false, false, false);
    }


}
