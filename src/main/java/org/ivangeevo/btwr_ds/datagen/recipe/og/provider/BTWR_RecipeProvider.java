package org.ivangeevo.btwr_ds.datagen.recipe.og.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.item.BTWR_Items;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.vegehenna.item.ModItems;

import java.util.concurrent.CompletableFuture;


public class BTWR_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    public BTWR_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    public void generate(RecipeExporter exporter) {
        // BTWR: Core
        this.overrideForBTWR(exporter);
    }



    private void overrideForBTWR(RecipeExporter exporter) {

        // BTWR overwritten recipes for food
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_RAW, 2)
                .input(BwtItems.rawEggItem)
                .input(Items.MILK_BUCKET)
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofBTWR("egg_scrambled_raw"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_RAW)
                .input('E', BwtItems.rawEggItem)
                .input('M', Items.BROWN_MUSHROOM)
                .pattern("EM")
                .pattern("MM")
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofBTWR("mushroom_omelette_raw"));

        CauldronRecipe.JsonBuilder.create().result(ModItems.BOILED_POTATO)
                .ingredient(Items.POTATO)
                .criterion("has_potato", conditionsFromItem(Items.POTATO))
                .offerTo(exporter, ID.ofBTWR("boiled_potato_from_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(ModItems.COOKED_CARROT)
                .ingredient(Items.CARROT)
                .criterion("has_carrot", conditionsFromItem(Items.CARROT))
                .offerTo(exporter, ID.ofBTWR("cooked_carrot_from_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(BTWR_Items.CHOWDER,2)
                .ingredient(ConventionalItemTags.COOKED_FISH_FOODS)
                .ingredient(Items.MILK_BUCKET)
                .ingredient(Items.BOWL, 2)
                .criterion("has_milk_bucket", conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter, ID.ofBTWR("chowder"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.STEAK_DINNER,3)
                .input(Items.COOKED_BEEF)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(ModItems.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(ModItems.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("steak_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.PORK_DINNER,3)
                .input(Items.COOKED_PORKCHOP)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(ModItems.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(ModItems.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("pork_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.WOLF_DINNER,3)
                .input(BwtItems.cookedWolfChopItem)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(ModItems.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(ModItems.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("wolf_dinner"));

        CookingRecipeJsonBuilder.createSmelting(
                Ingredient.ofItems(BTWR_Items.RAW_KEBAB), RecipeCategory.FOOD, BTWR_Items.COOKED_KEBAB, 0.20F, 2500)
                .criterion("has_raw_kebab", conditionsFromItem(BTWR_Items.RAW_KEBAB))
                .offerTo(exporter, ID.ofBTWR("cooked_kebab"));

        CookingRecipeJsonBuilder.createSmoking(
                Ingredient.ofItems(BTWR_Items.RAW_KEBAB), RecipeCategory.FOOD, BTWR_Items.COOKED_KEBAB, 0.30f, 1250)
                .criterion("has_raw_kebab", conditionsFromItem(BTWR_Items.RAW_KEBAB))
                .offerTo(exporter, ID.ofBTWR("cooked_kebab_from_smoking"));

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

        // Blocks
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

        // Items
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.DIAMOND_PLATE)
                .input('S', BwtItems.strapItem)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BwtItems.paddingItem)
                .pattern("SIS")
                .pattern(" P ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofBTWR("diamond_plate"));

    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

}
