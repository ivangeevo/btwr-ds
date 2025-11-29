package org.btwr.data_suite.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
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

        //ServerChunkGenerateEvents.createChunkReplaceEventGlobally(Blocks.GRASS_BLOCK, Blocks.RED_STAINED_GLASS);
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

}