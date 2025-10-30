package org.ivangeevo.btwr_ds.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;

import java.util.List;

public record OGStatusEffectIngredient(String effect, Integer duration, Integer amplifier, Double range) implements CustomIngredient {

    public static final Serializer SERIALIZER = new Serializer();

    public static OGStatusEffectIngredient of(String effect, Integer duration, Integer amplifier, Double range) {
        return new OGStatusEffectIngredient(effect, duration, amplifier, range);
    }

    @Override
    public boolean test(ItemStack stack) {
        return effect != null;
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of();
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer implements CustomIngredientSerializer<OGStatusEffectIngredient> {
        private static final Identifier ID = Identifier.of(BTWRDSMod.MOD_ID, "status_effect_ingredient");
        public static final MapCodec<OGStatusEffectIngredient> CODEC = createCodec();
        public static final PacketCodec<RegistryByteBuf, OGStatusEffectIngredient> PACKET_CODEC = PacketCodec.ofStatic(
                Serializer::write, Serializer::read
        );

        public static MapCodec<OGStatusEffectIngredient> createCodec() {
            return RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            Codec.STRING.fieldOf("effect").forGetter(OGStatusEffectIngredient::effect),
                            Codec.INT.fieldOf("duration").forGetter(OGStatusEffectIngredient::duration),
                            Codec.INT.fieldOf("amplifier").forGetter(OGStatusEffectIngredient::amplifier),
                            Codec.DOUBLE.fieldOf("range").forGetter(OGStatusEffectIngredient::range)
                    ).apply(instance, OGStatusEffectIngredient::new)
            );
        }

        @Override
        public Identifier getIdentifier() {
            return ID;
        }


        @Override
        public PacketCodec<RegistryByteBuf, OGStatusEffectIngredient> getPacketCodec() {
            return PACKET_CODEC;
        }

        @Override
        public MapCodec<OGStatusEffectIngredient> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        public static OGStatusEffectIngredient read(RegistryByteBuf buf) {
            String string = buf.readString();
            int duration = buf.readVarInt();
            int amplifier = buf.readVarInt();
            double range = buf.readDouble();
            return new OGStatusEffectIngredient(string, duration, amplifier, range);
        }

        public static void write(RegistryByteBuf buf, OGStatusEffectIngredient ingredient) {
            buf.writeString(ingredient.effect);
            buf.writeVarInt(ingredient.duration);
            buf.writeVarInt(ingredient.amplifier);
            buf.writeDouble(ingredient.range);
        }
    }
}
