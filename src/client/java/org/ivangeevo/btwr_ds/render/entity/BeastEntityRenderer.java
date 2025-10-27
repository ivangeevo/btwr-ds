package org.ivangeevo.btwr_ds.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EndermanEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper.Argb;
import org.ivangeevo.btwr_ds.BTWRDSMod;
import org.ivangeevo.btwr_ds.entity.entities.BeastEntity;
import org.ivangeevo.btwr_ds.render.entity.model.BeastEntityModel;
import org.ivangeevo.btwr_ds.render.entity.model.feature.BeastEyesFeatureRenderer;

@Environment(EnvType.CLIENT)
public class BeastEntityRenderer extends MobEntityRenderer<BeastEntity, BeastEntityModel<BeastEntity>> {

	private static final float SHADOW_SIZE = 0.5F;

	private static final Identifier BEAST_TEXTURE = Identifier.of(BTWRDSMod.MOD_ID, "textures/entity/beast.png");

	public BeastEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new BeastEntityModel<>(context.getPart(EntityModelLayers.WOLF)), SHADOW_SIZE);
		this.addFeature(new BeastEyesFeatureRenderer<>(this));
	}

	@Override
	public Identifier getTexture(BeastEntity entity) {
		return BEAST_TEXTURE;
	}

	@Override
	protected float getAnimationProgress(BeastEntity beastEntity, float f) {
		return beastEntity.getTailRotation();
	}

	@Override
	public void render(BeastEntity entity, float yaw, float tickDelta, MatrixStack matrices,
					   VertexConsumerProvider vertexConsumers, int light)
	{
		matrices.push();
		matrices.scale(1.5F, 1.5F, 1.5F); // 1.5× size
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
		matrices.pop();
	}



}
