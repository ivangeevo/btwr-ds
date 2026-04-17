package org.btwr.data_suite.mixin.material_beacons;

import issame.material_beacons.config.BlockOrTag;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockOrTag.class)
public interface BlockOrTagAccessor {
    @Accessor("block") Block getBlock();
    @Accessor("tag") TagKey<Block> getTag();
}