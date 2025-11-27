package org.btwr.data_suite.block;

import net.fabricmc.fabric.api.registry.TillableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.btwr.shared_library.tag.BTWRConventionalTags;
import org.btwr.data_suite.BTWRDSMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.Block.pushEntitiesUpBeforeBlockChange;

/** Contains code for managing modification of hoes usage for making farmland
 *
 */
public class BlockTillingManager {

    public static final Block[] tillableDirtBlock = {Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT};

    /** Normal calls to the TillableBlockRegistry
     * <p>Setting variations of dirt blocks to farmland
     * **/
    public static void registerNormalTillable() {
        if (!BTWRDSMod.getInstance().settings.shouldChangeHoesBTWStyle()) return;

        for (Block dirt : tillableDirtBlock) {
            TillableBlockRegistry.register(
                    dirt,
                    HoeItem::canTillFarmland,
                    context -> {
                        BlockState result = Blocks.FARMLAND.getDefaultState();
                        HoeItem.createTillAction(result).accept(context);
                    }
            );
        }
    }

    public static class MixinMod {

        private static final MixinMod INSTANCE = new MixinMod();
        private MixinMod() {}
        public static MixinMod getInstance() {
            return INSTANCE;
        }

        public void onUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
            if (!BTWRDSMod.getInstance().settings.shouldChangeHoesBTWStyle()) return;
            BlockPos pos = context.getBlockPos();
            BlockState state = context.getWorld().getBlockState(pos);

            boolean isGrassBlock = state.isOf(Blocks.GRASS_BLOCK) || state.getBlock() instanceof GrassBlock;

            if (isGrassBlock || state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_DIRT)) {
                cir.setReturnValue(ActionResult.FAIL);
            }
        }

        public void onAfterBreak(World world, BlockPos pos, BlockState state, ItemStack tool, PlayerEntity player) {

            if (!BTWRDSMod.getInstance().settings.shouldChangeHoesBTWStyle()) return;
            if (world.isClient() || !tool.isIn(ItemTags.HOES) || player.isCreative()) return;

            if (state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_GRASS) || state.getBlock() instanceof GrassBlock) {
                setState(world, pos, Blocks.DIRT.getDefaultState());
            }

            if (state.isIn(BTWRConventionalTags.Blocks.FARMLAND_VIABLE_DIRT)) {
                setState(world, pos, Blocks.FARMLAND.getDefaultState());
            }

        }
    }

    private static void setState(World world, BlockPos pos, BlockState newState) {
        BlockState oldState = world.getBlockState(pos);
        BlockState updatedState = pushEntitiesUpBeforeBlockChange(oldState, newState, world, pos);
        world.setBlockState(pos, updatedState);
    }

}