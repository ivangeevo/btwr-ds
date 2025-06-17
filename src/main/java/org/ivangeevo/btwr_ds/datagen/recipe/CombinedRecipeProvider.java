package org.ivangeevo.btwr_ds.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.datagen.recipe.providers.DisabledRecipeProvider;
import org.ivangeevo.btwr_ds.datagen.recipe.providers.PackingRecipeProvider;
import org.ivangeevo.btwr_ds.datagen.recipe.providers.*;

import java.util.concurrent.CompletableFuture;

public class CombinedRecipeProvider extends FabricRecipeProvider {

    protected ShapelessRecipeProvider shapelessRecipeProvider;
    protected ShapedRecipeProvider shapedRecipeProvider;
    protected SoulforgedRecipeProvider soulforgedRecipeProvider;
    protected CampfireCookingRecipeProvider campfireCookingRecipeProvider;
    protected OvenCookingRecipeProvider ovenCookingRecipeProvider;
    protected MillstoneRecipeProvider millstoneRecipeProvider;
    protected SawRecipeProvider sawRecipeProvider;
    protected CrucibleRecipeProvider crucibleRecipeProvider;
    protected StokedCrucibleRecipeProvider stokedCrucibleRecipeProvider;
    protected CauldronRecipeProvider cauldronRecipeProvider;
    protected StokedCauldronRecipeProvider stokedCauldronRecipeProvider;
    protected HopperFilteringRecipeProvider hopperFilteringRecipeProvider;
    protected TurntableRecipeProvider turntableRecipeProvider;
    protected SoulBottlingRecipeProvider soulBottlingRecipeProvider;
    protected KilnRecipeProvider kilnRecipeProvider;
    protected PackingRecipeProvider packingRecipeProvider;
    protected MobSpawnerConversionRecipeProvider mobSpawnerRecipeProvider;

    protected DisabledRecipeProvider disabledRecipeProvider;

    public CombinedRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
        this.shapelessRecipeProvider = new ShapelessRecipeProvider(output, registriesFuture);
        this.shapedRecipeProvider = new ShapedRecipeProvider(output, registriesFuture);
        this.soulforgedRecipeProvider = new SoulforgedRecipeProvider(output, registriesFuture);
        this.campfireCookingRecipeProvider = new CampfireCookingRecipeProvider(output, registriesFuture);
        this.ovenCookingRecipeProvider = new OvenCookingRecipeProvider(output, registriesFuture);
        this.millstoneRecipeProvider = new MillstoneRecipeProvider(output, registriesFuture);
        this.sawRecipeProvider = new SawRecipeProvider(output, registriesFuture);
        this.cauldronRecipeProvider = new CauldronRecipeProvider(output, registriesFuture);
        this.stokedCauldronRecipeProvider = new StokedCauldronRecipeProvider(output, registriesFuture);
        this.hopperFilteringRecipeProvider = new HopperFilteringRecipeProvider(output, registriesFuture);
        this.turntableRecipeProvider = new TurntableRecipeProvider(output, registriesFuture);
        this.crucibleRecipeProvider = new CrucibleRecipeProvider(output, registriesFuture);
        this.stokedCrucibleRecipeProvider = new StokedCrucibleRecipeProvider(output, registriesFuture);
        this.kilnRecipeProvider = new KilnRecipeProvider(output, registriesFuture);
        this.soulBottlingRecipeProvider = new SoulBottlingRecipeProvider(output, registriesFuture);
        this.packingRecipeProvider = new PackingRecipeProvider(output, registriesFuture);
        this.mobSpawnerRecipeProvider = new MobSpawnerConversionRecipeProvider(output, registriesFuture);

        this.disabledRecipeProvider = new DisabledRecipeProvider(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        shapelessRecipeProvider.generate(exporter);
        shapedRecipeProvider.generate(exporter);
        soulforgedRecipeProvider.generate(exporter);
        campfireCookingRecipeProvider.generate(exporter);
        ovenCookingRecipeProvider.generate(exporter);
        millstoneRecipeProvider.generate(exporter);
        sawRecipeProvider.generate(exporter);
        cauldronRecipeProvider.generate(exporter);
        stokedCauldronRecipeProvider.generate(exporter);
        crucibleRecipeProvider.generate(exporter);
        stokedCrucibleRecipeProvider.generate(exporter);
        hopperFilteringRecipeProvider.generate(exporter);
        turntableRecipeProvider.generate(exporter);
        soulBottlingRecipeProvider.generate(exporter);
        kilnRecipeProvider.generate(exporter);
        packingRecipeProvider.generate(exporter);
        mobSpawnerRecipeProvider.generate(exporter);

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
