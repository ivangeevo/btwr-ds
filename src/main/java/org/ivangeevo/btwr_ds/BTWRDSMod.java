package org.ivangeevo.btwr_ds;

import btwr.btwr_sl.lib.util.BlockReplacementRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.processor.*;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import org.ivangeevo.btwr_ds.event.ModLootTableEvents;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.ivangeevo.btwr_ds.recipe.BTWRDSRecipes;
import org.ivangeevo.btwr_ds.util.WorldGenBlockReplacements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BTWRDSMod implements ModInitializer {
	public static final String MOD_ID = "btwr_ds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	private static void addStructureProcessor(StructureProcessorList processorList, RuleStructureProcessor processor) {
		ArrayList<StructureProcessor> list = new ArrayList<>(processorList.getList());
		list.add(processor);
		processorList.list = list;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing BTWR: Datapack Suite!");
		ModLootTableEvents.initialize();

		BTWRDS_Items.registerAndAddToGroups();

		BTWRDSRecipes.init();

		WorldGenBlockReplacements.register();

		/**
		//tried removing crafting tables from world gen
		//ReplaceCraftingTableProcessor.register();


		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			Registry<StructureProcessorList> processorLists = server.getRegistryManager().get(RegistryKeys.PROCESSOR_LIST);
			if (processorLists != null) {
				addStructureProcessor(processorLists.getOrThrow(StructureProcessorLists.HOUSING),
						new RuleStructureProcessor(
								List.of(new StructureProcessorRule(
										new RandomBlockMatchRuleTest(Blocks.CRAFTING_TABLE, 1F),
										AlwaysTrueRuleTest.INSTANCE,
										Blocks.DIAMOND_BLOCK.getDefaultState()
								)
						))
				);
			}
		});
		 **/
	}


}
