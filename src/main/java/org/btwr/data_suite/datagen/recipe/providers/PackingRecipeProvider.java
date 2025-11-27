package org.btwr.data_suite.datagen.recipe.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.piston_packing.PistonPackingMod;
import org.ivangeevo.piston_packing.recipe.PackingRecipe;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;


public class PackingRecipeProvider extends FabricRecipeProvider {

    public PackingRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter) {

        /** Normal items packing **/
        offerPacking(Blocks.RAW_GOLD_BLOCK, Items.RAW_GOLD, 9, exporter);
        offerPacking(Blocks.RAW_IRON_BLOCK, Items.RAW_IRON, 9, exporter);
        offerPacking(Blocks.RAW_COPPER_BLOCK, Items.RAW_COPPER, 9, exporter);

        /** Non-Loose blocks packing **/
        offerPacking(Blocks.SANDSTONE, Items.SAND, 2, exporter);
        offerPacking(ModBlocks.DIRT_PACKED, ModBlocks.DIRT_LOOSE.asItem(), 2, exporter);
        offerPacking(Blocks.CLAY, ModItems.PILE_CLAY, 18, exporter);
        offerPacking(Blocks.CLAY, Items.CLAY_BALL, 9, exporter);

        /** Loose blocks packing **/
        offerPacking(ModBlocks.DIRT_LOOSE, ModItems.PILE_DIRT, 8, exporter);
        offerPacking(Blocks.GRAVEL, ModItems.PILE_GRAVEL, 8, exporter);
        offerPacking(Blocks.SAND, ModItems.PILE_SAND, 8, exporter);
        offerPacking(Blocks.RED_SAND, ModItems.PILE_RED_SAND, 8, exporter);

        offerPacking(ModBlocks.COBBLESTONE_LOOSE, ModItems.SMALL_STONE, 8, exporter);
        //offerPacking(ModBlocks.MANTLESTONE_LOOSE, ModItems.SMALL_STONE_1, 8, exporter);
        offerPacking(ModBlocks.COBBLED_DEEPSLATE_LOOSE, ModItems.SMALL_STONE_2, 8, exporter);

        offerPacking(ModBlocks.BRICKS_LOOSE, Items.BRICK, 8, exporter);
        offerPacking(ModBlocks.NETHER_BRICKS_LOOSE, Items.NETHER_BRICK, 8, exporter);

        offerPacking(ModBlocks.STONE_BRICKS_LOOSE, ModItems.STONE_BRICK, 4, exporter);
        //offerPacking(ModBlocks.MANTLESTONE_BRICKS_LOOSE, ModItems.STONE_BRICK_1, 4, exporter);
        offerPacking(ModBlocks.DEEPSLATE_BRICKS_LOOSE, ModItems.STONE_BRICK_2, 4, exporter);

        /** Mob drops packing **/
        //offerPacking(ModBlocks.BONE_BLOCK, Items.BONE, 9, exporter);
        //offerPacking(ModBlocks.ROTTEN_FLESH_BLOCK, Items.ROTTEN_FLESH, 9, exporter);

        //offerPacking(ModBlocks.SPIDER_EYE_BLOCK, Items.SPIDER_EYE, 16, exporter);
        //offerPacking(BTWR_Blocks.CREEPER_OYSTERS_BLOCK, BTWR_Items.CREEPER_OYSTERS, 16, exporter);


        //offerPacking(BTWR_Blocks.DUNG_BLOCK, BwtItems.dungItem, 8, exporter);
        //offerPacking(BTWR_Blocks.FLINT_BLOCK, Items.FLINT, 8, exporter);
        //offerPacking(BTWR_Blocks.SOAP_BLOCK, BwtItems.soapItem, 8, exporter);
    }

    private void offerPacking(Block result, Item ingredient, int count, RecipeExporter exporter) {
        String ingredientName = Registries.ITEM.getId(ingredient).getPath();
        String resultName = Registries.BLOCK.getId(result).getPath();
        PackingRecipe.JsonBuilder.create().result(result)
                .category(CraftingRecipeCategory.MISC)
                .ingredient(ingredient, count)
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(
                        exporter,
                        Identifier.of(PistonPackingMod.MOD_ID, "_from_piston_packing_")
                                .withPrefixedPath(resultName)
                                .withSuffixedPath(ingredientName)
                );
    }

}