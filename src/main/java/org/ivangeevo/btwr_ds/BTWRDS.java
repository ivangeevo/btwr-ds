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
import org.ivangeevo.btwr_ds.event.ModLootTableModification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class BTWRDS implements ModInitializer
{
	public static final String MOD_ID = "btwr-ds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		ModLootTableModification.modifyLootPools();
		LOGGER.info("Hello Fabric world!");
	}
}
