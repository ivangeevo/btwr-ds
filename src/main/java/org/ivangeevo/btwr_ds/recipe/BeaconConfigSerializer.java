package org.ivangeevo.btwr_ds.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import issame.material_beacons.config.BlockOrTag;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static issame.material_beacons.MaterialBeacons.LOGGER;

public class BeaconConfigSerializer {

    private List<String> bases;
    private List<List<EffectConfig>> powers;

    public BeaconConfigSerializer(List<String> bases, List<List<EffectConfig>> powers) {
        this.bases = bases;
        this.powers = powers;
    }

    public static class Serializer implements RecipeSerializer<MaterialBeaconsRecipe> {

        protected static final MapCodec<MaterialBeaconsRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance->instance.group(
                        Codec.STRING
                                .optionalFieldOf("group", "")
                                .forGetter(recipe -> recipe.group),
                        CraftingRecipeCategory.CODEC
                                .fieldOf("category")
                                .orElse(CraftingRecipeCategory.MISC)
                                .forGetter(recipe -> recipe.category),
                        BlockOrTagIngredient.Serializer.createCodec().codec()
                                .listOf()
                                .fieldOf("bases")
                                .forGetter(MaterialBeaconsRecipe::getBeaconBases),
                        StatusEffectIngredient.Serializer.createCodec().codec()
                                .listOf()
                                .listOf()
                                .fieldOf("powers")
                                .forGetter(MaterialBeaconsRecipe::getBeaconPowers)
                ).apply(instance, MaterialBeaconsRecipe::new)
        );

        public static final PacketCodec<RegistryByteBuf, MaterialBeaconsRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                MaterialBeaconsRecipe.Serializer::write, MaterialBeaconsRecipe.Serializer::read
        );

        public Serializer() {}

        @Override
        public MapCodec<MaterialBeaconsRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, MaterialBeaconsRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        protected static MaterialBeaconsRecipe read(RegistryByteBuf buf) {
            String group = buf.readString();
            CraftingRecipeCategory category = buf.readEnumConstant(CraftingRecipeCategory.class);
            List<BlockOrTagIngredient> bases = DefaultedList.of();
            bases.replaceAll(ignored -> BlockOrTagIngredient.Serializer.read(buf));
            List<List<StatusEffectIngredient>> powers = DefaultedList.of();
            powers.replaceAll(ignored -> Collections.singletonList(StatusEffectIngredient.Serializer.read(buf)));
            return new MaterialBeaconsRecipe(group, category, bases, powers);
        }

        protected static void write(RegistryByteBuf buf, MaterialBeaconsRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            for (BlockOrTagIngredient ingredient : recipe.getBeaconBases()) {
                BlockOrTagIngredient.Serializer.write(buf, ingredient);
            }
            for (List<StatusEffectIngredient> ingredient : recipe.getBeaconPowers()) {
                for (StatusEffectIngredient ingredient1 : ingredient) {
                    StatusEffectIngredient.Serializer.write(buf, ingredient1);
                }
            }
        }
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

    public List<List<StatusEffectInstance>> getPowerEffects() {
        return powers.stream()
                .map(list -> list.stream()
                        .map(config -> {
                            if (config.effect() == null || config.duration() == null || config.amplifier() == null) {
                                LOGGER.warn("Null value found in powers: {}", config);
                                return null;
                            }
                            return new StatusEffectInstance(
                                    Registries.STATUS_EFFECT.getEntry(Identifier.tryParse(config.effect())).orElse(null),
                                    config.duration() * 20,
                                    config.amplifier(),
                                    true,
                                    true);
                        })
                        .filter(Objects::nonNull)
                        .toList())
                .toList();
    }

    public List<List<Double>> getEffectRanges() {
        return powers.stream()
                .map(list -> list.stream()
                        .map(config -> {
                            if (config.range() == null) {
                                LOGGER.warn("Null range found in powers: {}", config);
                                return null;
                            }
                            return config.range();
                        })
                        .filter(Objects::nonNull)
                        .toList())
                .toList();
    }

    public record EffectConfig(String effect, Integer duration, Integer amplifier, Double range) {
    }


}
