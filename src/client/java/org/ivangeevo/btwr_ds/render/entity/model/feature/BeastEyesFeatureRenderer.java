package org.ivangeevo.btwr_ds.render.entity.model.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;
import org.ivangeevo.btwr_ds.entity.entities.BeastEntity;
import org.ivangeevo.btwr_ds.render.entity.model.BeastEntityModel;

@Environment(EnvType.CLIENT)
public class BeastEyesFeatureRenderer<T extends BeastEntity, M extends BeastEntityModel<T>> extends EyesFeatureRenderer<T, M> {

	private static final RenderLayer SKIN = RenderLayer.getEyes(Identifier.of(BTWRDSMod.MOD_ID, "textures/entity/beast_eyes.png"));

	public BeastEyesFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
		super(featureRendererContext);
	}

	@Override
	public RenderLayer getEyesTexture() {
		return SKIN;
	}
}
