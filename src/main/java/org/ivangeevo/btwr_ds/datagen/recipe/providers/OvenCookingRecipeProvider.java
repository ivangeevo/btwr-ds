package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.core.item.BTWR_Items;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.ivangeevo.self_sustainable.block.ModBlocks;
import net.ivangeevo.self_sustainable.data.server.recipe.ModCookingRecipeJsonBuilder;
import net.ivangeevo.self_sustainable.recipe.OvenCookingRecipe;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;

import static org.ivangeevo.vegehenna.item.ModItems.*;

public class OvenCookingRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    public OvenCookingRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        final String foc = "_from_oven_cooking";

        // Existing
        // Food
        int FOOD_COOK_TIME = 1600;
        offerOvenCooking(Items.COOKED_CHICKEN, RecipeCategory.FOOD, Ingredient.ofItems(Items.CHICKEN), 0.15f, FOOD_COOK_TIME).criterion("has_chicken", RecipeProvider.conditionsFromItem(Items.CHICKEN)).offerTo(exporter, ID.ofSS("cooked_chicken" + foc));
        offerOvenCooking(Items.COOKED_BEEF, RecipeCategory.FOOD, Ingredient.ofItems(Items.BEEF), 0.15f, FOOD_COOK_TIME).criterion("has_beef", RecipeProvider.conditionsFromItem(Items.BEEF)).offerTo(exporter, ID.ofSS("cooked_beef" + foc));
        offerOvenCooking(Items.COOKED_PORKCHOP, RecipeCategory.FOOD, Ingredient.ofItems(Items.PORKCHOP), 0.15f, FOOD_COOK_TIME).criterion("has_porkchop", RecipeProvider.conditionsFromItem(Items.PORKCHOP)).offerTo(exporter, ID.ofSS("cooked_porkchop" + foc));
        offerOvenCooking(Items.COOKED_MUTTON, RecipeCategory.FOOD, Ingredient.ofItems(Items.MUTTON), 0.15f, FOOD_COOK_TIME).criterion("has_mutton", RecipeProvider.conditionsFromItem(Items.MUTTON)).offerTo(exporter, ID.ofSS("cooked_mutton" + foc));
        offerOvenCooking(Items.COOKED_RABBIT, RecipeCategory.FOOD, Ingredient.ofItems(Items.RABBIT), 0.10f, FOOD_COOK_TIME).criterion("has_rabbit", RecipeProvider.conditionsFromItem(Items.RABBIT)).offerTo(exporter, ID.ofSS("cooked_rabbit" + foc));
        offerOvenCooking(Items.COOKED_COD, RecipeCategory.FOOD, Ingredient.ofItems(Items.COD), 0.10f, FOOD_COOK_TIME).criterion("has_cod", RecipeProvider.conditionsFromItem(Items.COD)).offerTo(exporter, ID.ofSS("cooked_cod" + foc));
        offerOvenCooking(Items.COOKED_SALMON, RecipeCategory.FOOD, Ingredient.ofItems(Items.SALMON), 0.10f, FOOD_COOK_TIME).criterion("has_salmon", RecipeProvider.conditionsFromItem(Items.SALMON)).offerTo(exporter, ID.ofSS("cooked_salmon" + foc));
        offerOvenCooking(Items.BAKED_POTATO, RecipeCategory.FOOD, Ingredient.ofItems(Items.POTATO), 0.10f, FOOD_COOK_TIME).criterion("has_potato", RecipeProvider.conditionsFromItem(Items.POTATO)).offerTo(exporter, ID.ofSS("baked_potato" + foc));

        // Ores
        int ORE_COOK_TIME = 12800;
        offerOvenCooking(Items.IRON_NUGGET, RecipeCategory.MISC, Ingredient.ofItems(Items.RAW_IRON), 0.25f, ORE_COOK_TIME).criterion("has_raw_iron", RecipeProvider.conditionsFromItem(Items.RAW_IRON)).offerTo(exporter, ID.ofSS("iron_nugget" + foc));
        offerOvenCooking(Items.GOLD_NUGGET , RecipeCategory.MISC, Ingredient.ofItems(Items.RAW_GOLD), 0.35f, ORE_COOK_TIME).criterion("has_raw_gold", RecipeProvider.conditionsFromItem(Items.RAW_GOLD)).offerTo(exporter, ID.ofSS("gold_nugget" + foc));
        offerOvenCooking(ModItems.COPPER_NUGGET, RecipeCategory.MISC, Ingredient.ofItems(Items.RAW_COPPER), 0.20f, ORE_COOK_TIME).criterion("has_raw_copper", RecipeProvider.conditionsFromItem(Items.RAW_COPPER)).offerTo(exporter, ID.ofSS("copper_nugget" + foc));

        // New
        offerOvenCooking(BTWR_Items.COOKED_KEBAB, RecipeCategory.FOOD, Ingredient.ofItems(BTWR_Items.RAW_KEBAB), 0.15f, FOOD_COOK_TIME).criterion("has_raw_kebab", RecipeProvider.conditionsFromItem(BTWR_Items.RAW_KEBAB)).offerTo(exporter, ID.ofSS("cooked_kebab" + foc));
        offerOvenCooking(Items.BRICK, RecipeCategory.MISC, Ingredient.ofItems(ModBlocks.BRICK_UNFIRED), 0.10f, 6000).criterion("has_brick_unfired", conditionsFromItem(ModBlocks.BRICK_UNFIRED)).offerTo(exporter, ID.ofSS("brick" + foc));
        offerOvenCooking(Items.BREAD, RecipeCategory.FOOD, Ingredient.ofItems(BREAD_DOUGH), 0.10f, FOOD_COOK_TIME).criterion("has_bread_dough", conditionsFromItem(BREAD_DOUGH)).offerTo(exporter, ID.ofSS("bread" + foc));
        offerOvenCooking(COOKED_CARROT, RecipeCategory.FOOD, Ingredient.ofItems(Items.CARROT), 0.10f, FOOD_COOK_TIME).criterion("has_carrot", conditionsFromItem(Items.CARROT)).offerTo(exporter, ID.ofSS("cooked_carrot" + foc));
        offerOvenCooking(Items.COOKIE, RecipeCategory.FOOD, Ingredient.ofItems(PASTRY_UNCOOKED_COOKIES), 0.10f, FOOD_COOK_TIME).criterion("has_pastry_uncooked_cookies", conditionsFromItem(PASTRY_UNCOOKED_COOKIES)).offerTo(exporter, ID.ofSS("cookie" + foc));
        offerOvenCooking(BwtItems.friedEggItem, RecipeCategory.FOOD, Ingredient.ofItems(BwtItems.rawEggItem), 0.10f, FOOD_COOK_TIME).criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem)).offerTo(exporter, ID.ofSS("fried_egg" + foc));
        offerOvenCooking(BTWR_Items.EGG_SCRAMBLED_COOKED, RecipeCategory.FOOD, Ingredient.ofItems(BTWR_Items.EGG_SCRAMBLED_RAW), 0.10f, FOOD_COOK_TIME).criterion("has_egg_scrambled_raw", conditionsFromItem(BTWR_Items.EGG_SCRAMBLED_RAW)).offerTo(exporter, ID.ofSS("scrambled_eggs_cooked" + foc));
        offerOvenCooking(BTWR_Items.MUSHROOM_OMELETTE_COOKED, RecipeCategory.FOOD, Ingredient.ofItems(BTWR_Items.MUSHROOM_OMELETTE_RAW), 0.10f, FOOD_COOK_TIME).criterion("has_egg_mushroom_omelette_raw", conditionsFromItem(BTWR_Items.MUSHROOM_OMELETTE_RAW)).offerTo(exporter, ID.ofSS("mushroom_omelette_cooked" + foc));

    }

    public static ModCookingRecipeJsonBuilder offerOvenCooking(ItemConvertible output, RecipeCategory category, Ingredient input , float experience, int cookingTime) {
        return new ModCookingRecipeJsonBuilder(category, ModCookingRecipeJsonBuilder.getRecipeCategory(output), output, input, experience, cookingTime, OvenCookingRecipe::new);
    }


}
