package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwrsl.lib.util.utils.RecipeProviderUtils;
import btwr.core.block.BTWR_Blocks;
import btwr.core.item.BTWR_Items;
import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import com.bwt.recipes.cooking_pots.StokedCrucibleRecipe;
import com.bwt.recipes.kiln.KilnRecipe;
import com.bwt.recipes.mill_stone.MillStoneRecipe;
import com.bwt.tags.BwtItemTags;
import ivangeevo.sturdy_trees.SturdyTreesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.ivangeevo.animageddon.item.ModItems;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.tough_environment.block.ModBlocks;

import java.util.concurrent.CompletableFuture;


public class ST_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public ST_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, SturdyTreesItems.STUMP_REMOVER)
                .input(Items.ROTTEN_FLESH)
                .input(Items.RED_MUSHROOM)
                .input(BTWR_Items.CREEPER_OYSTERS)
                .criterion("has_creeper_oysters", conditionsFromItem(BTWR_Items.CREEPER_OYSTERS))
                .offerTo(exporter, ID.ofDS("stump_remover"));
    }




}
