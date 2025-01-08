package org.ivangeevo.btwr_ds.registry;

import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.tags.BwtItemTags;
import net.minecraft.SharedConstants;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A provider class for a fully custom set of fuels, most likely incompatible with other mods that
 * add to the fuel map via {@link AbstractFurnaceBlockEntity#createFuelTimeMap()}.
 */
public class CustomFuelRegistry {

    private enum FuelCategory {
        COALS(14400),
        LOGS(16000),
        PLANKS(500),
        STAIRS(400),
        SLABS(250),
        TRAPDOORS(275),
        PRESSURE_PLATES(125),
        FENCES(275),
        FENCE_GATES(275),
        BUTTONS(65),
        SIGNS(275),
        HANGING_SIGNS(275),
        WOOL(100),
        MISC(50);

        private final int defaultFuelTime;

        FuelCategory(int defaultFuelTime) {
            this.defaultFuelTime = defaultFuelTime;
        }

        public int getDefaultFuelTime() {
            return defaultFuelTime;
        }
    }

    public static Map<Item, Integer> getFuelMap() {
        Map<Item, Integer> fuelMap = new LinkedHashMap<>();

        // Add fuels by category
        addFuelByCategory(fuelMap, FuelCategory.COALS, Items.COAL_BLOCK);
        addFuelByCategory(fuelMap, FuelCategory.LOGS, Items.BIRCH_LOG, Items.ACACIA_LOG, Items.OAK_LOG);
        addFuelByCategory(fuelMap, FuelCategory.PLANKS, Items.BIRCH_PLANKS, Items.ACACIA_PLANKS);
        addFuelByCategory(fuelMap, FuelCategory.BUTTONS, Items.BIRCH_BUTTON, Items.ACACIA_BUTTON);

        // Add tag-based fuels
        addFuelByTag(fuelMap, ItemTags.BAMBOO_BLOCKS, 500);
        addFuelByTag(fuelMap, ItemTags.SAPLINGS, 15);

        // Add Better With Time (BWT) fuels
        addBwtFuels(fuelMap);

        return fuelMap;
    }

    private static void addFuelByCategory(Map<Item, Integer> map, FuelCategory category, ItemConvertible... items) {
        for (ItemConvertible item : items) {
            addFuel(map, item, category.getDefaultFuelTime());
        }
    }

    private static void addFuelByTag(Map<Item, Integer> map, TagKey<Item> tag, int fuelTime) {
        for (RegistryEntry<Item> itemEntry : Registries.ITEM.iterateEntries(tag)) {
            if (!isNonFlammableWood(itemEntry.value())) {
                map.put(itemEntry.value(), fuelTime);
            }
        }
    }

    // TODO: Reconsider the values for BWT items. Most haven't been changed from the original values.
    // TODO: also move them to their appropriate categories instead of being grouped by the BWT namespace

    private static void addBwtFuels(Map<Item, Integer> map) {
        // BWT-specific fuels
        addFuel(map, BwtItemTags.WOODEN_SIDING_BLOCKS, 150);
        addFuel(map, BwtItemTags.WOODEN_MOULDING_BLOCKS, 75);
        addFuel(map, BwtItemTags.WOODEN_CORNER_BLOCKS, 38);
        addFuel(map, BwtBlocks.axleBlock, 150);
        addFuel(map, BwtBlocks.axlePowerSourceBlock, 150);
        addFuel(map, BwtBlocks.bellowsBlock, 450);
        //addFuel(map, BwtBlocks.bloodWoodBlock)
        addFuel(map, BwtBlocks.gearBoxBlock, 600);
        addFuel(map, BwtBlocks.grateBlock, 300);
        addFuel(map, BwtBlocks.hopperBlock, 300);
        addFuel(map, BwtBlocks.platformBlock, 375);
        addFuel(map, BwtBlocks.pulleyBlock, 600);
        addFuel(map, BwtBlocks.sawBlock, 300);
        addFuel(map, BwtBlocks.slatsBlock, 300);
        //addFuel(map, BwtBlocks.screwPumpBlock)
        //addFuel(map, BwtBlocks.tableBlock)
        addFuel(map, BwtItems.gearItem, 18);

        addFuel(map, BwtItems.sawDustItem, 25);
        addFuel(map, BwtItems.soulDustItem, 25);
    }

    private static void addFuel(Map<Item, Integer> map, ItemConvertible item, int fuelTime) {
        Item actualItem = item.asItem();
        if (isNonFlammableWood(actualItem)) {
            if (SharedConstants.isDevelopment) {
                throw Util.throwOrPause(new IllegalStateException(
                    "Attempted to add non-flammable item " + actualItem.getName(null).getString() + " as a fuel item."));
            }
        } else {
            map.put(actualItem, fuelTime);
        }
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, TagKey<Item> tag, int fuelTime) {
        for (RegistryEntry<Item> itemRegistryEntry : Registries.ITEM.iterateEntries(tag)) {
            if (!isNonFlammableWood(itemRegistryEntry.value())) {
                fuelTimes.put(itemRegistryEntry.value(), fuelTime);
            }
        }

    }


    private static boolean isNonFlammableWood(Item item) {
        return item.getDefaultStack().getRegistryEntry().isIn(ItemTags.NON_FLAMMABLE_WOOD);
    }
}
