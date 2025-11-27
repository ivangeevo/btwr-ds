package org.ivangeevo.btwr_ds.util;

import net.ivangeevo.self_sustainable.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import org.btwr.shared_library.api.ServerChunkGenerateEvents;
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
        removeBlock(Blocks.WATER_CAULDRON);
        removeBlock(Blocks.LANTERN);

        //BlockReplacementRegistry.registerReplacement(Blocks.WALL_TORCH, ModBlocks.CRUDE_WALL_TORCH_BURNED_OUT);
        //BlockReplacementRegistry.registerReplacement(Blocks.TORCH, ModBlocks.CRUDE_TORCH_BURNED_OUT);
        ServerChunkGenerateEvents.createChunkReplaceEventGlobally(Blocks.GRASS_BLOCK, Blocks.RED_STAINED_GLASS);

    }

    // sets a block to air
    private static void removeBlock(Block block) {
        //BlockReplacementRegistry.registerReplacement(block, Blocks.AIR);
    }

}
