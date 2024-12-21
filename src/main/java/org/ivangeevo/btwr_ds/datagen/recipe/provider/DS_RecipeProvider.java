package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwrsl.lib.util.utils.RecipeProviderUtils;
import btwr.core.block.BTWR_Blocks;
import btwr.core.item.BTWR_Items;
import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import com.bwt.recipes.cooking_pots.StokedCrucibleRecipe;
import com.bwt.recipes.hopper_filter.HopperFilterRecipe;
import com.bwt.recipes.kiln.KilnRecipe;
import com.bwt.recipes.mill_stone.MillStoneRecipe;
import com.bwt.recipes.soul_bottling.SoulBottlingRecipe;
import com.bwt.tags.BwtItemTags;
import ivangeevo.sturdy_trees.SturdyTreesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.ivangeevo.animageddon.item.ModItems;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.ivangeevo.bwt_hct.recipes.mill_stone.ModernMillStoneRecipe;
import org.tough_environment.block.ModBlocks;

import java.util.concurrent.CompletableFuture;



public class DS_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public DS_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
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

        this.generateModExclusiveRecipes(exporter);
    }

    private void generateShapeless(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.hempFiberItem,9)
                .input(BwtItems.fabricItem)
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, ID.ofDS("hemp_fiber_from_fabric"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_SCOURED_CUT,2)
                .input(BwtItems.scouredLeatherItem)
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .criterion("has_scoured_leather", conditionsFromItem(BwtItems.scouredLeatherItem))
                .offerTo(exporter, ID.ofDS("leather_scoured_cut"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_TANNED_CUT,2)
                .input(BwtItems.tannedLeatherItem)
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .criterion("has_tanned_leather", conditionsFromItem(BwtItems.tannedLeatherItem))
                .offerTo(exporter, ID.ofDS("leather_tanned_cut"));

        // TODO: figure out why .additionalDrop() builder is not working on datagen
        /**
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.bloodWoodBlocks.planksBlock)
         .input(BwtBlocks.bloodWoodBlocks.logBlock)
         .input(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
         .additionalDrop(BTWRDS_Items.BARK_BLOOD_WOOD.getDefaultStack())
         .additionalDrop(SturdyTreesItems.DUST_SAW.getDefaultStack())
         .criterion("has_blood_wood_log", conditionsFromItem(BwtBlocks.bloodWoodBlocks.logBlock))
         .offerTo(exporter, ID.ofBWT("blood_wood_planks"));
         **/
    }


    private void generateShaped(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK,2)
                .input('P', ItemTags.PLANKS)
                .pattern("P")
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, ID.ofDS("stick_from_single_planks"));

        // Blocks
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.FURNACE)
                .input('B', ModBlocks.SLAB_BRICKS_LOOSE)
                .pattern("BB")
                .pattern("BB")
                .criterion("has_slab_bricks_loose", conditionsFromItem(ModBlocks.SLAB_BRICKS_LOOSE))
                .offerTo(exporter, ID.ofDS("furnace_from_slab_bricks"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.bellowsBlock)
                .input('L', BTWR_Items.LEATHER_TANNED_CUT)
                .input('B', BwtItems.beltItem)
                .input('S', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('G', BwtItems.gearItem)
                .pattern("SSS")
                .pattern("LLL")
                .pattern("GBG")
                .criterion("has_belt", conditionsFromItem(BwtItems.beltItem))
                .offerTo(exporter, ID.ofDS("bellows_from_leather_tanned_cut"));

        // Tools
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                .input('R', Items.STICK)
                .input('S', ItemTags.STONE_TOOL_MATERIALS)
                .input('#', ConventionalItemTags.STRINGS)
                .pattern("SSS")
                .pattern(" R#")
                .pattern(" R ")
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, ID.ofDS("stone_pickaxe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.IRON_AXE)
                .input('M', Items.IRON_INGOT)
                .input('I', Items.STICK)
                .pattern(" M")
                .pattern("IM")
                .pattern("I ")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, ID.ofDS("iron_axe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.IRON_HOE)
                .input('M', Items.IRON_INGOT)
                .input('I', Items.STICK)
                .pattern("IM")
                .pattern("I ")
                .pattern("I ")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, ID.ofDS("iron_hoe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_AXE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern(" M")
                .pattern("IM")
                .pattern("I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofDS("diamond_axe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_HOE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern("IM")
                .pattern("I ")
                .pattern("I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofDS("diamond_hoe_right"));

    }

    /** Recipes that would usually be considered from other mod namespaces,
     *  but we generate them by our mod
     *  <p> They are new ones and it's more convenient to have them separate to avoid confusion
     *   **/
    private void generateModExclusiveRecipes(RecipeExporter exporter) {

        // Modern (HC) Millstone
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, org.ivangeevo.bwt_hct.block.ModBlocks.modernMillStoneBlock)
                .input('B', org.tough_environment.item.ModItems.STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BGB")
                .criterion("has_gear", conditionsFromItem(org.ivangeevo.bwt_hct.block.ModBlocks.modernMillStoneBlock))
                .offerTo(exporter, Identifier.of("bwt_hct","modern_mill_stone"));


        this.createTannedLeatherRecipes(exporter);

        // Millstone recipes
        /***
        ModernMillStoneRecipe.JsonBuilder.create().result(BTWR_Items.LEATHER_SCOURED_CUT)
                .ingredient(BTWR_Items.LEATHER_CUT)
                .criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT))
                .offerTo(exporter, ID.ofDS("leather_scoured_cut_from_mill_stone"));

        ModernMillStoneRecipe.JsonBuilder.create().result(Items.BLAZE_POWDER,2)
                .ingredient(Items.BLAZE_ROD)
                .criterion("has_blaze_rod", conditionsFromItem(Items.BLAZE_ROD))
                .offerTo(exporter, ID.ofDS("blaze_powder_from_mill_stone"));

        ModernMillStoneRecipe.JsonBuilder.create().result(BwtItems.hempFiberItem, 4)

         .ingredient(BTWR_Items.HEMP_LEAVES)
                .criterion("has_hemp_leaves", conditionsFromItem(BwtItems.hempItem))
                .offerTo(exporter, ID.ofDS("hemp_fiber_from_milling_hemp"));
         **/

        MillStoneRecipe.JsonBuilder.create().result(BTWR_Items.LEATHER_SCOURED_CUT)
                .ingredient(BTWR_Items.LEATHER_CUT)
                .criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT))
                .offerTo(exporter, ID.ofDS("leather_scoured_cut_from_mill_stone"));

        MillStoneRecipe.JsonBuilder.create().result(Items.BLAZE_POWDER,2)
                .ingredient(Items.BLAZE_ROD)
                .criterion("has_blaze_rod", conditionsFromItem(Items.BLAZE_ROD))
                .offerTo(exporter, ID.ofDS("blaze_powder_from_mill_stone"));

        MillStoneRecipe.JsonBuilder.create().result(BwtItems.hempFiberItem,4)
                .ingredient(BTWR_Items.HEMP_LEAVES)
                .criterion("has_hemp_leaves", conditionsFromItem(BwtItems.hempItem))
                .offerTo(exporter, ID.ofDS("hemp_fiber_from_milling_hemp"));


        // Cauldron recipes
        CauldronRecipe.JsonBuilder.create().result(BTWRDS_Items.ELEMENT)
                .ingredient(Items.BLAZE_POWDER)
                .ingredient(Items.REDSTONE)
                .ingredient(ConventionalItemTags.STRINGS)
                .criterion("has_blaze_powder", conditionsFromItem(Items.BLAZE_POWDER))
                .offerTo(exporter, ID.ofDS("element_from_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(Items.GUNPOWDER, 2)
                .ingredient(ModItems.NITRE)
                .ingredient(BTWRDS_Items.BRIMSTONE)
                .ingredient(BwtItems.coalDustItem)
                .criterion("has_brimstone", conditionsFromItem(BTWRDS_Items.BRIMSTONE))
                .offerTo(exporter, ID.ofDS("gunpowder_from_cauldron"));

        // Stoked Cauldron recipes

        // Crucible recipes

        // Stoked Crucible recipes
        StokedCrucibleRecipe.JsonBuilder.create().result(ModBlocks.WHITE_STONE.asItem())
                .ingredient(ModBlocks.WHITE_COBBLESTONE.asItem())
                .criterion("has_white_cobblestone", conditionsFromItem(ModBlocks.WHITE_COBBLESTONE.asItem()))
                .offerTo(exporter, ID.ofDS("white_stone_from_crucible"));


        // Kiln recipes
        KilnRecipe.JsonBuilder.create(BTWR_Blocks.BRICK_UNFIRED).result(Items.BRICK)
                .criterion("has_brick_unfired", conditionsFromItem(BTWR_Blocks.BRICK_UNFIRED))
                .offerTo(exporter, ID.ofDS("kiln_cook_brick"));

        // Hopper filtering recipes
        HopperFilterRecipe.JsonBuilder.create().filter(Items.SOUL_SAND).ingredient(Items.GLOWSTONE_DUST)
                .byproduct(BTWRDS_Items.BRIMSTONE).soulCount(1).offerTo(exporter);

        // Soul bottling recipes

    }


    private void createTannedLeatherRecipes(RecipeExporter exporter) {
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_JUNGLE, 2);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_DARK_OAK, 2);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_SPRUCE, 3);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_MANGROVE, 3);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_CHERRY, 3);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_OAK, 5);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_ACACIA, 5);
        this.leatherRecipeBuilder(exporter, SturdyTreesItems.BARK_BIRCH, 8);
        this.leatherRecipeBuilder(exporter, BTWRDS_Items.BARK_BLOOD_WOOD, 8);
    }

    /** Creates a tanned leather recipe by only passing the bark item and the amount **/
    private void leatherRecipeBuilder(RecipeExporter exporter, Item barkItem, int count) {
        CauldronRecipe.JsonBuilder.create().result(BwtItems.tannedLeatherItem)
                .ingredient(BwtItems.scouredLeatherItem)
                .ingredient(BwtItems.dungItem)
                .ingredient(barkItem, count)
                .criterion("has_scoured_leather", conditionsFromItem(BwtItems.scouredLeatherItem))
                .offerTo(exporter, ID.ofDS("tanned_leather_with_" + extractName(barkItem) + "_in_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(BTWR_Items.LEATHER_TANNED_CUT,2)
                .ingredient(BTWR_Items.LEATHER_SCOURED_CUT,2)
                .ingredient(BwtItems.dungItem)
                .ingredient(barkItem, count)
                .criterion("has_scoured_leather", conditionsFromItem(BTWR_Items.LEATHER_SCOURED_CUT))
                .offerTo(exporter, ID.ofDS("tanned_leather_cut_from_leather_scoured_cut_with_" + extractName(barkItem) + "_in_cauldron"));
    }

}
