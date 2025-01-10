package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.core.item.BTWR_Items;
import btwr.core.tag.BTWRTags;
import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import com.bwt.recipes.cooking_pots.StokedCauldronRecipe;
import com.bwt.recipes.cooking_pots.StokedCrucibleRecipe;
import com.bwt.recipes.kiln.KilnRecipe;
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
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;

import java.util.Map;
import java.util.concurrent.CompletableFuture;



public class BWT_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public BWT_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};


    @Override
    public void generate(RecipeExporter exporter) {
        // TODO : reorganise class better
        // Better With Time
        this.overrideForBWT(exporter);
        this.overrideHighEfficiency(exporter);
    }

    private void overrideHighEfficiency(RecipeExporter exporter) {
        for (String woodType : vanillaWoodTypes)
        {
            Identifier doorID = ID.ofMC(woodType + "_door");
            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(doorID))
                    .input('P', grabRaw("bwt", woodType + "_planks_siding"))
                    .pattern("PP")
                    .pattern("PP")
                    .pattern("PP")
                    .criterion("has_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                    .offerTo(exporter, ID.ofBWT("he_" + woodType + "_door"));
        }

        Identifier bloodWoodDoorID = ID.ofBWT("blood_wood_door");
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(bloodWoodDoorID))
                .input('P', grabRaw("bwt","blood_wood_planks_siding"))
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .criterion("has_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, bloodWoodDoorID.withPrefixedPath("he_"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtItems.sailItem)
                .input('F', BwtItems.fabricItem)
                .input('W', BwtItemTags.WOODEN_MOULDING_BLOCKS)
                .pattern("FFF")
                .pattern("WWW")
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, ID.ofBWT("he_sail"));

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.PISTON)
                .input('W', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('B', org.tough_environment.item.ModItems.STONE_BRICK)
                .input('I', Items.IRON_INGOT)
                .input('U', BwtItems.soulUrnItem)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .pattern("WIW")
                .pattern("BUB")
                .pattern("BLB")
                .criterion("has_redstone_latch", conditionsFromItem(BTWRDS_Items.REDSTONE_LATCH))
                .offerTo(exporter, ID.ofBWT("he_piston"));

    }

    private void overrideForBWT(RecipeExporter exporter) {

        // Create the recipe for the blood wood button
        Block bloodWoodCorner = Registries.BLOCK.get(ID.ofBWT("blood_wood_planks_corner"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.bloodWoodBlocks.buttonBlock)
                .input('S', bloodWoodCorner)  // Use the blood wood SidingBlock as the 'S' input
                .input('R', Items.REDSTONE) // Redstone for the 'R' input
                .pattern("S")
                .pattern("R")
                .criterion("has_blood_wood_corner", conditionsFromItem(bloodWoodCorner))
                .offerTo(exporter, ID.ofBWT("blood_wood_button"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.bloodWoodBlocks.doorBlock)
                .input('P', BwtBlocks.bloodWoodBlocks.planksBlock)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .criterion("has_blood_wood_planks", conditionsFromItem(BwtBlocks.bloodWoodBlocks.planksBlock))
                .offerTo(exporter, ID.ofBWT("blood_wood_door"));

        // Items
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.sailItem)
                .input('F', BwtItems.fabricItem)
                .input('W', ItemTags.PLANKS)
                .pattern("FFF")
                .pattern("WWW")
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, ID.ofBWT("sail"));


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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.soilPlanterBlock)
                .input('D', ModBlocks.DIRT_LOOSE)
                .input('P', BwtBlocks.planterBlock)
                .pattern("D")
                .pattern("P")
                .criterion("has_planter", conditionsFromItem(BwtBlocks.planterBlock))
                .offerTo(exporter, ID.ofBWT("soil_planter"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.grassPlanterBlock)
                .input('D', Blocks.GRASS_BLOCK)
                .input('P', BwtBlocks.planterBlock)
                .pattern("D")
                .pattern("P")
                .criterion("has_planter", conditionsFromItem(BwtBlocks.planterBlock))
                .offerTo(exporter, ID.ofBWT("grass_planter"));


        /**
        // Adding trapdoor recipes
        for (String woodType : vanillaWoodTypes) {
            Identifier resultId = ID.ofBWT("he_" + woodType + "_trapdoor");
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Registries.ITEM.get(resultId))
                    .input('P', grabRaw("bwt",woodType + "_planks_siding"))
                    .input('S', Items.STICK)
                    .pattern("SPP")
                    .pattern("SPP")
                    .criterion("has_planks_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                    .offerTo(exporter, resultId);asd

        }
         **/

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.handCrankBlock)
                .input('B', ModItems.STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .input('S', Items.STICK)
                .pattern("  S")
                .pattern(" S ")
                .pattern("BGB")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, ID.ofBWT("hand_crank"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.hibachiBlock)
                .input('H', BwtItems.concentratedHellfireItem)
                .input('E', BTWRDS_Items.ELEMENT)
                .input('B', ModItems.STONE_BRICK)
                .input('R', Items.REDSTONE)
                .pattern("HHH")
                .pattern("BEB")
                .pattern("BRB")
                .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, ID.ofBWT("hibachi"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.bellowsBlock)
                .input('L', BTWRTags.Items.TANNED_LEATHERS)
                .input('B', BwtItems.beltItem)
                .input('S', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('G', BwtItems.gearItem)
                .pattern("SSS")
                .pattern("LLL")
                .pattern("GBG")
                .criterion("has_wooden_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, ID.ofBWT("bellows"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.anchorBlock)
                .input('I', Items.IRON_NUGGET)
                .input('B', ModItems.STONE_BRICK)
                .pattern("   ")
                .pattern(" I ")
                .pattern("BBB")
                .criterion("has_stone_brick", conditionsFromItem(ModItems.STONE_BRICK))
                .offerTo(exporter, ID.ofBWT("anchor"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.turntableBlock)
                .input('C', Items.CLOCK)
                .input('S', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('B', ModItems.STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .pattern("SSS")
                .pattern("BCB")
                .pattern("BGB")
                .criterion("has_wooden_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, ID.ofBWT("turntable"));

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.detectorBlock)
                .input('B', ModItems.STONE_BRICK)
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
                .input('B', ModItems.STONE_BRICK)
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
                .input('B', ModItems.STONE_BRICK)
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

        CauldronRecipe.JsonBuilder.createFood().result(BwtItems.nethercoalItem)
                .ingredient(BwtItems.coalDustItem)
                .ingredient(BwtItems.hellfireDustItem)
                .criterion("has_hellfire_dust", conditionsFromItem(BwtItems.hellfireDustItem))
                .offerTo(exporter, ID.ofBWT("nethercoal_from_cauldron"));


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

    // TODO: add an identifier to the offerTo calls to recipes that only pass "exporter", so they get registered in the proper namespace
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
        Map<Item, Integer> SINGLE_COUNT_TO_GLUE_AMOUNTS = Util.make(Maps.newHashMap(), map -> {

            // Regular leathers
            map.put(Items.LEATHER_HELMET, 2);
            map.put(Items.LEATHER_CHESTPLATE, 4);
            map.put(Items.LEATHER_LEGGINGS, 3);
            map.put(Items.LEATHER_BOOTS, 2);
            map.put(Items.SADDLE, 2);

            // Leathers
            map.put(Items.LEATHER, 1);
            map.put(BwtItems.scouredLeatherItem, 1);
            map.put(BwtItems.tannedLeatherItem, 1);
            // TODO tanned leather armor, gimp armor, breeding harness
        });
        SINGLE_COUNT_TO_GLUE_AMOUNTS.forEach((key, value) -> StokedCauldronRecipe.JsonBuilder.create().ingredient(key).result(BwtItems.glueItem, value).offerTo(exporter, ID.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(key))));

        Map<Item, Integer> DOUBLE_COUNT_TO_GLUE_AMOUNTS = Util.make(Maps.newHashMap(), map -> {

            // Cut leathers
            map.put(BTWR_Items.LEATHER_CUT, 1);
            map.put(BTWR_Items.LEATHER_SCOURED_CUT, 1);
            map.put(BTWR_Items.LEATHER_TANNED_CUT, 1);
        });
        DOUBLE_COUNT_TO_GLUE_AMOUNTS.forEach((key, value) -> StokedCauldronRecipe.JsonBuilder.create().ingredient(key,2).result(BwtItems.glueItem, value).offerTo(exporter, ID.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(key))));


        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.strapItem, 8).result(BwtItems.glueItem, 1).offerTo(exporter, ID.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(BwtItems.strapItem)));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.beltItem, 2).result(BwtItems.glueItem, 1).offerTo(exporter, ID.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(BwtItems.beltItem)));

        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.BOOK, 2).result(BwtItems.glueItem, 1).offerTo(exporter, ID.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.BOOK)));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.WRITABLE_BOOK, 2).result(BwtItems.glueItem, 1).offerTo(exporter, ID.ofBWT(RecipeProvider.getItemPath(BwtItems.glueItem) + "_from_cauldron_rendering_" + RecipeProvider.getItemPath(Items.WRITABLE_BOOK)));


        // Tallow - unmodified

        // Potash
        StokedCauldronRecipe.JsonBuilder.create().ingredient(SturdyTreesTags.Items.BARK_ITEMS, 64).result(BwtItems.potashItem).offerTo(exporter, ID.ofBWT("potash") + "_from_cauldron_rendering_bark");
        StokedCauldronRecipe.JsonBuilder.create().ingredient(org.ivangeevo.vegehenna.item.ModItems.STRAW, 16).result(BwtItems.potashItem).offerTo(exporter, ID.ofBWT("potash") + "_from_cauldron_rendering_straw");

        // Arrows
        StokedCauldronRecipe.JsonBuilder.create().ingredient(Items.ARROW).result(Items.FLINT).result(Items.STICK).result(Items.FEATHER).offerTo(exporter, ID.ofBWT("cauldron_rendering_arrows"));
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BwtItems.rottedArrowItem).result(Items.FLINT).offerTo(exporter, ID.ofBWT("cauldron_rendering_rotted_arrows"));

        // Misc
        StokedCauldronRecipe.JsonBuilder.create().ingredient(BTWRDS_Items.ENDER_SLAG).result(BTWRDS_Items.SOUL_FLUX).result(BTWRDS_Items.BRIMSTONE).offerTo(exporter, ID.ofBWT("cauldron_rendering_ender_slag"));


        StokedCrucibleRecipe.JsonBuilder.create()
                .ingredient(Items.IRON_INGOT)
                .ingredient(Items.GOLD_INGOT)
                .ingredient(BwtItems.coalDustItem)
                .ingredient(BwtItems.soulUrnItem)
                .ingredient(BTWRDS_Items.SOUL_FLUX)
                .result(Items.NETHERITE_INGOT)
                .markDefault()
                .offerTo(exporter, ID.ofBWT("netherite_ingot_smelting"));

        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_SCRAP, 4)
                .ingredient(Items.GOLD_INGOT, 4)
                .ingredient(BTWRDS_Items.SOUL_FLUX)
                .result(Items.NETHERITE_INGOT)
                .offerTo(exporter, ID.ofBWT("netherite_ingot_from_scrap"));
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

    private void sawLogBuilder(RecipeExporter exporter, Block logBlock, Block planksBlock, Item barkItem) {
        SawRecipe.JsonBuilder.create(logBlock)
                .result(planksBlock,4)
                .result(barkItem)
                .result(BwtItems.sawDustItem, 2)
                .criterion("has_log", conditionsFromTag(ItemTags.LOGS))
                .offerTo(exporter, ID.ofBWT("saw_" + extractName(logBlock)));
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

}
