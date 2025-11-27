package org.btwr.data_suite;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.config.BTWRDSSettings;
import org.btwr.data_suite.entity.ModEntities;
import org.btwr.data_suite.render.entity.BeastEntityRenderer;
import org.btwr.data_suite.render.entity.model.BeastEntityModel;

// TODO: Disable the vanilla Tutorial pop ups for things like WASD, or craft planks TutorialStepHandlers, etc
public class BTWRDSModClient implements ClientModInitializer {

	public static final EntityModelLayer MODEL_BEAST_LAYER = new EntityModelLayer(Identifier.of(BTWRDSMod.MOD_ID, "beast"), "main");

	public BTWRDSSettings settings;
	private static BTWRDSModClient instance;

	private static final String RELEASE_VERSION_TYPE = "[Pre-Alpha]";
	public static final String MC_WINDOW_TITLE = "BTW: Remastered! " + RELEASE_VERSION_TYPE;

	/**
	 * Getter for current BTWRDSModClient instance
	 */
	public static BTWRDSModClient getInstance() {
		return instance;
	}

	/**
	 * Getter for current BTWRDSSettings instance
	 */
	public static BTWRDSSettings getSettings() {
		return getInstance().settings;
	}

	@Override
	public void onInitializeClient() {

		//PonderIndex.addPlugin(new BTWRPonderPlugin());

		EntityRendererRegistry.register(ModEntities.BEAST, BeastEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_BEAST_LAYER, BeastEntityModel::getTextureModelData);
	}

}