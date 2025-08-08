package org.ivangeevo.btwr_ds.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;
import org.ivangeevo.btwr_ds.effect.effects.FortuneStatusEffect;
import org.ivangeevo.btwr_ds.effect.effects.LootingStatusEffect;

public class ModStatusEffects {

    public static final StatusEffect FORTUNE = new LootingStatusEffect(StatusEffectCategory.BENEFICIAL, 14270531);
    public static final StatusEffect LOOTING = new FortuneStatusEffect(StatusEffectCategory.BENEFICIAL, 9643043);
    //public static final StatusEffect TRUE_SIGHT = new LootingStatusEffect(StatusEffectCategory.BENEFICIAL, 14270531);

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(BTWRDSMod.MOD_ID, "fortune"), FORTUNE);
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(BTWRDSMod.MOD_ID, "looting"), LOOTING);
       //Registry.register(Registries.STATUS_EFFECT, Identifier.of(BTWRDSMod.MOD_ID, "true_sight"), TRUE_SIGHT);
    }
}
