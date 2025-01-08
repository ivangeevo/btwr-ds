package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.vegehenna.item.ModItems;

import java.util.concurrent.CompletableFuture;


public class Vegehenna_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public Vegehenna_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter) {
        this.disableForVegehenna(exporter);
        this.overrideForVegehenna(exporter);
    }

    private void disableForVegehenna(RecipeExporter exporter) {
        disableVG(exporter,"cake_from_smoking");
    }

    private void overrideForVegehenna(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PASTRY_UNCOOKED_CAKE)
                .input('E', BwtItems.rawEggItem)
                .input('M', Items.MILK_BUCKET)
                .input('F', BwtItems.flourItem)
                .input('S', Items.SUGAR)
                .pattern("SSS")
                .pattern("MEM")
                .pattern("FFF")
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_cake"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PASTRY_UNCOOKED_PUMPKIN_PIE)
                .input(BwtItems.rawEggItem)
                .input(Items.SUGAR)
                .input(Items.PUMPKIN)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .criterion("flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_pumpkin_pie"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BREAD_DOUGH)
                .input('F', BwtItems.flourItem)
                .pattern("F ")
                .pattern("FF")
                .criterion("flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, ID.ofVG("bread_dough"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PASTRY_UNCOOKED_COOKIES)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(ModItems.CHOCOLATE)
                .criterion("has_chocolate", conditionsFromItem(ModItems.CHOCOLATE))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_cookies"));

        CookingRecipeJsonBuilder.createSmoking(
                Ingredient.ofItems(ModItems.BREAD_DOUGH),
                RecipeCategory.FOOD,
                Items.BREAD,
                0.35f,
                2500)
                .criterion("has_bread_dough", conditionsFromItem(ModItems.BREAD_DOUGH))
                .offerTo(exporter, ID.ofVG("bread_from_smoking")
                );

    }

}
