package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.core.item.BTWR_Items;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;


public class ST_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public ST_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, SturdyTreesItems.STUMP_REMOVER)
                .input(Items.ROTTEN_FLESH)
                .input(Items.RED_MUSHROOM)
                .input(BTWR_Items.CREEPER_OYSTERS)
                .criterion("has_creeper_oysters", conditionsFromItem(BTWR_Items.CREEPER_OYSTERS))
                .offerTo(exporter, ID.ofST("stump_remover"));
    }

}
