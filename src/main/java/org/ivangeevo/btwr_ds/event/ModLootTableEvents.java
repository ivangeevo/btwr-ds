package org.ivangeevo.btwr_ds.event;

import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.mixin.ItemEntryAccessor;
import org.ivangeevo.btwr_ds.mixin.LootPoolBuilderAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ModLootTableEvents
{
    private static final String ST = "sturdy_trees";
    private static final IntProperty VARIATION = IntProperty.of("variation", 0, 4);
    public static final LootCondition.Builder WITH_SHOVEL_FULLY_HARVESTS = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(BTWRConventionalTags.Items.SHOVELS_HARVEST_FULL_BLOCK));
    public static final LootCondition.Builder WITHOUT_HOE = MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.HOES)).invert();


    // Register loot table changes
    public static void initialize()
    {
        // replace the loot table for stripped log in sturdy trees to drop stick instead of planks
        replaceListWithCondition(createStrippedLogsList(), StatePredicate.Builder.create().exactMatch(VARIATION, 0), Items.STICK);

        /**
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            // Check if the key is for the grass block's loot table
            if (Blocks.GRASS_BLOCK.getLootTableKey() != key) return;

            tableBuilder.modifyPools(builder -> {
                List<LootPoolEntry> l = new ArrayList<>(((LootPoolBuilderAccessor) builder).getEntries().build());
                l.replaceAll(entry -> {
                    if (!(entry instanceof ItemEntry itemEntry))
                        return entry;
                    if (((ItemEntryAccessor) itemEntry).getItem().value() != Items.WHEAT_SEEDS)
                        return entry;
                    ((ItemEntryAccessor) entry).setItem(Registries.ITEM.getEntry(BTWR_Items.HEMP_SEEDS));
                    return entry;
                });

                ((LootPoolBuilderAccessor) builder).setEntries(ImmutableList.<LootPoolEntry>builder().addAll(l));
            });
        });
         **/
        replaceSpecificItem(Blocks.GRASS_BLOCK.getLootTableKey(), Items.WHEAT_SEEDS, BTWR_Items.HEMP_SEEDS);


    }

    private static void replaceSpecificItem(RegistryKey<LootTable> registryKey, Item target, Item toReplace)
    {
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

    private static void replace(Identifier blockID, Item itemToDrop)
    {
        LootTableEvents.REPLACE.register((key, original, source, registries) ->
        {
            if (source.isBuiltin())
            {

                Block blockToModify = Registries.BLOCK.get(blockID);

                // Check if the block matches the loot table id
                if (blockToModify.getLootTableKey().equals(key))
                {
                    // Create new loot table with the desired drop and conditions
                    LootTable.Builder newTable = LootTable.builder();
                    newTable.pool( createLootPool(blockToModify, itemToDrop) );

                    // Return the modified loot table
                    return newTable.build();

                }
            }
            return null; // Return null if no replacement occurs
        });
    }

    private static void replaceList(List<Identifier> blocksToModify, Item itemToDrop)
    {
        LootTableEvents.REPLACE.register((key, original, source, registries) ->
        {
            if (source.isBuiltin())
            {

                for (Identifier blockId : blocksToModify)
                {
                    Block blockToModify = Registries.BLOCK.get(blockId);

                    // Check if the block matches the loot table id
                    if (blockToModify.getLootTableKey().equals(key))
                    {
                        // Create new loot table with the desired drop and conditions
                        LootTable.Builder newTable = LootTable.builder();
                        newTable.pool( createLootPool(blockToModify, itemToDrop) );

                        // Return the modified loot table
                        return newTable.build();
                    }
                }
            }
            return null; // Return null if no replacement occurs
        });
    }

    private static void replaceListWithCondition(List<Identifier> blocksToModify, StatePredicate.Builder statePredicate, Item itemToDrop)
    {
        LootTableEvents.REPLACE.register((key, original, source, registries) ->
        {
            if (source.isBuiltin())
            {

                for (Identifier blockId : blocksToModify)
                {
                    Block blockToModify = Registries.BLOCK.get(blockId);

                    // Check if the block matches the loot table id
                    if (blockToModify.getLootTableKey().equals(key))
                    {
                        // Create new loot table with the desired drop and conditions
                        LootTable.Builder newTable = LootTable.builder();
                        newTable.pool( createLootPoolWithStateCheck(blockToModify, itemToDrop, statePredicate) );

                        // Return the modified loot table
                        return newTable.build();
                    }
                }
            }
            return null; // Return null if no replacement occurs
        });
    }

    // Create a loot pool with a block state predicate check
    public static LootPool.Builder createLootPoolWithStateCheck(Block blockToModify, Item itemToDrop, StatePredicate.Builder predicate)
    {
        LootPool.Builder newPool = createLootPool(blockToModify, itemToDrop);

        LootCondition condition = BlockStatePropertyLootCondition.builder(blockToModify)
                .properties(predicate)
                .build();

        return newPool
                .with(ItemEntry.builder(itemToDrop))
                .conditionally(condition);
    }

    // Create a loot pool
    public static LootPool.Builder createLootPool(Block blockToModify, Item itemToDrop)
    {
        return LootPool.builder().with(ItemEntry.builder(itemToDrop));
    }

    // Create a list of stripped logs
    private static List<Identifier> createStrippedLogsList()
    {
        List<Identifier> strippedLogs = new ArrayList<>();
        strippedLogs.add(Identifier.of(ST, "log_oak_stripped"));
        strippedLogs.add(Identifier.of(ST, "log_spruce_stripped"));
        strippedLogs.add(Identifier.of(ST, "log_birch_stripped"));
        strippedLogs.add(Identifier.of(ST, "log_jungle_stripped"));
        strippedLogs.add(Identifier.of(ST, "log_acacia_stripped"));
        strippedLogs.add(Identifier.of(ST, "log_dark_oak_stripped"));
        strippedLogs.add(Identifier.of(ST, "log_mangrove_stripped"));
        strippedLogs.add(Identifier.of(ST, "log_cherry_stripped"));
        return strippedLogs;
    }

}
