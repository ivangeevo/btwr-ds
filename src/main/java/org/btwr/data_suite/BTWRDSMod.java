package org.btwr.data_suite;

import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import org.btwr.data_suite.config.BTWRDSSettings;
import org.btwr.data_suite.attachment.ModAttachments;
import org.btwr.data_suite.data.ModDataAttachments;
import org.btwr.data_suite.effect.ModStatusEffects;
import org.btwr.data_suite.entity.ModEntities;
import org.btwr.data_suite.event.ModLootTableEvents;
import org.btwr.data_suite.item.BTWRDS_Items;
import org.btwr.data_suite.event.ItemCountModificationEvents;
import org.btwr.data_suite.event.FoodComponentModifierEvents;
import org.btwr.data_suite.loot.function.ModLootFunctions;
import org.btwr.data_suite.util.PlanterFertilizer;
import org.btwr.data_suite.util.WorldGenBlockReplacements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BTWRDSMod implements ModInitializer {

	public static final String MOD_ID = "btwr_ds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public BTWRDSSettings settings;
	private static BTWRDSMod instance;

	/**
	 * Getter for current BTWRDSModClient instance
	 */
	public static BTWRDSMod getInstance() {
		return instance;
	}


	@Override
	public void onInitialize() {
		LOGGER.info("Initializing BTWR: Datapack Suite!");
		this.loadSettings();
		instance = this;

		BTWRDS_Items.registerAndAddToGroups();
		WorldGenBlockReplacements.register();
		ModEntities.register();
		ModAttachments.register();
		FoodComponentModifierEvents.register();
		ModLootTableEvents.register();
		ItemCountModificationEvents.register();
		//SpawnChunksLoader.register();
		PlanterFertilizer.register();
		ModStatusEffects.register();
		ModLootFunctions.register();

		ModDataAttachments.register();

		//BlockSpeedRegistry.init();

		//ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);

		// Registers all tilling based interactions/modifications
		//BlockTillingManager.registerNormalTillable();

		//ServerChunkGenerateEvents.createChunkReplaceEventGlobally(Blocks.GRASS_BLOCK, Blocks.RED_STAINED_GLASS);
	}

	public void loadSettings() {
		File file = new File("./config/btwr/btwr_ds_common.json");
		Gson gson = new Gson();
		if (file.exists()) {
			try {
				FileReader fileReader = new FileReader(file);
				settings = gson.fromJson(fileReader, BTWRDSSettings.class);
				fileReader.close();
			} catch (IOException e) {
				LOGGER.warn("Could not load BTWRDS settings: {}", e.getLocalizedMessage());
			}
		} else {
			settings = new BTWRDSSettings();
		}
	}

	public void saveSettings() {
		Gson gson = new Gson();
		File file = new File("./config/btwr/btwr_ds_common.json");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(gson.toJson(settings));
			fileWriter.close();
		} catch (IOException e) {
			LOGGER.warn("Could not save BTWRDS settings: {}", e.getLocalizedMessage());
		}
	}

}


