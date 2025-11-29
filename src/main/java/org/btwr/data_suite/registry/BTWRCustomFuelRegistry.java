package org.btwr.data_suite.registry;

import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.tags.BwtItemTags;
import com.google.common.collect.Maps;
import org.btwr.sturdy_trees.tag.SturdyTreesTags;
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
import org.btwr.data_suite.item.BTWRDS_Items;
import org.btwr.tough_environment.item.ModItems;

import java.util.Map;

import static org.btwr.vegehenna.item.ModItems.SUGAR_CANE_ROOTS;

/** A provider class for a fully custom set fuel map;
 *  most likely incompatible with other mods that add to fuel with FuelRegistry
 *  to the{@link AbstractFurnaceBlockEntity#createFuelTimeMap()} **/
public class BTWRCustomFuelRegistry {

    // TODO: Add pink petals as fuel item
    public static Map<Item, Integer> getMap() {
        Map<Item, Integer> tempMap = Maps.newLinkedHashMap();

        // disabled all bamboo blocks from making fuel as they are too overpowered

        // disabled the coal block because it defeats the purpose of making coal and coal dust fuels as it
        // would need to be too high fuel value to match the coal items

        // could make coal and coal dust have much less fuelTime and then balance the coal block around that

        //addFuel(tempMap, Items.COAL_BLOCK, 14400);
        addFuel(tempMap, Items.BLAZE_ROD, 12800);
        addFuel(tempMap, Items.COAL, 12800);
        addFuel(tempMap, ModItems.DUST_COAL, 6400);

        // Logs & Wood
        addFuel(tempMap, Items.BIRCH_LOG, 16000);
        addFuel(tempMap, Items.BIRCH_WOOD, 16000);
        addFuel(tempMap, Items.ACACIA_LOG, 16000);
        addFuel(tempMap, Items.ACACIA_WOOD, 16000);
        addFuel(tempMap, Items.OAK_LOG, 12800);
        addFuel(tempMap, Items.OAK_WOOD, 12800);
        addFuel(tempMap, Items.DARK_OAK_LOG, 12800);
        addFuel(tempMap, Items.DARK_OAK_WOOD, 12800);
        addFuel(tempMap, Items.CHERRY_LOG, 12800);
        addFuel(tempMap, Items.CHERRY_WOOD, 12800);
        addFuel(tempMap, Items.SPRUCE_LOG, 9600);
        addFuel(tempMap, Items.SPRUCE_WOOD, 9600);
        addFuel(tempMap, Items.MANGROVE_LOG, 8400);
        addFuel(tempMap, Items.MANGROVE_WOOD, 8400);
        addFuel(tempMap, Items.JUNGLE_LOG, 6400);
        addFuel(tempMap, Items.JUNGLE_WOOD, 6400);

        addFuel(tempMap, Items.STRIPPED_BIRCH_LOG, 15975);
        addFuel(tempMap, Items.STRIPPED_BIRCH_WOOD, 15975);
        addFuel(tempMap, Items.STRIPPED_ACACIA_LOG, 15975);
        addFuel(tempMap, Items.STRIPPED_ACACIA_WOOD, 15975);
        addFuel(tempMap, Items.STRIPPED_OAK_LOG, 12775);
        addFuel(tempMap, Items.STRIPPED_OAK_WOOD, 12775);
        addFuel(tempMap, Items.STRIPPED_DARK_OAK_LOG, 12775);
        addFuel(tempMap, Items.STRIPPED_DARK_OAK_WOOD, 12775);
        addFuel(tempMap, Items.STRIPPED_CHERRY_LOG, 12775);
        addFuel(tempMap, Items.STRIPPED_CHERRY_WOOD, 12775);
        addFuel(tempMap, Items.STRIPPED_SPRUCE_LOG, 9757);
        addFuel(tempMap, Items.STRIPPED_SPRUCE_WOOD, 9757);
        addFuel(tempMap, Items.STRIPPED_MANGROVE_LOG, 8375);
        addFuel(tempMap, Items.STRIPPED_MANGROVE_WOOD, 8375);
        addFuel(tempMap, Items.STRIPPED_JUNGLE_LOG, 6375);
        addFuel(tempMap, Items.STRIPPED_JUNGLE_WOOD, 6375);

        addFuel(tempMap, ItemTags.WARPED_STEMS, 1500);
        addFuel(tempMap, ItemTags.CRIMSON_STEMS, 1500);
        //addFuel(tempMap, ItemTags.BAMBOO_BLOCKS, 500);

        // Planks
        addFuel(tempMap, Items.BIRCH_PLANKS, 4000);
        addFuel(tempMap, Items.ACACIA_PLANKS, 4000);
        addFuel(tempMap, Items.OAK_PLANKS, 3200);
        addFuel(tempMap, Items.DARK_OAK_PLANKS, 3200);
        addFuel(tempMap, Items.CHERRY_PLANKS, 2400);
        addFuel(tempMap, Items.SPRUCE_PLANKS, 2400);
        addFuel(tempMap, Items.MANGROVE_PLANKS, 1600);
        addFuel(tempMap, Items.JUNGLE_PLANKS, 1600);
        //addFuel(tempMap, Items.BAMBOO_PLANKS, 130);

        // Wooden Stairs
        addFuel(tempMap, Items.BIRCH_STAIRS, 3000);
        addFuel(tempMap, Items.ACACIA_STAIRS, 3000);
        addFuel(tempMap, Items.OAK_STAIRS, 2200);
        addFuel(tempMap, Items.DARK_OAK_STAIRS, 2200);
        addFuel(tempMap, Items.CHERRY_STAIRS, 1400);
        addFuel(tempMap, Items.SPRUCE_STAIRS, 1400);
        addFuel(tempMap, Items.MANGROVE_STAIRS, 600);
        addFuel(tempMap, Items.JUNGLE_STAIRS, 600);

        //addFuel(tempMap, Items.BAMBOO_STAIRS, 100);

        //addFuel(tempMap, Blocks.BAMBOO_MOSAIC_STAIRS, 150);

        // Wooden Slabs
        addFuel(tempMap, Items.BIRCH_SLAB, 2000);
        addFuel(tempMap, Items.ACACIA_SLAB, 2000);
        addFuel(tempMap, Items.OAK_SLAB, 1100);
        addFuel(tempMap, Items.DARK_OAK_SLAB, 1100);
        addFuel(tempMap, Items.CHERRY_SLAB, 700);
        addFuel(tempMap, Items.SPRUCE_SLAB, 700);
        addFuel(tempMap, Items.MANGROVE_SLAB, 300);
        addFuel(tempMap, Items.JUNGLE_SLAB, 300);
        //addFuel(tempMap, Items.BAMBOO_SLAB, 75);

        //addFuel(tempMap, Blocks.BAMBOO_MOSAIC_SLAB, 150);

        //TODO: Re-balance fuel values. All values below this one haven't been changed fully

        // Wooden Trapdoors
        addFuel(tempMap, Items.BIRCH_TRAPDOOR, 1000);
        addFuel(tempMap, Items.ACACIA_TRAPDOOR, 1000);
        addFuel(tempMap, Items.OAK_TRAPDOOR, 1000);
        addFuel(tempMap, Items.DARK_OAK_TRAPDOOR, 225);
        addFuel(tempMap, Items.CHERRY_TRAPDOOR, 225);
        addFuel(tempMap, Items.SPRUCE_TRAPDOOR, 175);
        addFuel(tempMap, Items.MANGROVE_TRAPDOOR, 175);
        addFuel(tempMap, Items.JUNGLE_TRAPDOOR, 125);

        //addFuel(tempMap, Items.BAMBOO_TRAPDOOR, 100);

        // Wooden Pressure Plates
        addFuel(tempMap, Items.BIRCH_PRESSURE_PLATE, 125);
        addFuel(tempMap, Items.ACACIA_PRESSURE_PLATE, 125);
        addFuel(tempMap, Items.OAK_PRESSURE_PLATE, 100);
        addFuel(tempMap, Items.DARK_OAK_PRESSURE_PLATE, 100);
        addFuel(tempMap, Items.CHERRY_PRESSURE_PLATE, 100);
        addFuel(tempMap, Items.SPRUCE_PRESSURE_PLATE, 75);
        addFuel(tempMap, Items.MANGROVE_PRESSURE_PLATE, 75);
        addFuel(tempMap, Items.JUNGLE_PRESSURE_PLATE, 50);

        //addFuel(tempMap, Items.BAMBOO_PRESSURE_PLATE, 50);

        // Wooden Fences
        addFuel(tempMap, Items.BIRCH_FENCE, 275);
        addFuel(tempMap, Items.ACACIA_FENCE, 275);
        addFuel(tempMap, Items.OAK_FENCE, 225);
        addFuel(tempMap, Items.DARK_OAK_FENCE, 225);
        addFuel(tempMap, Items.CHERRY_FENCE, 225);
        addFuel(tempMap, Items.SPRUCE_FENCE, 175);
        addFuel(tempMap, Items.MANGROVE_FENCE, 175);
        addFuel(tempMap, Items.JUNGLE_FENCE, 125);

        //addFuel(tempMap, Items.BAMBOO_FENCE, 100);

        // Wooden Fence Gates
        addFuel(tempMap, Items.BIRCH_FENCE_GATE, 275);
        addFuel(tempMap, Items.ACACIA_FENCE_GATE, 275);
        addFuel(tempMap, Items.OAK_FENCE_GATE, 225);
        addFuel(tempMap, Items.DARK_OAK_FENCE_GATE, 225);
        addFuel(tempMap, Items.CHERRY_FENCE_GATE, 225);
        addFuel(tempMap, Items.SPRUCE_FENCE_GATE, 175);
        addFuel(tempMap, Items.MANGROVE_FENCE_GATE, 175);
        addFuel(tempMap, Items.JUNGLE_FENCE_GATE, 125);

        //addFuel(tempMap, Items.BAMBOO_FENCE_GATE, 100);

        // Wooden Buttons
        addFuel(tempMap, Items.BIRCH_BUTTON, 65);
        addFuel(tempMap, Items.ACACIA_BUTTON, 65);
        addFuel(tempMap, Items.OAK_BUTTON, 50);
        addFuel(tempMap, Items.DARK_OAK_BUTTON, 50);
        addFuel(tempMap, Items.CHERRY_BUTTON, 50);
        addFuel(tempMap, Items.SPRUCE_BUTTON, 40);
        addFuel(tempMap, Items.MANGROVE_BUTTON, 40);
        addFuel(tempMap, Items.JUNGLE_BUTTON, 25);

        //addFuel(tempMap, Items.BAMBOO_BUTTON, 25);

        addFuel(tempMap, Items.STICK, 400);
        addFuel(tempMap, ItemTags.SAPLINGS, 100);

        // Signs
        addFuel(tempMap, Items.BIRCH_SIGN, 275);
        addFuel(tempMap, Items.ACACIA_SIGN, 275);
        addFuel(tempMap, Items.OAK_SIGN, 225);
        addFuel(tempMap, Items.DARK_OAK_SIGN, 225);
        addFuel(tempMap, Items.CHERRY_SIGN, 225);
        addFuel(tempMap, Items.SPRUCE_SIGN, 175);
        addFuel(tempMap, Items.MANGROVE_SIGN, 175);
        addFuel(tempMap, Items.JUNGLE_SIGN, 125);

        //addFuel(tempMap, Items.BAMBOO_SIGN, 100);

        // Hanging Signs
        addFuel(tempMap, Items.BIRCH_HANGING_SIGN, 275);
        addFuel(tempMap, Items.ACACIA_HANGING_SIGN, 275);
        addFuel(tempMap, Items.OAK_HANGING_SIGN, 225);
        addFuel(tempMap, Items.DARK_OAK_HANGING_SIGN, 225);
        addFuel(tempMap, Items.CHERRY_HANGING_SIGN, 225);
        addFuel(tempMap, Items.SPRUCE_HANGING_SIGN, 175);
        addFuel(tempMap, Items.MANGROVE_HANGING_SIGN, 175);
        addFuel(tempMap, Items.JUNGLE_HANGING_SIGN, 125);
        //addFuel(tempMap, Items.BAMBOO_HANGING_SIGN, 100);

        addFuel(tempMap, ItemTags.BANNERS, 300);

        // removed doors & boats
        addFuel(tempMap, ItemTags.WOOL, 100);

        addFuel(tempMap, Items.BOWL, 100);
        addFuel(tempMap, ItemTags.WOOL_CARPETS, 67);

        // TODO: Reconsider the values below up to MANGROVE ROOTS
        addFuel(tempMap, Items.CROSSBOW, 300);
        addFuel(tempMap, Blocks.BAMBOO, 15);
        addFuel(tempMap, Blocks.DEAD_BUSH, 100);
        addFuel(tempMap, Blocks.SCAFFOLDING, 50);
        addFuel(tempMap, Blocks.LOOM, 300);
        addFuel(tempMap, Blocks.BARREL, 300);
        addFuel(tempMap, Blocks.CARTOGRAPHY_TABLE, 300);
        addFuel(tempMap, Blocks.FLETCHING_TABLE, 300);
        addFuel(tempMap, Blocks.SMITHING_TABLE, 300);
        addFuel(tempMap, Blocks.COMPOSTER, 300);
        addFuel(tempMap, Blocks.AZALEA, 100);
        addFuel(tempMap, Blocks.FLOWERING_AZALEA, 100);
        addFuel(tempMap, Blocks.MANGROVE_ROOTS, 300);

        // TODO: Reconsider the values for bow, fishing rod and ladder
        addFuel(tempMap, Items.BOW, 300);
        addFuel(tempMap, Items.FISHING_ROD, 300);
        addFuel(tempMap, Blocks.LADDER, 300);

        // New added fuel items
        addFuel(tempMap, Items.FEATHER, 15);
        addFuel(tempMap, Items.SUGAR_CANE, 20);

        addFuel(tempMap, ItemTags.SMALL_FLOWERS, 100);
        addFuel(tempMap, ItemTags.TALL_FLOWERS, 200);

        addFuel(tempMap, SturdyTreesTags.Items.BARK_ITEMS, 200);
        addFuel(tempMap, BTWRDS_Items.BARK_BLOOD_WOOD, 200);
        addFuel(tempMap, SUGAR_CANE_ROOTS, 20);

        addBwtFuels(tempMap);

        return tempMap;
    }

    // TODO: reconsider the values for bwt items. Most haven't been changed from the original values.
    // TODO: also move them to their appropriate categories instead of being grouped by the BWT namespace
    private static void addBwtFuels(Map<Item, Integer> map) {
        addFuel(map, BwtItems.nethercoalItem, 14200);
        addFuel(map, BwtItems.coalDustItem, 6400);

        addFuel(map, BwtBlocks.bloodWoodBlocks.logBlock, 6400);
        addFuel(map, BwtBlocks.bloodWoodBlocks.woodBlock, 6400);
        addFuel(map, BwtBlocks.bloodWoodBlocks.strippedLogBlock, 6375);
        addFuel(map, BwtBlocks.bloodWoodBlocks.strippedWoodBlock, 6375);
        addFuel(map, BwtBlocks.bloodWoodBlocks.planksBlock, 1600);
        addFuel(map, BwtBlocks.bloodWoodBlocks.stairsBlock, 600);
        addFuel(map, BwtBlocks.bloodWoodBlocks.slabBlock, 300);
        addFuel(map, BwtBlocks.bloodWoodBlocks.trapdoorBlock, 125);
        addFuel(map, BwtBlocks.bloodWoodBlocks.pressurePlateBlock, 50);
        addFuel(map, BwtBlocks.bloodWoodBlocks.fenceBlock, 125);
        addFuel(map, BwtBlocks.bloodWoodBlocks.fenceGateBlock, 125);
        addFuel(map, BwtBlocks.bloodWoodBlocks.buttonBlock, 25);


        addFuel(map, BwtItemTags.WOODEN_SIDING_BLOCKS, 150);
        addFuel(map, BwtItemTags.WOODEN_MOULDING_BLOCKS, 75);
        addFuel(map, BwtItemTags.WOODEN_CORNER_BLOCKS, 38);
        addFuel(map, BwtBlocks.axleBlock, 150);
        addFuel(map, BwtBlocks.axlePowerSourceBlock, 150);
        addFuel(map, BwtBlocks.bellowsBlock, 450);
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

    private static boolean isNonFlammableWood(Item item) {
        return item.getDefaultStack().getRegistryEntry().isIn(ItemTags.NON_FLAMMABLE_WOOD);
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
                throw Util.throwOrPause(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName((ItemStack)null).getString() + " a furnace fuel. That will not work!"));
            }
        } else {
            fuelTimes.put(item2, fuelTime);
        }
    }

}