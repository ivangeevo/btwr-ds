package org.btwr.data_suite;

import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import org.btwr.data_suite.config.BTWRDSSettings;
import org.btwr.data_suite.data.ModDataAttachments;
import org.btwr.data_suite.effect.ModStatusEffects;
import org.btwr.data_suite.entity.ModEntityTypes;
import org.btwr.data_suite.event.ModEvents;
import org.btwr.data_suite.item.BTWRDS_Items;
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

		BTWRDS_Items.register();
		WorldGenBlockReplacements.register();
		ModEntityTypes.register();
		ModDataAttachments.register();
		ModEvents.register();
		// TODO: Not tested enough
		//SpawnChunksLoader.register();
		PlanterFertilizer.register();
		ModStatusEffects.register();
		ModLootFunctions.register();

		//BTWRPotions.register();

		//BlockSpeedRegistry.init();

		// Registers all tilling based interactions/modifications
		//BlockTillingManager.registerNormalTillable();


	}

	// TODO: Obsolete; No existing config options atm. Replace with the BTWR:SL config system if doing so
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


