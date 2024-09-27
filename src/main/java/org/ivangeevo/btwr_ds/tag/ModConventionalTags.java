package org.ivangeevo.btwr_ds.tag;

import net.fabricmc.fabric.impl.tag.convention.v2.TagRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

public class ModConventionalTags
{
    public static class Items
    {
        public static final TagKey<Item> GEARS = register("gears");

        private static TagKey<Item> register(String tagId) {
            return TagRegistration.ITEM_TAG.registerC(tagId);
        }
    }

    public static class Blocks
    {




        private static TagKey<Block> register(String tagId) {
            return TagRegistration.BLOCK_TAG.registerC(tagId);
        }
    }

}
