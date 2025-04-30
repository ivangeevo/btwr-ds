package org.ivangeevo.btwr_ds.item.component;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import tetro48.system.GranularHunger;

import java.util.LinkedHashMap;
import java.util.Map;

/** A class that modifies all foods that should have nutrition value less than 0.5 shanks (via the Granular Hunger mod) **/
public class GranularFoodComponentRegistry {

    // Map of items to their granular food data (ordered for consistency)
    private static final Map<Item, GranularEntry> GRANULAR_ENTRIES = new LinkedHashMap<>();

    public static void registerFoods() {

        // Define all granular food items
        // 1 pip = 1 hunger (out of 60)
        // 3 pips = 0.5 shank (1 hunger from original mc values)
        // 6 pips = 1 shank
        add(Items.BROWN_MUSHROOM, 1, BTWRFoodComponents.Granular.BROWN_MUSHROOM);
        add(Items.RED_MUSHROOM, 1, BTWRFoodComponents.Granular.RED_MUSHROOM);
        add(Items.PUMPKIN_SEEDS, 1, BTWRFoodComponents.Granular.PUMPKIN_SEEDS);
        add(Items.COCOA_BEANS, 1, BTWRFoodComponents.Granular.COCOA_BEANS);
        add(Items.MELON_SLICE, 2, BTWRFoodComponents.Granular.MELON_SLICE);

        // Register the event
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(GRANULAR_ENTRIES.keySet(), (builder, item) -> {
                // Get a granular entry from the map
                GranularEntry entry = GRANULAR_ENTRIES.get(item);
                // Set the new food component
                builder.add(DataComponentTypes.FOOD, entry.foodComponent());
                // Add the hunger pip component with its lesser nutrition value
                builder.add(GranularHunger.HUNGER_PIP_COMPONENT, entry.hungerPips());
            });
        });
    }

    private static void add(Item item, int pips, FoodComponent foodComponent) {
        GRANULAR_ENTRIES.put(item, new GranularEntry(pips, foodComponent));
    }

    private record GranularEntry(int hungerPips, FoodComponent foodComponent) {}
}
