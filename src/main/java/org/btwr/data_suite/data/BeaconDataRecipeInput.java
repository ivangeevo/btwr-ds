package org.btwr.data_suite.data;

import issame.material_beacons.config.BlockOrTag;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.mixin.material_beacons.BlockOrTagAccessor;

import java.util.List;
import java.util.Objects;

import static issame.material_beacons.MaterialBeacons.LOGGER;

public record BeaconDataRecipeInput(List<String> bases) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        for (BlockOrTag b : getBaseTags()) {
            BlockOrTagAccessor accessor = (BlockOrTagAccessor)b;

            if (b.isBlock()) {
                return accessor.getBlock().getDefaultState().getBlock().asItem().getDefaultStack();
            } else {
                Block blockFromTag = Registries.BLOCK.get(accessor.getTag().id());
                return blockFromTag.getDefaultState().getBlock().asItem().getDefaultStack();
            }
        }

        return ItemStack.EMPTY;
    }

    @Override public int getSize() {
        return bases.size();
    }

    public List<BlockOrTag> getBaseTags() {
        return bases.stream()
                .map(tag -> {
                    if (tag == null) {
                        LOGGER.warn("Null value found in bases: {}", bases);
                        return null;
                    } else if (tag.startsWith("#")) {
                        return new BlockOrTag(TagKey.of(RegistryKeys.BLOCK, Identifier.tryParse(tag.substring(1))));
                    } else {
                        return new BlockOrTag(Registries.BLOCK.get(Identifier.tryParse(tag)));
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}