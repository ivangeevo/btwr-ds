package org.btwr.data_suite.ponder.util;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import java.util.function.UnaryOperator;

public class PonderWorldHelper {
    private final SceneBuilder builder;
    private final SceneBuildingUtil util;

    public PonderWorldHelper(SceneBuilder builder, SceneBuildingUtil util) {
        this.builder = builder;
        this.util = util;
    }

    /** Shows a line of blocks one by one with a delay */
    public void showLine(BlockPos start, Direction direction, int length, int interval) {
        for (int i = 0; i < length; i++) {
            BlockPos current = start.offset(direction, i);
            builder.world().showSection(util.select().position(current), Direction.DOWN);
            builder.idle(interval);
        }
    }

    /** Helper to quickly set a property (like MECH_POWER) on a block */
    public <T extends Comparable<T>> void setBlockProperty(BlockPos pos, Property<T> property, T value) {
        builder.world().modifyBlock(pos, state -> state.with(property, value), false);
    }
}