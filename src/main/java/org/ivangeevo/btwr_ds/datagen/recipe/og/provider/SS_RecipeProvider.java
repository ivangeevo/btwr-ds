package org.ivangeevo.btwr_ds.datagen.recipe.og.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.ivangeevo.self_sustainable.block.ModBlocks;
import net.ivangeevo.self_sustainable.data.server.recipe.ModCookingRecipeJsonBuilder;
import net.ivangeevo.self_sustainable.item.ModItems;
import net.ivangeevo.self_sustainable.recipe.OvenCookingRecipe;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static org.tough_environment.block.ModBlocks.SLAB_BRICKS_LOOSE;


public class SS_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public SS_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter) {
        this.generateShaped(exporter);
        this.generateShapeless(exporter);

        this.generateOvenCooking(exporter);
    }

    private void generateShapeless(RecipeExporter exporter) {
    }

    private void generateOvenCooking(RecipeExporter exporter) {
        final String foc = "_from_oven_cooking";

        // Food
        offerOvenCooking(Items.BAKED_POTATO, RecipeCategory.FOOD, Ingredient.ofItems(Items.POTATO), 0.25f, 1600).criterion("has_potato", RecipeProvider.conditionsFromItem(Items.POTATO)).offerTo(exporter, Identifier.of("self_sustainable", "baked_potato" + foc));
        offerOvenCooking(Items.COOKED_CHICKEN, RecipeCategory.FOOD, Ingredient.ofItems(Items.CHICKEN), 0.15f, 1600).criterion("has_chicken", RecipeProvider.conditionsFromItem(Items.CHICKEN)).offerTo(exporter, Identifier.of("self_sustainable", "cooked_chicken" + foc));
        offerOvenCooking(Items.COOKED_BEEF, RecipeCategory.FOOD, Ingredient.ofItems(Items.BEEF), 0.15f, 1600).criterion("has_beef", RecipeProvider.conditionsFromItem(Items.BEEF)).offerTo(exporter, Identifier.of("self_sustainable", "cooked_beef" + foc));
        offerOvenCooking(Items.COOKED_PORKCHOP, RecipeCategory.FOOD, Ingredient.ofItems(Items.PORKCHOP), 0.15f, 1600).criterion("has_porkchop", RecipeProvider.conditionsFromItem(Items.PORKCHOP)).offerTo(exporter, Identifier.of("self_sustainable", "cooked_porkchop" + foc));
        offerOvenCooking(Items.COOKED_MUTTON, RecipeCategory.FOOD, Ingredient.ofItems(Items.MUTTON), 0.15f, 1600).criterion("has_mutton", RecipeProvider.conditionsFromItem(Items.MUTTON)).offerTo(exporter, Identifier.of("self_sustainable", "cooked_mutton" + foc));
        offerOvenCooking(Items.COOKED_RABBIT, RecipeCategory.FOOD, Ingredient.ofItems(Items.RABBIT), 0.10f, 1600).criterion("has_rabbit", RecipeProvider.conditionsFromItem(Items.RABBIT)).offerTo(exporter, Identifier.of("self_sustainable", "cooked_rabbit" + foc));
        offerOvenCooking(Items.COOKED_COD, RecipeCategory.FOOD, Ingredient.ofItems(Items.COD), 0.10f, 1600).criterion("has_cod", RecipeProvider.conditionsFromItem(Items.COD)).offerTo(exporter, Identifier.of("self_sustainable", "cooked_cod" + foc));
        offerOvenCooking(Items.COOKED_SALMON, RecipeCategory.FOOD, Ingredient.ofItems(Items.SALMON), 0.10f, 1600).criterion("has_salmon", RecipeProvider.conditionsFromItem(Items.SALMON)).offerTo(exporter, Identifier.of("self_sustainable", "cooked_salmon" + foc));

        // Ores
        offerOvenCooking(Items.IRON_INGOT, RecipeCategory.MISC, Ingredient.ofItems(Items.RAW_IRON), 0.25f, 12800).criterion("has_raw_iron", RecipeProvider.conditionsFromItem(Items.RAW_IRON)).offerTo(exporter);
        offerOvenCooking(Items.GOLD_INGOT , RecipeCategory.MISC, Ingredient.ofItems(Items.RAW_GOLD), 0.35f, 12800).criterion("has_raw_gold", RecipeProvider.conditionsFromItem(Items.RAW_GOLD)).offerTo(exporter);
        offerOvenCooking(Items.COPPER_INGOT, RecipeCategory.MISC, Ingredient.ofItems(Items.RAW_COPPER), 0.20f, 10000).criterion("has_raw_copper", RecipeProvider.conditionsFromItem(Items.RAW_COPPER)).offerTo(exporter);
    }

    public static ModCookingRecipeJsonBuilder offerOvenCooking(ItemConvertible output, RecipeCategory category, Ingredient input , float experience, int cookingTime) {
        return new ModCookingRecipeJsonBuilder(category, ModCookingRecipeJsonBuilder.getRecipeCategory(output), output, input, experience, cookingTime, OvenCookingRecipe::new);
    }

    private void generateShaped(RecipeExporter exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.OVEN_BRICK)
                .input('S', SLAB_BRICKS_LOOSE)
                .pattern("SS")
                .pattern("SS")
                .criterion("has_slab_bricks_loose", conditionsFromItem(SLAB_BRICKS_LOOSE))
                .offerTo(exporter, ID.ofSS("oven_brick"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRUDE_TORCH_UNLIT, 1)
                .input('C', ItemTags.COALS)
                .input('I', Items.STICK)
                .pattern("C")
                .pattern("I")
                .criterion("has_coal", conditionsFromTag(ItemTags.COALS))
                .offerTo(exporter, ID.ofSS("crude_torch_unlit"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TORCH_UNLIT, 1)
                .input('C', BwtItems.nethercoalItem)
                .input('I', Items.STICK)
                .pattern("C")
                .pattern("I")
                .criterion("has_nethercoal", conditionsFromItem(BwtItems.nethercoalItem))
                .offerTo(exporter, ID.ofSS("torch_unlit"));
    }



}
