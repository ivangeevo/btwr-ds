package org.btwr.data_suite.effect.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.ColorHelper;
import org.btwr.data_suite.effect.ModStatusEffects;

public class LootingStatusEffect extends StatusEffect {
    public LootingStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(RegistryEntry.of(ModStatusEffects.LOOTING), 180), player);
            return true;
        }
        return false;
    }

    @Override
    public ParticleEffect createParticle(StatusEffectInstance effect) {
        // Use default particle with alpha depending on ambient flag (like vanilla effects)
        int alpha = effect.isAmbient() ? 38 : 255;
        return EntityEffectParticleEffect.create(ParticleTypes.ENTITY_EFFECT, ColorHelper.Argb.withAlpha(alpha, this.getColor()));
    }
}