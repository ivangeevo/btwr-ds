package org.ivangeevo.btwr_ds;

import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.ivangeevo.btwr_ds.config.BTWRDSSettings;
import org.ivangeevo.btwr_ds.ponder.BTWRPonderPlugin;

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

		/**
		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, lines) -> {
			if (isNetheriteToolOrArmor(itemStack.getItem())) {
				// Insert after the first line (which is the item name)
				int insertIndex = 1;
				Text soulforged = Text.literal("[Soulforged]")
						.formatted(Formatting.GOLD, Formatting.ITALIC);
				lines.add(insertIndex, soulforged);
			}
		});
		 **/
	}

	private static boolean isNetheriteToolOrArmor(Item item) {
		// Example: check by item class or by tag
		// Suppose you have a tag “mod:netherite_tools_armor”
		return item.getDefaultStack().isOf(Items.NETHERITE_PICKAXE);
	}


}
