package org.ivangeevo.btwr_ds.registry;

import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.tags.BwtItemTags;
import com.google.common.collect.Maps;
import ivangeevo.sturdy_trees.SturdyTreesItems;
import ivangeevo.sturdy_trees.tag.SturdyTreesTags;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.SharedConstants;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Util;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;

import java.util.Map;

/**
 * A provider class for a fully custom set fuel map;
 * most likely incompatible with other mods that add to fuel with FuelRegistry
 * to the {@link AbstractFurnaceBlockEntity#createFuelTimeMap()}.
 */
public class CustomFuelRegistry {

    public static Map<Item, Integer> getMap() {
        Map<Item, Integer> fuelMap = Maps.newLinkedHashMap();

        // Add high-level categories for better readability
        addHighEnergyFuels(fuelMap);
        addWoodVariants(fuelMap);
        addFuel(fuelMap, SturdyTreesTags.Items.BARK_ITEMS, 25);
        addFuel(fuelMap, BTWRDS_Items.BARK_BLOOD_WOOD, 25);
        addMiscellaneousFuels(fuelMap);

        addBwtFuels(fuelMap);

        return fuelMap;
    }

    private static void addHighEnergyFuels(Map<Item, Integer> map) {
        addFuel(map, Items.COAL_BLOCK, 14400);
        addFuel(map, Items.BLAZE_ROD, 12800);
        addWoodCategory(map, "LOGS",
                new Item[] {Items.BIRCH_LOG, Items.ACACIA_LOG, Items.OAK_LOG, Items.DARK_OAK_LOG, Items.JUNGLE_LOG},
                new int[] {16000, 12800, 9600, 8400, 6400}
        );
    }

    private static void addWoodVariants(Map<Item, Integer> map) {

        addWoodCategory(map, "PLANKS",
            new Item[] {Items.BIRCH_PLANKS, Items.ACACIA_PLANKS, Items.OAK_PLANKS, Items.DARK_OAK_PLANKS, Items.JUNGLE_PLANKS},
            new int[] {500, 400, 300, 200, 130}
        );
        addWoodCategory(map, "STAIRS",
            new Item[] {Items.BIRCH_STAIRS, Items.ACACIA_STAIRS, Items.OAK_STAIRS, Items.DARK_OAK_STAIRS, Items.JUNGLE_STAIRS},
            new int[] {400, 300, 200, 150, 100}
        );
        addWoodCategory(map, "SLABS",
            new Item[] {Items.BIRCH_SLAB, Items.ACACIA_SLAB, Items.OAK_SLAB, Items.DARK_OAK_SLAB, Items.JUNGLE_SLAB},
            new int[] {250, 200, 150, 100, 75}
        );
        addWoodCategory(map, "TRAPDOORS",
            new Item[] {Items.BIRCH_TRAPDOOR, Items.ACACIA_TRAPDOOR, Items.OAK_TRAPDOOR, Items.DARK_OAK_TRAPDOOR, Items.JUNGLE_TRAPDOOR},
            new int[] {275, 225, 175, 125, 100}
        );
        addWoodCategory(map, "PRESSURE_PLATES",
            new Item[] {Items.BIRCH_PRESSURE_PLATE, Items.ACACIA_PRESSURE_PLATE, Items.OAK_PRESSURE_PLATE, Items.DARK_OAK_PRESSURE_PLATE, Items.JUNGLE_PRESSURE_PLATE},
            new int[] {125, 100, 75, 50, 50}
        );
        addWoodCategory(map, "FENCES",
            new Item[] {Items.BIRCH_FENCE, Items.ACACIA_FENCE, Items.OAK_FENCE, Items.DARK_OAK_FENCE, Items.JUNGLE_FENCE},
            new int[] {275, 225, 175, 125, 100}
        );
        addWoodCategory(map, "FENCE_GATES",
            new Item[] {Items.BIRCH_FENCE_GATE, Items.ACACIA_FENCE_GATE, Items.OAK_FENCE_GATE, Items.DARK_OAK_FENCE_GATE, Items.JUNGLE_FENCE_GATE},
            new int[] {275, 225, 175, 125, 100}
        );
        addWoodCategory(map, "BUTTONS",
            new Item[] {Items.BIRCH_BUTTON, Items.ACACIA_BUTTON, Items.OAK_BUTTON, Items.DARK_OAK_BUTTON, Items.JUNGLE_BUTTON},
            new int[] {65, 50, 40, 25, 25}
        );
        addWoodCategory(map, "SIGNS",
            new Item[] {Items.BIRCH_SIGN, Items.ACACIA_SIGN, Items.OAK_SIGN, Items.DARK_OAK_SIGN, Items.JUNGLE_SIGN},
            new int[] {275, 225, 175, 125, 100}
        );
        addFuel(map, Blocks.BAMBOO_MOSAIC_STAIRS, 150);
        addFuel(map, Blocks.BAMBOO_MOSAIC_SLAB, 150);
    }

    // TODO: reconsider the values for bwt items. Most haven't been changed from the original values.
    // TODO: also move them to their appropriate categories instead of being grouped by the BWT namespace
    private static void addBwtFuels(Map<Item, Integer> map) {
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

    private static void addMiscellaneousFuels(Map<Item, Integer> map) {
        addFuel(map, Items.STICK, 50);
        addFuel(map, ItemTags.SAPLINGS, 15);
        addFuel(map, ItemTags.BANNERS, 300);
        addFuel(map, ItemTags.WOOL, 100);
        addFuel(map, Items.BOWL, 100);
        addFuel(map, ItemTags.WOOL_CARPETS, 67);
        addFuel(map, Items.CROSSBOW, 300);
        addFuel(map, Blocks.BAMBOO, 50);
        addFuel(map, Blocks.DEAD_BUSH, 100);
        addFuel(map, Blocks.SCAFFOLDING, 50);
        addFuel(map, Blocks.LOOM, 300);
        addFuel(map, Blocks.BARREL, 300);
        addFuel(map, Blocks.CARTOGRAPHY_TABLE, 300);
        addFuel(map, Blocks.FLETCHING_TABLE, 300);
        addFuel(map, Blocks.SMITHING_TABLE, 300);
        addFuel(map, Blocks.COMPOSTER, 300);
        addFuel(map, Blocks.AZALEA, 100);
        addFuel(map, Blocks.FLOWERING_AZALEA, 100);
        addFuel(map, Blocks.MANGROVE_ROOTS, 300);
        addFuel(map, Items.BOW, 300);
        addFuel(map, Items.FISHING_ROD, 300);
        addFuel(map, Blocks.LADDER, 300);
        addFuel(map, Items.FEATHER, 15);
        addFuel(map, ItemTags.SMALL_FLOWERS, 15);
        addFuel(map, ItemTags.TALL_FLOWERS, 25);
    }

    private static void addWoodCategory(Map<Item, Integer> map, String category, Item[] items, int[] values) {
        if (items.length != values.length) {
            throw new IllegalArgumentException("Mismatch between items and values for category: " + category);
        }
        for (int i = 0; i < items.length; i++) {
            addFuel(map, items[i], values[i]);
        }
    }

    private static boolean isNonFlammableWood(Item item) {
        return item.getRegistryEntry().isIn(ItemTags.NON_FLAMMABLE_WOOD);
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, TagKey<Item> tag, int fuelTime) {
        for (RegistryEntry<Item> itemRegistryEntry : Registries.ITEM.iterateEntries(tag)) {
            if (!isNonFlammableWood(itemRegistryEntry.value())) {
                fuelTimes.put(itemRegistryEntry.value(), fuelTime);
            }
        }
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {
        Item item2 = item.asItem();
        if (isNonFlammableWood(item2)) {
            if (SharedConstants.isDevelopment) {
                throw Util.throwOrPause(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName((ItemStack) null).getString() + " a furnace fuel. That will not work!"));
            }
        } else {
            fuelTimes.put(item2, fuelTime);
        }
    }
}
