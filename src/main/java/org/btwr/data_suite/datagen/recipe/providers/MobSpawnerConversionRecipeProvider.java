package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.recipes.mob_spawner_conversion.MobSpawnerConversionRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.tough_environment.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class MobSpawnerConversionRecipeProvider extends FabricRecipeProvider {

    public MobSpawnerConversionRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        MobSpawnerConversionRecipe.JsonBuilder.create(ModBlocks.COBBLESTONE_LOOSE)
                .convertsTo(Blocks.MOSSY_COBBLESTONE)
                .criterion("has_cobblestone_loose", conditionsFromItem(ModBlocks.COBBLESTONE_LOOSE))
                .offerTo(exporter, IdUtils.ofDS("mob_spawner_conversion_from_cobblestone_loose_to_mossy_cobblestone"));

        MobSpawnerConversionRecipe.JsonBuilder.create(ModBlocks.SLAB_COBBLESTONE_LOOSE)
                .convertsTo(Blocks.MOSSY_COBBLESTONE_SLAB)
                .criterion("has_slab_cobblestone_loose", conditionsFromItem(ModBlocks.SLAB_COBBLESTONE_LOOSE))
                .offerTo(exporter, IdUtils.ofDS("mob_spawner_conversion_from_slab_cobblestone_loose_to_mossy_cobblestone_slab"));

        MobSpawnerConversionRecipe.JsonBuilder.create(ModBlocks.COBBLESTONE_LOOSE_STAIRS)
                .convertsTo(Blocks.MOSSY_COBBLESTONE_STAIRS)
                .criterion("has_cobblestone_loose_stairs", conditionsFromItem(ModBlocks.COBBLESTONE_LOOSE_STAIRS))
                .offerTo(exporter, IdUtils.ofDS("mob_spawner_conversion_from_cobblestone_loose_stairs_to_mossy_cobblestone_stairs"));
    }

}