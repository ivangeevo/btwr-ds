package org.btwr.data_suite.ponder.util;

import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class StructureGuidanceHelper {
    private final SceneBuilder builder;
    private final SceneBuildingUtil util;
    private final Object zoneSlot = new Object();

    public StructureGuidanceHelper(SceneBuilder builder, SceneBuildingUtil util) {
        this.builder = builder;
        this.util = util;
    }

    /**
     * Shows a "Danger" or "Clearance" zone.
     * Perfect for the 5x5 Water Wheel area or the Saw's kill zone.
     */
    public void markClearanceZone(BlockPos center, int radius, PonderPalette palette, int duration) {
        Selection area = util.select().cuboid(
            center.add(-radius, -radius, 0),
            center.add(radius, radius, 0)
        );
        builder.overlay().showOutline(palette, zoneSlot, area, duration);
    }

    /**
     * Draws an animated bounding box expansion.
     * Useful for showing the "Search Area" of a Block Detector (Buddy Block).
     */
    public void flashBoundingBox(Box box, PonderPalette palette) {
        Object pulseSlot = new Object();
        builder.overlay().chaseBoundingBoxOutline(palette, pulseSlot, box.expand(-0.5), 1);
        builder.idle(1);
        builder.overlay().chaseBoundingBoxOutline(palette, pulseSlot, box, 20);
    }
}