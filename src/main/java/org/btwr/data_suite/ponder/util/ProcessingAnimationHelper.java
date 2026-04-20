package org.btwr.data_suite.ponder.util;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ProcessingAnimationHelper {
    private final SceneBuilder builder;
    private final SceneBuildingUtil util;

    public ProcessingAnimationHelper(SceneBuilder builder, SceneBuildingUtil util) {
        this.builder = builder;
        this.util = util;
    }

    /**
     * Simulates an item being "ground up" or "processed".
     * Item drops in top, idles, then results pop out the side.
     */
    public void simulateProcessing(BlockPos machinePos, ItemStack input, ItemStack output, Vec3d outputOffset) {
        // Drop input into top
        builder.world().createItemEntity(util.vector().topOf(machinePos).add(0, 1, 0), Vec3d.ZERO, input);
        builder.idle(20);
        
        // Hide the input (simulating it being "sucked in")
        // Note: In Ponder, we usually do this by creating the entity and then letting it expire or killing it
        
        builder.idle(20); // Machine working time
        
        // Eject output
        builder.world().createItemEntity(util.vector().centerOf(machinePos).add(outputOffset), 
            new Vec3d(outputOffset.x * 0.1, 0.1, outputOffset.z * 0.1), output);
    }
}