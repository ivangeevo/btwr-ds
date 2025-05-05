package org.ivangeevo.btwr_ds.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.ivangeevo.btwr_ds.datagen.*;
import org.ivangeevo.btwr_ds.datagen.recipe.CombinedRecipeProvider;

public class BTWRDSDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		// Combined generator class for all mods that get recipe modifications
		pack.addProvider(CombinedRecipeProvider::new);

		pack.addProvider(DS_BlockLootTableProvider::new);
		pack.addProvider(DS_BlockTagProvider::new);
		pack.addProvider(DS_ItemTagProvider::new);
		pack.addProvider(DS_LangGenerator::new);
		pack.addProvider(DS_ModelProvider::new);
	}
}
