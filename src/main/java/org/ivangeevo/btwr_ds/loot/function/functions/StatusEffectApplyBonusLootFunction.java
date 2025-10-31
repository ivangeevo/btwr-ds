package org.ivangeevo.btwr_ds.loot.function.functions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.random.Random;
import org.ivangeevo.btwr_ds.loot.function.ModLootFunctions;

public class StatusEffectApplyBonusLootFunction extends ConditionalLootFunction {

	private static final Map<Identifier, StatusEffectApplyBonusLootFunction.Type> FACTORIES = Stream.of(
			BinomialWithBonusCount.TYPE,
			OreDrops.TYPE,
			UniformBonusCount.TYPE
		).collect(Collectors.toMap(Type::id, Function.identity()));

	private static final Codec<StatusEffectApplyBonusLootFunction.Type> TYPE_CODEC = Identifier.CODEC.comapFlatMap(id -> {
		StatusEffectApplyBonusLootFunction.Type type = FACTORIES.get(id);
		return type != null ? DataResult.success(type) : DataResult.error(() -> "No formula type with id: '" + id + "'");
	}, StatusEffectApplyBonusLootFunction.Type::id);

	private static final MapCodec<StatusEffectApplyBonusLootFunction.Formula> FORMULA_CODEC = Codecs.parameters(
		"formula", "parameters", TYPE_CODEC, StatusEffectApplyBonusLootFunction.Formula::getType, StatusEffectApplyBonusLootFunction.Type::codec
	);

	public static final MapCodec<StatusEffectApplyBonusLootFunction> CODEC = RecordCodecBuilder.mapCodec(
		instance -> addConditionsField(instance)
				.and(
					instance.group(
						StatusEffect.ENTRY_CODEC.fieldOf("effect").forGetter(function -> function.effect), FORMULA_CODEC.forGetter(function -> function.formula)
					)
				)
				.apply(instance, StatusEffectApplyBonusLootFunction::new)
	);
	private final RegistryEntry<StatusEffect> effect;
	private final StatusEffectApplyBonusLootFunction.Formula formula;

	private StatusEffectApplyBonusLootFunction(List<LootCondition> conditions, RegistryEntry<StatusEffect> effect, StatusEffectApplyBonusLootFunction.Formula formula) {
		super(conditions);
		this.effect = effect;
		this.formula = formula;
	}

	@Override
	public LootFunctionType<StatusEffectApplyBonusLootFunction> getType() {
		return ModLootFunctions.STATUS_EFFECT_APPLY_BONUS;
	}

	@Override
	public Set<LootContextParameter<?>> getRequiredParameters() {
		return ImmutableSet.of(LootContextParameters.TOOL);
	}

	@Override
	public ItemStack process(ItemStack stack, LootContext context) {
		Entity entity = context.get(LootContextParameters.THIS_ENTITY);
		if (entity instanceof LivingEntity livingEntity) {
			StatusEffectInstance effectInstance = livingEntity.getStatusEffect(effect);
			if (effectInstance == null) return stack;

			int level = effectInstance.getAmplifier();
			int j = this.formula.getValue(context.getRandom(), stack.getCount(), level);
			stack.setCount(j);
		}

		return stack;
	}

	public static ConditionalLootFunction.Builder<?> binomialWithBonusCount(RegistryEntry<StatusEffect> effect, float probability, int extra) {
		return builder(conditions -> new StatusEffectApplyBonusLootFunction(conditions, effect, new StatusEffectApplyBonusLootFunction.BinomialWithBonusCount(extra, probability)));
	}

	public static ConditionalLootFunction.Builder<?> oreDrops(RegistryEntry<StatusEffect> effect) {
		return builder(conditions -> new StatusEffectApplyBonusLootFunction(conditions, effect, new StatusEffectApplyBonusLootFunction.OreDrops()));
	}

	public static ConditionalLootFunction.Builder<?> uniformBonusCount(RegistryEntry<StatusEffect> effect) {
		return builder(conditions -> new StatusEffectApplyBonusLootFunction(conditions, effect, new StatusEffectApplyBonusLootFunction.UniformBonusCount(1)));
	}

	public static ConditionalLootFunction.Builder<?> uniformBonusCount(RegistryEntry<StatusEffect> effect, int bonusMultiplier) {
		return builder(conditions -> new StatusEffectApplyBonusLootFunction(conditions, effect, new StatusEffectApplyBonusLootFunction.UniformBonusCount(bonusMultiplier)));
	}

	static record BinomialWithBonusCount(int extra, float probability) implements StatusEffectApplyBonusLootFunction.Formula {
		private static final Codec<StatusEffectApplyBonusLootFunction.BinomialWithBonusCount> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
						Codec.INT.fieldOf("extra").forGetter(StatusEffectApplyBonusLootFunction.BinomialWithBonusCount::extra),
						Codec.FLOAT.fieldOf("probability").forGetter(StatusEffectApplyBonusLootFunction.BinomialWithBonusCount::probability)
					)
					.apply(instance, StatusEffectApplyBonusLootFunction.BinomialWithBonusCount::new)
		);
		public static final StatusEffectApplyBonusLootFunction.Type TYPE = new StatusEffectApplyBonusLootFunction.Type(Identifier.ofVanilla("binomial_with_bonus_count"), CODEC);

		@Override
		public int getValue(Random random, int initialCount, int effectLevel) {
			for (int i = 0; i < effectLevel + this.extra; i++) {
				if (random.nextFloat() < this.probability) {
					initialCount++;
				}
			}

			return initialCount;
		}

		@Override
		public StatusEffectApplyBonusLootFunction.Type getType() {
			return TYPE;
		}
	}

	interface Formula {
		int getValue(Random random, int initialCount, int effectLevel);

		StatusEffectApplyBonusLootFunction.Type getType();
	}

	record OreDrops() implements StatusEffectApplyBonusLootFunction.Formula {
		public static final Codec<StatusEffectApplyBonusLootFunction.OreDrops> CODEC = Codec.unit(StatusEffectApplyBonusLootFunction.OreDrops::new);
		public static final StatusEffectApplyBonusLootFunction.Type TYPE = new StatusEffectApplyBonusLootFunction.Type(Identifier.ofVanilla("ore_drops"), CODEC);

		@Override
		public int getValue(Random random, int initialCount, int effectLevel) {
			if (effectLevel > 0) {
				int i = random.nextInt(effectLevel + 2) - 1;
				if (i < 0) {
					i = 0;
				}

				return initialCount * (i + 1);
			} else {
				return initialCount;
			}
		}

		@Override
		public StatusEffectApplyBonusLootFunction.Type getType() {
			return TYPE;
		}
	}

	record Type(Identifier id, Codec<? extends StatusEffectApplyBonusLootFunction.Formula> codec) {
	}

	record UniformBonusCount(int bonusMultiplier) implements StatusEffectApplyBonusLootFunction.Formula {
		public static final Codec<StatusEffectApplyBonusLootFunction.UniformBonusCount> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.INT
							.fieldOf("bonusMultiplier")
							.forGetter(StatusEffectApplyBonusLootFunction.UniformBonusCount::bonusMultiplier)
					)
					.apply(instance, StatusEffectApplyBonusLootFunction.UniformBonusCount::new)
		);
		public static final StatusEffectApplyBonusLootFunction.Type TYPE = new StatusEffectApplyBonusLootFunction.Type(Identifier.ofVanilla("uniform_bonus_count"), CODEC);

		@Override
		public int getValue(Random random, int initialCount, int effectLevel) {
			return initialCount + random.nextInt(this.bonusMultiplier * effectLevel + 1);
		}

		@Override
		public StatusEffectApplyBonusLootFunction.Type getType() {
			return TYPE;
		}
	}
}
