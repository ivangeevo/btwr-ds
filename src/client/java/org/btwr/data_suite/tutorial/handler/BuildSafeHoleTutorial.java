package org.btwr.data_suite.tutorial.handler;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.tutorial.TutorialManager;
import net.minecraft.client.tutorial.TutorialStep;
import net.minecraft.client.tutorial.TutorialStepHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class BuildSafeHoleTutorial implements TutorialStepHandler {

    private static final int DELAY_TICKS = 20 * 60; // e.g., 60 seconds
    private final TutorialManager manager;
    private TutorialToast toast;
    private int ticks;

    public BuildSafeHoleTutorial(TutorialManager manager) {
        this.manager = manager;
    }

    @Override
    public void tick() {
        ticks++;
        if (!manager.isInSurvival()) {
            manager.setStep(TutorialStep.NONE);
            return;
        }

        if (ticks == 1) {
            // initial check: if the safe hole is already built
            ClientPlayerEntity player = manager.getClient().player;
            if (player != null && checkSafeHoleBuilt(player)) {
                manager.setStep(TutorialStep.NONE);
                return;
            }
        }

        if (ticks >= DELAY_TICKS && toast == null) {
            toast = new TutorialToast(
                TutorialToast.Type.WOODEN_PLANKS,
                Text.literal("Build a safe shelter"),
                Text.literal("Dig out a small enclosure or shelter before night falls."),
                false
            );
            manager.getClient().getToastManager().add(toast);
        }
    }

    @Override
    public void destroy() {
        if (toast != null) {
            toast.hide();
            toast = null;
        }
    }

    @Override
    public void onSlotUpdate(ItemStack stack) {
        if (checkSafeHoleBuilt(manager.getClient().player)) {
            // advance to next step or finish
            //manager.setStep(ModTutorialStep.NONE);
        }
    }

    private boolean checkSafeHoleBuilt(ClientPlayerEntity player) {
        // Example logic: check if player has placed at least 8 blocks of solid wall with roof and door/entrance
        // or check if the player is currently inside a hole of dimensions 3x3x2 with walls of dirt/stone
        // Use world inspection around player, e.g.:
        BlockPos pos = player.getBlockPos();
        // Simplified: check blocks below and around
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos checkPos = pos.add(dx, 0, dz);
                BlockState state = player.getWorld().getBlockState(checkPos);
                if (state.isAir()) {
                    return false;
                }
            }
        }
        // Also check above head (roof)
        BlockState roof = player.getWorld().getBlockState(pos.up(2));
        if (roof.isAir()) {
            return false;
        }
        return true;
    }

}