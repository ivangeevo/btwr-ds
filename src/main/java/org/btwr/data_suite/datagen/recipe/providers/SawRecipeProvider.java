package org.btwr.data_suite.datagen.recipe.providers;

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
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.shared_library.util.utils.RecipeUtils;
import org.btwr.data_suite.item.BTWRDS_Items;
import org.btwr.vegehenna.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class SawRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    public SawRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        SawRecipe.JsonBuilder.create(Blocks.MELON)
                .result(Items.MELON_SLICE, 5)
                .criterion("has_melon", conditionsFromItem(Items.MELON))
                .offerTo(exporter, IdUtils.ofBWT("saw_melon"));

        SawRecipe.JsonBuilder.create(Blocks.HAY_BLOCK)
                .result(ModItems.STRAW, 6)
                .criterion("has_hay_block", conditionsFromItem(Items.HAY_BLOCK))
                .offerTo(exporter, IdUtils.ofBWT("saw_hay_block"));

        // Recipes for sawing log blocks
        this.createSawLogRecipes(exporter);

    }

    private void createSawLogRecipes(RecipeExporter exporter) {
        this.sawLogBuilder(exporter, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_PLANKS, SturdyTreesItems.BARK_OAK);
        this.sawLogBuilder(exporter, Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.SPRUCE_PLANKS, SturdyTreesItems.BARK_SPRUCE);
        this.sawLogBuilder(exporter, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_PLANKS, SturdyTreesItems.BARK_BIRCH);
        this.sawLogBuilder(exporter, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_PLANKS, SturdyTreesItems.BARK_JUNGLE);
        this.sawLogBuilder(exporter, Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_PLANKS, SturdyTreesItems.BARK_ACACIA);
        this.sawLogBuilder(exporter, Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG, Blocks.CHERRY_PLANKS, SturdyTreesItems.BARK_CHERRY);
        this.sawLogBuilder(exporter, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, SturdyTreesItems.BARK_DARK_OAK);
        this.sawLogBuilder(exporter, Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG, Blocks.MANGROVE_PLANKS, SturdyTreesItems.BARK_MANGROVE);

        // Blood wood
        SawRecipe.JsonBuilder.create(BwtBlocks.bloodWoodBlocks.logBlock)
                .result(BwtBlocks.bloodWoodBlocks.planksBlock,4)
                .result(BTWRDS_Items.BARK_BLOOD_WOOD)
                .result(BwtItems.sawDustItem)
                .result(BwtItems.soulDustItem)
                .criterion("has_", conditionsFromItem(BwtBlocks.bloodWoodBlocks.logBlock))
                .offerTo(exporter, IdUtils.ofBWT("saw_" + extractName(BwtBlocks.bloodWoodBlocks.logBlock)));

        SawRecipe.JsonBuilder.create(BwtBlocks.bloodWoodBlocks.strippedLogBlock)
                .result(BwtBlocks.bloodWoodBlocks.planksBlock,4)
                .result(BwtItems.soulDustItem, 1)
                .criterion("has_", conditionsFromItem(BwtBlocks.bloodWoodBlocks.strippedLogBlock))
                .offerTo(exporter, IdUtils.ofBWT("saw_" + extractName(BwtBlocks.bloodWoodBlocks.strippedLogBlock)));
    }

    private void sawLogBuilder(RecipeExporter exporter, Block logBlock, Block strippedLogBlock, Block planksBlock, Item barkItem) {
        SawRecipe.JsonBuilder.create(logBlock)
                .result(planksBlock,4)
                .result(barkItem)
                .result(BwtItems.sawDustItem, 2)
                .criterion("has_log", conditionsFromItem(logBlock))
                .offerTo(exporter, IdUtils.ofBWT("saw_" + extractName(logBlock)));

        SawRecipe.JsonBuilder.create(strippedLogBlock)
                .result(planksBlock,4)
                .result(barkItem)
                .result(BwtItems.sawDustItem, 2)
                .criterion("has_", conditionsFromItem(strippedLogBlock))
                .offerTo(exporter, IdUtils.ofBWT("saw_" + extractName(strippedLogBlock)));
    }

}