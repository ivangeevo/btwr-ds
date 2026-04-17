package org.btwr.data_suite.world;

import com.bwt.blocks.BwtBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import org.btwr.vegehenna.block.ModBlocks;

import static net.minecraft.block.FarmlandBlock.MOISTURE;
import static org.btwr.data_suite.data.ModProperties.FERTILIZED;

/**
 * Helper functions related to the world
 */
public class WorldUtils {
    /** Fertilizes a farmland block **/
    public static void fertilizeFarmland(ItemUsageContext context, BlockPos pos, BlockState farmlandState) {
        context.getWorld().setBlockState(pos, ModBlocks.FARMLAND_FERTILIZED.getDefaultState().with(MOISTURE, farmlandState.get(MOISTURE)));
        if (context.getPlayer() != null && context.getPlayer().isCreative()) return;
        context.getStack().decrement(1);
    }

    /** Fertilizes a soil planter block **/
    public static void fertilizePlanter(ItemUsageContext context, BlockPos pos, BlockState planterState) {
        context.getWorld().setBlockState(pos, BwtBlocks.soilPlanterBlock.getDefaultState()
                .with(MOISTURE, planterState.get(MOISTURE))
                .with(FERTILIZED, true)
        );
        if (context.getPlayer() != null && context.getPlayer().isCreative()) return;
        context.getStack().decrement(1);
    }
}