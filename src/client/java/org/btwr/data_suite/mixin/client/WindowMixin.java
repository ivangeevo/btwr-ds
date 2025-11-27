package org.btwr.data_suite.mixin.client;

import org.btwr.data_suite.BTWRDSModClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.util.Window;

@Mixin(Window.class)
public abstract class WindowMixin {

    @Final @Shadow private long handle;

    // Set a custom title for the Minecraft window
    @Inject(method = "setTitle", at = @At("HEAD"), cancellable = true)
    private void onSetTitle(String title, CallbackInfo ci) {
        // Always force our title
        GLFW.glfwSetWindowTitle(this.handle, BTWRDSModClient.MC_WINDOW_TITLE);
        ci.cancel(); // prevent vanilla code from running
    }

}