package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import com.bwt.recipes.kiln.KilnRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class KilnRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{
    public KilnRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        // DS recipes
        KilnRecipe.JsonBuilder.create(net.ivangeevo.self_sustainable.block.ModBlocks.BRICK_UNFIRED).result(Items.BRICK)
                .criterion("has_brick_unfired", conditionsFromItem(net.ivangeevo.self_sustainable.block.ModBlocks.BRICK_UNFIRED))
                .offerTo(exporter, ID.ofDS("kiln_cook_brick"));

        Block breadDoughBlock = org.ivangeevo.vegehenna.block.ModBlocks.BREAD_DOUGH;
        KilnRecipe.JsonBuilder.create(breadDoughBlock).result(Items.BREAD)
                .criterion("has_bread_dough", conditionsFromItem(org.ivangeevo.vegehenna.item.ModItems.BREAD_DOUGH))
                .offerTo(exporter, ID.ofDS("kiln_cook_bread"));

        Block uncookedCakeBlock = org.ivangeevo.vegehenna.block.ModBlocks.UNCOOKED_CAKE;
        KilnRecipe.JsonBuilder.create(uncookedCakeBlock).result(Items.CAKE)
                .criterion("has_uncooked_cake_pastry", conditionsFromItem(org.ivangeevo.vegehenna.item.ModItems.PASTRY_UNCOOKED_CAKE))
                .offerTo(exporter, ID.ofDS("kiln_cook_cake"));

    }
}
