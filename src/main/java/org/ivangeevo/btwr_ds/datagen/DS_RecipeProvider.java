package org.ivangeevo.btwr_ds.datagen;

import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRConventionalTags;
import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import com.bwt.recipes.mill_stone.MillStoneRecipe;
import com.bwt.recipes.saw.SawRecipe;
import com.bwt.recipes.soul_forge.SoulForgeShapedRecipe;
import com.bwt.tags.BwtItemTags;
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
import org.ivangeevo.btwr_ds.RecipeProviderUtils;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;

import java.util.concurrent.CompletableFuture;


public class DS_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    public DS_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};


    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter)
    {
        // Recipes that get removed
        this.generateRecipesToRemove(exporter);

        // Minecraft
        this.overrideForVanilla(exporter);

        // BTWR-DS
        this.generateForMod(exporter);

        // Better With Time
        this.overrideForBWT(exporter);

        // BTWR: Core
        this.overrideForBTWR(exporter);

    }

    private void generateForMod(RecipeExporter exporter)
    {
        // Items
        this.createTannedLeatherRecipes(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK,2)
                .input('P', ItemTags.PLANKS)
                .pattern("P")
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, ID.ofDS("stick_from_single_planks"));

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

        // Millstone recipes
        MillStoneRecipe.JsonBuilder.create().result(BTWR_Items.LEATHER_SCOURED_CUT)
                .ingredient(BTWR_Items.LEATHER_CUT)
                .criterion("has_leather_cut", conditionsFromItem(BTWR_Items.LEATHER_CUT))
                .offerTo(exporter, ID.ofDS("leather_scoured_cut_from_mill_stone"));



        // Blocks
        // TODO: figure out why .additionalDrop() builder is not working on datagen
        /**
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.bloodWoodBlocks.planksBlock)
                .input(BwtBlocks.bloodWoodBlocks.logBlock)
                .input(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
                .additionalDrop(BTWRDS_Items.BLOOD_WOOD_BARK.getDefaultStack())
                .additionalDrop(SturdyTreesItems.DUST_SAW.getDefaultStack())
                .criterion("has_blood_wood_log", conditionsFromItem(BwtBlocks.bloodWoodBlocks.logBlock))
                .offerTo(exporter, ID.ofBWT("blood_wood_planks"));
         **/


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

    private void overrideForVanilla(RecipeExporter exporter)
    {

        // Blocks
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHAIN,4)
                .input('N', Items.IRON_NUGGET)
                .input('I', Items.IRON_INGOT)
                .pattern(" N ")
                .pattern(" I ")
                .pattern(" N ")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, ID.ofMC("chain"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LANTERN)
                .input('N', Items.IRON_NUGGET)
                .input('T', Items.TORCH)
                .pattern(" N ")
                .pattern("NTN")
                .pattern(" N ")
                .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, ID.ofMC("lantern"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LANTERN)
                .input('N', Items.IRON_NUGGET)
                .input('T', Items.SOUL_TORCH)
                .pattern(" N ")
                .pattern("NTN")
                .pattern(" N ")
                .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, ID.ofMC("soul_lantern"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.BONE_BLOCK)
                .input('S', Items.BONE)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .criterion("has_bone", conditionsFromItem(Items.BONE))
                .offerTo(exporter, ID.ofMC("bone_block"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Blocks.BLAST_FURNACE)
                .input('N', Items.IRON_NUGGET)
                .input('F', Items.FURNACE)
                .input('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("SSS")
                .pattern("NFN")
                .pattern("SNS")
                .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, ID.ofMC("blast_furnace"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LADDER, 2)
                .input('S', Items.STICK)
                .input('F', ConventionalItemTags.STRINGS)
                .pattern("SFS")
                .pattern("SSS")
                .pattern("SFS")
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, ID.ofMC( "ladder"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.HOPPER)
                .input('S', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('G', BTWRConventionalTags.Items.GEARS)
                .input('P', ItemTags.WOODEN_PRESSURE_PLATES)
                .input('W', BwtItemTags.WOODEN_CORNER_BLOCKS)
                .pattern("S S")
                .pattern("GPG")
                .pattern(" W ")
                .criterion("has_moulding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter);

        // Adding door recipes
        for (String woodType : vanillaWoodTypes)
        {
            Identifier resultId = ID.ofMC(woodType + "_door");
            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(resultId))
                    .input('P', grabRaw(woodType + "_planks"))
                    .pattern("PP")
                    .pattern("PP")
                    .pattern("PP")
                    .criterion("has_planks", conditionsFromItem(Registries.ITEM.get(ID.ofMC(woodType + "_planks"))))
                    .offerTo(exporter, resultId);
        }

        // Adding pressure plate recipes for each SidingBlock in BwtBlocks.sidingBlocks
        for (String woodType : vanillaWoodTypes)
        {
            Identifier resultId = ID.ofMC(woodType + "_pressure_plate");
            Block sidingBlock = Registries.BLOCK.get(ID.ofBWT(woodType + "_planks_siding"));

            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.BLOCK.get(resultId))
                    .input('S', sidingBlock)  // Use the current SidingBlock as the 'S' input
                    .input('R', Items.REDSTONE) // Redstone for the 'R' input
                    .pattern("S")
                    .pattern("R")
                    .criterion("has_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                    .offerTo(exporter, resultId);
        }


        // Create the recipe for the blood wood pressure plate
        Block bloodWoodSiding = Registries.BLOCK.get(ID.ofBWT("blood_wood_planks_siding"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.bloodWoodBlocks.pressurePlateBlock)
                .input('S', bloodWoodSiding)  // Use the blood wood SidingBlock as the 'S' input
                .input('R', Items.REDSTONE) // Redstone for the 'R' input
                .pattern("S")
                .pattern("R")
                .criterion("has_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, ID.ofBWT("blood_wood_pressure_plate"));


        // Adding button recipes for each CornerBlock in BwtBlocks.cornerBlock
        for (String woodType : vanillaWoodTypes)
        {
            Identifier resultId = ID.ofMC(woodType + "_button");
            Block block = Registries.BLOCK.get(ID.ofBWT(woodType + "_planks_corner"));

            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(resultId))
                    .input('S', block)
                    .input('R', Items.REDSTONE) // Redstone for the 'R' input
                    .pattern("S")
                    .pattern("R")
                    .criterion("has_wooden_corner", conditionsFromItem(block))
                    .offerTo(exporter, resultId);
        }

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.LEVER)
                .input('S', Items.STICK)
                .input('B', BTWR_Items.STONE_BRICK)
                .input('R', Items.REDSTONE)
                .pattern("S")
                .pattern("B")
                .pattern("R")
                .criterion("has_has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, ID.ofMC("lever"));

        // TODO: Add dispenser recipe when Redstone Latch item is added as item to BTWR: Core
        /**
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.DISPENSER)
                .input('S', Items.BOW)
                .input('B', BTWR_Items.STONE_BRICK)
                .input('L', BTWR_Items.REDSTONE_LATCH)
                .pattern("BBB")
                .pattern("BSB")
                .pattern("BLB")
                .criterion("has_has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, ID.ofMC("dispenser"));
         **/

        // TODO: Add piston recipe when Redstone Latch item is added as item to BTWR: Core
        /**
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.PISTON)
                .input('S', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('B', BTWR_Items.STONE_BRICK)
                .input('I', Items.IRON_INGOT)
                .input('U', Items.REDSTONE)
                .input('L', BTWR_Items.REDSTONE_LATCH)
                .pattern("SIS")
                .pattern("BUB")
                .pattern("BLB")
                .criterion("has_has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, ID.ofMC("piston"));
         **/

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.REPEATER)
                .input('C', Items.CLOCK)
                .input('R', Items.REDSTONE_TORCH)
                .input('B', BTWR_Items.STONE_BRICK)
                .pattern("RCR")
                .pattern("BBB")
                .criterion("has_redstone_torch", conditionsFromItem(Items.REDSTONE_TORCH))
                .offerTo(exporter, ID.ofMC("repeater"));


        // Create the recipe for the blood wood button
        Block bloodWoodCorner = Registries.BLOCK.get(ID.ofBWT("blood_wood_planks_corner"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.bloodWoodBlocks.buttonBlock)
                .input('S', bloodWoodCorner)  // Use the blood wood SidingBlock as the 'S' input
                .input('R', Items.REDSTONE) // Redstone for the 'R' input
                .pattern("S")
                .pattern("R")
                .criterion("has_blood_wood_corner", conditionsFromItem(bloodWoodCorner))
                .offerTo(exporter, ID.ofBWT("blood_wood_button"));



        // Items
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ARROW, 2)
                .input(Items.STICK)
                .input(Items.FEATHER)
                .input(Items.FLINT)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, ID.ofMC("arrow"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BONE, 9)
                .input(Items.BONE_BLOCK)
                .criterion("has_bone_block", conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, ID.ofMC("bone"));

        // Tools

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_SHOVEL)
                .input(Items.STICK)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, ID.ofMC("stone_shovel"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_AXE)
                .input(Items.STICK)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, ID.ofMC("stone_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                .input('R', Items.STICK)
                .input('S', ItemTags.STONE_TOOL_MATERIALS)
                .input('#', ConventionalItemTags.STRINGS)
                .pattern("SSS")
                .pattern("#R ")
                .pattern(" R ")
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, ID.ofMC("stone_pickaxe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.IRON_AXE)
                .input('M', Items.IRON_INGOT)
                .input('I', Items.STICK)
                .pattern("M ")
                .pattern("MI")
                .pattern(" I")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, ID.ofMC("iron_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.IRON_HOE)
                .input('M', Items.IRON_INGOT)
                .input('I', Items.STICK)
                .pattern("MI")
                .pattern(" I")
                .pattern(" I")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, ID.ofMC("iron_hoe"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_AXE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern("M ")
                .pattern("MI")
                .pattern(" I")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern("MMM")
                .pattern(" I ")
                .pattern(" I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_pickaxe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_HOE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern("MI")
                .pattern(" I")
                .pattern(" I")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_hoe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern(" M ")
                .pattern(" I ")
                .pattern(" I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_shovel"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_SWORD)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern(" M ")
                .pattern(" M ")
                .pattern(" I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_sword"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.FISHING_ROD)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(Items.IRON_NUGGET)
                .criterion("has_fishing_hook_material", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, ID.ofMC("fishing_rod"));


        // 1 torch for infini-torches
        // 2 when the rework in Self Sustainable for torches happens.
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.TORCH)
                .input('C', ItemTags.COALS)
                .input('S', Items.STICK)
                .pattern("C")
                .pattern("S")
                .criterion("has_coal", conditionsFromTag(ItemTags.COALS))
                .offerTo(exporter, ID.ofMC("torch"));


        // Cooking recipes
        // TODO: Remove the smelting recipes for ores when we add the Brick oven from Self Sustainable
        //offerSmelting(exporter, IRON_ORES, RecipeCategory.MISC, Items.IRON_NUGGET, 0.35F, 200, "iron_nugget");

        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Items.RAW_IRON), RecipeCategory.MISC, Items.IRON_NUGGET, 0.35F, 12000)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES))
                .offerTo(exporter, ID.ofMC("iron_ingot_from_smelting_raw_iron"));
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Items.IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.45F, 12000)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES))
                .offerTo(exporter, ID.ofMC( "iron_ingot_from_smelting_iron_ore"));
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Items.DEEPSLATE_IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.55F, 12000)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES))
                .offerTo(exporter, ID.ofMC( "iron_ingot_from_smelting_deepslate_iron_ore"));

        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(Items.RAW_IRON), RecipeCategory.MISC, Items.IRON_NUGGET, 0.45F, 6000)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES))
                .offerTo(exporter, ID.ofMC("iron_ingot_from_blasting_raw_iron"));
        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(Items.IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.55F, 6000)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES))
                .offerTo(exporter, ID.ofMC( "iron_ingot_from_blasting_iron_ore"));
        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(Items.DEEPSLATE_IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.65F, 6000)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES))
                .offerTo(exporter, ID.ofMC( "iron_ingot_from_blasting_deepslate_iron_ore"));


        // Armor
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_HELMET)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BTWR_Items.DIAMOND_PLATE)
                .pattern("III")
                .pattern("IPI")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_helmet"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BTWR_Items.DIAMOND_PLATE)
                .pattern("P P")
                .pattern("III")
                .pattern("III")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_chestplate"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BTWR_Items.DIAMOND_PLATE)
                .pattern("III")
                .pattern("P P")
                .pattern("P P")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_leggings"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .pattern("I I")
                .pattern("I I")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofMC("diamond_boots"));

    }





    private void overrideForBWT(RecipeExporter exporter)
    {
        // Items
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.sailItem)
                .input('F', BwtItems.fabricItem)
                .input('W', ItemTags.PLANKS)
                .pattern("FFF")
                .pattern("WWW")
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, ID.ofBWT("sail"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtItems.sailItem)
                .input('F', BwtItems.fabricItem)
                .input('W', BwtItemTags.WOODEN_MOULDING_BLOCKS)
                .pattern("FFF")
                .pattern("WWW")
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, ID.ofBWT("he_sail"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.paddingItem)
                .input('F', BwtItems.fabricItem)
                .input('W', ItemTags.WOOL)
                .input('C', Items.FEATHER)
                .pattern(" F ")
                .pattern("CWC")
                .pattern(" F ")
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, ID.ofBWT("padding"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.strapItem,4)
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .input(BTWR_Items.LEATHER_TANNED_CUT)
                .criterion("has_leather_tanned_cut", conditionsFromItem(BTWR_Items.LEATHER_TANNED_CUT))
                .offerTo(exporter, ID.ofBWT("strap"));



        // Blocks
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.gearBoxBlock)
                .input('W', ItemTags.PLANKS)
                .input('A', BwtBlocks.axleBlock)
                .input('G', BwtItems.gearItem)
                .pattern("WGW")
                .pattern("GAG")
                .pattern("WGW")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, ID.ofBWT("gear_box"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.gearBoxBlock)
                .input('W', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('A', BwtBlocks.axleBlock)
                .input('G', BwtItems.gearItem)
                .pattern("WGW")
                .pattern("GAG")
                .pattern("WGW")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, ID.ofBWT("he_gear_box"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LADDER,2)
                .input('P', BwtItemTags.WOODEN_MOULDING_BLOCKS)
                .input('S', ConventionalItemTags.STRINGS)
                .pattern("PSP")
                .pattern("PPP")
                .pattern("PSP")
                .criterion("has_wooden_moulding", RecipeProvider.conditionsFromTag(BwtItemTags.WOODEN_MOULDING_BLOCKS))
                .offerTo(exporter, ID.ofBWT("he_ladder"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.millStoneBlock)
                .input('b', BTWR_Items.STONE_BRICK)
                .input('g', BwtItems.gearItem)
                .pattern("bbb")
                .pattern("bbb")
                .pattern("bgb")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, ID.ofBWT("mill_stone"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.handCrankBlock)
                .input('b', BTWR_Items.STONE_BRICK)
                .input('g', BwtItems.gearItem)
                .input('s', Items.STICK)
                .pattern("  s")
                .pattern(" s ")
                .pattern("bgb")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, ID.ofBWT("hand_crank"));

        // TODO: Add piston recipe when Element item is added to BTWR: Core
        /**
         ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.hibachiBlock)
         .input('H', BwtItems.concentratedHellfireItem)
         .input('E', BTWR_Items.ELEMENT)
         .input('B', BTWR_Items.STONE_BRICK)
         .input('R', Items.REDSTONE)
         .pattern("HHH")
         .pattern("BEB")
         .pattern("BRB")
         .criterion("has_has_redstone", conditionsFromItem(Items.REDSTONE))
         .offerTo(exporter, ID.ofBWT("hibachi"));
         **/


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.anchorBlock)
                .input('I', Items.IRON_NUGGET)
                .input('B', BTWR_Items.STONE_BRICK)
                .pattern("   ")
                .pattern(" I ")
                .pattern("BBB")
                .criterion("has_stone_brick", conditionsFromItem(BTWR_Items.STONE_BRICK))
                .offerTo(exporter, ID.ofBWT("anchor"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.turntableBlock)
                .input('C', Items.CLOCK)
                .input('S', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('B', BTWR_Items.STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .pattern("SSS")
                .pattern("BCB")
                .pattern("BGB")
                .criterion("has_wooden_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, ID.ofBWT("turntable"));

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.detectorBlock)
                .input('B', BTWR_Items.STONE_BRICK)
                .input('E', BwtItems.redstoneEyeItem)
                .input('T', Items.REDSTONE_TORCH)
                .input('R', Items.REDSTONE)
                .pattern("BBBB")
                .pattern("ETTE")
                .pattern("BRRB")
                .pattern("BRRB")
                .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, ID.ofBWT("detector_block"));

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.blockDispenserBlock)
                .input('B', BTWR_Items.STONE_BRICK)
                .input('M', Blocks.MOSSY_COBBLESTONE)
                .input('U', BwtItems.soulUrnItem)
                .input('T', Items.REDSTONE_TORCH)
                .input('R', Items.REDSTONE)
                .pattern("MMMM")
                .pattern("MUUM")
                .pattern("BTTB")
                .pattern("BRRB")
                .criterion("has_soul_urn", conditionsFromItem(BwtItems.soulUrnItem))
                .offerTo(exporter, ID.ofBWT("block_dispenser"));

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.buddyBlock)
                .input('B', BTWR_Items.STONE_BRICK)
                .input('E', BwtItems.redstoneEyeItem)
                .input('T', Items.REDSTONE_TORCH)
                .pattern("BBEB")
                .pattern("ETTB")
                .pattern("BTTE")
                .pattern("BEBB")
                .criterion("has_soul_urn", conditionsFromItem(BwtItems.soulUrnItem))
                .offerTo(exporter, ID.ofBWT("buddy_block"));

        // TODO: Add pulley recipe when Redstone Latch is added as item to BTWR: Core
        /**
         ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.pulleyBlock)
         .input('W', BTWR_Items.STONE_BRICK)
         .input('I', Items.IRON_INGOT)
         .input('G', BwtItems.gearItem)
         .input('L', BTWR_Items.REDSTONE_LATCH)
         .pattern("WIW")
         .pattern("GLG")
         .pattern("WIW")
         .criterion("has_redstone_latch", conditionsFromItem(BTWR_Items.REDSTONE_LATCH))
         .offerTo(exporter, ID.ofBWT("pulley"));
         **/


        // Overwritten cauldron recipes
        CauldronRecipe.JsonBuilder.createFood().result(BwtItems.donutItem,2)
                .ingredient(BwtItems.flourItem)
                .ingredient(Items.SUGAR)
                .criterion("has_flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, ID.ofBWT("donut_from_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(BwtItems.kibbleItem,2)
                .ingredient(Items.BONE_MEAL,4)
                .ingredient(Items.ROTTEN_FLESH,4)
                .ingredient(Items.SUGAR)
                .criterion("has_bone_meal", conditionsFromItem(Items.BONE_MEAL))
                .offerTo(exporter, ID.ofBWT("kibble_from_stoked_cauldron"));



        // Tools
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BwtItems.compositeBowItem)
                .input('m', BwtItemTags.WOODEN_MOULDING_BLOCKS)
                .input('g', BwtItems.glueItem)
                .input('b', Items.BONE)
                .input('s', Items.STRING)
                .pattern("gmb")
                .pattern("mbs")
                .pattern("gmb")
                .criterion("has_glue", conditionsFromItem(BwtItems.glueItem))
                .offerTo(exporter, ID.ofBWT("composite_bow"));


        // Millstone recipes replacement
        MillStoneRecipe.JsonBuilder.create()
                .ingredient(BTWR_Items.HEMP_LEAVES)
                .result(BwtItems.hempFiberItem, 4)
                .criterion("has_hemp_leaves", conditionsFromItem(BwtItems.hempItem))
                .offerTo(exporter, ID.ofBWT("hemp_fiber_from_milling_hemp"));

        // Saw recipes replacement
        this.createSawLogRecipes(exporter);


    }

    private void overrideForBTWR(RecipeExporter exporter)
    {


        // BTWR overwritten recipes for food
        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_RAW, 2)
                .input(BwtItems.rawEggItem)
                .input(Items.MILK_BUCKET)
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofBTWR("egg_scrambled_raw"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_RAW)
                .input('E', BwtItems.rawEggItem)
                .input('M', Items.BROWN_MUSHROOM)
                .pattern("EM")
                .pattern("MM")
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofBTWR("mushroom_omelette_raw"));

        CauldronRecipe.JsonBuilder.create().result(BTWR_Items.BOILED_POTATO)
                .ingredient(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .criterion("has_potato", conditionsFromItem(Items.POTATO))
                .offerTo(exporter, ID.ofBTWR("boiled_potato_from_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(BTWR_Items.COOKED_CARROT)
                .ingredient(Items.CARROT)
                .criterion("has_carrot", conditionsFromItem(Items.CARROT))
                .offerTo(exporter, ID.ofBTWR("cooked_carrot_from_cauldron"));


        CauldronRecipe.JsonBuilder.create().result(BTWR_Items.CHOWDER,2)
                .ingredient(ConventionalItemTags.COOKED_FISH_FOODS)
                .ingredient(Items.MILK_BUCKET)
                .ingredient(Items.BOWL, 2)
                .criterion("has_milk_bucket", conditionsFromItem(Items.MILK_BUCKET))
                .offerTo(exporter, ID.ofBTWR("chowder"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.WOLF_DINNER,3)
                .input(BwtItems.cookedWolfChopItem)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(BTWR_Items.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(BTWR_Items.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("wolf_dinner"));

        CauldronRecipe.JsonBuilder.create().result(BTWR_Items.COOKED_KEBAB)
                .ingredient(BTWR_Items.RAW_KEBAB)
                .criterion("has_raw_kebab", conditionsFromItem(BTWR_Items.RAW_KEBAB))
                .offerTo(exporter, ID.ofBTWR("cooked_kebab_from_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(BTWR_Items.CHICKEN_SOUP, 3)
                .ingredient(Items.COOKED_CHICKEN)
                .ingredient(BTWR_Items.COOKED_CARROT)
                .ingredient(BTWR_Items.BOILED_POTATO)
                .ingredient(Items.BOWL, 3)
                .criterion("has_boiled_potato", conditionsFromItem(BTWR_Items.BOILED_POTATO))
                .offerTo(exporter, ID.ofBTWR("chicken_soup_from_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(BTWR_Items.HEARTY_STEW, 5)
                .ingredient(BTWRConventionalTags.Items.COOKED_MEATS)
                .ingredient(BTWR_Items.COOKED_CARROT)
                .ingredient(BTWR_Items.BOILED_POTATO)
                .ingredient(Items.BOWL, 5)
                .ingredient(Items.BROWN_MUSHROOM, 3)
                .ingredient(BwtItems.flourItem)
                .criterion("has_boiled_potato", conditionsFromItem(BTWR_Items.BOILED_POTATO))
                .offerTo(exporter, ID.ofBTWR("hearty_stew_from_cauldron"));

        // Blocks
        // TODO: Add soulforged recipe for the chopping block when it's added to BTWR: Core
        /**
         SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BTWR_Blocks.CHOPPING_BLOCK)
         .input('B', BTWR_Items.STONE_BRICK)
         .pattern("B  B")
         .pattern("B  B")
         .pattern("BBBB")
         .criterion("has_stone_brick", conditionsFromItem(BTWR_Items.STONE_BRICK))
         .offerTo(exporter, ID.ofBTWR("chopping_block"));
         **/


    }

    private void generateRecipesToRemove(RecipeExporter exporter)
    {
        /** Vanilla recipes to remove **/

        // Remove item recipes
        removeRecipe(exporter, ID.ofMC("bone_meal"));


        // Remove blocks recipes
        removeRecipe(exporter, ID.ofMC("crafting_table"));
        removeRecipe(exporter, ID.ofMC("chest"));

        // Remove tool recipes
        removeRecipe(exporter, ID.ofMC("wooden_sword"));
        removeRecipe(exporter, ID.ofMC("wooden_pickaxe"));
        removeRecipe(exporter, ID.ofMC("wooden_axe"));
        removeRecipe(exporter, ID.ofMC("wooden_shovel"));
        removeRecipe(exporter, ID.ofMC("wooden_hoe"));
        removeRecipe(exporter, ID.ofMC("stone_sword"));
        removeRecipe(exporter, ID.ofMC("stone_hoe"));

        // Remove cooking recipes
        removeRecipe(exporter, ID.ofMC("charcoal"));

        // Remove the ability to repair items by combining them
        removeRecipe(exporter, ID.ofMC("repair_item"));

        /** Tough Environment recipes to remove **/

        removeRecipe(exporter, ID.ofTE("furnace"));

        /** BWT recipes to remove **/
        removeRecipe(exporter, ID.ofBWT("grate"));
        removeRecipe(exporter, ID.ofBWT("fried_egg_from_campfire_cooking"));
        removeRecipe(exporter, ID.ofBWT("tanned_leather_from_cauldron"));

        // Removing High efficiency button recipes
        for (String woodType : vanillaWoodTypes)
        {
            removeRecipe(exporter, ID.ofBWT("he_" + woodType + "_button"));
        }
        removeRecipe(exporter, ID.ofBWT("he_blood_wood_button"));

        // Removing High efficiency pressure plate recipes
        for (String woodType : vanillaWoodTypes)
        {
            removeRecipe(exporter, ID.ofBWT("he_" + woodType + "_pressure_plate"));
        }
        removeRecipe(exporter, ID.ofBWT("he_blood_wood_pressure_plate"));



        /** BTWR recipes to remove **/
        removeRecipe(exporter, ID.ofBTWR("egg_scrambled_cooked_from_campfire_cooking"));
        removeRecipe(exporter, ID.ofBTWR("mushroom_omelette_cooked_from_campfire_cooking"));
        removeRecipe(exporter, ID.ofBTWR("steak_dinner"));
        removeRecipe(exporter, ID.ofBTWR("pork_dinner"));
        removeRecipe(exporter, ID.ofBTWR("chicken_soup"));
        removeRecipe(exporter, ID.ofBTWR("hearty_stew"));

        removeRecipe(exporter, ID.ofBTWR("gear"));
        removeRecipe(exporter, ID.ofBTWR("strap"));
        removeRecipe(exporter, ID.ofBTWR("leather_scoured"));
        removeRecipe(exporter, ID.ofBTWR("leather_tanned"));
        removeRecipe(exporter, ID.ofBTWR("leather_scoured_cut"));
        removeRecipe(exporter, ID.ofBTWR("leather_tanned_cut"));


    }

    private void createTannedLeatherRecipes(RecipeExporter exporter)
    {
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

    private void createSawLogRecipes(RecipeExporter exporter)
    {
        this.sawLogBuilder(exporter, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS, SturdyTreesItems.BARK_JUNGLE);
        this.sawLogBuilder(exporter, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, SturdyTreesItems.BARK_DARK_OAK);
        this.sawLogBuilder(exporter, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, SturdyTreesItems.BARK_SPRUCE);
        this.sawLogBuilder(exporter, Blocks.MANGROVE_LOG, Blocks.MANGROVE_PLANKS, SturdyTreesItems.BARK_MANGROVE);
        this.sawLogBuilder(exporter, Blocks.CHERRY_LOG, Blocks.CHERRY_PLANKS, SturdyTreesItems.BARK_CHERRY);
        this.sawLogBuilder(exporter, Blocks.OAK_LOG, Blocks.OAK_PLANKS, SturdyTreesItems.BARK_OAK);
        this.sawLogBuilder(exporter, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS, SturdyTreesItems.BARK_ACACIA);
        this.sawLogBuilder(exporter, BwtBlocks.bloodWoodBlocks.logBlock, BwtBlocks.bloodWoodBlocks.planksBlock, BTWRDS_Items.BARK_BLOOD_WOOD);
    }
    /** Creates a tanned leather recipe by only passing the bark item and the amount **/
    private void leatherRecipeBuilder(RecipeExporter exporter, Item barkItem, int count)
    {
        CauldronRecipe.JsonBuilder.create().result(BwtItems.tannedLeatherItem)
                .ingredient(BwtItems.scouredLeatherItem)
                .ingredient(BwtItems.dungItem)
                .ingredient(barkItem, count)
                .criterion("has_scoured_leather", conditionsFromItem(BwtItems.scouredLeatherItem))
                .offerTo(exporter, ID.ofDS("tanned_leather_with_" + extractName(barkItem) + "_in_cauldron"));

        CauldronRecipe.JsonBuilder.create().result(BwtItems.tannedLeatherItem)
                .ingredient(BTWR_Items.LEATHER_SCOURED_CUT,2)
                .ingredient(BwtItems.dungItem)
                .ingredient(barkItem, count)
                .criterion("has_scoured_leather", conditionsFromItem(BwtItems.scouredLeatherItem))
                .offerTo(exporter, ID.ofDS("tanned_leather_from_cut_scoured_leather_with_" + extractName(barkItem) + "_in_cauldron"));
    }

    private void sawLogBuilder(RecipeExporter exporter, Block logBlock, Block planksBlock, Item barkItem)
    {
        SawRecipe.JsonBuilder.create(logBlock)
                .result(planksBlock,4)
                .result(barkItem)
                .result(BwtItems.sawDustItem, 2)
                .criterion("has_log", conditionsFromTag(ItemTags.LOGS))
                .offerTo(exporter, ID.ofBWT("saw_" + extractName(logBlock)));
    }

    /** Helper method to extract wood type from an item's translation key **/
    private String extractName(Item barkItem) {
        // Extracts the actual bark type (e.g., "oak_bark") from the translation key.
        String[] parts = barkItem.getTranslationKey().split("\\.");
        return parts[parts.length - 1];
    }

    private String extractName(Block logBlock) {
        // Extracts the actual name of the item/block (e.g., "oak_bark") from the translation key.
        String[] parts = logBlock.getTranslationKey().split("\\.");
        return parts[parts.length - 1];
    }

    private static class ID
    {
        static Identifier ofMC(String item) { return Identifier.ofVanilla(item); }
        /** DS - Datapack Suite **/
        static Identifier ofDS(String item) { return Identifier.of("btwr-ds", item); }
        /** BTWR: Core **/
        static Identifier ofBTWR(String item) { return Identifier.of("btwr", item); }
        /** BWT - Better With Time **/
        static Identifier ofBWT(String item)
        {
            return Identifier.of("bwt", item);
        }
        static Identifier ofTE(String item) { return Identifier.of("tough_environment", item); }
        static Identifier ofST(String item) { return Identifier.of("sturdy_trees", item); }
        static Identifier ofSS(String item) { return Identifier.of("self_sustainable", item); }

    }


}
