package org.ivangeevo.btwr_ds.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModLootTableReplacement
{

    private static final String ST = "sturdy_trees";
    private static final IntProperty VARIATION = IntProperty.of("variation", 0, 4);


    // Register loot table replacements
    public static void initialize()
    {
        replaceWithCondition(createStrippedLogsList(), StatePredicate.Builder.create().exactMatch(VARIATION, 0), Items.STICK);
    }

    private static void replaceWithCondition(List<Identifier> blocksToModify, StatePredicate.Builder statePredicate, Item itemToDrop)
    {
        LootTableEvents.REPLACE.register((key, original, source, registries) -> {
            if (source.isBuiltin()) {

                for (Identifier blockId : blocksToModify)
                {
                    Block blockToModify = Registries.BLOCK.get(blockId);

                    // Check if the block matches the loot table id
                    if (blockToModify.getLootTableKey().equals(key)) {
                        // Create new loot table with the desired drop and conditions
                        LootTable.Builder newTable = LootTable.builder();
                        newTable.pool(
                                createLootPoolWithStateCheck(
                                        blockToModify,
                                        statePredicate,
                                        itemToDrop));

                        // Return the modified loot table
                        return newTable.build();
                    }
                }
            }
            return null; // Return null if no replacement occurs
        });
    }

    // Create a loot pool with a block state predicate check
    public static LootPool.Builder createLootPoolWithStateCheck(Block blockToModify, StatePredicate.Builder predicate, Item itemToDrop) {
        LootCondition condition = BlockStatePropertyLootCondition.builder(blockToModify)
                .properties(predicate)
                .build();

        return LootPool.builder()
                .with(ItemEntry.builder(itemToDrop))
                .conditionally(condition);
    }

    // Create a list of stripped logs
    private static List<Identifier> createStrippedLogsList() {
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
