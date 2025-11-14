package org.ivangeevo.btwr_ds;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.option.StickyKeyBinding;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.config.BTWRDSSettings;
import org.ivangeevo.btwr_ds.entity.ModEntities;
import org.ivangeevo.btwr_ds.packet.CrawlToggleC2SPacket;
import org.ivangeevo.btwr_ds.render.entity.BeastEntityRenderer;
import org.ivangeevo.btwr_ds.render.entity.model.BeastEntityModel;
import org.lwjgl.glfw.GLFW;

public class BTWRDSModClient implements ClientModInitializer
{

	public static final EntityModelLayer MODEL_BEAST_LAYER = new EntityModelLayer(Identifier.of(BTWRDSMod.MOD_ID, "beast"), "main");

	public BTWRDSSettings settings;
	private static BTWRDSModClient instance;

	private static final String RELEASE_VERSION_TYPE = "[Pre-Alpha]";
	public static final String MC_WINDOW_TITLE = "BTW: Remastered! " + RELEASE_VERSION_TYPE;

	private static KeyBinding CRAWL_KEY;

	private static final Text TOGGLE_KEY_TEXT = Text.translatable("options.key.toggle");
	private static final Text HOLD_KEY_TEXT = Text.translatable("options.key.hold");

	private final SimpleOption<Boolean> crawlToggled = new SimpleOption<>(
			"key.crawl", SimpleOption.emptyTooltip(), (optionText, value) -> value ? TOGGLE_KEY_TEXT : HOLD_KEY_TEXT, SimpleOption.BOOLEAN, false, value -> {
	});

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


		CRAWL_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.mod.crawl",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C,
				"key.categories.movement"
		));

		/**
		CRAWL_KEY = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding(
				"key.mod.crawl",
				GLFW.GLFW_KEY_C,
				KeyBinding.MOVEMENT_CATEGORY,
				this.crawlToggled::getValue

		));
		 **/

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (CRAWL_KEY.wasPressed()) {
				ClientPlayNetworking.send(new CrawlToggleC2SPacket());
				client.player.sendMessage(Text.literal("Crawl key was pressed!"), false);
			}
		});
	}

		// TODO: Disable the vanilla Tutorial pop ups for things like WASD, or craft planks TutorialStepHandlers, etc

}
