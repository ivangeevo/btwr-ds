package org.ivangeevo.btwr_ds.item.component;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class BTWRFoodComponents {

    public static class Granular {
        public static final FoodComponent MELON_SLICE = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
        public static final FoodComponent SWEET_BERRIES = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
        public static final FoodComponent BROWN_MUSHROOM = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
        public static final FoodComponent RED_MUSHROOM = new FoodComponent.Builder().nutrition(0).saturationModifier(0).statusEffect(addPoisonEffect(100, 0), 1f).build();
        public static final FoodComponent PUMPKIN_SEEDS = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
        public static final FoodComponent COCOA_BEANS = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
        public static final FoodComponent GLOW_BERRIES = new FoodComponent.Builder().nutrition(0).saturationModifier(0).build();
    }

    // From vanilla
    public static final FoodComponent ENCHANTED_GOLDEN_APPLE = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.ENCHANTED_GOLDEN_APPLE.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1F).alwaysEdible().build();
    public static final FoodComponent GOLDEN_APPLE = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.GOLDEN_APPLE.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1F).alwaysEdible().build();
    public static final FoodComponent BREAD = new FoodComponent.Builder().nutrition(3).saturationModifier(FoodComponents.BREAD.saturation() / 4).build();
    public static final FoodComponent DRIED_KELP = new FoodComponent.Builder().nutrition(0).saturationModifier(FoodComponents.DRIED_KELP.saturation() / 4).build();
    public static final FoodComponent CHICKEN = new FoodComponent.Builder().nutrition(3).saturationModifier(FoodComponents.CHICKEN.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 2), 0.1F).build();
    public static final FoodComponent PORKCHOP = new FoodComponent.Builder().nutrition(3).saturationModifier(FoodComponents.PORKCHOP.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 2), 0.1F).build();
    public static final FoodComponent BEEF = new FoodComponent.Builder().nutrition(3).saturationModifier(FoodComponents.BEEF.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 2), 0.1F).build();
    public static final FoodComponent MUTTON = new FoodComponent.Builder().nutrition(2).saturationModifier(FoodComponents.MUTTON.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1), 0.1F).build();
    public static final FoodComponent RABBIT = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.RABBIT.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1), 0.1F).build();
    public static final FoodComponent COD = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.COD.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1), 0.1F).build();
    public static final FoodComponent SALMON = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.SALMON.saturation() / 4).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 1200, 2), 0.25F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1000, 1), 0.1F).build();
    public static final FoodComponent COOKED_CHICKEN = new FoodComponent.Builder().nutrition(4).saturationModifier(FoodComponents.COOKED_CHICKEN.saturation() / 4).build();
    public static final FoodComponent COOKED_BEEF = new FoodComponent.Builder().nutrition(4).saturationModifier(FoodComponents.COOKED_BEEF.saturation() / 4).build();
    public static final FoodComponent COOKED_PORKCHOP = new FoodComponent.Builder().nutrition(5).saturationModifier(FoodComponents.COOKED_PORKCHOP.saturation() / 4).build();
    public static final FoodComponent COOKED_MUTTON = new FoodComponent.Builder().nutrition(4).saturationModifier(FoodComponents.COOKED_MUTTON.saturation() / 4).build();
    public static final FoodComponent COOKED_RABBIT = new FoodComponent.Builder().nutrition(3).saturationModifier(FoodComponents.COOKED_RABBIT.saturation() / 4).build();
    public static final FoodComponent COOKED_COD = new FoodComponent.Builder().nutrition(2).saturationModifier(FoodComponents.COOKED_COD.saturation() / 4).build();
    public static final FoodComponent COOKED_SALMON = new FoodComponent.Builder().nutrition(2).saturationModifier(FoodComponents.COOKED_SALMON.saturation() / 4).build();
    public static final FoodComponent APPLE = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.APPLE.saturation() / 4).build();
    public static final FoodComponent MUSHROOM_STEW = new FoodComponent.Builder().nutrition(3).saturationModifier(FoodComponents.MUSHROOM_STEW.saturation() / 4).build();
    public static final FoodComponent COOKIE = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.COOKIE.saturation() / 4).build();
    public static final FoodComponent CARROT = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.CARROT.saturation() / 4).build();
    public static final FoodComponent POTATO = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.POTATO.saturation() / 4).statusEffect(addHungerEffect(700, 3), 0.35F).build();
    public static final FoodComponent BAKED_POTATO = new FoodComponent.Builder().nutrition(2).saturationModifier(FoodComponents.BAKED_POTATO.saturation() / 4).build();
    public static final FoodComponent PUMPKIN_PIE = new FoodComponent.Builder().nutrition(2).saturationModifier(FoodComponents.PUMPKIN_PIE.saturation() / 4).build();
    public static final FoodComponent RABBIT_STEW = new FoodComponent.Builder().nutrition(6).saturationModifier(FoodComponents.RABBIT_STEW.saturation() / 4).build();
    public static final FoodComponent BEETROOT = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.BEETROOT.saturation() / 4).build();
    public static final FoodComponent BEETROOT_SOUP = new FoodComponent.Builder().nutrition(3).saturationModifier(FoodComponents.BEETROOT_SOUP.saturation() / 4).build();
    public static final FoodComponent HONEY_BOTTLE = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.HONEY_BOTTLE.saturation() / 4).build();
    public static final FoodComponent ROTTEN_FLESH = new FoodComponent.Builder().nutrition(3).saturationModifier(FoodComponents.ROTTEN_FLESH.saturation() / 4).statusEffect(addHungerEffect(600, 4), 0.8F).build();
    public static final FoodComponent SPIDER_EYE = new FoodComponent.Builder().nutrition(2).saturationModifier(FoodComponents.SPIDER_EYE.saturation() / 4).statusEffect(addPoisonEffect(100, 0), 1.0F).build();
    public static final FoodComponent PUFFERFISH = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.PUFFERFISH.saturation() / 4).statusEffect(addPoisonEffect(400, 1), 1.0F).build();
    public static final FoodComponent POISONOUS_POTATO = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.POISONOUS_POTATO.saturation() / 4).statusEffect(addPoisonEffect(200, 1), 1.0F).build();
    public static final FoodComponent CHORUS_FRUIT = new FoodComponent.Builder().nutrition(1).saturationModifier(FoodComponents.CHORUS_FRUIT.saturation() / 4).build();

    // From BTWR: Core
    public static final FoodComponent CHOWDER = new FoodComponent.Builder().nutrition(5).saturationModifier(0.25f).build();
    public static final FoodComponent EGG_SCRAMBLED_RAW = new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).statusEffect(addHungerEffect(600, 2), 0.3F).statusEffect(addSlownessEffect(1000, 1), 0.1F).build();
    public static final FoodComponent MUSHROOM_OMELETTE_RAW = new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).statusEffect(addHungerEffect(600, 2), 0.3F).statusEffect(addSlownessEffect(1000, 1), 0.1F).build();
    public static final FoodComponent RAW_KEBAB = new FoodComponent.Builder().nutrition(6).saturationModifier(0.25f).statusEffect(addHungerEffect(600, 2), 0.3F).statusEffect(addSlownessEffect(1000, 1), 0.1F).build();
    public static final FoodComponent EGG_SCRAMBLED_COOKED = new FoodComponent.Builder().nutrition(4).saturationModifier(0.25f).build();
    public static final FoodComponent MUSHROOM_OMELETTE_COOKED = new FoodComponent.Builder().nutrition(4).saturationModifier(0.25f).build();
    public static final FoodComponent SANDWICH = new FoodComponent.Builder().nutrition(5).saturationModifier(0.25f).build();
    public static final FoodComponent HAM_AND_EGGS = new FoodComponent.Builder().nutrition(6).saturationModifier(0.25f).build();
    public static final FoodComponent STEAK_AND_POTATOES = new FoodComponent.Builder().nutrition(6).saturationModifier(0.25f).build();
    public static final FoodComponent COOKED_KEBAB = new FoodComponent.Builder().nutrition(8).saturationModifier(0.25f).build();
    public static final FoodComponent STEAK_DINNER = new FoodComponent.Builder().nutrition(8).saturationModifier(0.25f).build();
    public static final FoodComponent PORK_DINNER = new FoodComponent.Builder().nutrition(8).saturationModifier(0.25f).build();
    public static final FoodComponent WOLF_DINNER = new FoodComponent.Builder().nutrition(8).saturationModifier(0.25f).build();
    public static final FoodComponent CHICKEN_SOUP = new FoodComponent.Builder().nutrition(8).saturationModifier(0.25f).build();
    public static final FoodComponent HEARTY_STEW = new FoodComponent.Builder().nutrition(10).saturationModifier(0.25f).build();
    public static final FoodComponent CREEPER_OYSTERS = new FoodComponent.Builder().nutrition(1).saturationModifier(0.80f).statusEffect(addPoisonEffect(100, 0), 1.0F).build();
    public static final FoodComponent BEAST_LIVER_RAW = new FoodComponent.Builder().nutrition(5).saturationModifier(0.5f).statusEffect(addHungerEffect(600, 1), 0.3F).statusEffect(addSlownessEffect(1000, 1), 0.1F).build();
    public static final FoodComponent BEAST_LIVER_COOKED = new FoodComponent.Builder().nutrition(6).saturationModifier(0.5f).build();

    // From Vegehenna
    public static final FoodComponent BOILED_POTATO = new FoodComponent.Builder().nutrition(1).saturationModifier(0).build();
    public static final FoodComponent COOKED_CARROT = new FoodComponent.Builder().nutrition(2).saturationModifier(0).build();
    public static final FoodComponent CHOCOLATE = new FoodComponent.Builder().nutrition(2).saturationModifier(0.5f).build();
    public static final FoodComponent CHOCOLATE_MILK = new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).build();

    // From Animageddon
    public static final FoodComponent BAT_WING = new FoodComponent.Builder().nutrition(1).saturationModifier(0.80f).build();

    // From Better With Time
    public static final FoodComponent RAW_EGG = new FoodComponent.Builder().nutrition(1).saturationModifier(0.25f).statusEffect(addHungerEffect(600, 2), 0.3F).build();
    public static final FoodComponent WOLFCHOP = new FoodComponent.Builder().nutrition(2).saturationModifier(0.25f).statusEffect(addHungerEffect(1200, 2), 0.3F).statusEffect(addSlownessEffect(1000, 2), 0.1F).build();
    public static final FoodComponent FRIED_EGG = new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodComponent POACHED_EGG = new FoodComponent.Builder().nutrition(3).saturationModifier(0.25f).build();
    public static final FoodComponent COOKED_WOLFCHOP = new FoodComponent.Builder().nutrition(5).saturationModifier(0.25f).build();
    public static final FoodComponent DONUT = new FoodComponent.Builder().nutrition(1).saturationModifier(0.5f).alwaysEdible().build();

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
