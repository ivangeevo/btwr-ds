package org.ivangeevo.btwr_ds;

import btwr.btwr_sl.BTWRSLMod;
import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import org.ivangeevo.btwr_ds.config.BTWRDSSettings;
import org.ivangeevo.btwr_ds.event.ModLootTableEvents;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.ivangeevo.btwr_ds.item.ItemCountModification;
import org.ivangeevo.btwr_ds.item.component.FoodComponentModifier;
import org.ivangeevo.btwr_ds.recipe.BTWRDSRecipes;
import org.ivangeevo.btwr_ds.util.WorldGenBlockReplacements;
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
		ModLootTableEvents.initialize();

		BTWRDS_Items.registerAndAddToGroups();
		BTWRDSRecipes.init();
		WorldGenBlockReplacements.register();
		FoodComponentModifier.register();

		ItemCountModification.init();

		//ServerTickEvents.END_SERVER_TICK.register(this::onServerTick);

		// Registers all tilling based interactions/modifications
		//BlockTillingManager.registerNormalTillable();
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
				BTWRDSMod.LOGGER.warn("Could not load BTWRDS settings: {}", e.getLocalizedMessage());
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
			BTWRSLMod.LOGGER.warn("Could not save BTWRDS settings: {}", e.getLocalizedMessage());
		}
	}

}


