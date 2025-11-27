package org.btwr.data_suite.mixin.bwt;

import com.bwt.blocks.BwtBlocks;
import com.bwt.blocks.PlanterBlock;
import com.bwt.blocks.SoilPlanterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.btwr.data_suite.data.ModProperties.FERTILIZED;

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
    public boolean btwr$getIsFertilizedForPlantGrowth(World world, BlockPos pos) {
        return world.getBlockState(pos).get(FERTILIZED);
    }

    @Override
    public void btwr$notifyOfFullStagePlantGrowthOn(World world, BlockPos pos, Block plantBlock) {
        // revert back to unfertilized soil
        BlockState newState = BwtBlocks.soilPlanterBlock.getDefaultState().with(MOISTURE, world.getBlockState(pos).get(MOISTURE));
        world.setBlockState(pos, newState);
    }

    @Override
    public float btwr$getPlantGrowthOnMultiplier(World world, BlockPos pos, Block plantBlock) {
        return 2F;
    }

    @Override
    public boolean btwr$isBlockHydratedForPlantGrowthOn(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.get(MOISTURE) == 7;
    }

}