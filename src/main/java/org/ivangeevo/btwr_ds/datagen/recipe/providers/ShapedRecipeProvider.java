package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.item.BTWR_Items;
import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.tags.BwtItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.ivangeevo.self_sustainable.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;

import static org.tough_environment.block.ModBlocks.SLAB_BRICKS_LOOSE;

public class ShapedRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};


    public ShapedRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {


        // TODO FIX recipe not generating
        /**
         // Adding boat recipes for each SidingBlock in BwtBlocks.sidingBlocks
         for (String woodType : vanillaWoodTypes) {
         Identifier resultId = ID.ofMC(woodType + "_boat");
         ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, Registries.BLOCK.get(resultId))
         .input('S', grabRaw(ID.ofBWT(woodType + "_planks_siding")))  // Use the current SidingBlock as the 'S' input
         .pattern("S S")
         .pattern("SSS")
         .criterion("has_wooden_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
         .offerTo(exporter, resultId);
         }
         **/

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

        /**
         // Modern (HC) Millstone
         ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, org.ivangeevo.bwt_hct.block.ModBlocks.modernMillStoneBlock)
         .input('B', org.tough_environment.item.ModItems.STONE_BRICK)
         .input('G', BwtItems.gearItem)
         .pattern("BBB")
         .pattern("BBB")
         .pattern("BGB")
         .criterion("has_gear", conditionsFromItem(org.ivangeevo.bwt_hct.block.ModBlocks.modernMillStoneBlock))
         .offerTo(exporter, Identifier.of("bwt_hct", "modern_mill_stone"));
         **/

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHEST)
                .input('W', BwtBlocks.wickerBlock)
                .pattern("WWW")
                .pattern("W W")
                .pattern("WWW")
                .criterion("has_wicker_block", RecipeProvider.conditionsFromItem(BwtBlocks.wickerBlock))
                .offerTo(exporter, ID.ofMC("chest"));

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.SOUL_LANTERN)
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
        for (String woodType : vanillaWoodTypes) {
            Identifier resultId = ID.ofMC(woodType + "_door");
            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.BLOCK.get(resultId))
                    .input('P', grabRaw(woodType + "_planks"))
                    .pattern("PP")
                    .pattern("PP")
                    .pattern("PP")
                    .criterion("has_planks", conditionsFromItem(Registries.ITEM.get(ID.ofMC(woodType + "_planks"))))
                    .offerTo(exporter, resultId);
        }

        // Adding trapdoor recipes
        for (String woodType : vanillaWoodTypes) {
            Identifier resultId = ID.ofMC(woodType + "_trapdoor");
            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(resultId))
                    .input('P', grabRaw(woodType + "_planks"))
                    .input('S', Items.STICK)
                    .pattern("SPP")
                    .pattern("SPP")
                    .criterion("has_planks", conditionsFromItem(Registries.ITEM.get(ID.ofMC(woodType + "_planks"))))
                    .offerTo(exporter, resultId);
        }

        // Adding pressure plate recipes for each SidingBlock in BwtBlocks.sidingBlocks
        for (String woodType : vanillaWoodTypes) {
            Identifier resultId = ID.ofMC(woodType + "_pressure_plate");
            Block sidingBlock = Registries.BLOCK.get(ID.ofBWT(woodType + "_planks_siding"));

            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.BLOCK.get(resultId))
                    .input('S', sidingBlock)  // Use the current SidingBlock as the 'S' input
                    .input('R', Items.REDSTONE) // Redstone for the 'R' input
                    .pattern("S")
                    .pattern("R")
                    .criterion("has_planks_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                    .offerTo(exporter, resultId);
        }

        // Adding button recipes for each CornerBlock in BwtBlocks.cornerBlock
        for (String woodType : vanillaWoodTypes) {
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
                .input('B', ModItems.STONE_BRICK)
                .input('R', Items.REDSTONE)
                .pattern("S")
                .pattern("B")
                .pattern("R")
                .criterion("has_has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, ID.ofMC("lever"));

        // TODO : Consider changing the redstone latch in this recipe if this item gets added to BWT
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.PISTON)
                .input('W', ItemTags.PLANKS)
                .input('B', ModItems.STONE_BRICK)
                .input('I', Items.IRON_INGOT)
                .input('U', BwtItems.soulUrnItem)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .pattern("WIW")
                .pattern("BUB")
                .pattern("BLB")
                .criterion("has_redstone_latch", conditionsFromItem(BTWRDS_Items.REDSTONE_LATCH))
                .offerTo(exporter, ID.ofMC("piston"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.DISPENSER)
                .input('S', ModItems.STONE_BRICK)
                .input('B', Items.BOW)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .pattern("SSS")
                .pattern("SBS")
                .pattern("SLS")
                .criterion(hasItem(Items.DISPENSER), conditionsFromItem(Items.DISPENSER))
                .offerTo(exporter, ID.ofMC("dispenser"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.REPEATER)
                .input('C', Items.CLOCK)
                .input('R', Items.REDSTONE_TORCH)
                .input('B', ModItems.STONE_BRICK)
                .pattern("RCR")
                .pattern("BBB")
                .criterion("has_redstone_torch", conditionsFromItem(Items.REDSTONE_TORCH))
                .offerTo(exporter, ID.ofMC("repeater"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LEAD, 2)
                .input('F', BwtItems.hempFiberItem)
                .input('R', BwtItems.ropeItem)
                .pattern(" FF")
                .pattern(" FF")
                .pattern("R  ")
                .criterion("has_rope", conditionsFromItem(BwtItems.ropeItem))
                .offerTo(exporter, ID.ofMC("lead"));


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

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.SHIELD)
                .input('I', Items.STICK)
                .input('N', Items.IRON_NUGGET)
                .pattern("NIN")
                .pattern("III")
                .pattern("NIN")
                .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, ID.ofMC("shield"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.SOUL_TORCH, 1)
                .input('C', BwtItems.nethercoalItem)
                .input('I', Items.STICK)
                .input('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .pattern("C")
                .pattern("I")
                .pattern("S")
                .criterion("has_nethercoal", conditionsFromItem(BwtItems.nethercoalItem))
                .offerTo(exporter, ID.ofMC("soul_torch"));

        this.overrideRecipesForVanillaBeds(exporter);


        // Self Sustainable
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.OVEN_BRICK)
                .input('S', SLAB_BRICKS_LOOSE)
                .pattern("SS")
                .pattern("SS")
                .criterion("has_slab_bricks_loose", conditionsFromItem(SLAB_BRICKS_LOOSE))
                .offerTo(exporter, ID.ofSS("oven_brick"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, net.ivangeevo.self_sustainable.item.ModItems.CRUDE_TORCH_UNLIT, 1)
                .input('C', ItemTags.COALS)
                .input('I', Items.STICK)
                .pattern("C")
                .pattern("I")
                .criterion("has_coal", conditionsFromTag(ItemTags.COALS))
                .offerTo(exporter, ID.ofSS("crude_torch_unlit"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, net.ivangeevo.self_sustainable.item.ModItems.TORCH_UNLIT, 1)
                .input('C', BwtItems.nethercoalItem)
                .input('I', Items.STICK)
                .pattern("C")
                .pattern("I")
                .criterion("has_nethercoal", conditionsFromItem(BwtItems.nethercoalItem))
                .offerTo(exporter, ID.ofSS("torch_unlit"));


        // Better With Time

        // Create the recipe for the blood wood pressure plate
        Block bloodWoodSiding = Registries.BLOCK.get(ID.ofBWT("blood_wood_planks_siding"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.bloodWoodBlocks.pressurePlateBlock)
                .input('S', bloodWoodSiding)
                .input('R', Items.REDSTONE)
                .pattern("S")
                .pattern("R")
                .criterion("has_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, ID.ofBWT("blood_wood_pressure_plate"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.grateBlock)
                .input('S', Items.STICK)
                .input('F', ConventionalItemTags.STRINGS)
                .pattern("FSF")
                .pattern("SSS")
                .pattern("FSF")
                .criterion("has_strings", conditionsFromTag(ConventionalItemTags.STRINGS))
                .offerTo(exporter, ID.ofBWT("grate"));


        // BTWR: Core
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_RAW)
                .input('E', BwtItems.rawEggItem)
                .input('M', Items.BROWN_MUSHROOM)
                .pattern("EM")
                .pattern("MM")
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofBTWR("mushroom_omelette_raw"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.DIAMOND_PLATE)
                .input('S', BwtItems.strapItem)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BwtItems.paddingItem)
                .pattern("SIS")
                .pattern(" P ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofBTWR("diamond_plate"));

        // Vegehenna
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, org.ivangeevo.vegehenna.item.ModItems.PASTRY_UNCOOKED_CAKE)
                .input('E', BwtItems.rawEggItem)
                .input('M', Items.MILK_BUCKET)
                .input('F', BwtItems.flourItem)
                .input('S', Items.SUGAR)
                .pattern("SSS")
                .pattern("MEM")
                .pattern("FFF")
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_cake"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, org.ivangeevo.vegehenna.item.ModItems.BREAD_DOUGH)
                .input('F', BwtItems.flourItem)
                .pattern("F ")
                .pattern("FF")
                .criterion("flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, ID.ofVG("bread_dough"));

        // DS
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK,2)
                .input('P', ItemTags.PLANKS)
                .pattern("P")
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, ID.ofDS("stick_from_single_planks"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWRDS_Items.REDSTONE_LATCH)
                .input('G', Items.GOLD_NUGGET)
                .input('R', Items.REDSTONE)
                .pattern("GGG")
                .pattern(" R ")
                .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, ID.ofDS("redstone_latch"));

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

    private void overrideRecipesForVanillaBeds(RecipeExporter exporter) {
        offerBedRecipe(exporter, Items.WHITE_BED, Items.WHITE_WOOL);
        offerBedRecipe(exporter, Items.ORANGE_BED, Items.ORANGE_WOOL);
        offerBedRecipe(exporter, Items.MAGENTA_BED, Items.MAGENTA_WOOL);
        offerBedRecipe(exporter, Items.LIGHT_BLUE_BED, Items.LIGHT_BLUE_WOOL);
        offerBedRecipe(exporter, Items.YELLOW_BED, Items.YELLOW_WOOL);
        offerBedRecipe(exporter, Items.LIME_BED, Items.LIME_WOOL);
        offerBedRecipe(exporter, Items.PINK_BED, Items.PINK_WOOL);
        offerBedRecipe(exporter, Items.GRAY_BED, Items.GRAY_WOOL);
        offerBedRecipe(exporter, Items.LIGHT_GRAY_BED, Items.LIGHT_GRAY_WOOL);
        offerBedRecipe(exporter, Items.CYAN_BED, Items.CYAN_WOOL);
        offerBedRecipe(exporter, Items.PURPLE_BED, Items.PURPLE_WOOL);
        offerBedRecipe(exporter, Items.BLUE_BED, Items.BLUE_WOOL);
        offerBedRecipe(exporter, Items.BROWN_BED, Items.BROWN_WOOL);
        offerBedRecipe(exporter, Items.GREEN_BED, Items.GREEN_WOOL);
        offerBedRecipe(exporter, Items.RED_BED, Items.RED_WOOL);
        offerBedRecipe(exporter, Items.BLACK_BED, Items.BLACK_WOOL);
    }

    public static void offerBedRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible woolInput) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output)
                .input('W', woolInput)
                .input('#', BwtItems.paddingItem)
                .input('X', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .pattern(" W ")
                .pattern("###")
                .pattern("XXX")
                .group("bed")
                .criterion(RecipeProvider.hasItem(woolInput), RecipeProvider.conditionsFromItem(woolInput))
                .offerTo(exporter);
    }
}
