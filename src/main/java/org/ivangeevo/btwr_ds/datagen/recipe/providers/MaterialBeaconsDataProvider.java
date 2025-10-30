package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DefaultedList;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MaterialBeaconsDataProvider implements DataProvider {


    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return null;
    }

    @Override
    public String getName() {
        return "Material Beacons Data";
    }

    /**
     * Builds the nested power list for Redstone beacon.
     */
    private List<List<StatusEffectInstance>> addRedstoneEffects() {
        List<List<StatusEffectInstance>> powers = new ArrayList<>();

        // Tier 1
        powers.add(addEffectsList(
                addEffectIngredient(StatusEffects.SPEED, 200, 0),
                addEffectIngredient(StatusEffects.HASTE, 200, 0)
        ));

        // Tier 2
        powers.add(addEffectsList(
                addEffectIngredient(StatusEffects.STRENGTH, 200, 0)
        ));

        // Tier 3
        powers.add(addEffectsList(
                addEffectIngredient(StatusEffects.RESISTANCE, 200, 0),
                addEffectIngredient(StatusEffects.REGENERATION, 200, 0)
        ));

        return powers;
    }

    private List<StatusEffectInstance> addEffectsList(StatusEffectInstance... effects) {
        DefaultedList<StatusEffectInstance> list = DefaultedList.of();
        Collections.addAll(list, effects);
        return list;
    }

    private StatusEffectInstance addEffectIngredient(RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        return new StatusEffectInstance(effect, duration, amplifier);
    }


}
