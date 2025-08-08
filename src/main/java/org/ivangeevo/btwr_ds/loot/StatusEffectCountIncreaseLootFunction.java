package org.ivangeevo.btwr_ds.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;

import java.util.List;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;

import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.entry.RegistryEntry;
import org.ivangeevo.btwr_ds.effect.ModStatusEffects;

public class StatusEffectCountIncreaseLootFunction extends ConditionalLootFunction {
    
    public static final int DEFAULT_LIMIT = 0;
    public static final MapCodec<StatusEffectCountIncreaseLootFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> addConditionsField(instance)
                    .and(
                            instance.group(
                                    StatusEffect.ENTRY_CODEC.fieldOf("effect").forGetter(function -> function.effect),
                                    LootNumberProviderTypes.CODEC.fieldOf("count").forGetter(function -> function.count),
                                    Codec.INT.optionalFieldOf("limit", 0).forGetter(function -> function.limit)
                            )
                    )
                    .apply(instance, StatusEffectCountIncreaseLootFunction::new)
    );
    private final RegistryEntry<StatusEffect> effect;
    private final LootNumberProvider count;
    private final int limit;

    StatusEffectCountIncreaseLootFunction(List<LootCondition> conditions, RegistryEntry<StatusEffect> effect, LootNumberProvider count, int limit) {
        super(conditions);
        this.effect = effect;
        this.count = count;
        this.limit = limit;
    }

    @Override
    public LootFunctionType<StatusEffectCountIncreaseLootFunction> getType() {
        return ModLootFunctions.STATUS_EFFECT_LOOT;
    }

    @Override
    public Set<LootContextParameter<?>> getRequiredParameters() {
        return Sets.union(ImmutableSet.of(LootContextParameters.ATTACKING_ENTITY), this.count.getRequiredParameters());
    }

    private boolean hasLimit() {
        return this.limit > DEFAULT_LIMIT;
    }

    @Override
    public ItemStack process(ItemStack stack, LootContext context) {
        Entity entity = context.get(LootContextParameters.ATTACKING_ENTITY);
        if (entity instanceof LivingEntity livingEntity) {
            StatusEffectInstance effectInstance = livingEntity.getStatusEffect(effect);
            if (effectInstance == null) return stack;

            int level = effectInstance.getAmplifier() + 1;
            float f = level * count.nextFloat(context);
            stack.increment(Math.round(f));
            if (limit > DEFAULT_LIMIT) stack.capCount(limit);
        }
        return stack;
    }


    public static StatusEffectCountIncreaseLootFunction.Builder builder(LootNumberProvider count) {
        RegistryEntry<StatusEffect> lootingEffectEntry = RegistryEntry.of(ModStatusEffects.LOOTING);
        return new StatusEffectCountIncreaseLootFunction.Builder(lootingEffectEntry, count);
    }

    public static class Builder extends ConditionalLootFunction.Builder<StatusEffectCountIncreaseLootFunction.Builder> {
        private final RegistryEntry<StatusEffect> effect;
        private final LootNumberProvider count;
        private int limit = DEFAULT_LIMIT;

        public Builder(RegistryEntry<StatusEffect> effect, LootNumberProvider count) {
            this.effect = effect;
            this.count = count;
        }

        protected StatusEffectCountIncreaseLootFunction.Builder getThisBuilder() {
            return this;
        }

        public StatusEffectCountIncreaseLootFunction.Builder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public LootFunction build() {
            return new StatusEffectCountIncreaseLootFunction(this.getConditions(), this.effect, this.count, this.limit);
        }
    }
}
