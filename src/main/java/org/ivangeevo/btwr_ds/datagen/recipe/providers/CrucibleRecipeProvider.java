package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.core.item.BTWR_Items;
import com.bwt.recipes.cooking_pots.StokedCrucibleRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.tough_environment.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

import static org.tough_environment.item.ModItems.CHISEL_DIAMOND;
import static org.tough_environment.item.ModItems.CHISEL_IRON;

public class CrucibleRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{
    public CrucibleRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // Crucible recipes


        // Stoked Crucible recipes
        StokedCrucibleRecipe.JsonBuilder.create().result(ModBlocks.WHITE_STONE.asItem())
                .ingredient(ModBlocks.WHITE_COBBLESTONE.asItem())
                .criterion("has_white_cobblestone", conditionsFromItem(ModBlocks.WHITE_COBBLESTONE.asItem()))
                .offerTo(exporter, ID.ofDS("white_stone_from_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(BTWR_Items.DIAMOND_INGOT,2)
                .ingredient(BTWR_Items.DIAMOND_SHEARS)
                .criterion("has_diamond_shears", conditionsFromItem(BTWR_Items.DIAMOND_SHEARS))
                .offerTo(exporter, ID.ofDS("smelt_diamond_shears_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(BTWR_Items.DIAMOND_INGOT)
                .ingredient(CHISEL_DIAMOND)
                .criterion("has_chisel_diamond", conditionsFromItem(CHISEL_DIAMOND))
                .offerTo(exporter, ID.ofDS("smelt_chisel_diamond_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(Items.IRON_NUGGET)
                .ingredient(CHISEL_IRON)
                .criterion("has_chisel_iron", conditionsFromItem(CHISEL_IRON))
                .offerTo(exporter, ID.ofDS("smelt_chisel_iron_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(Items.IRON_NUGGET,3)
                .ingredient(Items.SHIELD)
                .criterion("has_shield", conditionsFromItem(Items.SHIELD))
                .offerTo(exporter, ID.ofDS("smelt_shield_in_crucible"));

    }
}
