package org.ivangeevo.btwr_ds.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.datagen.recipe.og.DisabledRecipeProvider;
import org.ivangeevo.btwr_ds.datagen.recipe.og.provider.*;

import java.util.concurrent.CompletableFuture;

public class OGCombinedRecipeProvider extends FabricRecipeProvider {

    //protected org.ivangeevo.btwr_ds.datagen.recipe.og.Vanilla_RecipeProvider vanillaRecipeProvider;
    //protected org.ivangeevo.btwr_ds.datagen.recipe.og.DS_RecipeProvider modRecipeProvider;
    //protected org.ivangeevo.btwr_ds.datagen.recipe.og.BWT_RecipeProvider bwtRecipeProvider;
    //protected org.ivangeevo.btwr_ds.datagen.recipe.og.BTWR_RecipeProvider btwrRecipeProvider;
    //protected org.ivangeevo.btwr_ds.datagen.recipe.og.VG_RecipeProvider vegehennaRecipeProvider;
    //protected org.ivangeevo.btwr_ds.datagen.recipe.og.TE_RecipeProvider toughEnvironmentRecipeProvider;
    protected SS_RecipeProvider selfSustainableRecipeProvider;
    protected org.ivangeevo.btwr_ds.datagen.recipe.og.DisabledRecipeProvider disabledRecipeProvider;

    public OGCombinedRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
        //this.vanillaRecipeProvider = new org.ivangeevo.btwr_ds.datagen.recipe.og.Vanilla_RecipeProvider(output, registriesFuture);
        //this.modRecipeProvider = new org.ivangeevo.btwr_ds.datagen.recipe.og.DS_RecipeProvider(output, registriesFuture);
        //this.bwtRecipeProvider = new org.ivangeevo.btwr_ds.datagen.recipe.og.BWT_RecipeProvider(output, registriesFuture);
        //this.btwrRecipeProvider = new org.ivangeevo.btwr_ds.datagen.recipe.og.BTWR_RecipeProvider(output, registriesFuture);
        //this.vegehennaRecipeProvider = new org.ivangeevo.btwr_ds.datagen.recipe.og.VG_RecipeProvider(output, registriesFuture);
        //this.sturdyTreesRecipeProvider = new org.ivangeevo.btwr_ds.datagen.recipe.og.ST_RecipeProvider(output, registriesFuture);
        //this.toughEnvironmentRecipeProvider = new org.ivangeevo.btwr_ds.datagen.recipe.og.TE_RecipeProvider(output, registriesFuture);
        this.selfSustainableRecipeProvider = new SS_RecipeProvider(output, registriesFuture);
        this.disabledRecipeProvider = new DisabledRecipeProvider(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        //vanillaRecipeProvider.generate(exporter);
        //modRecipeProvider.generate(exporter);
        //bwtRecipeProvider.generate(exporter);
       //btwrRecipeProvider.generate(exporter);
        //vegehennaRecipeProvider.generate(exporter);
        //sturdyTreesRecipeProvider.generate(exporter);
        //toughEnvironmentRecipeProvider.generate(exporter);
        selfSustainableRecipeProvider.generate(exporter);
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
