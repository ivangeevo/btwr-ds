package org.btwr.data_suite.potion;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;
import org.btwr.data_suite.effect.ModStatusEffects;

public class BTWRPotions {

    public static final Potion REDUCED_HUNGER =
            Registry.register(
                    Registries.POTION,
                    Identifier.of(BTWRDSMod.MOD_ID, "reduced_hunger"),
                    new Potion(new StatusEffectInstance(ModStatusEffects.REDUCED_HUNGER, 3600))
            );

    public static void register() {
        BTWRDSMod.LOGGER.info("Registering Potion Items for " + BTWRDSMod.MOD_ID);

        // Register the recipes
        registerPotionRecipes();
    }

    private static void registerPotionRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register( builder -> {
            builder.registerPotionRecipe(
                    Potions.HEALING,
                    Ingredient.ofItems(Items.DRAGON_BREATH),
                    Registries.POTION.getEntry(REDUCED_HUNGER)
            );
        });
    }
}
