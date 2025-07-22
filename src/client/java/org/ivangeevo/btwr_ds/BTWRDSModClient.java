package org.ivangeevo.btwr_ds;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderContext;
import org.ivangeevo.btwr_ds.config.BTWRDSSettings;

public class BTWRDSModClient implements ClientModInitializer
{

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


	}


}
