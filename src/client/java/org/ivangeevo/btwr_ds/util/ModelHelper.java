package org.ivangeevo.btwr_ds.util;

import net.minecraft.client.model.*;
import net.minecraft.util.math.Vec3d;

/**
 * Utility for building readable and composable entity models.
 * Provides shortcuts for common shapes, transformations, and structure.
 */
public final class ModelHelper {

	private ModelHelper() {}

	/* -------------------------------------------------------------
	 *  Cuboid builders
	 * ------------------------------------------------------------- */

	public static ModelPartBuilder box(float x, float y, float z, float w, float h, float d) {
		return ModelPartBuilder.create().cuboid(x, y, z, w, h, d);
	}

	public static ModelPartBuilder box(float x, float y, float z, float w, float h, float d, Dilation dilation) {
		return ModelPartBuilder.create().cuboid(x, y, z, w, h, d, dilation);
	}

	/** Centered cuboid around pivot, instead of starting from corner. */
	public static ModelPartBuilder centeredBox(float w, float h, float d, Dilation dilation) {
		float x = -w / 2f;
		float y = -h / 2f;
		float z = -d / 2f;
		return ModelPartBuilder.create().cuboid(x, y, z, w, h, d, dilation);
	}

	/* -------------------------------------------------------------
	 *  Transform helpers
	 * ------------------------------------------------------------- */

	public static ModelTransform pivot(float x, float y, float z) {
		return ModelTransform.pivot(x, y, z);
	}

	public static ModelTransform of(float x, float y, float z, float pitch, float yaw, float roll) {
		return ModelTransform.of(x, y, z, pitch, yaw, roll);
	}

	public static ModelTransform rotated(Vec3d pivot, Vec3d rotation) {
		return ModelTransform.of((float)pivot.getX(), (float)pivot.getY(), (float)pivot.getZ(),
				(float)rotation.getX(), (float)rotation.getY(), (float)rotation.getZ());
	}

	/* -------------------------------------------------------------
	 *  Common shapes
	 * ------------------------------------------------------------- */

	public static ModelPartBuilder leg(float width, float height, float depth, Dilation dilation) {
		return centeredBox(width, height, depth, dilation)
				.uv(0, 18); // Default leg texture UV slot
	}

	public static ModelPartBuilder tail(float length, float thickness, Dilation dilation) {
		return centeredBox(thickness, length, thickness, dilation)
				.uv(9, 18);
	}

	public static ModelPartBuilder head(float width, float height, float depth, Dilation dilation) {
		return centeredBox(width, height, depth, dilation)
				.uv(0, 0);
	}

	public static ModelPartBuilder body(float width, float height, float depth, Dilation dilation) {
		return centeredBox(width, height, depth, dilation)
				.uv(18, 14);
	}

	/* -------------------------------------------------------------
	 *  Structural helpers
	 * ------------------------------------------------------------- */

	/** Creates a new model root (used in getModelData). */
	public static ModelPartData root() {
		return new ModelData().getRoot();
	}

	/** Adds a named child with builder + transform. */
	public static ModelPartData addChild(ModelPartData parent, String name,
										 ModelPartBuilder builder, ModelTransform transform) {
		return parent.addChild(name, builder, transform);
	}

	/** Shortcut for static part (no transform). */
	public static ModelPartData addStaticChild(ModelPartData parent, String name,
											   ModelPartBuilder builder) {
		return parent.addChild(name, builder, ModelTransform.NONE);
	}
}
