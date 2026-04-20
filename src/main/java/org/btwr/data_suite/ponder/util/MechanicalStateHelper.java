package org.btwr.data_suite.ponder.util;

import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class MechanicalStateHelper {
    private final SceneBuilder builder;
    private final SceneBuildingUtil util;
    // Unique slot for this helper's highlights
    private final Object highlightSlot = new Object();

    public MechanicalStateHelper(SceneBuilder builder, SceneBuildingUtil util) {
        this.builder = builder;
        this.util = util;
    }

    /**
     * Highlights a block and changes its state. 
     * Useful for showing Hibachis igniting or Bellows activating.
     */
    public void transitionBlock(BlockPos pos, BlockState newState, PonderPalette palette, int duration) {
        Selection selection = util.select().position(pos);
        builder.overlay().showOutline(palette, highlightSlot, selection, duration);
        builder.world().replaceBlocks(selection, newState, true);
    }
}