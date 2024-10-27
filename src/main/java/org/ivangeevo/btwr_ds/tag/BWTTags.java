package org.ivangeevo.btwr_ds.tag;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BWTTags
{

    public static class Blocks
    {



        private static TagKey<Block> register(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of("bwt", name));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> WOODEN_SIDING_BLOCKS = register("wooden_siding_blocks");

        public static final TagKey<Item> WOODEN_MOULDING_BLOCKS = register("wooden_moulding_blocks");
        public static final TagKey<Item> WOODEN_CORNER_BLOCKS = register("wooden_corner_blocks");


        private static TagKey<Item> register(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of("bwt", name));
        }
    }
}
