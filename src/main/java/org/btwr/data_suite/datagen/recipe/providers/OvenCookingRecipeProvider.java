package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.ivangeevo.self_sustainable.block.ModBlocks;
import net.ivangeevo.self_sustainable.data.server.recipe.ModCookingRecipeJsonBuilder;
import net.ivangeevo.self_sustainable.recipe.cooking.OvenCookingRecipe;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.core.item.BTWR_Items;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.shared_library.util.utils.RecipeUtils;
import org.btwr.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;

import static org.btwr.vegehenna.item.ModItems.*;


public class OvenCookingRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    private static final String SUFFIX = "_from_oven_cooking";
    private static final int FOOD_COOK_TIME = 1600;
    private static final int ORE_COOK_TIME = 12800;

    public OvenCookingRecipeProvider(FabricDataOutput output,
                                     CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // Foods
        this.generateFoods(exporter);

        // Ores
        ore(exporter, Items.IRON_NUGGET, Items.RAW_IRON, 0.25f);
        ore(exporter, Items.GOLD_NUGGET, Items.RAW_GOLD, 0.35f);
        ore(exporter, ModItems.COPPER_NUGGET, Items.RAW_COPPER, 0.20f);

        // Custom
        custom(exporter, Items.BRICK, RecipeCategory.MISC, ModBlocks.BRICK_UNFIRED, 0.10f, 6000);
    }

    private void generateFoods(RecipeExporter exporter) {
        // Vanilla foods
        food(exporter, Items.COOKED_CHICKEN, Items.CHICKEN, 0.15f);
        food(exporter, Items.COOKED_BEEF, Items.BEEF, 0.15f);
        food(exporter, Items.COOKED_PORKCHOP, Items.PORKCHOP, 0.15f);
        food(exporter, Items.COOKED_MUTTON, Items.MUTTON, 0.15f);
        food(exporter, Items.COOKED_RABBIT, Items.RABBIT, 0.10f);
        food(exporter, Items.COOKED_COD, Items.COD, 0.10f);
        food(exporter, Items.COOKED_SALMON, Items.SALMON, 0.10f);
        food(exporter, Items.BAKED_POTATO, Items.POTATO, 0.10f);
        food(exporter, Items.BREAD, BREAD_DOUGH, 0.10f);
        food(exporter, Items.COOKIE, PASTRY_UNCOOKED_COOKIES, 0.10f);

        // Foods from other mods
        food(exporter, COOKED_CARROT, Items.CARROT, 0.10f);
        food(exporter, BwtItems.friedEggItem, BwtItems.rawEggItem, 0.10f);
        food(exporter, BTWR_Items.EGG_SCRAMBLED_COOKED, BTWR_Items.EGG_SCRAMBLED_RAW, 0.10f);
        food(exporter, BTWR_Items.MUSHROOM_OMELETTE_COOKED, BTWR_Items.MUSHROOM_OMELETTE_RAW, 0.10f);
        food(exporter, BTWR_Items.COOKED_KEBAB, BTWR_Items.RAW_KEBAB, 0.15f);
    }

    private void food(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, float xp) {
        custom(exporter, output, RecipeCategory.FOOD, input, xp, FOOD_COOK_TIME);
    }

    private void ore(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, float xp) {
        custom(exporter, output, RecipeCategory.MISC, input, xp, ORE_COOK_TIME);
    }

    private void custom(RecipeExporter exporter, ItemConvertible output, RecipeCategory category,
                        ItemConvertible input, float xp, int cookTime) {
        custom(exporter, output, category, input, xp, cookTime, getItemPath(output));
    }

    private void custom(RecipeExporter exporter, ItemConvertible output, RecipeCategory category,
                        ItemConvertible input, float xp, int cookTime, String name) {
        offerOvenCooking(output, category, Ingredient.ofItems(input), xp, cookTime)
                .criterion("has_" + getItemPath(input), conditionsFromItem(input))
                .offerTo(exporter, IdUtils.ofSS(name + SUFFIX));
    }

    public static ModCookingRecipeJsonBuilder offerOvenCooking(ItemConvertible output, RecipeCategory category,
                                                               Ingredient input, float xp, int cookTime) {
        return new ModCookingRecipeJsonBuilder(
                category,
                ModCookingRecipeJsonBuilder.getRecipeCategory(output),
                output,
                input,
                xp,
                cookTime,
                OvenCookingRecipe::new
        );
    }

}