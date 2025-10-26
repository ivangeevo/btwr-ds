package org.ivangeevo.btwr_ds.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper.Argb;
import org.ivangeevo.btwr_ds.BTWRDSMod;
import org.ivangeevo.btwr_ds.entity.entities.BeastEntity;
import org.ivangeevo.btwr_ds.render.entity.model.BeastEntityModel;

@Environment(EnvType.CLIENT)
public class BeastEntityRenderer extends MobEntityRenderer<BeastEntity, BeastEntityModel<BeastEntity>> {

	private static final float SHADOW_SIZE = 0.5F;
	private static final Identifier BEAST_TEXTURE = Identifier.of(BTWRDSMod.MOD_ID, "textures/entity/beast.png");

	public BeastEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new BeastEntityModel<>(context.getPart(EntityModelLayers.WOLF)), SHADOW_SIZE);
	}

	@Override
	public Identifier getTexture(BeastEntity entity) {
		return BEAST_TEXTURE;
	}

	protected float getAnimationProgress(BeastEntity beastEntity, float f) {
		return beastEntity.getTailRotation();
	}

	public void render(BeastEntity beastEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		// TODO: Should we make the beast have wet fur and shake abilities like the wolf?
		/**
		if (beastEntity.isFurWet()) {
			float h = beastEntity.getFurWetBrightnessMultiplier(g);
			this.model.setColorMultiplier(Argb.fromFloats(1.0F, h, h, h));
		}
		 **/

		super.render(beastEntity, f, g, matrixStack, vertexConsumerProvider, i);
		/**
		if (beastEntity.isFurWet()) {
			this.model.setColorMultiplier(-1);
		}
		 **/
	}

}
