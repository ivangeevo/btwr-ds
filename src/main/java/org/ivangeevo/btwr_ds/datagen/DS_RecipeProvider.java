package org.ivangeevo.btwr_ds.datagen;

import btwr.btwrsl.lib.util.utils.RecipeProviderUtils;
import btwr.btwrsl.tag.BTWRConventionalTags;
import btwr.core.item.BTWR_Items;
import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import com.bwt.recipes.cooking_pots.StokedCauldronRecipe;
import com.bwt.recipes.cooking_pots.StokedCrucibleRecipe;
import com.bwt.recipes.kiln.KilnRecipe;
import com.bwt.recipes.mill_stone.MillStoneRecipe;
import com.bwt.recipes.saw.SawRecipe;
import com.bwt.recipes.soul_forge.SoulForgeShapedRecipe;
import com.bwt.recipes.turntable.TurntableRecipe;
import com.bwt.tags.BwtItemTags;
import com.google.common.collect.Maps;
import ivangeevo.sturdy_trees.SturdyTreesItems;
import ivangeevo.sturdy_trees.tag.SturdyTreesTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.ivangeevo.vegehenna.item.ModItems;
import org.tough_environment.block.ModBlocks;

import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class DS_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    public DS_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};

    private static final String MC = "minecraft";
    private static final String TE = "tough_environment";
    private static final String BTWR = "btwr";
    private static final String BWT = "bwt";
    private static final String VG = "vegehenna";
    private static final String DS = "btwr-ds";


    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    // recipes to remove are only for ones that we don't overwrite with another ingredients/output.
    // the ones we overwrite are in the override methods, and this mod is in the generateForMod() method
    @Override
    public void generate(RecipeExporter exporter)
    {
        // Recipes that get removed
        //this.generateRecipesToRemove(exporter);

        // Minecraft
        //this.overrideForVanilla(exporter);

        // BTWR-DS
        this.generateForMod(exporter);

        // Better With Time
        //this.overrideForBWT(exporter);

        // BTWR: Core
        //this.overrideForBTWR(exporter);

        // Vegehenna
        //this.overrideForVegehenna(exporter);

    }

    private void generateForMod(RecipeExporter exporter)
    {
        // Items
        this.createTannedLeatherRecipes(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWRDS_Items.COPPER_NUGGET, 9)
                .input(Items.COPPER_INGOT)
                .criterion("has_copper_ingot", conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, ID.ofDS("copper_nugget_from_copper_ingot"));

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

        MillStoneRecipe.JsonBuilder.create().result(Items.BLAZE_POWDER,2)
                .ingredient(Items.BLAZE_ROD)
                .criterion("has_blaze_rod", conditionsFromItem(Items.BLAZE_ROD))
                .offerTo(exporter, ID.ofDS("blaze_powder_from_mill_stone"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.FURNACE)
                .input('B', ModBlocks.SLAB_BRICKS_LOOSE)
                .pattern("BB")
                .pattern("BB")
                .criterion("has_slab_bricks_loose", conditionsFromItem(ModBlocks.SLAB_BRICKS_LOOSE))
                .offerTo(exporter, ID.ofDS("furnace_from_slab_bricks"));


        // Blocks
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

        // Cauldron recipes
        CauldronRecipe.JsonBuilder.create().result(BTWR_Items.ELEMENT)
                .ingredient(Items.BLAZE_POWDER)
                .ingredient(Items.REDSTONE)
                .ingredient(ConventionalItemTags.STRINGS)
                .criterion("has_blaze_powder", conditionsFromItem(Items.BLAZE_POWDER))
                .offerTo(exporter, ID.ofDS("element_from_cauldron"));
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
                .input('B', Items.IRON_INGOT)
                .input('N', Items.IRON_NUGGET)
                .input('C', Items.CHEST)
                .pattern("B B")
                .pattern("NCN")
                .pattern(" N ")
                .criterion("has_chest", conditionsFromItem(Items.CHEST))
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LEAD, 2)
                .input('F', BwtItems.hempFiberItem)
                .input('R', BwtItems.ropeItem)
                .pattern(" FF")
                .pattern(" FF")
                .pattern("R  ")
                .criterion("has_rope", conditionsFromItem(BwtItems.ropeItem))
                .offerTo(exporter, ID.ofMC("lead"));

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

        // TODO: Remove the nugget recipes when we add the Brick oven from Self Sustainable
        this.createNuggetRecipes(exporter);

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
                .input('B', BTWR_Items.STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BGB")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, ID.ofBWT("mill_stone"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.handCrankBlock)
                .input('B', BTWR_Items.STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .input('S', Items.STICK)
                .pattern("  S")
                .pattern(" S ")
                .pattern("BGB")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, ID.ofBWT("hand_crank"));

         ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.hibachiBlock)
         .input('H', BwtItems.concentratedHellfireItem)
         .input('E', BTWR_Items.ELEMENT)
         .input('B', BTWR_Items.STONE_BRICK)
         .input('R', Items.REDSTONE)
         .pattern("HHH")
         .pattern("BEB")
         .pattern("BRB")
         .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
         .offerTo(exporter, ID.ofBWT("hibachi"));


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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.soulForgeBlock)
                .input(Items.ANVIL)
                .input(Items.NETHER_STAR)
                .criterion("has_anvil", conditionsFromItem(Items.ANVIL))
                .offerTo(exporter, ID.ofBWT("soul_forge"));



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

        TurntableRecipe.JsonBuilder.create(ModBlocks.CLAY_BLOCK, BwtBlocks.unfiredCrucibleBlock)
                .drops(Items.CLAY_BALL).offerTo(exporter, ID.ofBWT("turntable_clay"));

        // Stoked crucible smelting recipes
        this.generateResmeltingRecipes(exporter);

        // Kiln recipes
        this.generateKilnRecipes(exporter);

        // Saw recipes replacement
        this.createSawLogRecipes(exporter);

        // Cauldron Recipes

        // Unstoked

        // Stoked
        this.generateStokedRecipes(exporter);

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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.STEAK_DINNER,3)
                .input(Items.COOKED_BEEF)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(BTWR_Items.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(BTWR_Items.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("steak_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.PORK_DINNER,3)
                .input(Items.PORKCHOP)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(BTWR_Items.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(BTWR_Items.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("pork_dinner"));

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

        // Items
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.DIAMOND_PLATE)
                .input('S', BwtItems.strapItem)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BwtItems.paddingItem)
                .pattern("SIS")
                .pattern(" P ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofBTWR("diamond_plate"));


    }

    private void overrideForVegehenna(RecipeExporter exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PASTRY_UNCOOKED_CAKE)
                .input('E', BwtItems.rawEggItem)
                .input('M', Items.MILK_BUCKET)
                .input('F', BwtItems.flourItem)
                .input('S', Items.SUGAR)
                .pattern("SSS")
                .pattern("MEM")
                .pattern("FFF")
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_cake"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PASTRY_UNCOOKED_PUMPKIN_PIE)
                .input(BwtItems.rawEggItem)
                .input(Items.SUGAR)
                .input(Items.PUMPKIN)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .criterion("flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_pumpkin_pie"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BREAD_DOUGH)
                .input('F', BwtItems.flourItem)
                .pattern("F ")
                .pattern("FF")
                .criterion("flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, ID.ofVG("bread_dough"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PASTRY_UNCOOKED_COOKIES)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(ModItems.CHOCOLATE)
                .criterion("has_chocolate", conditionsFromItem(ModItems.CHOCOLATE))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_cookies"));


    }

    private void generateRecipesToRemove(RecipeExporter exporter) {
        /** Vanilla recipes to remove **/

        // Remove item recipes
        disableVanilla(exporter, "bone_meal");

        // Food item recipes
        disableVanilla(exporter, "bread");
        disableVanilla(exporter, "cookie");
        disableVanilla(exporter, "sugar_from_sugar_cane");

        disableVanilla(exporter, "blaze_powder");

        // Remove blocks recipes
        disableVanilla(exporter, "crafting_table");
        disableVanilla(exporter, "chest");
        disableVanilla(exporter, "furnace");


        // Remove tool recipes
        disableVanilla(exporter, "wooden_sword");
        disableVanilla(exporter, "wooden_pickaxe");
        disableVanilla(exporter, "wooden_axe");
        disableVanilla(exporter, "wooden_shovel");
        disableVanilla(exporter, "wooden_hoe");
        disableVanilla(exporter, "stone_sword");
        disableVanilla(exporter, "stone_hoe");

        // Remove cooking recipes
        disableVanilla(exporter, "charcoal");

        // Remove the ability to repair items by combining them
        disableVanilla(exporter, "repair_item");

        /** Tough Environment recipes to remove **/

        disableTE(exporter, "furnace");

        disableTE(exporter, "white_cobblestone");
        disableTE(exporter, "white_cobblestone_from_blasting");


        /** BWT recipes to remove **/
        disableBWT(exporter, "grate");
        disableBWT(exporter, "fried_egg_from_campfire_cooking");
        disableBWT(exporter, "tanned_leather_from_cauldron");

        // Removing High efficiency button recipes
        for (String woodType : vanillaWoodTypes)
        {
            disableBWT(exporter, "he_" + woodType + "_button");
        }

        disableBWT(exporter, "he_blood_wood_button");

        // Removing High efficiency pressure plate recipes
        for (String woodType : vanillaWoodTypes)
        {
            disableBWT(exporter, "he_" + woodType + "_pressure_plate");
        }
        disableBWT(exporter, "he_blood_wood_pressure_plate");

        disableBWT(exporter, "smelt_flint_and_steel_in_crucible");

        disableBWT(exporter, "kiln_cook_coal_ores");
        disableBWT(exporter, "kiln_cook_diamond_ores");
        disableBWT(exporter, "kiln_cook_emerald_ores");
        disableBWT(exporter, "kiln_cook_lapis_ores");
        disableBWT(exporter, "kiln_cook_redstone_ores");



        /** BTWR recipes to remove **/
        disableBTWR(exporter, "egg_scrambled_cooked_from_campfire_cooking");
        disableBTWR(exporter, "mushroom_omelette_cooked_from_campfire_cooking");
        disableBTWR(exporter, "chicken_soup");
        disableBTWR(exporter, "hearty_stew");

        disableBTWR(exporter, "gear");
        disableBTWR(exporter, "strap");
        disableBTWR(exporter, "leather_scoured");
        disableBTWR(exporter, "leather_tanned");
        disableBTWR(exporter, "leather_scoured_cut");
        disableBTWR(exporter, "leather_tanned_cut");

        disableBTWR(exporter, "element");

        /** Vegehenna recipes to remove **/
        disableVG(exporter, "flour");

    }

    private void createHERecipes() {

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

    private void createSawLogRecipes(RecipeExporter exporter) {
        this.sawLogBuilder(exporter, Blocks.JUNGLE_LOG, Blocks.JUNGLE_PLANKS, SturdyTreesItems.BARK_JUNGLE);
        this.sawLogBuilder(exporter, Blocks.DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, SturdyTreesItems.BARK_DARK_OAK);
        this.sawLogBuilder(exporter, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, SturdyTreesItems.BARK_SPRUCE);
        this.sawLogBuilder(exporter, Blocks.MANGROVE_LOG, Blocks.MANGROVE_PLANKS, SturdyTreesItems.BARK_MANGROVE);
        this.sawLogBuilder(exporter, Blocks.CHERRY_LOG, Blocks.CHERRY_PLANKS, SturdyTreesItems.BARK_CHERRY);
        this.sawLogBuilder(exporter, Blocks.OAK_LOG, Blocks.OAK_PLANKS, SturdyTreesItems.BARK_OAK);
        this.sawLogBuilder(exporter, Blocks.ACACIA_LOG, Blocks.ACACIA_PLANKS, SturdyTreesItems.BARK_ACACIA);
        this.sawLogBuilder(exporter, BwtBlocks.bloodWoodBlocks.logBlock, BwtBlocks.bloodWoodBlocks.planksBlock, BTWRDS_Items.BARK_BLOOD_WOOD);
    }

    private void generateResmeltingRecipes(RecipeExporter exporter) {
        // Iron
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_HELMET).result(Items.IRON_NUGGET, 30).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_CHESTPLATE).result(Items.IRON_NUGGET, 48).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_LEGGINGS).result(Items.IRON_NUGGET, 42).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_BOOTS).result(Items.IRON_NUGGET, 24).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_PICKAXE).result(Items.IRON_NUGGET, 18).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_SHOVEL).result(Items.IRON_NUGGET, 6).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_AXE).result(Items.IRON_NUGGET, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_HOE).result(Items.IRON_NUGGET, 6).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_SWORD).result(Items.IRON_NUGGET, 12).offerTo(exporter);

        // Chainmail
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.CHAINMAIL_HELMET).result(Items.IRON_NUGGET, 20).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.CHAINMAIL_CHESTPLATE).result(Items.IRON_NUGGET, 38).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.CHAINMAIL_LEGGINGS).result(Items.IRON_NUGGET, 32).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.CHAINMAIL_BOOTS).result(Items.IRON_NUGGET, 14).offerTo(exporter);

        // Golden
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_HELMET).result(Items.GOLD_NUGGET, 30).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_CHESTPLATE).result(Items.GOLD_NUGGET, 48).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_LEGGINGS).result(Items.GOLD_NUGGET, 42).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_BOOTS).result(Items.GOLD_NUGGET, 24).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_PICKAXE).result(Items.GOLD_NUGGET, 18).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_SHOVEL).result(Items.GOLD_NUGGET, 6).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_AXE).result(Items.GOLD_NUGGET, 12).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_HOE).result(Items.GOLD_NUGGET, 6).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.GOLDEN_SWORD).result(Items.GOLD_NUGGET, 12).offerTo(exporter);

        // Diamond
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_HELMET).result(BTWR_Items.DIAMOND_INGOT, 5).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_CHESTPLATE).result(BTWR_Items.DIAMOND_INGOT, 8).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_LEGGINGS).result(BTWR_Items.DIAMOND_INGOT, 7).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_BOOTS).result(BTWR_Items.DIAMOND_INGOT, 4).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_PICKAXE).result(BTWR_Items.DIAMOND_INGOT, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_SHOVEL).result(BTWR_Items.DIAMOND_INGOT, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_AXE).result(BTWR_Items.DIAMOND_INGOT, 2).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_HOE).result(BTWR_Items.DIAMOND_INGOT, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DIAMOND_SWORD).result(BTWR_Items.DIAMOND_INGOT, 2).offerTo(exporter);

        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_HELMET).result(Items.NETHERITE_INGOT, 8).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_CHESTPLATE).result(Items.NETHERITE_INGOT, 12).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_LEGGINGS).result(Items.NETHERITE_INGOT, 6).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_BOOTS).result(Items.NETHERITE_INGOT, 6).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(BwtItems.netheriteMattockItem).result(Items.NETHERITE_INGOT, 4).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(BwtItems.netheriteBattleAxeItem).result(Items.NETHERITE_INGOT, 5).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_PICKAXE).result(Items.NETHERITE_INGOT, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_SHOVEL).result(Items.NETHERITE_INGOT, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_AXE).result(Items.NETHERITE_INGOT, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_HOE).result(Items.NETHERITE_INGOT, 2).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_SWORD).result(Items.NETHERITE_INGOT, 2).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_BLOCK).result(Items.NETHERITE_INGOT, 16).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(BwtBlocks.cauldronBlock.asItem()).result(Items.IRON_NUGGET, 42).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.RAIL, 8).result(Items.IRON_INGOT, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.POWERED_RAIL).result(Items.GOLD_INGOT, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DETECTOR_RAIL).result(Items.IRON_INGOT, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_DOOR).result(Items.IRON_INGOT, 4).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(BwtBlocks.stoneDetectorRailBlock.asItem()).result(Items.IRON_INGOT, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(BwtBlocks.obsidianDetectorRailBlock.asItem()).result(Items.IRON_INGOT, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.COMPASS).result(Items.IRON_NUGGET, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.CLOCK).result(Items.GOLD_NUGGET, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.MINECART).result(Items.IRON_NUGGET, 30).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.SHEARS).result(Items.IRON_NUGGET, 12).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NOTE_BLOCK).result(Items.GOLD_NUGGET, 2).offerTo(exporter);

    }

    private void generateKilnRecipes(RecipeExporter exporter) {
        // Ore blocks
        KilnRecipe.JsonBuilder.create(BlockTags.IRON_ORES).drops(Items.IRON_NUGGET).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BlockTags.GOLD_ORES).drops(Items.GOLD_NUGGET).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BlockTags.COPPER_ORES).drops(BTWRDS_Items.COPPER_NUGGET).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(Blocks.ANCIENT_DEBRIS).drops(Items.NETHERITE_SCRAP).offerTo(exporter);

        // Ore blocks
        KilnRecipe.JsonBuilder.create(ConventionalBlockTags.STORAGE_BLOCKS_RAW_IRON).drops(Items.IRON_INGOT).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(ConventionalBlockTags.STORAGE_BLOCKS_RAW_GOLD).drops(Items.GOLD_INGOT).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(ConventionalBlockTags.STORAGE_BLOCKS_RAW_COPPER).drops(Items.COPPER_INGOT).offerTo(exporter);

        KilnRecipe.JsonBuilder.create(BlockTags.LOGS).drops(Items.CHARCOAL).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredCrucibleBlock).drops(BwtBlocks.crucibleBlock).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredPlanterBlock).drops(BwtBlocks.planterBlock).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredVaseBlock).drops((ItemConvertible)BwtBlocks.vaseBlocks.get(DyeColor.WHITE)).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredUrnBlock).drops(BwtBlocks.urnBlock).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(BwtBlocks.unfiredMouldBlock).drops(BwtItems.mouldItem).offerTo(exporter);
        KilnRecipe.JsonBuilder.create(ModBlocks.CLAY_BLOCK).drops(Blocks.TERRACOTTA).offerTo(exporter);
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

    private void generateStokedRecipes(RecipeExporter exporter) {
        // Glue
        Map<Item, Integer> GLUE_AMOUNTS = Util.make(Maps.newHashMap(), map -> {

            // Regular leathers
            map.put(Items.LEATHER_HELMET, 2);
            map.put(Items.LEATHER_CHESTPLATE, 4);
            map.put(Items.LEATHER_LEGGINGS, 3);
            map.put(Items.LEATHER_BOOTS, 2);
            map.put(Items.SADDLE, 2);
            map.put(Items.LEATHER, 1);
            map.put(BTWR_Items.LEATHER_CUT, 2);

            // Scoured
            map.put(BwtItems.scouredLeatherItem, 1);
            map.put(BTWR_Items.LEATHER_SCOURED_CUT, 2);

            // Tanned
            map.put(BwtItems.tannedLeatherItem, 1);
            map.put(BTWR_Items.LEATHER_TANNED_CUT, 2);
            // TODO tanned leather armor, gimp armor, breeding harness
        });
        GLUE_AMOUNTS.forEach((key, value) -> StokedCauldronRecipe.JsonBuilder.create().ingredient(key).result(BwtItems.glueItem, value).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(key)));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.strapItem, 8).result(BwtItems.glueItem, 1).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(BwtItems.strapItem));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.beltItem, 2).result(BwtItems.glueItem, 1).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(BwtItems.beltItem));

        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.BOOK, 2).result(BwtItems.glueItem, 1).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.BOOK));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.WRITABLE_BOOK, 2).result(BwtItems.glueItem, 1).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.WRITABLE_BOOK));


        // Tallow
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.PORKCHOP).result(BwtItems.tallowItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.tallowItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.PORKCHOP));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.COOKED_PORKCHOP).result(BwtItems.tallowItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.tallowItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.COOKED_PORKCHOP));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.wolfChopItem, 8).result(BwtItems.tallowItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.tallowItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(BwtItems.wolfChopItem));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.cookedWolfChopItem, 8).result(BwtItems.tallowItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.tallowItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(BwtItems.cookedWolfChopItem));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.BEEF, 4).result(BwtItems.tallowItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.tallowItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.BEEF));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.COOKED_BEEF, 4).result(BwtItems.tallowItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.tallowItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.COOKED_BEEF));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.MUTTON, 4).result(BwtItems.tallowItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.tallowItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.MUTTON));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.COOKED_MUTTON, 4).result(BwtItems.tallowItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.tallowItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.COOKED_MUTTON));
        // Potash
        StokedCauldronRecipe.JsonBuilder.create().ingredient(ItemTags.LOGS).result(BwtItems.potashItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.potashItem) + "_from_cauldron_rendering_logs");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(ItemTags.PLANKS, 6).result(BwtItems.potashItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.potashItem) + "_from_cauldron_rendering_planks");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItemTags.WOODEN_SIDING_BLOCKS, 12).result(BwtItems.potashItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.potashItem) + "_from_cauldron_rendering_siding");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItemTags.WOODEN_MOULDING_BLOCKS, 24).result(BwtItems.potashItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.potashItem) + "_from_cauldron_rendering_moulding");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItemTags.WOODEN_CORNER_BLOCKS, 48).result(BwtItems.potashItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.potashItem) + "_from_cauldron_rendering_corners");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.sawDustItem, 16).result(BwtItems.potashItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.potashItem) + "_from_cauldron_rendering_saw_dust");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.soulDustItem, 16).result(BwtItems.potashItem).offerTo(exporter, RecipeProvider.getItemPath(BwtItems.potashItem) + "_from_cauldron_rendering_soul_dust");
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(SturdyTreesTags.Items.BARK_ITEMS, 64).result(BwtItems.potashItem).offerTo(exporter, getItemPath(BwtItems.potashItem) + "_from_cauldron_rendering_bark");

        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.ARROW, 8).result(Items.FLINT, 2).result(Items.STICK).result(Items.FEATHER).offerTo(exporter, "bwt:cauldron_rendering_arrows");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.rottedArrowItem, 8).result(Items.FLINT, 2).offerTo(exporter, "bwt:cauldron_rendering_rotted_arrows");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.potashItem).ingredient(BwtItems.tallowItem).result(BwtItems.soapItem).group("soap").offerTo(exporter);
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.ROTTEN_FLESH, 4).ingredient(Items.BONE_MEAL, 4).ingredient(Items.SUGAR).result(BwtItems.kibbleItem).offerTo(exporter);

        StokedCauldronRecipe.JsonBuilder.create().ingredient(BTWRDS_Items.ENDER_SLAG).result(BTWRDS_Items.SOUL_FLUX).result(BTWRDS_Items.BRIMSTONE).offerTo(exporter);

    }

    private void createNuggetRecipes(RecipeExporter exporter) {
        // prefix should be the name of the recipe you want to replace; in vanilla's case it's the ingot ones

        offerOreCookingRecipe("copper_ingot", Items.RAW_COPPER, Items.COPPER_ORE, Items.DEEPSLATE_COPPER_ORE,
                BTWRDS_Items.COPPER_NUGGET, 10000, 5000, ItemTags.COPPER_ORES, MC, exporter);

        offerOreCookingRecipe("gold_ingot", Items.RAW_GOLD, Items.GOLD_ORE, Items.DEEPSLATE_GOLD_ORE,
                Items.GOLD_NUGGET, 10000, 5000, ItemTags.GOLD_ORES, MC,exporter);

        offerOreCookingRecipe("iron_ingot", Items.RAW_IRON, Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE,
                Items.IRON_NUGGET, 12000, 6000, ItemTags.IRON_ORES, MC, exporter);

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

    /**
     * Creates smelting and blasting recipes for three ore item variants: raw ore, normal ore, and deepslate ore.
     *
     * @param prefix          The recipe identifier prefix. This represents the base recipe string of the recipe
     *                        being modified. For example, if the original recipe is for an iron ingot, but it is
     *                        being replaced with iron nuggets, the prefix would still be for the iron ingot.
     * @param conditionTag    A condition tag used to check if the recipe should be included based on certain criteria.
     * @param namespace       The namespace for the generated recipe JSON files (e.g., "minecraft" or "modid").
     */
    private void offerOreCookingRecipe(String prefix, Item rawItem, Item oreItem, Item deepslateOreItem, Item smeltedItem,
                                        int smeltTime, int blastTime, TagKey<Item> conditionTag, String namespace, RecipeExporter exporter) {
        // Smelting recipes
        oreCookingRecipeBuilder(
                (ingredient, category, experience, time) ->
                        CookingRecipeJsonBuilder.createSmelting(ingredient, category, smeltedItem, experience, time),
                prefix, "smelting", rawItem, oreItem, deepslateOreItem, new float[] {0.35F, 0.45F, 0.55F},
                smeltTime, conditionTag, namespace, exporter);

        // Blasting recipes
        oreCookingRecipeBuilder(
                (ingredient, category, experience, time) ->
                        CookingRecipeJsonBuilder.createBlasting(ingredient, category, smeltedItem, experience, time),
                prefix, "blasting", rawItem, oreItem, deepslateOreItem, new float[] {0.45F, 0.55F, 0.65F},
                blastTime, conditionTag, namespace,  exporter);
    }

    private void oreCookingRecipeBuilder(
            BlockStateVariantMap.QuadFunction<Ingredient, RecipeCategory, Float, Integer, CookingRecipeJsonBuilder> builderFunction,
            String prefix, String type, Item rawItem, Item oreItem, Item deepslateOreItem, float[] experiences,
            int cookTime, TagKey<Item> conditionTag, String namespace,  RecipeExporter exporter)
    {
        Item[] items = {rawItem, oreItem, deepslateOreItem};
        for (int i = 0; i < items.length; i++) {
            builderFunction.apply(Ingredient.ofItems(items[i]), RecipeCategory.MISC, experiences[i], cookTime)
                    .criterion("has_" + extractName(conditionTag), RecipeProvider.conditionsFromTag(conditionTag))
                    .offerTo(exporter, Identifier.of(namespace,prefix + "_from_" + type + "_" + extractName(items[i])));
        }
    }




}
