package org.ivangeevo.btwr_ds;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import org.ivangeevo.btwr_ds.attachment.MagneticPointAttachedData;
import org.ivangeevo.btwr_ds.attachment.ModAttachmentTypes;
import org.ivangeevo.btwr_ds.config.BTWRDSSettings;
import org.ivangeevo.btwr_ds.util.MagneticPointGlobalPos;
import org.jetbrains.annotations.Nullable;

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
		//PonderIndex.addPlugin(new BTWRPonderPlugin());
	}

}
