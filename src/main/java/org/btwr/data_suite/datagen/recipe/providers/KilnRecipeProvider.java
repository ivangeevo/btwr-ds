package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.blocks.BwtBlocks;
import com.bwt.recipes.kiln.KilnRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.DyeColor;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.data_suite.item.BTWRDS_Items;
import org.btwr.tough_environment.block.ModBlocks;
import org.btwr.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;

import static org.btwr.vegehenna.block.ModBlocks.*;
import static org.btwr.vegehenna.block.ModBlocks.BREAD_DOUGH;
import static org.btwr.vegehenna.item.ModItems.PASTRY_UNCOOKED_CAKE;

public class KilnRecipeProvider extends FabricRecipeProvider {

    public KilnRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        // DS recipes
        KilnRecipe.JsonBuilder.create(org.btwr.self_sustainable.block.ModBlocks.BRICK_UNFIRED).result(Items.BRICK)
                .criterion("has_brick_unfired", conditionsFromItem(org.btwr.self_sustainable.block.ModBlocks.BRICK_UNFIRED))
                .offerTo(exporter, IdUtils.ofDS("kiln_cook_brick"));

        Block breadDoughBlock = BREAD_DOUGH;
        KilnRecipe.JsonBuilder.create(breadDoughBlock).result(Items.BREAD)
                .criterion("has_bread_dough", conditionsFromItem(BREAD_DOUGH))
                .offerTo(exporter, IdUtils.ofDS("kiln_cook_bread"));

        Block uncookedCakeBlock = UNCOOKED_CAKE;
        KilnRecipe.JsonBuilder.create(uncookedCakeBlock).result(Items.CAKE)
                .criterion("has_uncooked_cake_pastry", conditionsFromItem(PASTRY_UNCOOKED_CAKE))
                .offerTo(exporter, IdUtils.ofDS("kiln_cook_cake"));

        // Ore blocks
        KilnRecipe.JsonBuilder.create(BlockTags.IRON_ORES).drops(Items.IRON_NUGGET).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BlockTags.GOLD_ORES).drops(Items.GOLD_NUGGET).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BlockTags.COPPER_ORES).drops(ModItems.COPPER_NUGGET).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.ANCIENT_DEBRIS).drops(Items.NETHERITE_SCRAP).offerTo(exporter);

        // Ore blocks
        KilnRecipe.JsonBuilder.create(ConventionalBlockTags.STORAGE_BLOCKS_RAW_IRON).drops(Items.IRON_INGOT).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(ConventionalBlockTags.STORAGE_BLOCKS_RAW_GOLD).drops(Items.GOLD_INGOT).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(ConventionalBlockTags.STORAGE_BLOCKS_RAW_COPPER).drops(Items.COPPER_INGOT).offerTo(exporter);

        KilnRecipe.JsonBuilder.create(BlockTags.LOGS).drops(Items.CHARCOAL).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredCrucibleBlock).drops(BwtBlocks.crucibleBlock).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredPlanterBlock).drops(BwtBlocks.planterBlock).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredVaseBlock).drops(BwtBlocks.vaseBlocks.get(DyeColor.WHITE)).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredUrnBlock).drops(BwtBlocks.urnBlock).offerTo(exporter);
        //KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredMouldBlock).drops(BwtItems.mouldItem).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.CLAY).drops(Blocks.TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.WHITE_TERRACOTTA).drops(Blocks.WHITE_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.LIGHT_GRAY_TERRACOTTA).drops(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.GRAY_TERRACOTTA).drops(Blocks.GRAY_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.BLACK_TERRACOTTA).drops(Blocks.BLACK_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.BROWN_TERRACOTTA).drops(Blocks.BROWN_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.RED_TERRACOTTA).drops(Blocks.RED_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.ORANGE_TERRACOTTA).drops(Blocks.ORANGE_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.YELLOW_TERRACOTTA).drops(Blocks.YELLOW_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.LIME_TERRACOTTA).drops(Blocks.LIME_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.GREEN_TERRACOTTA).drops(Blocks.GREEN_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.CYAN_TERRACOTTA).drops(Blocks.CYAN_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.LIGHT_BLUE_TERRACOTTA).drops(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.BLUE_TERRACOTTA).drops(Blocks.BLUE_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.PURPLE_TERRACOTTA).drops(Blocks.PURPLE_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.MAGENTA_TERRACOTTA).drops(Blocks.MAGENTA_GLAZED_TERRACOTTA).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.PINK_TERRACOTTA).drops(Blocks.PINK_GLAZED_TERRACOTTA).offerTo(exporter);

        KilnRecipe.JsonBuilder.create(Blocks.END_STONE).drops(ModBlocks.WHITE_COBBLESTONE).drops(BTWRDS_Items.ENDER_SLAG).offerTo(exporter);

    }
}
