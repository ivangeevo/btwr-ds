package org.btwr.data_suite.ponder.util;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class NbtFluidHelper {
    private final SceneBuilder builder;
    private final SceneBuildingUtil util;

    public NbtFluidHelper(SceneBuilder builder, SceneBuildingUtil util) {
        this.builder = builder;
        this.util = util;
    }

    /**
     * Forces the Ponder renderer to acknowledge the fluid level from the NBT.
     */
    public void redrawFluid(BlockPos pos) {
        // We pass the current state back to itself. 
        // This triggers a 'block update' in the Ponder world without changing the data.
        builder.world().modifyBlock(pos, state -> state, false);
    }

    /**
     * Shows the water section and immediately forces a redraw.
     */
    public void showWaterSection(int x1, int y1, int z1, int x2, int y2, int z2) {
        builder.world().showSection(util.select().fromTo(x1, y1, z1, x2, y2, z2), Direction.UP);
        
        // Iterate through the coordinates to force-update the fluids
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                    redrawFluid(util.grid().at(x, y, z));
                }
            }
        }
    }
}