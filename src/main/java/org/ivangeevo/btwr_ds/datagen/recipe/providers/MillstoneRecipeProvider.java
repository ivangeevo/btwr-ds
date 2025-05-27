package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.core.item.BTWR_Items;
import com.bwt.items.BwtItems;
import com.bwt.recipes.mill_stone.MillStoneRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class MillstoneRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{
    public MillstoneRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        // DS recipes
        /**
         ModernMillStoneRecipe.JsonBuilder.create().result(BTWR_Items.LEATHER_SCOURED_CUT)
         .ingredient(BTWR_Items.LEATHER_CUT)
         .criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT))
         .offerTo(exporter, ID.ofDS("leather_scoured_cut_from_mill_stone"));

         ModernMillStoneRecipe.JsonBuilder.create().result(Items.BLAZE_POWDER,2)
         .ingredient(Items.BLAZE_ROD)
         .criterion("has_blaze_rod", conditionsFromItem(Items.BLAZE_ROD))
         .offerTo(exporter, ID.ofDS("blaze_powder_from_mill_stone"));

         ModernMillStoneRecipe.JsonBuilder.create().result(BwtItems.hempFiberItem, 4)
         .ingredient(BTWR_Items.HEMP_LEAVES)
         .criterion("has_hemp_leaves", conditionsFromItem(BwtItems.hempItem))
         .offerTo(exporter, ID.ofDS("hemp_fiber_from_milling_hemp"));

         ModernMillStoneRecipe.JsonBuilder.create().result(BwtItems.coalDustItem,2)
         .ingredient(Items.CHARCOAL)
         .criterion("has_charcoal", conditionsFromItem(Items.CHARCOAL))
         .offerTo(exporter, ID.ofDS("coal_dust_from_milling_charcoal"));

         ModernMillStoneRecipe.JsonBuilder.create().result(org.ivangeevo.vegehenna.item.ModItems.COCOA_POWDER)
         .ingredient(Items.COCOA_BEANS)
         .criterion("has_cocoa_beans", conditionsFromItem(Items.COCOA_BEANS))
         .offerTo(exporter, ID.ofDS("cocoa_powder_from_milling_cocoa_beans"));
         **/

        MillStoneRecipe.JsonBuilder.create().result(BTWR_Items.LEATHER_SCOURED_CUT)
                .ingredient(BTWR_Items.LEATHER_CUT)
                .criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT))
                .offerTo(exporter, ID.ofDS("leather_scoured_cut_from_mill_stone"));

        MillStoneRecipe.JsonBuilder.create().result(Items.BLAZE_POWDER,2)
                .ingredient(Items.BLAZE_ROD)
                .criterion("has_blaze_rod", conditionsFromItem(Items.BLAZE_ROD))
                .offerTo(exporter, ID.ofDS("blaze_powder_from_mill_stone"));

        MillStoneRecipe.JsonBuilder.create().result(BwtItems.hempFiberItem,4)
                .ingredient(BwtItems.hempItem)
                .criterion("has_hemp_leaves", conditionsFromItem(BwtItems.hempItem))
                .offerTo(exporter, ID.ofDS("hemp_fiber_from_milling_hemp"));

        MillStoneRecipe.JsonBuilder.create().result(BwtItems.coalDustItem,2)
                .ingredient(Items.CHARCOAL)
                .criterion("has_charcoal", conditionsFromItem(Items.CHARCOAL))
                .offerTo(exporter, ID.ofDS("coal_dust_from_milling_charcoal"));

        MillStoneRecipe.JsonBuilder.create().result(org.ivangeevo.vegehenna.item.ModItems.COCOA_POWDER)
                .ingredient(Items.COCOA_BEANS)
                .criterion("has_cocoa_beans", conditionsFromItem(Items.COCOA_BEANS))
                .offerTo(exporter, ID.ofDS("cocoa_powder_from_milling_cocoa_beans"));

    }
}
