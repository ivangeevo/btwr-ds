package org.ivangeevo.btwr_ds.mixin.client;

import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(EntityModel.class)
public abstract class MixinEntityModel<T extends Entity> {
    /**
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
        if (this instanceof BipedEntityModel) {
            // Get the entity somehow (may need to add shadowed field or pass it)
            // For example, get saturation from entity and adjust matrices scale
            float saturation = getEntitySaturation(); // you need a way to get this
            
            matrices.pushPose();
            matrices.scale(1.0f + saturation, 1.0f + saturation, 1.0f + saturation);
            // call the original render method or allow it to proceed
            matrices.popPose();
        }
    }
    **/
}
