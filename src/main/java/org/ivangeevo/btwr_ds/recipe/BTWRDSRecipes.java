package org.ivangeevo.btwr_ds.recipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;

public class BTWRDSRecipes {

    public static final DisabledRecipe.Serializer DISABLED_RECIPE_SERIALIZER = new DisabledRecipe.Serializer();
    public static final RecipeType<DisabledRecipe> DISABLED_RECIPE_TYPE = new RecipeType<>() {};

    public static final ShapelessRecipeWithCounts.Serializer SHAPELESS_WITH_COUNTS_SERIALIZER = new ShapelessRecipeWithCounts.Serializer();
    public static final RecipeType<ShapelessRecipeWithCounts> COUNTED_SHAPELESS_RECIPE_TYPE = new RecipeType<>() {};


    public static void register() {
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(BTWRDSMod.MOD_ID, "disabled"), DISABLED_RECIPE_TYPE);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(BTWRDSMod.MOD_ID,"disabled"), DISABLED_RECIPE_SERIALIZER);
    }

}
