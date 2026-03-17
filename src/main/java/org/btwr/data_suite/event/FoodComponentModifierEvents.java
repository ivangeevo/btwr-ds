package org.btwr.data_suite.event;

import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import org.btwr.core.item.BTWR_Items;
import org.btwr.vegehenna.item.ModItems;
import org.btwr.data_suite.item.component.BTWRFoodComponents;
import tetro48.system.GranularHunger;

/** Used to modify all food items to work for BTWR **/
public class FoodComponentModifierEvents {

    /**
     * Registers a listener to modify the food components of food items.
     */
    public static void register() {
        // register normal food modifications
        DefaultItemComponentEvents.MODIFY.register(FoodComponentModifierEvents::modifyNonGranularFoods);

        // register all granular food entries/modifications
        //GranularFoodComponentRegistry.registerFoods();
        DefaultItemComponentEvents.MODIFY.register(FoodComponentModifierEvents::modifyGranularFoods);
    }

    // Define all granular food items
    // 1 pip = 1 hunger (out of 60)
    // 3 pips = 0.5 shank (1 hunger from original mc values)
    // 6 pips = 1 shank
    private static void modifyGranularFoods(DefaultItemComponentEvents.ModifyContext context) {
        context.modify(Items.MELON_SLICE, builder -> modifyEntryGranular(builder,2, BTWRFoodComponents.Granular.MELON_SLICE));
        context.modify(ModItems.MASHED_MELON, builder -> modifyEntryGranular(builder,2, BTWRFoodComponents.Granular.MASHED_MELON));
        context.modify(Items.SWEET_BERRIES, builder -> modifyEntryGranular(builder,2, BTWRFoodComponents.Granular.SWEET_BERRIES));
        context.modify(Items.BROWN_MUSHROOM, builder -> modifyEntryGranular(builder,1, BTWRFoodComponents.Granular.BROWN_MUSHROOM));
        context.modify(Items.RED_MUSHROOM, builder -> modifyEntryGranular(builder,1, BTWRFoodComponents.Granular.RED_MUSHROOM));
        context.modify(Items.PUMPKIN_SEEDS, builder -> modifyEntryGranular(builder,1, BTWRFoodComponents.Granular.PUMPKIN_SEEDS));
        context.modify(Items.COCOA_BEANS, builder -> modifyEntryGranular(builder,1, BTWRFoodComponents.Granular.COCOA_BEANS));
        context.modify(Items.GLOW_BERRIES, builder -> modifyEntryGranular(builder,1, BTWRFoodComponents.Granular.GLOW_BERRIES));
        context.modify(ModItems.CHOCOLATE_MILK, builder -> modifyEntryGranular(builder,9, BTWRFoodComponents.Granular.CHOCOLATE_MILK));
    }

    // Method to modify vanilla components
    private static void modifyNonGranularFoods(DefaultItemComponentEvents.ModifyContext context) {
        context.modify(Items.ENCHANTED_GOLDEN_APPLE, builder -> modifyEntry(builder, BTWRFoodComponents.ENCHANTED_GOLDEN_APPLE));
        context.modify(Items.GOLDEN_APPLE, builder -> modifyEntry(builder, BTWRFoodComponents.GOLDEN_APPLE));
        context.modify(Items.BREAD, builder -> modifyEntry(builder, BTWRFoodComponents.BREAD));
        context.modify(Items.DRIED_KELP, builder -> modifyEntry(builder, BTWRFoodComponents.DRIED_KELP));
        context.modify(Items.CHICKEN, builder -> modifyEntry(builder, BTWRFoodComponents.CHICKEN));
        context.modify(Items.PORKCHOP, builder -> modifyEntry(builder, BTWRFoodComponents.PORKCHOP));
        context.modify(Items.BEEF, builder -> modifyEntry(builder, BTWRFoodComponents.BEEF));
        context.modify(Items.MUTTON, builder -> modifyEntry(builder, BTWRFoodComponents.MUTTON));
        context.modify(Items.RABBIT, builder -> modifyEntry(builder, BTWRFoodComponents.RABBIT));
        context.modify(Items.COD, builder -> modifyEntry(builder, BTWRFoodComponents.COD));
        context.modify(Items.SALMON, builder -> modifyEntry(builder, BTWRFoodComponents.SALMON));
        context.modify(Items.COOKED_CHICKEN, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_CHICKEN));
        context.modify(Items.COOKED_BEEF, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_BEEF));
        context.modify(Items.COOKED_PORKCHOP, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_PORKCHOP));
        context.modify(Items.COOKED_MUTTON, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_MUTTON));
        context.modify(Items.COOKED_RABBIT, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_RABBIT));
        context.modify(Items.COOKED_COD, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_COD));
        context.modify(Items.COOKED_SALMON, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_SALMON));
        context.modify(Items.APPLE, builder -> modifyEntry(builder, BTWRFoodComponents.APPLE));
        // Mushroom Stew is actually called cream of mushroom in BTW, but it's still the same vanilla item
        context.modify(Items.MUSHROOM_STEW, builder -> modifyEntry(builder, BTWRFoodComponents.MUSHROOM_STEW));
        context.modify(Items.COOKIE, builder -> modifyEntry(builder, BTWRFoodComponents.COOKIE));
        context.modify(Items.CARROT, builder -> modifyEntry(builder, BTWRFoodComponents.CARROT));
        context.modify(Items.POTATO, builder -> modifyEntry(builder, BTWRFoodComponents.POTATO));
        context.modify(Items.BAKED_POTATO, builder -> modifyEntry(builder, BTWRFoodComponents.BAKED_POTATO));
        context.modify(Items.PUMPKIN_PIE, builder -> modifyEntry(builder, BTWRFoodComponents.PUMPKIN_PIE));
        context.modify(Items.RABBIT_STEW, builder -> modifyEntry(builder, BTWRFoodComponents.RABBIT_STEW));
        context.modify(Items.BEETROOT, builder -> modifyEntry(builder, BTWRFoodComponents.BEETROOT));
        context.modify(Items.BEETROOT_SOUP, builder -> modifyEntry(builder, BTWRFoodComponents.BEETROOT_SOUP));
        context.modify(Items.HONEY_BOTTLE, builder -> modifyEntry(builder, BTWRFoodComponents.HONEY_BOTTLE));
        context.modify(Items.ROTTEN_FLESH, builder -> modifyEntry(builder, BTWRFoodComponents.ROTTEN_FLESH));
        context.modify(Items.SPIDER_EYE, builder -> modifyEntry(builder, BTWRFoodComponents.SPIDER_EYE));
        context.modify(Items.PUFFERFISH, builder -> modifyEntry(builder, BTWRFoodComponents.PUFFERFISH));
        context.modify(Items.POISONOUS_POTATO, builder -> modifyEntry(builder, BTWRFoodComponents.POISONOUS_POTATO));
        context.modify(Items.CHORUS_FRUIT, builder -> modifyEntry(builder, BTWRFoodComponents.CHORUS_FRUIT));

        if (FabricLoader.getInstance().isModLoaded("btwr")) {
            context.modify(BTWR_Items.CHOWDER, builder -> modifyEntry(builder, BTWRFoodComponents.CHOWDER));
            context.modify(BTWR_Items.EGG_SCRAMBLED_RAW, builder -> modifyEntry(builder, BTWRFoodComponents.EGG_SCRAMBLED_RAW));
            context.modify(BTWR_Items.MUSHROOM_OMELETTE_RAW, builder -> modifyEntry(builder, BTWRFoodComponents.MUSHROOM_OMELETTE_RAW));
            context.modify(BTWR_Items.RAW_KEBAB, builder -> modifyEntry(builder, BTWRFoodComponents.RAW_KEBAB));
            context.modify(BTWR_Items.EGG_SCRAMBLED_COOKED, builder -> modifyEntry(builder, BTWRFoodComponents.EGG_SCRAMBLED_COOKED));
            context.modify(BTWR_Items.MUSHROOM_OMELETTE_COOKED, builder -> modifyEntry(builder, BTWRFoodComponents.MUSHROOM_OMELETTE_COOKED));
            context.modify(BTWR_Items.SANDWICH, builder -> modifyEntry(builder, BTWRFoodComponents.SANDWICH));
            context.modify(BTWR_Items.HAM_AND_EGGS, builder -> modifyEntry(builder, BTWRFoodComponents.HAM_AND_EGGS));
            context.modify(BTWR_Items.STEAK_AND_POTATOES, builder -> modifyEntry(builder, BTWRFoodComponents.STEAK_AND_POTATOES));
            context.modify(BTWR_Items.COOKED_KEBAB, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_KEBAB));
            context.modify(BTWR_Items.STEAK_DINNER, builder -> modifyEntry(builder, BTWRFoodComponents.STEAK_DINNER));
            context.modify(BTWR_Items.PORK_DINNER, builder -> modifyEntry(builder, BTWRFoodComponents.PORK_DINNER));
            context.modify(BTWR_Items.WOLF_DINNER, builder -> modifyEntry(builder, BTWRFoodComponents.WOLF_DINNER));
            context.modify(BTWR_Items.CHICKEN_SOUP, builder -> modifyEntry(builder, BTWRFoodComponents.CHICKEN_SOUP));
            context.modify(BTWR_Items.HEARTY_STEW, builder -> modifyEntry(builder, BTWRFoodComponents.HEARTY_STEW));
            context.modify(BTWR_Items.CREEPER_OYSTERS, builder -> modifyEntry(builder, BTWRFoodComponents.CREEPER_OYSTERS));
            context.modify(BTWR_Items.BEAST_LIVER_RAW, builder -> modifyEntry(builder, BTWRFoodComponents.BEAST_LIVER_RAW));
            context.modify(BTWR_Items.BEAST_LIVER_COOKED, builder -> modifyEntry(builder, BTWRFoodComponents.BEAST_LIVER_COOKED));
        }

        if (FabricLoader.getInstance().isModLoaded("vegehenna")) {
            context.modify(ModItems.BOILED_POTATO, builder -> modifyEntry(builder, BTWRFoodComponents.BOILED_POTATO));
            context.modify(ModItems.COOKED_CARROT, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_CARROT));
            context.modify(ModItems.CHOCOLATE, builder -> modifyEntry(builder, BTWRFoodComponents.CHOCOLATE));
        }

        if (FabricLoader.getInstance().isModLoaded("bwt")) {
            context.modify(BwtItems.rawEggItem, builder -> modifyEntry(builder, BTWRFoodComponents.RAW_EGG));
            context.modify(BwtItems.wolfChopItem, builder -> modifyEntry(builder, BTWRFoodComponents.WOLFCHOP));
            context.modify(BwtItems.friedEggItem, builder -> modifyEntry(builder, BTWRFoodComponents.FRIED_EGG));
            context.modify(BwtItems.poachedEggItem, builder -> modifyEntry(builder, BTWRFoodComponents.POACHED_EGG));
            context.modify(BwtItems.cookedWolfChopItem, builder -> modifyEntry(builder, BTWRFoodComponents.COOKED_WOLFCHOP));
            context.modify(BwtItems.donutItem, builder -> modifyEntry(builder, BTWRFoodComponents.DONUT));
        }
    }

    // Directly modify the builder with access widening the put method (it was reflection before)
    private static void modifyEntry(ComponentMap.Builder builder, FoodComponent foodComponent) {
        builder.add(DataComponentTypes.FOOD, foodComponent);
    }

    private static void modifyEntryGranular(ComponentMap.Builder builder, int hungerPips, FoodComponent foodComponent) {
        // Set the new food component
        builder.add(DataComponentTypes.FOOD, foodComponent);
        // Add the hunger pip component with its lesser nutrition value
        builder.add(GranularHunger.HUNGER_PIP_COMPONENT, hungerPips);
    }

    private static StatusEffectInstance addAbsorptionEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.ABSORPTION, dur, amp, false, false, false);
    }

    private static StatusEffectInstance addRegenerationEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.REGENERATION, dur, amp, false, false, false);
    }

    private static StatusEffectInstance addSlownessEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.SLOWNESS, dur, amp, false, false, false);
    }

    private static StatusEffectInstance addWeaknessEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.WEAKNESS, dur, amp, false, false, false);
    }

    private static StatusEffectInstance addHungerEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.HUNGER, dur, amp, false, false, false);
    }

    private static StatusEffectInstance addPoisonEffect(int dur, int amp) {
        return new StatusEffectInstance(StatusEffects.POISON, dur, amp, false, false, false);
    }

}