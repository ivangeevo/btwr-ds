package org.ivangeevo.btwr_ds;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.config.BTWRDSSettings;
import org.ivangeevo.btwr_ds.entity.ModEntities;
import org.ivangeevo.btwr_ds.render.entity.BeastEntityRenderer;
import org.ivangeevo.btwr_ds.render.entity.model.BeastEntityModel;

public class BTWRDSModClient implements ClientModInitializer
{

	public static final EntityModelLayer MODEL_BEAST_LAYER = new EntityModelLayer(Identifier.of(BTWRDSMod.MOD_ID, "beast"), "main");

	public BTWRDSSettings settings;
	private static BTWRDSModClient instance;

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

		EntityRendererRegistry.INSTANCE.register(ModEntities.BEAST, BeastEntityRenderer::new);


		EntityModelLayerRegistry.registerModelLayer(MODEL_BEAST_LAYER, BeastEntityModel::getTextureModelData);
	}

}
