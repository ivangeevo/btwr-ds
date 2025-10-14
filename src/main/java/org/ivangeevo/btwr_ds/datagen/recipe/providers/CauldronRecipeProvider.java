package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.core.item.BTWR_Items;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.ivangeevo.vegehenna.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class CauldronRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    public CauldronRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        // DS recipes
        CauldronRecipe.JsonBuilder.createFood().result(Items.RABBIT_STEW, 5)
                .ingredient(Items.COOKED_RABBIT)
                .ingredient(ModItems.COOKED_CARROT)
                .ingredient(ModItems.BOILED_POTATO)
                .ingredient(Items.BOWL, 5)
                .ingredient(Items.BROWN_MUSHROOM, 3)
                .ingredient(BwtItems.flourItem)
                .criterion("has_boiled_potato", conditionsFromItem(ModItems.BOILED_POTATO))
                .offerTo(exporter, ID.ofBTWR("rabbit_stew_from_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(Items.BEETROOT_SOUP, 3)
                .ingredient(Items.BEETROOT, 6)
                .ingredient(BwtItems.flourItem, 2)
                .ingredient(Items.BOWL, 3)
                .criterion("has_beetroot", conditionsFromItem(Items.BLAZE_POWDER))
                .offerTo(exporter, ID.ofDS("beetroot_soup_from_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(BTWRDS_Items.ELEMENT)
                .ingredient(Items.BLAZE_POWDER)
                .ingredient(Items.REDSTONE)
                .ingredient(ConventionalItemTags.STRINGS)
                .criterion("has_blaze_powder", conditionsFromItem(Items.BLAZE_POWDER))
                .offerTo(exporter, ID.ofDS("element_from_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(Items.GUNPOWDER, 2)
                .ingredient(org.ivangeevo.animageddon.item.ModItems.NITRE)
                .ingredient(BTWRDS_Items.BRIMSTONE)
                .ingredient(BwtItems.coalDustItem)
                .criterion("has_brimstone", conditionsFromItem(BTWRDS_Items.BRIMSTONE))
                .offerTo(exporter, ID.ofDS("gunpowder_from_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(org.tough_environment.item.ModItems.NETHER_SLUDGE)
                .ingredient(BwtItems.potashItem)
                .ingredient(BwtItems.hellfireDustItem, 8)
                .criterion("has_hellfire_dust", conditionsFromItem(BwtItems.hellfireDustItem))
                .offerTo(exporter, ID.ofDS("nether_sludge_from_stoked_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(ModItems.CHOCOLATE,2)
                .ingredient(org.ivangeevo.vegehenna.item.ModItems.COCOA_POWDER)
                .ingredient(Items.SUGAR)
                .ingredient(Items.MILK_BUCKET)
                .criterion("has_cocoa_powder", conditionsFromItem(ModItems.COCOA_POWDER))
                .offerTo(exporter, ID.ofDS("chocolate_from_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(Items.MUSHROOM_STEW,2)
                .ingredient(Items.BROWN_MUSHROOM, 3)
                .ingredient(Items.MILK_BUCKET)
                .ingredient(Items.BOWL,2)
                .criterion("has_milk_bucket", conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter, ID.ofDS("cream_of_mushroom"));


        // BTWR: Core
        CauldronRecipe.JsonBuilder.createFood().result(ModItems.BOILED_POTATO)
                .ingredient(Items.POTATO)
                .criterion("has_potato", conditionsFromItem(Items.POTATO))
                .offerTo(exporter, ID.ofBTWR("boiled_potato_from_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(ModItems.COOKED_CARROT)
                .ingredient(Items.CARROT)
                .criterion("has_carrot", conditionsFromItem(Items.CARROT))
                .offerTo(exporter, ID.ofBTWR("cooked_carrot_from_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(BTWR_Items.CHOWDER,2)
                .ingredient(ConventionalItemTags.COOKED_FISH_FOODS)
                .ingredient(Items.MILK_BUCKET)
                .ingredient(Items.BOWL, 2)
                .criterion("has_milk_bucket", conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter, ID.ofBTWR("chowder"));

        CauldronRecipe.JsonBuilder.createFood().result(BTWR_Items.CHICKEN_SOUP, 3)
                .ingredient(Items.COOKED_CHICKEN)
                .ingredient(ModItems.COOKED_CARROT)
                .ingredient(ModItems.BOILED_POTATO)
                .ingredient(Items.BOWL, 3)
                .criterion("has_boiled_potato", conditionsFromItem(ModItems.BOILED_POTATO))
                .offerTo(exporter, ID.ofBTWR("chicken_soup_from_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(BTWR_Items.HEARTY_STEW, 5)
                .ingredient(ConventionalItemTags.COOKED_MEAT_FOODS)
                .ingredient(ModItems.COOKED_CARROT)
                .ingredient(ModItems.BOILED_POTATO)
                .ingredient(Items.BOWL, 5)
                .ingredient(Items.BROWN_MUSHROOM, 3)
                .ingredient(BwtItems.flourItem)
                .criterion("has_boiled_potato", conditionsFromItem(ModItems.BOILED_POTATO))
                .offerTo(exporter, ID.ofBTWR("hearty_stew_from_cauldron"));

        // Better With Time
        this.createTannedLeatherRecipes(exporter);
    }

    private void createTannedLeatherRecipes(RecipeExporter exporter) {
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_JUNGLE, 2);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_DARK_OAK, 2);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_SPRUCE, 3);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_MANGROVE, 3);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_CHERRY, 3);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_OAK, 5);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_ACACIA, 5);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_BIRCH, 8);
        this.leatherRecipeBuilder(exporter, BTWRDS_Items.BARK_BLOOD_WOOD, 8);
    }

    /** Creates a tanned leather recipe by only passing the bark item and the amount **/
    private void leatherRecipeBuilder(RecipeExporter exporter, Item barkItem, int count) {
        CauldronRecipe.JsonBuilder.create().result(BwtItems.tannedLeatherItem)
                .ingredient(BwtItems.scouredLeatherItem)
                .ingredient(BwtItems.dungItem)
                .ingredient(barkItem, count)
                .criterion("has_scoured_leather", conditionsFromItem(BwtItems.scouredLeatherItem))
                .offerTo(exporter, ID.ofBWT("tanned_leather_with_" + extractName(barkItem) + "_in_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(BTWR_Items.LEATHER_TANNED_CUT,2)
                .ingredient(BTWR_Items.LEATHER_SCOURED_CUT,2)
                .ingredient(BwtItems.dungItem)
                .ingredient(barkItem, count)
                .criterion("has_scoured_leather", conditionsFromItem(BTWR_Items.LEATHER_SCOURED_CUT))
                .offerTo(exporter, ID.ofBWT("tanned_leather_cut_from_leather_scoured_cut_with_" + extractName(barkItem) + "_in_cauldron"));
    }
}
