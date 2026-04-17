package org.btwr.data_suite.recipe.ingredients;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;

import java.util.List;
import java.util.Optional;

public record StatusEffectIngredient(StatusEffectInstance effectInstance, Integer duration, Integer amplifier, Double range) implements CustomIngredient {
    public static final Serializer SERIALIZER = new Serializer();

    public static StatusEffectIngredient create(StatusEffectInstance effectInstance, Integer duration, Integer amplifier, Double range) {
        return new StatusEffectIngredient(effectInstance, duration, amplifier, range);
    }

    @Override public boolean test(ItemStack stack) {
        return effectInstance != null;
    }
    @Override public List<ItemStack> getMatchingStacks() {
        return List.of();
    }
    @Override public boolean requiresTesting() {
        return true;
    }
    @Override public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer implements CustomIngredientSerializer<StatusEffectIngredient> {
        private static final Identifier ID = Identifier.of(BTWRDSMod.MOD_ID, "status_effect_ingredient");
        public static final MapCodec<StatusEffectIngredient> CODEC = createCodec();
        public static final PacketCodec<RegistryByteBuf, StatusEffectIngredient> PACKET_CODEC = PacketCodec.ofStatic(
                Serializer::write, Serializer::read
        );

        public static MapCodec<StatusEffectIngredient> createCodec() {
            return RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            StatusEffectInstance.CODEC.fieldOf("effect").forGetter((StatusEffectIngredient::effectInstance)),
                            Codec.INT.fieldOf("duration").forGetter(StatusEffectIngredient::duration),
                            Codec.INT.fieldOf("amplifier").forGetter(StatusEffectIngredient::amplifier),
                            Codec.DOUBLE.fieldOf("range").forGetter(StatusEffectIngredient::range)
                    ).apply(instance, StatusEffectIngredient::new)
            );
        }

        @Override public Identifier getIdentifier() {
            return ID;
        }

        @Override public PacketCodec<RegistryByteBuf, StatusEffectIngredient> getPacketCodec() {
            return PACKET_CODEC;
        }

        @Override public MapCodec<StatusEffectIngredient> getCodec(boolean allowEmpty) {
            return CODEC;
        }

        public static StatusEffectIngredient read(RegistryByteBuf buf) {
            StatusEffectInstance effect = StatusEffectInstance.fromNbt(buf.readNbt());
            int duration = buf.readVarInt();
            int amplifier = buf.readVarInt();
            double range = buf.readDouble();
            return new StatusEffectIngredient(effect, duration, amplifier, range);
        }

        public static void write(RegistryByteBuf buf, StatusEffectIngredient ingredient) {
            Optional<RegistryKey<StatusEffect>> effectOptional = ingredient.effectInstance.getEffectType().getKey();
            effectOptional.ifPresent(statusEffectRegistryKey ->
                    buf.writeRegistryKey(statusEffectRegistryKey.getRegistryRef())
            );
            buf.writeVarInt(ingredient.duration);
            buf.writeVarInt(ingredient.amplifier);
            buf.writeDouble(ingredient.range);
        }
    }
}