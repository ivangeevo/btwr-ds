package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.StokedCauldronRecipe;
import com.google.common.collect.Maps;
import org.btwr.sturdy_trees.tag.SturdyTreesTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Util;
import org.btwr.core.item.BTWR_Items;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.shared_library.util.utils.RecipeUtils;
import org.btwr.data_suite.item.BTWRDS_Items;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.btwr.vegehenna.item.ModItems.*;

public class StokedCauldronRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    public StokedCauldronRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // Glue
        Map<Item, Integer> SINGLE_COUNT_TO_GLUE_AMOUNTS = Util.make(Maps.newHashMap(), map -> {

            // Regular leathers
            map.put(Items.LEATHER_HELMET, 2);
            map.put(Items.LEATHER_CHESTPLATE, 4);
            map.put(Items.LEATHER_LEGGINGS, 3);
            map.put(Items.LEATHER_BOOTS, 2);
            map.put(Items.SADDLE, 2);

            // Leathers
            map.put(Items.LEATHER, 1);
            map.put(BwtItems.scouredLeatherItem, 1);
            map.put(BwtItems.tannedLeatherItem, 1);
            // TODO tanned leather armor, gimp armor, breeding harness
        });

        SINGLE_COUNT_TO_GLUE_AMOUNTS.forEach((key, value) ->
                StokedCauldronRecipe.JsonBuilder.create()
                        .ingredient(key)
                        .result(BwtItems.glueItem, value)
                        .offerTo(exporter, IdUtils.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(key)))
        );

        Map<Item, Integer> DOUBLE_COUNT_TO_GLUE_AMOUNTS = Util.make(Maps.newHashMap(), map -> {

            // Cut leathers
            map.put(BTWR_Items.LEATHER_CUT, 1);
            map.put(BTWR_Items.LEATHER_SCOURED_CUT, 1);
            map.put(BTWR_Items.LEATHER_TANNED_CUT, 1);
        });
        DOUBLE_COUNT_TO_GLUE_AMOUNTS.forEach((key, value) -> StokedCauldronRecipe.JsonBuilder.create().ingredient(key,2).result(BwtItems.glueItem, value).offerTo(exporter, IdUtils.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(key))));

        StokedCauldronRecipe.JsonBuilder.create()
                .ingredient(Items.RABBIT_HIDE)
                .ingredient(Items.RABBIT_HIDE)
                .ingredient(Items.RABBIT_HIDE)
                .ingredient(Items.RABBIT_HIDE)
                .result(BwtItems.glueItem, 1)
                .offerTo(exporter, IdUtils.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.RABBIT_HIDE)));


        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.strapItem, 8).result(BwtItems.glueItem, 1).offerTo(exporter, IdUtils.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(BwtItems.strapItem)));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.beltItem, 2).result(BwtItems.glueItem, 1).offerTo(exporter, IdUtils.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(BwtItems.beltItem)));

        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.BOOK, 2).result(BwtItems.glueItem, 1).offerTo(exporter, IdUtils.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.BOOK)));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.WRITABLE_BOOK, 2).result(BwtItems.glueItem, 1).offerTo(exporter, IdUtils.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.WRITABLE_BOOK)));

        // Tallow - unmodified

        // Potash
        StokedCauldronRecipe.JsonBuilder.create()
                .ingredient(SturdyTreesTags.Items.BARK_ITEMS, 64)
                .result(BwtItems.potashItem)
                .offerTo(exporter, IdUtils.ofBWT("potash").withSuffixedPath("_from_cauldron_rendering_bark"));

        StokedCauldronRecipe.JsonBuilder.create()
                .ingredient(STRAW, 16)
                .result(BwtItems.potashItem)
                .offerTo(exporter, IdUtils.ofBWT("potash").withSuffixedPath("_from_cauldron_rendering_straw"));

        // Arrows
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.ARROW).result(Items.FLINT).result(Items.STICK).result(Items.FEATHER).offerTo(exporter, IdUtils.ofBWT("cauldron_rendering_arrows"));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.rottedArrowItem).result(Items.FLINT).offerTo(exporter, IdUtils.ofBWT("cauldron_rendering_rotted_arrows"));

        // Misc
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BTWRDS_Items.ENDER_SLAG).result(BTWRDS_Items.SOUL_FLUX).result(BTWRDS_Items.BRIMSTONE).offerTo(exporter, IdUtils.ofBWT("cauldron_rendering_ender_slag"));

    }

}