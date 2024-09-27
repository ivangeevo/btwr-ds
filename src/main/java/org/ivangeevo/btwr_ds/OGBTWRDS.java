package org.ivangeevo.btwr_ds;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class OGBTWRDS implements ModInitializer
{
	public static final String MOD_ID = "btwr-ds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			// List of all stripped logs to modify
			List<Identifier> strippedLogs = Arrays.asList(
					Identifier.of("sturdy_trees", "log_oak_stripped"),
					Identifier.of("sturdy_trees", "log_spruce_stripped"),
					Identifier.of("sturdy_trees", "log_birch_stripped"),
					Identifier.of("sturdy_trees", "log_jungle_stripped"),
					Identifier.of("sturdy_trees", "log_acacia_stripped"),
					Identifier.of("sturdy_trees", "log_dark_oak_stripped"),
					Identifier.of("sturdy_trees", "log_mangrove_stripped"),
					Identifier.of("sturdy_trees", "log_cherry_stripped")
			);

			// Loop through each stripped log
			for (Identifier strippedLogId : strippedLogs) {
				Block strippedLog = Registries.BLOCK.get(strippedLogId);

				// Check if the current loot table matches the stripped log's loot table and is built-in
				if (strippedLog.getLootTableKey() == key && source.isBuiltin()) {
					// Clear the original loot table
					tableBuilder.pools(List.of());

					// Create a new loot pool that drops sticks
					LootPool pool = LootPool.builder()
							.with(ItemEntry.builder(Items.STICK))
							.conditionally(SurvivesExplosionLootCondition.builder())
							.build();

					// Add the new loot pool to the loot table
					tableBuilder.pool(pool);
				}
			}
		});

		LOGGER.info("Hello Fabric world!");
	}
}
