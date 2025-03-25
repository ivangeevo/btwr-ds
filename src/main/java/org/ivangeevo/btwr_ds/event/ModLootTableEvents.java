package org.ivangeevo.btwr_ds.event;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import com.bwt.items.BwtItems;
import com.google.common.collect.ImmutableList;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.mixin.ItemEntryAccessor;
import org.ivangeevo.btwr_ds.mixin.LootPoolBuilderAccessor;

import java.util.ArrayList;
import java.util.List;

public class ModLootTableEvents {
    private static final String ST = "sturdy_trees";
    private static final IntProperty VARIATION = IntProperty.of("variation", 0, 4);
    public static final LootCondition.Builder WITH_SHOVEL_FULLY_HARVESTS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK));
    public static final LootCondition.Builder WITHOUT_HOE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.HOES)).invert();

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};

    // tough wood types require an axe to break fully
    private static final String[] overworldToughWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry"};


    // Register loot table changes
    public static void initialize() {
        replaceSawDustItemDrops();
        replacePlankItemDrops();

        // Change the wheat seeds dropped by left click breaking grass block with hoe (tough environment change)
        // to drop hemp seeds instead.
        modifySpecificItem(Blocks.GRASS_BLOCK.getLootTableKey(), Items.WHEAT_SEEDS, BwtItems.hempSeedsItem);
    }

    private static void replaceSawDustItemDrops() {
        for (String woodType : overworldToughWoodTypes) {
            String[] logTypes = new String[]{"stripped", "chewed", "spike_up", "spike_down"};

            for (String logType : logTypes) {
                RegistryKey<LootTable> key = RegistryKey.of(
                        RegistryKeys.LOOT_TABLE,
                        ID.ofST("blocks/log_" + woodType + "_" + logType)
                );

                modifySpecificItem(key, SturdyTreesItems.DUST_SAW, BwtItems.sawDustItem);
            }
        }
    }

    private static void replacePlankItemDrops() {
        for (String woodType : overworldToughWoodTypes) {
            Item planksItem = Registries.ITEM.get(ID.ofMC(woodType + "_planks"));
            RegistryKey<LootTable> key = RegistryKey.of(
                    RegistryKeys.LOOT_TABLE,
                    ID.ofST("blocks/log_" + woodType + "_stripped")
            );

            modifySpecificItem(key, planksItem, Items.STICK);
        }
    }


    private static void modifySpecificItem(RegistryKey<LootTable> registryKey, Item target, Item toReplace) {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            // Check if the key is for target's loot table
            if (registryKey != key) return;

            tableBuilder.modifyPools(builder -> {
                List<LootPoolEntry> l = new ArrayList<>(((LootPoolBuilderAccessor) builder).getEntries().build());
                l.replaceAll(entry -> {
                    if (!(entry instanceof ItemEntry itemEntry))
                        return entry;
                    if (((ItemEntryAccessor) itemEntry).getItem().value() != target)
                        return entry;
                    ((ItemEntryAccessor) entry).setItem(Registries.ITEM.getEntry(toReplace));
                    return entry;
                });

                ((LootPoolBuilderAccessor) builder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(l));
            });
        });
    }


    private static List<Identifier> getStrippedLogsIDs() {
        List<Identifier> strippedLogs = new ArrayList<>();
        for (String woodType : overworldToughWoodTypes) {
            strippedLogs.add(Identifier.of(ST, "log_" + woodType + "_stripped"));
        }

        return strippedLogs;
    }

    private static List<Identifier> getSpikeLogsIDs() {
        List<Identifier> list = new ArrayList<>();

        for (String woodType : overworldToughWoodTypes) {
            list.add(Identifier.of(ST, "log_" + woodType + "_spike_up"));
            list.add(Identifier.of(ST, "log_" + woodType + "_spike_down"));
        }

        return list;
    }

    private static List<Identifier> getChewedLogsIDs() {
        List<Identifier> list = new ArrayList<>();

        for (String woodType : overworldToughWoodTypes) {
            list.add(Identifier.of(ST, "log_" + woodType + "_chewed"));
        }

        return list;
    }


    private static class ID
    {
        static Identifier ofMC(String item) {
            return Identifier.ofVanilla(item);
        }

        /**
         * DS - Datapack Suite
         **/
        static Identifier ofDS(String item) {
            return Identifier.of("btwr_ds", item);
        }

        /**
         * BTWR: Core
         **/
        static Identifier ofBTWR(String item) {
            return Identifier.of("btwr", item);
        }

        /**
         * BWT - Better With Time
         **/
        static Identifier ofBWT(String item) {
            return Identifier.of("bwt", item);
        }

        static Identifier ofTE(String item) {
            return Identifier.of("tough_environment", item);
        }

        static Identifier ofST(String item) {
            return Identifier.of("sturdy_trees", item);
        }

        static Identifier ofSS(String item) {
            return Identifier.of("self_sustainable", item);
        }

        static Identifier ofVG(String item) {
            return Identifier.of("vegehenna", item);
        }

    }
}


