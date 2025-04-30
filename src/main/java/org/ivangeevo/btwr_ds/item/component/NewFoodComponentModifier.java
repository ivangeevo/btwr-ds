package org.ivangeevo.btwr_ds.item.component;

import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.ivangeevo.btwr_ds.item.GranularFoodComponents;
import tetro48.system.GranularHunger;

import java.util.LinkedHashMap;
import java.util.Map;

public class NewFoodComponentModifier {

    // Map of items to their granular food data (ordered for consistency)
    private static final Map<Item, GranularEntry> GRANULAR_ENTRIES = new LinkedHashMap<>();

    public static void register() {
        // Define all granular items
        registerGranularFoodEntries();

        // Register the event ONCE
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(GRANULAR_ENTRIES.keySet(), (builder, item) -> {
                GranularEntry entry = GRANULAR_ENTRIES.get(item);
                builder.add(DataComponentTypes.FOOD, entry.foodComponent());
                builder.add(GranularHunger.HUNGER_PIP_COMPONENT, entry.hungerPips());
            });
        });
    }

    private static void registerGranularFoodEntries() {
        add(Items.BROWN_MUSHROOM, 1, GranularFoodComponents.BROWN_MUSHROOM);
        add(Items.RED_MUSHROOM, 1, GranularFoodComponents.RED_MUSHROOM);
        add(Items.PUMPKIN_SEEDS, 1, GranularFoodComponents.PUMPKIN_SEEDS);
        add(Items.COCOA_BEANS, 1, GranularFoodComponents.COCOA_BEANS);
        add(Items.MELON_SLICE, 2, GranularFoodComponents.MELON_SLICE);
        add(Items.COOKIE, 3, GranularFoodComponents.COOKIE);
        add(Items.PUMPKIN_PIE, 6, GranularFoodComponents.PUMPKIN_PIE);
        add(BwtItems.donutItem, 3, GranularFoodComponents.DONUT);
    }

    private static void add(Item item, int pips, FoodComponent foodComponent) {
        GRANULAR_ENTRIES.put(item, new GranularEntry(pips, foodComponent));
    }

    private record GranularEntry(int hungerPips, FoodComponent foodComponent) {}
}
