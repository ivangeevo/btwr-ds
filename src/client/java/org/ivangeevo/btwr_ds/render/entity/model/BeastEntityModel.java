package org.ivangeevo.btwr_ds.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.TintableAnimalModel;
import net.minecraft.util.math.MathHelper;
import org.ivangeevo.btwr_ds.entity.entities.BeastEntity;
import org.ivangeevo.btwr_ds.util.ModelHelper;

@Environment(EnvType.CLIENT)
public class BeastEntityModel<T extends BeastEntity> extends TintableAnimalModel<T> {

	private final ModelPart head;
	private final ModelPart torso;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;

	private final ModelPart tail;
	private final ModelPart neck;

	public BeastEntityModel(ModelPart root) {
		this.head = root.getChild(EntityModelPartNames.HEAD);
		this.torso = root.getChild(EntityModelPartNames.BODY);
		this.neck = root.getChild("upper_body");
		this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
		this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
		this.tail = root.getChild(EntityModelPartNames.TAIL);
	}

	public static ModelData getModelData(Dilation d) {
		ModelPartData root = ModelHelper.root();

		var head = ModelHelper.addChild(root, EntityModelPartNames.HEAD,
				ModelPartBuilder.create(), ModelHelper.pivot(-1.0F, 13.5F, -7.0F));

		ModelHelper.addStaticChild(head, "real_head",
				ModelHelper.head(6, 6, 4, d)
						.uv(16, 14).cuboid(-2, -5, 0, 2, 2, 1, d)
						.uv(16, 14).cuboid(2, -5, 0, 2, 2, 1, d)
						.uv(0, 10).cuboid(-0.5F, 0, -5, 3, 3, 4, d)
		);

		ModelHelper.addChild(root, EntityModelPartNames.BODY,
				ModelHelper.body(6, 9, 6, d),
				ModelHelper.of(0, 14, 2, (float) Math.PI / 2, 0, 0));

		ModelHelper.addChild(root, "upper_body",
				ModelPartBuilder.create().uv(21, 0).cuboid(-3, -3, -3, 8, 6, 7, d),
				ModelHelper.of(-1, 14, -3, (float) Math.PI / 2, 0, 0));

		ModelHelper.addChild(root, EntityModelPartNames.RIGHT_HIND_LEG,
				ModelHelper.leg(2, 8, 2, d),
				ModelHelper.pivot(-2.5F, 16, 7));

		ModelHelper.addChild(root, EntityModelPartNames.LEFT_HIND_LEG,
				ModelHelper.leg(2, 8, 2, d),
				ModelHelper.pivot(0.5F, 16, 7));

		ModelHelper.addChild(root, EntityModelPartNames.RIGHT_FRONT_LEG,
				ModelHelper.leg(2, 8, 2, d),
				ModelHelper.pivot(-2.5F, 16, -4));

		ModelHelper.addChild(root, EntityModelPartNames.LEFT_FRONT_LEG,
				ModelHelper.leg(2, 8, 2, d),
				ModelHelper.pivot(0.5F, 16, -4));

		var tail = ModelHelper.addChild(root, EntityModelPartNames.TAIL,
				ModelPartBuilder.create(),
				ModelHelper.of(-1, 12, 8, (float) (Math.PI / 5), 0, 0));

		ModelHelper.addStaticChild(tail, "real_tail", ModelHelper.tail(8, 2, d));

		return new ModelData(); // wrap root if needed elsewhere
	}


	/**
	public static ModelData getModelData(Dilation d) {
		ModelPartData root = ModelHelper.root();

		var head = ModelHelper.addChild(root, EntityModelPartNames.HEAD,
				ModelPartBuilder.create(), ModelHelper.pivot(-1.0F, 13.5F, -7.0F));

		ModelHelper.addStaticChild(head, "real_head",
				ModelHelper.head(6, 6, 4, d)
						.uv(16, 14).cuboid(-2, -5, 0, 2, 2, 1, d)
						.uv(16, 14).cuboid(2, -5, 0, 2, 2, 1, d)
						.uv(0, 10).cuboid(-0.5F, 0, -5, 3, 3, 4, d)
		);

		ModelHelper.addChild(root, EntityModelPartNames.BODY,
				ModelHelper.body(6, 9, 6, d),
				ModelHelper.of(0, 14, 2, (float) Math.PI / 2, 0, 0));

		ModelHelper.addChild(root, "upper_body",
				ModelPartBuilder.create().uv(21, 0).cuboid(-3, -3, -3, 8, 6, 7, d),
				ModelHelper.of(-1, 14, -3, (float) Math.PI / 2, 0, 0));

		ModelHelper.addChild(root, EntityModelPartNames.RIGHT_HIND_LEG,
				ModelHelper.leg(2, 8, 2, d),
				ModelHelper.pivot(-2.5F, 16, 7));

		ModelHelper.addChild(root, EntityModelPartNames.LEFT_HIND_LEG,
				ModelHelper.leg(2, 8, 2, d),
				ModelHelper.pivot(0.5F, 16, 7));

		ModelHelper.addChild(root, EntityModelPartNames.RIGHT_FRONT_LEG,
				ModelHelper.leg(2, 8, 2, d),
				ModelHelper.pivot(-2.5F, 16, -4));

		ModelHelper.addChild(root, EntityModelPartNames.LEFT_FRONT_LEG,
				ModelHelper.leg(2, 8, 2, d),
				ModelHelper.pivot(0.5F, 16, -4));

		var tail = ModelHelper.addChild(root, EntityModelPartNames.TAIL,
				ModelPartBuilder.create(),
				ModelHelper.of(-1, 12, 8, (float) (Math.PI / 5), 0, 0));

		ModelHelper.addStaticChild(tail, "real_tail", ModelHelper.tail(8, 2, d));

		return new ModelData(); // wrap root if needed elsewhere
	}
	**/


	public static TexturedModelData getTextureModelData() {
		return TexturedModelData.of(getModelData(Dilation.NONE), 64, 64);
	}

	@Override
    public Iterable<ModelPart> getHeadParts() {
		return ImmutableList.<ModelPart>of(this.head);
	}

	@Override
    public Iterable<ModelPart> getBodyParts() {
		return ImmutableList.<ModelPart>of(this.torso, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.neck);
	}

	public void animateModel(T wolfEntity, float f, float g, float h) {
		this.tail.yaw = MathHelper.cos(f * 0.6662F) * 1.4F * g;

		this.torso.setPivot(0.0F, 14.0F, 2.0F);
		this.torso.pitch = (float) (Math.PI / 2);
		this.neck.setPivot(-1.0F, 14.0F, -3.0F);
		this.neck.pitch = this.torso.pitch;
		this.tail.setPivot(-1.0F, 12.0F, 8.0F);
		this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
		this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
		this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
		this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
		this.rightHindLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
		this.leftHindLeg.pitch = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * g;
		this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * g;
		this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;

	}

	public void setAngles(T wolfEntity, float f, float g, float h, float i, float j) {
		this.head.pitch = j * (float) (Math.PI / 180.0);
		this.head.yaw = i * (float) (Math.PI / 180.0);
		this.tail.pitch = h;
	}


}
