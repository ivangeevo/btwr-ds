package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.core.item.BTWR_Items;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import com.bwt.recipes.cooking_pots.StokedCrucibleRecipe;
import com.bwt.recipes.hopper_filter.HopperFilterRecipe;
import com.bwt.recipes.kiln.KilnRecipe;
import com.bwt.recipes.mill_stone.MillStoneRecipe;
import com.bwt.recipes.mob_spawner_conversion.MobSpawnerConversionRecipe;
import ivangeevo.sturdy_trees.SturdyTreesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.ivangeevo.self_sustainable.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.tough_environment.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

import static org.tough_environment.item.ModItems.CHISEL_DIAMOND;
import static org.tough_environment.item.ModItems.CHISEL_IRON;


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
    }

    private void generateShapeless(RecipeExporter exporter) {
    }


    private void generateShaped(RecipeExporter exporter) {

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
