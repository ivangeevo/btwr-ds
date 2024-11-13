package org.ivangeevo.btwr_ds;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.ivangeevo.btwr_ds.datagen.DS_BlockLootTableProvider;
import org.ivangeevo.btwr_ds.datagen.DS_BlockTagProvider;
import org.ivangeevo.btwr_ds.datagen.DS_ItemTagProvider;
import org.ivangeevo.btwr_ds.datagen.DS_RecipeProvider;

public class BTWRDSDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(DS_RecipeProvider::new);
		pack.addProvider(DS_BlockLootTableProvider::new);
		pack.addProvider(DS_BlockTagProvider::new);
		pack.addProvider(DS_ItemTagProvider::new);



	}
}
