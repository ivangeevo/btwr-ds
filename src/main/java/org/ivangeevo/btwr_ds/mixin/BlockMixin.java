package org.ivangeevo.btwr_ds.mixin;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.ivangeevo.btwr_ds.block.BlockTillingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "afterBreak", at = @At("HEAD"))
    private void onAfterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity,
                              ItemStack tool, CallbackInfo ci) {
        //BlockTillingManager.MixinMod.getInstance().onAfterBreak(world, pos, state, tool, player);

        // Play break sound for plank type blocks (like slabs, stairs, fences) etc.
        // if broken with an inappropriate axe tool.
        if (!isWoodenPlankLike(state)) return;
        if (!tool.isIn(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)) {
            world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, SoundCategory.BLOCKS,
                    0.25F, 1.0F + (world.getRandom().nextFloat() * 0.25F)
            );
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
}
