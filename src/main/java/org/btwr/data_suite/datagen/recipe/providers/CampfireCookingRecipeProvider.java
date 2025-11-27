package org.btwr.data_suite.datagen.recipe.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.shared_library.util.utils.RecipeUtils;
import org.btwr.vegehenna.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class CampfireCookingRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    public CampfireCookingRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        // Vegehenna
        CookingRecipeJsonBuilder.createCampfireCooking(
                        Ingredient.ofItems(Items.CARROT),
                        RecipeCategory.FOOD,
                        ModItems.COOKED_CARROT,
                        0.20f,
                        6000)
                .criterion("has_carrot", conditionsFromItem(Items.CARROT))
                .offerTo(exporter, IdUtils.ofVG("cooked_carrot_from_campfire_cooking"));

    }
}