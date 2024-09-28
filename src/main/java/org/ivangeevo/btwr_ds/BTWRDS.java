package org.ivangeevo.btwr_ds;

import net.fabricmc.api.ModInitializer;
import org.ivangeevo.btwr_ds.event.ModLootTableReplacement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRDS implements ModInitializer
{
	public static final String MOD_ID = "btwr-ds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		//ModLootTableModification.modifyLootPools();
		ModLootTableReplacement.initialize();

		LOGGER.info("Hello Fabric world!");
	}
}
