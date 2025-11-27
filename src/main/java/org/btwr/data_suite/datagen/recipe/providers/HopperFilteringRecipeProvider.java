package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.recipes.hopper_filter.HopperFilterRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.data_suite.item.BTWRDS_Items;

import java.util.concurrent.CompletableFuture;

public class HopperFilteringRecipeProvider extends FabricRecipeProvider {

    public HopperFilteringRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // DS recipes
        HopperFilterRecipe.JsonBuilder.create()
                .filter(Items.SOUL_SAND)
                .ingredient(Items.GLOWSTONE_DUST)
                .byproduct(BTWRDS_Items.BRIMSTONE)
                .soulCount(1)
                .offerTo(exporter);
    }

}