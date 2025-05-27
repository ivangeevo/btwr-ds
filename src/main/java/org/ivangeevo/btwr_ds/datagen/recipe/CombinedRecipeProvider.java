package org.ivangeevo.btwr_ds.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.datagen.recipe.og.DisabledRecipeProvider;
import org.ivangeevo.btwr_ds.datagen.recipe.providers.PackingRecipeProvider;
import org.ivangeevo.btwr_ds.datagen.recipe.providers.*;

import java.util.concurrent.CompletableFuture;

public class CombinedRecipeProvider extends FabricRecipeProvider {

    protected ShapelessRecipeProvider shapelessRecipeProvider;
    protected ShapedRecipeProvider shapedRecipeProvider;
    protected CampfireCookingRecipeProvider campfireCookingRecipeProvider;
    protected OvenCookingRecipeProvider ovenCookingRecipeProvider;
    protected CrucibleRecipeProvider crucibleRecipeProvider;
    protected CauldronRecipeProvider cauldronRecipeProvider;
    protected HopperFilteringRecipeProvider hopperFilteringRecipeProvider;
    protected SoulBottlingRecipeProvider soulBottlingRecipeProvider;
    protected KilnRecipeProvider kilnRecipeProvider;
    protected PackingRecipeProvider packingRecipeProvider;
    protected MobSpawnerConversionRecipeProvider mobSpawnerRecipeProvider;

    protected org.ivangeevo.btwr_ds.datagen.recipe.og.BWT_RecipeProvider bwtRecipeProvider;
    protected DisabledRecipeProvider disabledRecipeProvider;

    public CombinedRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
        this.shapelessRecipeProvider = new ShapelessRecipeProvider(output, registriesFuture);
        this.shapedRecipeProvider = new ShapedRecipeProvider(output, registriesFuture);
        this.campfireCookingRecipeProvider = new CampfireCookingRecipeProvider(output, registriesFuture);
        this.ovenCookingRecipeProvider = new OvenCookingRecipeProvider(output, registriesFuture);
        this.crucibleRecipeProvider = new CrucibleRecipeProvider(output, registriesFuture);
        this.cauldronRecipeProvider = new CauldronRecipeProvider(output, registriesFuture);
        this.hopperFilteringRecipeProvider = new HopperFilteringRecipeProvider(output, registriesFuture);
        this.soulBottlingRecipeProvider = new SoulBottlingRecipeProvider(output, registriesFuture);
        this.kilnRecipeProvider = new KilnRecipeProvider(output, registriesFuture);
        this.packingRecipeProvider = new PackingRecipeProvider(output, registriesFuture);
        this.mobSpawnerRecipeProvider = new MobSpawnerConversionRecipeProvider(output, registriesFuture);
        this.bwtRecipeProvider = new org.ivangeevo.btwr_ds.datagen.recipe.og.BWT_RecipeProvider(output, registriesFuture);
        this.disabledRecipeProvider = new DisabledRecipeProvider(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        // TODO: Add soulforged recipe for the chopping block when it's added to BTWR: Core
        /**
         SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BTWR_Blocks.CHOPPING_BLOCK)
         .input('B', BTWR_Items.STONE_BRICK)
         .pattern("B  B")
         .pattern("B  B")
         .pattern("BBBB")
         .criterion("has_stone_brick", conditionsFromItem(BTWR_Items.STONE_BRICK))
         .offerTo(exporter, ID.ofBTWR("chopping_block"));
         **/

        shapelessRecipeProvider.generate(exporter);
        shapedRecipeProvider.generate(exporter);
        campfireCookingRecipeProvider.generate(exporter);
        ovenCookingRecipeProvider.generate(exporter);
        crucibleRecipeProvider.generate(exporter);
        cauldronRecipeProvider.generate(exporter);
        hopperFilteringRecipeProvider.generate(exporter);
        soulBottlingRecipeProvider.generate(exporter);
        kilnRecipeProvider.generate(exporter);
        packingRecipeProvider.generate(exporter);
        mobSpawnerRecipeProvider.generate(exporter);
        bwtRecipeProvider.generate(exporter);
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
