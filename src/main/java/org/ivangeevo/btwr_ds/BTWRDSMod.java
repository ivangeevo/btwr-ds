package org.ivangeevo.btwr_ds;

import btwr.btwr_sl.BTWRSLMod;
import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.ivangeevo.btwr_ds.config.BTWRDSSettings;
import org.ivangeevo.btwr_ds.attachment.ModAttachments;
import org.ivangeevo.btwr_ds.data.ModDataAttachments;
import org.ivangeevo.btwr_ds.effect.ModStatusEffects;
import org.ivangeevo.btwr_ds.entity.ModEntities;
import org.ivangeevo.btwr_ds.event.ModLootTableEvents;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.ivangeevo.btwr_ds.event.ItemCountModificationEvents;
import org.ivangeevo.btwr_ds.event.FoodComponentModifierEvents;
import org.ivangeevo.btwr_ds.loot.function.ModLootFunctions;
import org.ivangeevo.btwr_ds.packet.CrawlToggleC2SPacket;
import org.ivangeevo.btwr_ds.recipe.BTWRDSRecipes;
import org.ivangeevo.btwr_ds.util.CrawlHandler;
import org.ivangeevo.btwr_ds.util.PlanterFertilizer;
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

		BTWRDS_Items.registerAndAddToGroups();
		BTWRDSRecipes.register();
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

		PayloadTypeRegistry.playC2S().register(CrawlToggleC2SPacket.ID, CrawlToggleC2SPacket.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(CrawlToggleC2SPacket.ID, ((payload, context) -> {
			context.server().execute(() -> {
				CrawlHandler.toggleCrawl(context.player());
			});
		}));


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


