package org.btwr.data_suite.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.btwr.data_suite.BTWRDSMod;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ModTutorialToast implements Toast {

	private static final Identifier TEXTURE = Identifier.of(BTWRDSMod.MOD_ID, "toast/tutorial");
	public static final int PROGRESS_BAR_WIDTH = 154;
	public static final int PROGRESS_BAR_HEIGHT = 1;
	public static final int PROGRESS_BAR_X = 3;
	public static final int PROGRESS_BAR_Y = 28;
	private final ModTutorialToast.Type type;
	private final Text title;
	@Nullable
	private final Text description;
	private Toast.Visibility visibility = Toast.Visibility.SHOW;
	private long lastTime;
	private float lastProgress;
	private float progress;
	private final boolean hasProgressBar;

	public ModTutorialToast(ModTutorialToast.Type type, Text title, @Nullable Text description, boolean hasProgressBar) {
		this.type = type;
		this.title = title;
		this.description = description;
		this.hasProgressBar = hasProgressBar;
	}

	@Override
	public Toast.Visibility draw(DrawContext context, ToastManager manager, long startTime) {
		context.drawGuiTexture(TEXTURE, 0, 0, this.getWidth(), this.getHeight());
		this.type.drawIcon(context, 6, 6);
		if (this.description == null) {
			context.drawText(manager.getClient().textRenderer, this.title, 30, 12, -11534256, false);
		} else {
			context.drawText(manager.getClient().textRenderer, this.title, 30, 7, -11534256, false);
			context.drawText(manager.getClient().textRenderer, this.description, 30, 18, Colors.BLACK, false);
		}

		if (this.hasProgressBar) {
			context.fill(3, 28, 157, 29, -1);
			float f = MathHelper.clampedLerp(this.lastProgress, this.progress, (float)(startTime - this.lastTime) / 100.0F);
			int i;
			if (this.progress >= this.lastProgress) {
				i = -16755456;
			} else {
				i = -11206656;
			}

			context.fill(3, 28, (int)(3.0F + 154.0F * f), 29, i);
			this.lastProgress = f;
			this.lastTime = startTime;
		}

		return this.visibility;
	}

	public void hide() {
		this.visibility = Toast.Visibility.HIDE;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	@Environment(EnvType.CLIENT)
	public static enum Type {

		// BTWR custom toasts
		MOVEMENT_KEYS(Identifier.of(BTWRDSMod.MOD_ID,"toast/movement_keys")),
		MOUSE(Identifier.of(BTWRDSMod.MOD_ID,"toast/mouse")),
		TREE(Identifier.of(BTWRDSMod.MOD_ID,"toast/tree")),
		RECIPE_BOOK(Identifier.of(BTWRDSMod.MOD_ID,"toast/recipe_book")),
		WOODEN_PLANKS(Identifier.of(BTWRDSMod.MOD_ID,"toast/wooden_planks")),
		SOCIAL_INTERACTIONS(Identifier.of(BTWRDSMod.MOD_ID,"toast/social_interactions")),
		RIGHT_CLICK(Identifier.of(BTWRDSMod.MOD_ID,"toast/right_click"))


		// Vanilla ones for reference
		/**
		MOVEMENT_KEYS(Identifier.ofVanilla("toast/movement_keys")),
		MOUSE(Identifier.ofVanilla("toast/mouse")),
		TREE(Identifier.ofVanilla("toast/tree")),
		RECIPE_BOOK(Identifier.ofVanilla("toast/recipe_book")),
		WOODEN_PLANKS(Identifier.ofVanilla("toast/wooden_planks")),
		SOCIAL_INTERACTIONS(Identifier.ofVanilla("toast/social_interactions")),
		RIGHT_CLICK(Identifier.ofVanilla("toast/right_click"))
		**/

		;

		private final Identifier texture;

		private Type(final Identifier texture) {
			this.texture = texture;
		}

		public void drawIcon(DrawContext context, int x, int y) {
			RenderSystem.enableBlend();
			context.drawGuiTexture(this.texture, x, y, 20, 20);
		}
	}

}