package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import com.bwt.blocks.BwtBlocks;
import com.bwt.recipes.turntable.TurntableRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.shared_library.util.utils.IdUtils;

import java.util.concurrent.CompletableFuture;

public class TurntableRecipeProvider extends FabricRecipeProvider
{
    public TurntableRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        TurntableRecipe.JsonBuilder.create(Blocks.CLAY, BwtBlocks.unfiredCrucibleBlock)
                .drops(Items.CLAY_BALL).offerTo(exporter, IdUtils.ofBWT("turntable_clay"));

    }
}
