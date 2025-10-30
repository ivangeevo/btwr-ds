package org.ivangeevo.btwr_ds.recipe;

import btwr.btwr_sl.BTWRSLMod;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;

public class BTWRDSRecipes {

    public static final DisabledRecipe.Serializer DISABLED_RECIPE_SERIALIZER = new DisabledRecipe.Serializer();
    public static final RecipeType<DisabledRecipe> DISABLED_RECIPE_TYPE = new RecipeType<>() {};

    public static final MaterialBeaconsRecipe.Serializer MATERIAL_BEACONS_RECIPE_SERIALIZER = new MaterialBeaconsRecipe.Serializer();
    public static final RecipeType<MaterialBeaconsRecipe> MATERIAL_BEACONS_RECIPE_TYPE = new RecipeType<>() {};


    public static void register() {
        registerRecipe("disabled", DISABLED_RECIPE_TYPE, DISABLED_RECIPE_SERIALIZER);
        registerRecipe("material_beacons", MATERIAL_BEACONS_RECIPE_TYPE, MATERIAL_BEACONS_RECIPE_SERIALIZER);
    }

    private static void registerRecipe(String path, RecipeType<?> type, RecipeSerializer<?> serializer) {
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(BTWRSLMod.MOD_ID, path), type);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(BTWRSLMod.MOD_ID, path), serializer);
    }

}
