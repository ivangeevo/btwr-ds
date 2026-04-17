package org.btwr.data_suite.item.component;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;

public class BTWRFoodComponents {
    public static class Granular {
        public static final FoodComponent MELON_SLICE = newGranular(0f).build();
        public static final FoodComponent MASHED_MELON = newGranular(0f).build();
        public static final FoodComponent SWEET_BERRIES = newGranular(0f).build();
        public static final FoodComponent BROWN_MUSHROOM = newGranular(0f).build();
        public static final FoodComponent RED_MUSHROOM = newGranular(0f).statusEffect(addPoisonEffect(100, 0), 1f).build();
        public static final FoodComponent PUMPKIN_SEEDS = newGranular(0f).build();
        public static final FoodComponent GLOW_BERRIES = newGranular(0f).build();
        public static final FoodComponent COCOA_BEANS = newGranular(0.25f).build();
        public static final FoodComponent CHOCOLATE_MILK = newGranular(0.25f).build();
    }

    // From vanilla
    public static final FoodComponent ENCHANTED_GOLDEN_APPLE = registerNew(1, 0f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1F).alwaysEdible().build();
    public static final FoodComponent GOLDEN_APPLE = registerNew(1, 0f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1F).alwaysEdible().build();
    public static final FoodComponent BREAD = registerNew(3, 0.25f).build();
    public static final FoodComponent DRIED_KELP = new FoodComponent.Builder().nutrition(0).saturationModifier(FoodComponents.DRIED_KELP.saturation()).build();
    public static final FoodComponent CHICKEN = registerNew(3, 0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 2), 0.1F).build();
    public static final FoodComponent PORKCHOP = registerNew(4, 0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 2), 0.1F).build();
    public static final FoodComponent BEEF = registerNew(4, 0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 2), 0.1F).build();
    public static final FoodComponent MUTTON = registerNew(3, 0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1), 0.1F).build();
    public static final FoodComponent RABBIT = registerNew(2, 0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1), 0.1F).build();
    public static final FoodComponent COD = registerNew(4, 0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1), 0.1F).build();
    public static final FoodComponent SALMON = registerNew(4, 0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.25F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1), 0.1F).build();
    public static final FoodComponent COOKED_CHICKEN = registerNew(4, 0.25f).build();
    public static final FoodComponent COOKED_BEEF = registerNew(5, 0.25f).build();
    public static final FoodComponent COOKED_PORKCHOP = registerNew(5, 0.25f).build();
    public static final FoodComponent COOKED_MUTTON = registerNew(4, 0.25f).build();
    public static final FoodComponent COOKED_RABBIT = registerNew(3, 0.25f).build();
    public static final FoodComponent COOKED_COD = registerNew(5, 0.25f).build();
    public static final FoodComponent COOKED_SALMON = registerNew(5, 0.25f).build();
    public static final FoodComponent APPLE = registerNew(1, 0f).build();
    public static final FoodComponent MUSHROOM_STEW = registerNew(3, 0.25f).build();
    public static final FoodComponent COOKIE = registerNew(1, 1f).build();
    public static final FoodComponent CARROT = registerNew(1, 0f).build();
    public static final FoodComponent POTATO = registerNew(1, 0f).statusEffect(addHungerEffect(700, 3), 0.35F).build();
    public static final FoodComponent BAKED_POTATO = registerNew(2, 0f).build();
    public static final FoodComponent PUMPKIN_PIE = registerNew(2, 2.5f).build();
    public static final FoodComponent RABBIT_STEW = registerNew(5, 0.25f).build();
    public static final FoodComponent BEETROOT = registerNew(1, 0f).build();
    public static final FoodComponent BEETROOT_SOUP = registerNew(3, 0f).usingConvertsTo(Items.BOWL).build();
    public static final FoodComponent HONEY_BOTTLE = registerNew(1, 0.5f).usingConvertsTo(Items.GLASS_BOTTLE).build();
    public static final FoodComponent ROTTEN_FLESH = registerNew(3, 0f).statusEffect(addHungerEffect(600, 4), 0.8F).build();
    public static final FoodComponent SPIDER_EYE = registerNew(2, 0.8f).statusEffect(addPoisonEffect(100, 0), 1.0F).build();
    public static final FoodComponent PUFFERFISH = registerNew(1, 0.5f).statusEffect(addPoisonEffect(400, 1), 1.0F).build();
    public static final FoodComponent POISONOUS_POTATO = registerNew(1, 0f).statusEffect(addPoisonEffect(200, 1), 1.0F).build();
    public static final FoodComponent CHORUS_FRUIT = registerNew(1, 0f).build();

    // From BTWR: Core
    public static final FoodComponent EGG_SCRAMBLED_RAW = registerNew(3, 0.25f).statusEffect(addHungerEffect(600, 2), 0.3F).statusEffect(addSlownessEffect(1000, 1), 0.1F).build();
    public static final FoodComponent EGG_SCRAMBLED_COOKED = registerNew(4, 0.25f).build();
    public static final FoodComponent MUSHROOM_OMELETTE_RAW = registerNew(3, 0.25f).statusEffect(addHungerEffect(600, 2), 0.3F).statusEffect(addSlownessEffect(1000, 1), 0.1F).build();
    public static final FoodComponent MUSHROOM_OMELETTE_COOKED = registerNew(4, 0.25f).build();
    public static final FoodComponent SANDWICH = registerNew(6, 0.25f).build();
    public static final FoodComponent HAM_AND_EGGS = registerNew(6, 0.25f).build();
    public static final FoodComponent CHOWDER = registerNew(4, 0.25f).usingConvertsTo(Items.BOWL).build();
    public static final FoodComponent STEAK_AND_POTATOES = registerNew(6, 0.25f).build();
    public static final FoodComponent RAW_KEBAB = registerNew(6, 0.25f).statusEffect(addHungerEffect(600, 2), 0.3F).statusEffect(addSlownessEffect(1000, 1), 0.1F).build();
    public static final FoodComponent COOKED_KEBAB = registerNew(8, 0.25f).build();
    public static final FoodComponent STEAK_DINNER = registerNew(8, 0.25f).build();
    public static final FoodComponent PORK_DINNER = registerNew(8, 0.25f).build();
    public static final FoodComponent WOLF_DINNER = registerNew(8, 0.25f).build();
    public static final FoodComponent CHICKEN_SOUP = registerNew(8, 0.25f).usingConvertsTo(Items.BOWL).build();
    public static final FoodComponent HEARTY_STEW = registerNew(10, 0.25f).usingConvertsTo(Items.BOWL).build();
    public static final FoodComponent BEAST_LIVER_RAW = registerNew(5, 0.5f).statusEffect(addHungerEffect(600, 1), 0.3F).statusEffect(addSlownessEffect(1000, 1), 0.1F).build();
    public static final FoodComponent BEAST_LIVER_COOKED = registerNew(6, 0.5f).build();
    public static final FoodComponent CREEPER_OYSTERS = registerNew(2, 0.8f).statusEffect(addPoisonEffect(100, 0), 1.0F).build();

    // From Vegehenna
    public static final FoodComponent BOILED_POTATO = registerNew(2, 0f).build();
    public static final FoodComponent COOKED_CARROT = registerNew(2, 0f).build();
    public static final FoodComponent CHOCOLATE = registerNew(2, 0.5f).build();

    // From Animageddon
    public static final FoodComponent BAT_WING = registerNew(1, 0.8f).build();

    // From Better With Time
    public static final FoodComponent RAW_EGG = registerNew(2, 0.25f).statusEffect(addHungerEffect(600, 2), 0.3F).build();
    public static final FoodComponent WOLFCHOP = registerNew(4, 0.25f).statusEffect(addHungerEffect(1200, 2), 0.3F).statusEffect(addSlownessEffect(1000, 2), 0.1F).build();
    public static final FoodComponent FRIED_EGG = registerNew(3, 0.25f).build();
    public static final FoodComponent POACHED_EGG = registerNew(3, 0.25f).build();
    public static final FoodComponent COOKED_WOLFCHOP = registerNew(5, 0.25f).build();
    public static final FoodComponent DONUT = registerNew(1, 0.5f).alwaysEdible().build();

    private static FoodComponent.Builder newGranular(float saturation) {
        return registerNew(0, saturation);
    }

    private static FoodComponent.Builder registerNew(int nutrition, float saturation) {
        return new FoodComponent.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation);
    }

    private static StatusEffectInstance addPoisonEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.POISON, dur, amp);
    }

    private static StatusEffectInstance addHungerEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.HUNGER, dur, amp);
    }

    private static StatusEffectInstance addSlownessEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.SLOWNESS, dur, amp);
    }

    private static StatusEffectInstance addAbsorptionEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.ABSORPTION, dur, amp);
    }

    private static StatusEffectInstance addRegenerationEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.REGENERATION, dur, amp);
    }
}