package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.core.block.BTWR_Blocks;
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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.ivangeevo.animageddon.item.ModItems;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.tough_environment.block.ModBlocks;


import java.util.concurrent.CompletableFuture;

import static org.tough_environment.item.ModItems.CHISEL_DIAMOND;
import static org.tough_environment.item.ModItems.CHISEL_IRON;


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
        // Items
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK,2)
                .input('P', ItemTags.PLANKS)
                .pattern("P")
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, ID.ofDS("stick_from_single_planks"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.TORCH,2)
                .input('C', BwtItems.nethercoalItem)
                .input('S', Items.STICK)
                .pattern("C")
                .pattern("S")
                .criterion("has_nethercoal", conditionsFromItem(BwtItems.nethercoalItem))
                .offerTo(exporter, ID.ofDS("torch"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWRDS_Items.REDSTONE_LATCH)
                .input('G', Items.GOLD_NUGGET)
                .input('R', Items.REDSTONE)
                .pattern("GGG")
                .pattern(" R ")
                .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, ID.ofDS("redstone_latch"));


        // Blocks
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.FURNACE)
                .input('B', ModBlocks.SLAB_BRICKS_LOOSE)
                .pattern("BB")
                .pattern("BB")
                .criterion("has_slab_bricks_loose", conditionsFromItem(ModBlocks.SLAB_BRICKS_LOOSE))
                .offerTo(exporter, ID.ofDS("furnace_from_slab_bricks"));


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

        /**
        // Modern (HC) Millstone
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, org.ivangeevo.bwt_hct.block.ModBlocks.modernMillStoneBlock)
                .input('B', org.tough_environment.item.ModItems.STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BGB")
                .criterion("has_gear", conditionsFromItem(org.ivangeevo.bwt_hct.block.ModBlocks.modernMillStoneBlock))
                .offerTo(exporter, Identifier.of("bwt_hct","modern_mill_stone"));
        **/


        this.createTannedLeatherRecipes(exporter);

        // Millstone recipes

        /**
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

        ModernMillStoneRecipe.JsonBuilder.create().result(BwtItems.coalDustItem,2)
                 .ingredient(Items.CHARCOAL)
                 .criterion("has_charcoal", conditionsFromItem(Items.CHARCOAL))
                 .offerTo(exporter, ID.ofDS("coal_dust_from_milling_charcoal"));

        ModernMillStoneRecipe.JsonBuilder.create().result(org.ivangeevo.vegehenna.item.ModItems.COCOA_POWDER)
                .ingredient(Items.COCOA_BEANS)
                .criterion("has_cocoa_beans", conditionsFromItem(Items.COCOA_BEANS))
                .offerTo(exporter, ID.ofDS("cocoa_powder_from_milling_cocoa_beans"));
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

        MillStoneRecipe.JsonBuilder.create().result(BwtItems.coalDustItem,2)
                .ingredient(Items.CHARCOAL)
                .criterion("has_charcoal", conditionsFromItem(Items.CHARCOAL))
                .offerTo(exporter, ID.ofDS("coal_dust_from_milling_charcoal"));

        MillStoneRecipe.JsonBuilder.create().result(org.ivangeevo.vegehenna.item.ModItems.COCOA_POWDER)
                .ingredient(Items.COCOA_BEANS)
                .criterion("has_cocoa_beans", conditionsFromItem(Items.COCOA_BEANS))
                .offerTo(exporter, ID.ofDS("cocoa_powder_from_milling_cocoa_beans"));


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

        CauldronRecipe.JsonBuilder.create().result(org.tough_environment.item.ModItems.NETHER_SLUDGE)
                .ingredient(BwtItems.potashItem)
                .ingredient(BwtItems.hellfireDustItem, 8)
                .criterion("has_hellfire_dust", conditionsFromItem(BwtItems.hellfireDustItem))
                .offerTo(exporter, ID.ofDS("nether_sludge_from_stoked_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(org.ivangeevo.vegehenna.item.ModItems.CHOCOLATE,2)
                .ingredient(org.ivangeevo.vegehenna.item.ModItems.COCOA_POWDER)
                .ingredient(Items.SUGAR)
                .ingredient(Items.MILK_BUCKET)
                .criterion("has_cocoa_powder", conditionsFromItem(org.ivangeevo.vegehenna.item.ModItems.COCOA_POWDER))
                .offerTo(exporter, ID.ofDS("chocolate_from_cauldron"));


        // Stoked Cauldron recipes

        // Crucible recipes

        // Stoked Crucible recipes
        StokedCrucibleRecipe.JsonBuilder.create().result(ModBlocks.WHITE_STONE.asItem())
                .ingredient(ModBlocks.WHITE_COBBLESTONE.asItem())
                .criterion("has_white_cobblestone", conditionsFromItem(ModBlocks.WHITE_COBBLESTONE.asItem()))
                .offerTo(exporter, ID.ofDS("white_stone_from_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(BTWR_Items.DIAMOND_INGOT,2)
                .ingredient(BTWR_Items.DIAMOND_SHEARS)
                .criterion("has_diamond_shears", conditionsFromItem(BTWR_Items.DIAMOND_SHEARS))
                .offerTo(exporter, ID.ofDS("smelt_diamond_shears_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(BTWR_Items.DIAMOND_INGOT)
                .ingredient(CHISEL_DIAMOND)
                .criterion("has_chisel_diamond", conditionsFromItem(CHISEL_DIAMOND))
                .offerTo(exporter, ID.ofDS("smelt_chisel_diamond_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(Items.IRON_NUGGET)
                .ingredient(CHISEL_IRON)
                .criterion("has_chisel_iron", conditionsFromItem(CHISEL_IRON))
                .offerTo(exporter, ID.ofDS("smelt_chisel_iron_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(Items.IRON_NUGGET,3)
                .ingredient(Items.SHIELD)
                .criterion("has_shield", conditionsFromItem(Items.SHIELD))
                .offerTo(exporter, ID.ofDS("smelt_shield_in_crucible"));


        // Kiln recipes
        KilnRecipe.JsonBuilder.create(BTWR_Blocks.BRICK_UNFIRED).result(Items.BRICK)
                .criterion("has_brick_unfired", conditionsFromItem(BTWR_Blocks.BRICK_UNFIRED))
                .offerTo(exporter, ID.ofDS("kiln_cook_brick"));

        Block breadDoughBlock = org.ivangeevo.vegehenna.block.ModBlocks.BREAD_DOUGH;
        KilnRecipe.JsonBuilder.create(breadDoughBlock).result(Items.BREAD)
                .criterion("has_bread_dough", conditionsFromItem(org.ivangeevo.vegehenna.item.ModItems.BREAD_DOUGH))
                .offerTo(exporter, ID.ofDS("kiln_cook_bread"));

        Block uncookedCakeBlock = org.ivangeevo.vegehenna.block.ModBlocks.UNCOOKED_CAKE;
        KilnRecipe.JsonBuilder.create(uncookedCakeBlock).result(Items.CAKE)
                .criterion("has_uncooked_cake_pastry", conditionsFromItem(org.ivangeevo.vegehenna.item.ModItems.PASTRY_UNCOOKED_CAKE))
                .offerTo(exporter, ID.ofDS("kiln_cook_cake"));


        // Hopper filtering recipes
        HopperFilterRecipe.JsonBuilder.create().filter(Items.SOUL_SAND).ingredient(Items.GLOWSTONE_DUST)
                .byproduct(BTWRDS_Items.BRIMSTONE).soulCount(1).offerTo(exporter);

        // Soul bottling recipes

        // Spawner block conversion recipes
        MobSpawnerConversionRecipe.JsonBuilder.create(ModBlocks.COBBLESTONE_LOOSE)
                .convertsTo(Blocks.MOSSY_COBBLESTONE)
                .criterion("has_cobblestone_loose", conditionsFromItem(ModBlocks.COBBLESTONE_LOOSE))
                .offerTo(exporter, ID.ofDS("mob_spawner_conversion_from_cobblestone_loose_to_mossy_cobblestone"));

        MobSpawnerConversionRecipe.JsonBuilder.create(ModBlocks.SLAB_COBBLESTONE_LOOSE)
                .convertsTo(Blocks.MOSSY_COBBLESTONE_SLAB)
                .criterion("has_slab_cobblestone_loose", conditionsFromItem(ModBlocks.SLAB_COBBLESTONE_LOOSE))
                .offerTo(exporter, ID.ofDS("mob_spawner_conversion_from_slab_cobblestone_loose_to_mossy_cobblestone_slab"));

        MobSpawnerConversionRecipe.JsonBuilder.create(ModBlocks.COBBLESTONE_LOOSE_STAIRS)
                .convertsTo(Blocks.MOSSY_COBBLESTONE_STAIRS)
                .criterion("has_cobblestone_loose_stairs", conditionsFromItem(ModBlocks.COBBLESTONE_LOOSE_STAIRS))
                .offerTo(exporter, ID.ofDS("mob_spawner_conversion_from_cobblestone_loose_stairs_to_mossy_cobblestone_stairs"));





        // Cooking recipes
        CookingRecipeJsonBuilder.createSmelting(
                Ingredient.ofItems(BwtItems.rawEggItem), RecipeCategory.FOOD, BwtItems.friedEggItem, 0.20f, 2500)
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofBWT("fried_egg"));

        CookingRecipeJsonBuilder.createSmoking(
                        Ingredient.ofItems(BwtItems.rawEggItem), RecipeCategory.FOOD, BwtItems.friedEggItem, 0.30f, 1250)
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofBWT("fried_egg_from_smoking"));


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
