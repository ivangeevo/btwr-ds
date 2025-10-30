package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DefaultedList;
import org.ivangeevo.btwr_ds.recipe.StatusEffectIngredient;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MaterialBeaconsRecipeProvider extends FabricDynamicRegistryProvider {

    public MaterialBeaconsRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {

    }

    @Override
    public String getName() {
        return "Material Beacons Data";
    }

    /**
     * Builds the nested power list for Redstone beacon.
     */
    private List<List<StatusEffectIngredient>> addRedstoneEffects() {
        List<List<StatusEffectIngredient>> powers = new ArrayList<>();

        // Tier 1
        powers.add(addEffectsList(
                addEffectIngredient(StatusEffects.SPEED, 200, 0, 10.0),
                addEffectIngredient(StatusEffects.HASTE, 200, 0, 10.0)
        ));

        // Tier 2
        powers.add(addEffectsList(
                addEffectIngredient(StatusEffects.STRENGTH, 200, 0, 12.0)
        ));

        // Tier 3
        powers.add(addEffectsList(
                addEffectIngredient(StatusEffects.RESISTANCE, 200, 0, 14.0),
                addEffectIngredient(StatusEffects.REGENERATION, 200, 0, 14.0)
        ));

        return powers;
    }

    /**
     * Creates a list (tier) of StatusEffectIngredients.
     */
    private List<StatusEffectIngredient> addEffectsList(StatusEffectIngredient... ingredients) {
        DefaultedList<StatusEffectIngredient> list = DefaultedList.of();
        Collections.addAll(list, ingredients);
        return list;
    }

    /**
     * Wraps a StatusEffect into your ingredient structure.
     */
    private StatusEffectIngredient addEffectIngredient(RegistryEntry<StatusEffect> effect, int duration, int amplifier, double range) {
        return new StatusEffectIngredient(
                new StatusEffectInstance(effect, duration, amplifier),
                duration, amplifier, range
        );
    }


}
