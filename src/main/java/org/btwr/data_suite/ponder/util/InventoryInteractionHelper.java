package org.btwr.data_suite.ponder.util;

import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class InventoryInteractionHelper {
    private final SceneBuilder builder;
    private final SceneBuildingUtil util;

    public InventoryInteractionHelper(SceneBuilder builder, SceneBuildingUtil util) {
        this.builder = builder;
        this.util = util;
    }

    /**
     * Shows a player inserting a specific item into a machine face.
     */
    public void showInsertion(BlockPos pos, Direction side, ItemStack item, int duration) {
        Vec3d surface = util.vector().blockSurface(pos, side);
        builder.overlay().showControls(surface, Pointing.DOWN, duration)
            .rightClick()
            .withItem(item);
    }

    /**
     * Specifically for the Hopper: Shows the "Filter Slot" interaction.
     */
    public void showFiltering(BlockPos hopperPos, ItemStack filterItem) {
        builder.overlay().showText(40)
            .text("Place a filter in the inventory")
            .pointAt(util.vector().blockSurface(hopperPos, Direction.NORTH))
            .placeNearTarget();
        
        showInsertion(hopperPos, Direction.UP, filterItem, 40);
    }
}