package org.ivangeevo.btwr_ds;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.registry.Registries;
import org.ivangeevo.btwr_ds.event.ModLootTableEvents;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.ivangeevo.btwr_ds.item.component.FoodComponentModifier;
import org.ivangeevo.btwr_ds.recipe.BTWRDSRecipes;
import org.ivangeevo.btwr_ds.util.WorldGenBlockReplacements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BTWRDSMod implements ModInitializer {
	public static final String MOD_ID = "btwr_ds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing BTWR: Datapack Suite!");
		ModLootTableEvents.initialize();

		BTWRDS_Items.registerAndAddToGroups();

		BTWRDSRecipes.init();

		WorldGenBlockReplacements.register();

		DefaultItemComponentEvents.MODIFY.register(context -> {
			context.modify(item -> item.getComponents()
							.contains(DataComponentTypes.FOOD), (builder, item) -> {
				System.out.println("Modifying: " + Registries.ITEM.getId(item));
				builder.put(DataComponentTypes.FOOD, new FoodComponent.Builder().nutrition(0).saturationModifier(0).build());
			});
		});

		FoodComponentModifier.register();

	}



}


