package org.ivangeevo.btwr_ds.util;

import com.bwt.blocks.SoilPlanterBlock;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

import static org.ivangeevo.btwr_ds.data.ModProperties.FERTILIZED;


public class PlanterFertilizer {

    public static void init() {
        UseItemCallback.EVENT.register(PlanterFertilizer::tryFertilize);
    }

    private static TypedActionResult<ItemStack> tryFertilize(PlayerEntity player, World world, Hand hand) {
        if (world.isClient) return TypedActionResult.pass(player.getStackInHand(hand));

        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isOf(Items.BONE_MEAL)) return TypedActionResult.pass(stack);

        if (!(player.raycast(5.0D, 0.0F, false) instanceof BlockHitResult hit)) {
            return TypedActionResult.pass(stack);
        }

        BlockPos pos = hit.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        // Case 1: fertilize the SoilPlanter directly
        if (block instanceof SoilPlanterBlock && !state.get(FERTILIZED)) {
            world.setBlockState(pos, state.with(FERTILIZED, true));
            if (!player.isCreative()) stack.decrement(1);
            return TypedActionResult.success(stack, true);
        }

        // Case 2: fertilize the SoilPlanter below a crop
        BlockPos belowPos = pos.down();
        BlockState belowState = world.getBlockState(belowPos);
        if (block instanceof CropBlock && belowState.getBlock() instanceof SoilPlanterBlock && !belowState.get(FERTILIZED)) {
            world.setBlockState(belowPos, belowState.with(FERTILIZED, true));
            if (!player.isCreative()) stack.decrement(1);
            return TypedActionResult.success(stack, true);
        }

        return TypedActionResult.pass(stack);
    }
}