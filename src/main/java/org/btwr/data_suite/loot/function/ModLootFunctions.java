package org.btwr.data_suite.loot.function;

import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;
import org.btwr.data_suite.loot.function.functions.StatusEffectApplyBonusLootFunction;
import org.btwr.data_suite.loot.function.functions.StatusEffectCountIncreaseLootFunction;

public class ModLootFunctions {

    public static final LootFunctionType<StatusEffectCountIncreaseLootFunction> STATUS_EFFECT_LOOT =
            new LootFunctionType<>(StatusEffectCountIncreaseLootFunction.CODEC);

    public static final LootFunctionType<StatusEffectApplyBonusLootFunction> STATUS_EFFECT_APPLY_BONUS =
            new LootFunctionType<>(StatusEffectApplyBonusLootFunction.CODEC);

    public static void register() {
        Registry.register(
                Registries.LOOT_FUNCTION_TYPE,
                Identifier.of(BTWRDSMod.MOD_ID, "status_effect_loot"),
                STATUS_EFFECT_LOOT
        );

        Registry.register(
                Registries.LOOT_FUNCTION_TYPE,
                Identifier.of(BTWRDSMod.MOD_ID, "status_effect_apply_bonus"),
                STATUS_EFFECT_APPLY_BONUS
        );
    }

}