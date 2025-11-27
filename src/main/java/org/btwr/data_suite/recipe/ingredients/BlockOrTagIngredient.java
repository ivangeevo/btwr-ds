package org.btwr.data_suite.recipe.ingredients;

import com.bwt.recipes.BlockIngredient;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;

import java.util.List;
import java.util.Optional;

public record BlockOrTagIngredient(BlockIngredient blockIngredient) implements CustomIngredient {

    public static final BlockOrTagIngredient.Serializer SERIALIZER = new BlockOrTagIngredient.Serializer();
    public static final BlockOrTagIngredient EMPTY = new BlockOrTagIngredient(
            new BlockIngredient(Optional.empty(), Optional.empty())
    );

    public static BlockOrTagIngredient ofBlock(Block block) {
        return new BlockOrTagIngredient(BlockIngredient.fromBlock(block));
    }

    public static BlockOrTagIngredient ofTag(TagKey<Block> tag) {
        return new BlockOrTagIngredient(BlockIngredient.fromTag(tag));
    }

    @Override
    public boolean test(ItemStack stack) {
        return false;
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of();
    }

    @Override
    public boolean requiresTesting() {
        return false;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer implements CustomIngredientSerializer<BlockOrTagIngredient> {
        private static final Identifier ID = Identifier.of(BTWRDSMod.MOD_ID, "block_or_tag_ingredient");
        public static final MapCodec<BlockOrTagIngredient> CODEC = createCodec();
        public static final PacketCodec<RegistryByteBuf, BlockOrTagIngredient> PACKET_CODEC = PacketCodec.ofStatic(
                BlockOrTagIngredient.Serializer::write, BlockOrTagIngredient.Serializer::read
        );

        public static MapCodec<BlockOrTagIngredient> createCodec() {
            return RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BlockIngredient.Serializer.createCodec().codec()
                            .fieldOf("ingredient")
                            .forGetter(BlockOrTagIngredient::blockIngredient)
                    ).apply(instance, BlockOrTagIngredient::new)
            );
        }

        @Override
        public Identifier getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<BlockOrTagIngredient> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, BlockOrTagIngredient> getPacketCodec() {
            return PACKET_CODEC;
        }

        public static BlockOrTagIngredient read(RegistryByteBuf buf) {
            BlockIngredient base = BlockIngredient.Serializer.PACKET_CODEC.decode(buf);
            int count = buf.readInt();
            return new BlockOrTagIngredient(base);
        }

        public static void write(RegistryByteBuf buf, BlockOrTagIngredient ingredient) {
            BlockIngredient.Serializer.PACKET_CODEC.encode(buf, ingredient.blockIngredient);
        }
    }

}