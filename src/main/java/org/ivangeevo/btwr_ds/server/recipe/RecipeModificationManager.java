package org.ivangeevo.btwr_ds.server.recipe;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.resource.LifecycledResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class RecipeModificationManager
{
    public static void initialize() {
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register(RecipeModificationManager::onServerStarted);
    }

    private static void onServerStarted(MinecraftServer server, LifecycledResourceManager resourceManager)
    {
        RecipeManager recipeManager = server.getRecipeManager();
        overrideShapelessRecipes(recipeManager);
    }

    public static void overrideShapelessRecipes(RecipeManager recipeManager)
    {
        forShapeless(Items.CAMPFIRE.getDefaultStack(), "campfire", recipeManager);

    }

    private static void forShapeless(ItemStack result, String recipePath, RecipeManager manager)
    {
        List<RecipeEntry<?>> newRecipes = new ArrayList<>();

        // Example: Create a custom shapeless recipe
        Identifier recipeId = Identifier.ofVanilla(recipePath);

        // Define the ingredients of the recipe
        DefaultedList<Ingredient> ingredients = DefaultedList.of();
        ingredients.set(0, Ingredient.ofItems(Items.STONE));  // Replace with your desired ingredients
        ingredients.set(1, Ingredient.ofItems(Items.STICK));

        // Replace this with your actual recipe implementation
        ShapelessRecipe customRecipe = new ShapelessRecipe(
                "", // Group (can be empty)
                CraftingRecipeCategory.MISC, // Recipe category
                result,
                ingredients
        );

        newRecipes.add(new RecipeEntry<>(recipeId, customRecipe));

        // Now, set the recipes in the RecipeManager
        manager.setRecipes(newRecipes);

    }

    private static void forShaped()
    {

    }

}
