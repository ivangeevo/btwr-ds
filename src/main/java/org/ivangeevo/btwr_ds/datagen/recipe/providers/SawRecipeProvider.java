package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.saw.SawRecipe;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;

import java.util.concurrent.CompletableFuture;

public class SawRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{
    public SawRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        SawRecipe.JsonBuilder.create(Blocks.MELON)
                .result(Items.MELON_SLICE, 5)
                .criterion("has_melon", conditionsFromItem(Items.MELON))
                .offerTo(exporter, ID.ofBWT("saw_melon"));

        // Recipes for sawing log blocks
        this.createSawLogRecipes(exporter);

    }

    private void createSawLogRecipes(RecipeExporter exporter) {
        this.sawLogBuilder(exporter, Blocks.OAK_LOG, Blocks.OAK_PLANKS, SturdyTreesItems.BARK_OAK);
        this.sawLogBuilder(exporter, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, SturdyTreesItems.BARK_SPRUCE);
        this.sawLogBuilder(exporter, Blocks.BIRCH_LOG, Blocks.BIRCH_PLANKS, SturdyTreesItems.BARK_BIRCH);
        this.sawLogBuilder(exporter, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS, SturdyTreesItems.BARK_JUNGLE);
        this.sawLogBuilder(exporter, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS, SturdyTreesItems.BARK_ACACIA);
        this.sawLogBuilder(exporter, Blocks.CHERRY_LOG, Blocks.CHERRY_PLANKS, SturdyTreesItems.BARK_CHERRY);
        this.sawLogBuilder(exporter, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, SturdyTreesItems.BARK_DARK_OAK);
        this.sawLogBuilder(exporter, Blocks.MANGROVE_LOG, Blocks.MANGROVE_PLANKS, SturdyTreesItems.BARK_MANGROVE);
        this.sawLogBuilder(exporter, BwtBlocks.bloodWoodBlocks.logBlock, BwtBlocks.bloodWoodBlocks.planksBlock, BTWRDS_Items.BARK_BLOOD_WOOD);
    }

    private void sawLogBuilder(RecipeExporter exporter, Block logBlock, Block planksBlock, Item barkItem) {
        SawRecipe.JsonBuilder.create(logBlock)
                .result(planksBlock,4)
                .result(barkItem)
                .result(BwtItems.sawDustItem, 2)
                .criterion("has_log", conditionsFromTag(ItemTags.LOGS))
                .offerTo(exporter, ID.ofBWT("saw_" + extractName(logBlock)));
    }

}
