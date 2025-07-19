package org.ivangeevo.btwr_ds.mixin.block;

import com.bwt.blocks.BwtBlocks;
import com.bwt.blocks.PlanterBlock;
import com.bwt.blocks.SoilPlanterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.ivangeevo.btwr_ds.data.ModProperties.FERTILIZED;


@Mixin(SoilPlanterBlock.class)
public abstract class SoilPlanterBlockMixin extends PlanterBlock {

    @Unique
    private static final IntProperty MOISTURE = Properties.MOISTURE;

    public SoilPlanterBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectConstructor(Settings settings, CallbackInfo ci) {
        this.setDefaultState(this.stateManager.getDefaultState().with(MOISTURE, 0).with(FERTILIZED, false));
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.BONE_MEAL)) {
            world.setBlockState(pos, state.with(FERTILIZED, true));
            if (!player.isCreative()) {
                stack.decrement(1);
            }
            return ItemActionResult.success(true);
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public boolean getIsFertilizedForPlantGrowth(World world, BlockPos pos) {
        return world.getBlockState(pos).get(FERTILIZED);
    }

    @Override
    public void notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock) {
        // revert back to unfertilized soil
        BlockState newState = BwtBlocks.soilPlanterBlock.getDefaultState().with(MOISTURE, world.getBlockState(pos).get(MOISTURE));
        world.setBlockState(pos, newState);
    }

    @Override
    public float getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock) {
        return 2F;
    }

    @Override
    public boolean isBlockHydratedForPlantGrowthOn(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.get(MOISTURE) == 7;
    }
}
