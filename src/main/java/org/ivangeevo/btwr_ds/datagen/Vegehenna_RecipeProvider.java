package org.ivangeevo.btwr_ds.datagen;

import btwr.btwrsl.lib.util.utils.RecipeProviderUtils;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.vegehenna.item.ModItems;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class Vegehenna_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public Vegehenna_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    // recipes to remove are only for ones that we don't overwrite with another ingredients/output.
    // the ones we overwrite are in the override methods, and this mod is in the generateForMod() method
    @Override
    public void generate(RecipeExporter exporter)
    {
        // Recipes that get removed
        this.generateRecipesToRemove(exporter);

        // Vegehenna
        this.overrideForVegehenna(exporter);
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
    }

    private void generateRecipesToRemove(RecipeExporter exporter) {
        /** Vegehenna recipes to remove **/
        disableVG(exporter, "flour");
    }

}
