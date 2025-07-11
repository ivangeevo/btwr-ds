package org.ivangeevo.btwr_ds.mixin.block;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import com.bwt.blocks.SoilPlanterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.state.property.Properties.MOISTURE;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity,
                              ItemStack tool, CallbackInfo ci) {

        // Execute block tilling behavior from breaking blocks with a hoe
        //BlockTillingManager.MixinMod.getInstance().onAfterBreak(world, pos, state, tool, player);

        // Play break sound for plank type blocks (like slabs, stairs, fences) etc.
        // if broken with an inappropriate axe tool.
        if (isWoodenPlankLike(state) && !tool.isIn(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)) {
            world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.BLOCKS,
                    0.25F, 1.0F + (world.getRandom().nextFloat() * 0.25F)
            );
        }

        // Add increased exhaustion for breaking blocks
        // Adding 0.02 exhaustion and vanilla adds 0.005 which adds to 0.025 which matches the value from retail BTW
        player.addExhaustion(0.02f);
    }

    // Add exhaustion when placing blocks (0.005f is the default vanilla block breaking amount)
    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void exhaustionOnPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if (placer instanceof PlayerEntity player) {
            player.getHungerManager().addExhaustion(0.005f);
        }
    }

    @Unique
    private static boolean isWoodenPlankLike(BlockState state) {
        return state.isIn(BlockTags.PLANKS)
                || state.isIn(BlockTags.WOODEN_SLABS)
                || state.isIn(BlockTags.WOODEN_STAIRS)
                || state.isIn(BlockTags.WOODEN_FENCES)
                || state.isIn(BlockTags.WOODEN_TRAPDOORS)
                || state.isIn(BlockTags.WOODEN_DOORS);
    }

    @Inject(method = "appendProperties", at = @At("HEAD"))
    private void onAppendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        if (((Block)(Object)this) instanceof SoilPlanterBlock) {
            builder.add(MOISTURE);
        }
    }

}
