package org.ivangeevo.btwr_ds.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Unique
    private boolean canUseFoodAgain = true;

    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    private void onInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.get(DataComponentTypes.FOOD) != null) {
            if (!canUseFoodAgain) {
                // block repeated food use
                cir.setReturnValue(ActionResult.PASS);
                cir.cancel();
            }
        }
    }

    @Inject(method = "interactItem", at = @At("RETURN"))
    private void afterInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.get(DataComponentTypes.FOOD) != null) {
            // block further use until released
            canUseFoodAgain = false;
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onClientTick(CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        // Reset when right-click is released
        if (!mc.options.useKey.isPressed()) {
            canUseFoodAgain = true;
        }
    }
}
