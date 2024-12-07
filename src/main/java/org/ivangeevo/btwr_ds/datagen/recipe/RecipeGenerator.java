package org.ivangeevo.btwr_ds.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.datagen.recipe.provider.*;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends FabricRecipeProvider {

    protected Vanilla_RecipeProvider vanillaRecipeProvider;
    protected DS_RecipeProvider modRecipeProvider;
    protected BWT_RecipeProvider bwtRecipeProvider;
    protected BTWR_RecipeProvider btwrRecipeProvider;
    protected Vegehenna_RecipeProvider vegehennaRecipeProvider;
    protected DisabledRecipeProvider disabledRecipeProvider;

    public RecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
        this.vanillaRecipeProvider = new Vanilla_RecipeProvider(output, registriesFuture);
        this.modRecipeProvider = new DS_RecipeProvider(output, registriesFuture);
        this.bwtRecipeProvider = new BWT_RecipeProvider(output, registriesFuture);
        this.btwrRecipeProvider = new BTWR_RecipeProvider(output, registriesFuture);
        this.vegehennaRecipeProvider = new Vegehenna_RecipeProvider(output, registriesFuture);
        this.disabledRecipeProvider = new DisabledRecipeProvider(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        vanillaRecipeProvider.generate(exporter);
        modRecipeProvider.generate(exporter);
        bwtRecipeProvider.generate(exporter);
        btwrRecipeProvider.generate(exporter);
        vegehennaRecipeProvider.generate(exporter);
        disabledRecipeProvider.generate(exporter);
    }

    @Override
    public CompletableFuture<?> run(DataWriter writer, RegistryWrapper.WrapperLookup wrapperLookup) {
        return CompletableFuture.allOf(super.run(writer, wrapperLookup), disabledRecipeProvider.run(writer, wrapperLookup));
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }
}
