package org.btwr.data_suite.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionTypes;
import org.btwr.self_sustainable.block.ModBlocks;
import org.btwr.shared_library.util.utils.IdUtils;

public class WorldGenBlockReplacements {
    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};

    // Remove all blocks of said types from world generation.
    // These blocks are removed from anywhere in the world that they would be generated,
    // so it should contain only blocks that the player shouldn't have access to right away.
    // Any other replacements of blocks for specific things (like structures only) should be handled differently.
    public static void register() {
        // remove beds
        for (DyeColor color : DyeColor.values()) {
            Block bedBlock = Registries.BLOCK.get(IdUtils.ofMC(color.asString() + "_bed"));
            removeBlock(bedBlock);
        }

        removeBlock(Blocks.CRAFTING_TABLE);
        removeBlock(Blocks.CAULDRON);
        removeBlock(Blocks.CAMPFIRE);
        removeBlock(Blocks.WATER_CAULDRON);
        removeBlock(Blocks.LANTERN);
        removeBlock(Blocks.BLAST_FURNACE);
        removeBlock(Blocks.SMOKER);
        removeBlock(Blocks.BREWING_STAND);
        removeBlock(Blocks.BARREL);

        replaceWithCopyFacing(Blocks.WALL_TORCH, ModBlocks.CRUDE_WALL_TORCH_BURNED_OUT, Properties.HORIZONTAL_FACING);
        replaceWithCopyFacing(Blocks.TORCH, ModBlocks.CRUDE_TORCH_BURNED_OUT, Properties.HORIZONTAL_FACING);
        replaceWithCopyFacing(Blocks.FURNACE, ModBlocks.OVEN_BRICK, Properties.HORIZONTAL_FACING);

        // With fabric's replace events
        registerWithEvents();
    }

    private static void replaceWithCopyFacing(Block target, Block replacement, DirectionProperty directionProperty) {
        replaceBlockWithProperties(target, replacement, (from, to) -> {
            if (from.contains(directionProperty)) {
                to = to.with(directionProperty, from.get(directionProperty));
            }
            return to;
        });
    }

    /** Replaces a block with another one with an option to copy the properties from the old blockstate **/
    private static void replaceBlockWithProperties(Block from, Block to, boolean copy) {
        BlockReplacementRegistry.registerReplacement(from, to, (from1, to1) -> copy ? from1 : to1);
    }

    /** Replaces a block with another one with a specified state to return **/
    private static void replaceBlockWithProperties(Block from, Block to, BlockReplacementRegistry.StateTransformer transformer) {
        BlockReplacementRegistry.registerReplacement(from, to, transformer);
    }

    /** Simply replaces a block with air **/
    private static void removeBlock(Block block) {
        BlockReplacementRegistry.registerReplacement(block, Blocks.AIR);
    }


    private static void registerWithEvents() {
        // Replace netherrack in the overworld with nether bricks to prevent early nethercoal
        ServerChunkEvents.CHUNK_GENERATE.register((world, chunk) -> {
            BlockPos.Mutable pos = new BlockPos.Mutable();

            if (!world.getDimensionEntry().matchesId(DimensionTypes.OVERWORLD_ID)) return;

            for(int y = -64; y <= 319; ++y) {
                for(int x = 0; x < 16; ++x) {
                    for(int z = 0; z < 16; ++z) {
                        pos.set(x + chunk.getPos().getStartX(), y, z + chunk.getPos().getStartZ());
                        if (chunk.getBlockState(pos).isOf(Blocks.NETHERRACK)) {
                            chunk.setBlockState(pos, Blocks.BLACKSTONE.getDefaultState(), false);
                        }
                    }
                }
            }

        });
    }
}