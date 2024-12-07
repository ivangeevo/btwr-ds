package org.ivangeevo.btwr_ds.recipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BTWRDSRecipes {
    public static final DisabledRecipe.Serializer DISABLED_RECIPE_SERIALIZER = new DisabledRecipe.Serializer();
    public static final RecipeType<DisabledRecipe> DISABLED_RECIPE_TYPE = new RecipeType<>() {};

    public static void init() {
        Registry.register(Registries.RECIPE_TYPE, Identifier.of("btwr-ds", "disabled"), DISABLED_RECIPE_TYPE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of("btwr-ds","disabled"), DISABLED_RECIPE_SERIALIZER);
    }
}
