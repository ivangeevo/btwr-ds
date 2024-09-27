package org.ivangeevo.btwr_ds.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ModLootTableModification
{

    private final List<Identifier> strippedLogsList;

    private static final String ST = "sturdy_trees";

    public ModLootTableModification(List<Identifier> strippedLogsList) {
        this.strippedLogsList = strippedLogsList;
    }

    public static void modifyLootPools()
    {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) ->
        {
            // List of all stripped logs to modify
            List<Identifier> strippedLogs = Arrays.asList(
                    Identifier.of(ST, "log_oak_stripped"),
                    Identifier.of(ST, "log_spruce_stripped"),
                    Identifier.of(ST, "log_birch_stripped"),
                    Identifier.of(ST, "log_jungle_stripped"),
                    Identifier.of(ST, "log_acacia_stripped"),
                    Identifier.of(ST, "log_dark_oak_stripped"),
                    Identifier.of(ST, "log_mangrove_stripped"),
                    Identifier.of(ST, "log_cherry_stripped")
            );

            // Loop through each stripped log
            for (Identifier strippedLogId : strippedLogs) {
                Block strippedLog = Registries.BLOCK.get(strippedLogId);

                // Check if the current loot table matches the stripped log's loot table and is built-in
                if (strippedLog.getLootTableKey() == key && source.isBuiltin()) {

                    // Define a Consumer that modifies the LootPool.Builder
                    Consumer<LootPool.Builder> poolConsumer = poolBuilder -> {
                        poolBuilder.with(ItemEntry.builder(Items.STICK)
                                .conditionally(SurvivesExplosionLootCondition.builder()));
                    };

                    // Modify the existing loot pools
                    tableBuilder.modifyPools(poolConsumer);
                }
            }
        });
    }

}
