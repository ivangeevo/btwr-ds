package org.btwr.data_suite.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;
import org.btwr.data_suite.effect.effects.FortuneStatusEffect;
import org.btwr.data_suite.effect.effects.LootingStatusEffect;
import org.btwr.data_suite.effect.effects.ReducedHungerStatusEffect;

public class ModStatusEffects {
    public static final StatusEffect FORTUNE = new LootingStatusEffect(StatusEffectCategory.BENEFICIAL, 14270531);
    public static final StatusEffect LOOTING = new FortuneStatusEffect(StatusEffectCategory.BENEFICIAL, 9643043);
    //public static final StatusEffect TRUE_SIGHT = new TrueSightStatusEffect(StatusEffectCategory.BENEFICIAL, 14270531);

    public static final RegistryEntry<StatusEffect> REDUCED_HUNGER;

    static {
        REDUCED_HUNGER = Registry.registerReference(
                Registries.STATUS_EFFECT,
                Identifier.of(BTWRDSMod.MOD_ID, "reduced_hunger"),
                new ReducedHungerStatusEffect(StatusEffectCategory.BENEFICIAL, 1667072)
        );
    }

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(BTWRDSMod.MOD_ID, "fortune"), FORTUNE);
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(BTWRDSMod.MOD_ID, "looting"), LOOTING);
       //Registry.register(Registries.STATUS_EFFECT, Identifier.of(BTWRDSMod.MOD_ID, "true_sight"), TRUE_SIGHT);
    }
}