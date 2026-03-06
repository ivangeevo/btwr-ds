package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.CauldronRecipe;
import com.bwt.recipes.soul_forge.SoulForgeShapedRecipe;
import com.bwt.tags.BwtItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import org.btwr.self_sustainable.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
import org.btwr.core.item.BTWR_Items;
import org.btwr.core.tag.BTWRTags;
import org.btwr.self_sustainable.item.ModItems;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.shared_library.util.utils.RecipeUtils;
import org.btwr.data_suite.item.BTWRDS_Items;

import java.util.concurrent.CompletableFuture;

import static org.btwr.tough_environment.block.ModBlocks.DIRT_LOOSE;
import static org.btwr.tough_environment.block.ModBlocks.SLAB_BRICKS_LOOSE;
import static org.btwr.tough_environment.item.ModItems.STONE_BRICK;
import static org.btwr.vegehenna.item.ModItems.*;

public class ShapedRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};


    public ShapedRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // Add recipe override for barrel

        /**
        // TODO FIX recipe not generating
         // Adding boat recipes for each SidingBlock in BwtBlocks.sidingBlocks
         for (String woodType : vanillaWoodTypes) {
             Identifier resultId = IdUtils.ofMC(woodType + "_boat");
             ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, Registries.ITEM.get(resultId))
                     .input('S', grabRaw("bwt",woodType + "_planks_siding"))
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
         .offerTo(exporter, IdUtils.ofMC("dispenser"));
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

        // High efficiency
        for (String woodType : vanillaWoodTypes) {
            Identifier doorID = IdUtils.ofMC(woodType + "_door");
            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(doorID))
                    .input('P', IdUtils.grabRaw("bwt", woodType + "_planks_siding"))
                    .pattern("PP")
                    .pattern("PP")
                    .pattern("PP")
                    .criterion("has_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                    .offerTo(exporter, IdUtils.ofBWT("he_" + woodType + "_door"));
        }

        Identifier bloodWoodDoorID = IdUtils.ofBWT("blood_wood_door");
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(bloodWoodDoorID))
                .input('P', IdUtils.grabRaw("bwt","blood_wood_planks_siding"))
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
                .offerTo(exporter, IdUtils.ofBWT("he_sail"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.gearBoxBlock)
                .input('W', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('A', BwtBlocks.axleBlock)
                .input('G', BwtItems.gearItem)
                .pattern("WGW")
                .pattern("GAG")
                .pattern("WGW")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, IdUtils.ofBWT("he_gear_box"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LADDER,2)
                .input('P', BwtItemTags.WOODEN_MOULDING_BLOCKS)
                .input('S', ConventionalItemTags.STRINGS)
                .pattern("PSP")
                .pattern("PPP")
                .pattern("PSP")
                .criterion("has_wooden_moulding", RecipeProvider.conditionsFromTag(BwtItemTags.WOODEN_MOULDING_BLOCKS))
                .offerTo(exporter, IdUtils.ofBWT("he_ladder"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.PISTON)
                .input('W', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('B', STONE_BRICK)
                .input('I', Items.IRON_INGOT)
                .input('U', BwtItems.soulUrnItem)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .pattern("WIW")
                .pattern("BUB")
                .pattern("BLB")
                .criterion("has_redstone_latch", conditionsFromItem(BTWRDS_Items.REDSTONE_LATCH))
                .offerTo(exporter, IdUtils.ofBWT("he_piston"));

        // Create the recipe for the blood wood button
        Block bloodWoodCorner = Registries.BLOCK.get(IdUtils.ofBWT("blood_wood_planks_corner"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.bloodWoodBlocks.buttonBlock)
                .input('S', bloodWoodCorner)  // Use the blood wood SidingBlock as the 'S' input
                .input('R', Items.REDSTONE) // Redstone for the 'R' input
                .pattern("S")
                .pattern("R")
                .criterion("has_blood_wood_corner", conditionsFromItem(bloodWoodCorner))
                .offerTo(exporter, IdUtils.ofBWT("blood_wood_button"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.bloodWoodBlocks.doorBlock)
                .input('P', BwtBlocks.bloodWoodBlocks.planksBlock)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .criterion("has_blood_wood_planks", conditionsFromItem(BwtBlocks.bloodWoodBlocks.planksBlock))
                .offerTo(exporter, IdUtils.ofBWT("blood_wood_door"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CLAY)
                .input('C', Items.CLAY_BALL)
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .criterion("has_clay_ball", RecipeProvider.conditionsFromItem(Items.CLAY_BALL))
                .offerTo(exporter, IdUtils.ofMC("clay"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHEST)
                .input('W', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .pattern("WWW")
                .pattern("W W")
                .pattern("WWW")
                .criterion("has_wooden_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, IdUtils.ofMC("chest"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHAIN,4)
                .input('N', Items.IRON_NUGGET)
                .input('I', Items.IRON_INGOT)
                .pattern(" N ")
                .pattern(" I ")
                .pattern(" N ")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, IdUtils.ofMC("chain"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LANTERN)
                .input('N', Items.IRON_NUGGET)
                .input('T', Items.TORCH)
                .pattern(" N ")
                .pattern("NTN")
                .pattern(" N ")
                .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, IdUtils.ofMC("lantern"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.SOUL_LANTERN)
                .input('N', Items.IRON_NUGGET)
                .input('T', Items.SOUL_TORCH)
                .pattern(" N ")
                .pattern("NTN")
                .pattern(" N ")
                .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, IdUtils.ofMC("soul_lantern"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.BONE_BLOCK)
                .input('S', Items.BONE)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .criterion("has_bone", conditionsFromItem(Items.BONE))
                .offerTo(exporter, IdUtils.ofMC("bone_block"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LADDER, 2)
                .input('S', Items.STICK)
                .input('F', ConventionalItemTags.STRINGS)
                .pattern("SFS")
                .pattern("SSS")
                .pattern("SFS")
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, IdUtils.ofMC( "ladder"));

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
            Identifier resultId = IdUtils.ofMC(woodType + "_door");
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Registries.BLOCK.get(resultId))
                    .input('P', IdUtils.grabRaw(woodType + "_planks"))
                    .pattern("PP")
                    .pattern("PP")
                    .pattern("PP")
                    .criterion("has_planks", conditionsFromItem(Registries.ITEM.get(IdUtils.ofMC(woodType + "_planks"))))
                    .offerTo(exporter, resultId);
        }

        // Adding trapdoor recipes
        for (String woodType : vanillaWoodTypes) {
            Identifier resultId = IdUtils.ofMC(woodType + "_trapdoor");
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Registries.ITEM.get(resultId))
                    .input('P', IdUtils.grabRaw(woodType + "_planks"))
                    .input('S', Items.STICK)
                    .pattern("SPP")
                    .pattern("SPP")
                    .criterion("has_planks", conditionsFromItem(Registries.ITEM.get(IdUtils.ofMC(woodType + "_planks"))))
                    .offerTo(exporter, resultId);
        }

        // Adding High Efficiency trapdoor recipes
        for (String woodType : vanillaWoodTypes) {
            String name = woodType + "_trapdoor";
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Registries.ITEM.get(IdUtils.ofMC(name)), 2)
                    .input('P', IdUtils.grabRaw("bwt",woodType + "_planks_siding" ))
                    .input('S', Items.STICK)
                    .pattern("SPP")
                    .pattern("SPP")
                    .criterion("has_planks_siding", conditionsFromItem(Registries.ITEM.get(IdUtils.ofBWT(woodType + "_planks_siding"))))
                    .offerTo(exporter, IdUtils.ofBWT(name).withPrefixedPath("he_"));
        }

        // Adding pressure plate recipes for each SidingBlock in BwtBlocks.sidingBlocks
        for (String woodType : vanillaWoodTypes) {
            Identifier resultId = IdUtils.ofMC(woodType + "_pressure_plate");
            Block sidingBlock = Registries.BLOCK.get(IdUtils.ofBWT(woodType + "_planks_siding"));

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
            Identifier resultId = IdUtils.ofMC(woodType + "_button");
            Block block = Registries.BLOCK.get(IdUtils.ofBWT(woodType + "_planks_corner"));

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
                .input('B', STONE_BRICK)
                .input('R', Items.REDSTONE)
                .pattern("S")
                .pattern("B")
                .pattern("R")
                .criterion("has_has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, IdUtils.ofMC("lever"));

        // TODO : Consider changing the redstone latch in this recipe if this item gets added to BWT
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.PISTON)
                .input('W', ItemTags.PLANKS)
                .input('B', STONE_BRICK)
                .input('I', Items.IRON_INGOT)
                .input('U', BwtItems.soulUrnItem)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .pattern("WIW")
                .pattern("BUB")
                .pattern("BLB")
                .criterion("has_redstone_latch", conditionsFromItem(BTWRDS_Items.REDSTONE_LATCH))
                .offerTo(exporter, IdUtils.ofMC("piston"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.DISPENSER)
                .input('S', STONE_BRICK)
                .input('B', Items.BOW)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .pattern("SSS")
                .pattern("SBS")
                .pattern("SLS")
                .criterion(hasItem(Items.DISPENSER), conditionsFromItem(Items.DISPENSER))
                .offerTo(exporter, IdUtils.ofMC("dispenser"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.DROPPER)
                .input('S', STONE_BRICK)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .pattern("SSS")
                .pattern("S S")
                .pattern("SLS")
                .criterion(hasItem(Items.DROPPER), conditionsFromItem(Items.DROPPER))
                .offerTo(exporter, IdUtils.ofMC("dropper"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.REPEATER)
                .input('C', Items.CLOCK)
                .input('R', Items.REDSTONE_TORCH)
                .input('B', STONE_BRICK)
                .pattern("RCR")
                .pattern("BBB")
                .criterion("has_redstone_torch", conditionsFromItem(Items.REDSTONE_TORCH))
                .offerTo(exporter, IdUtils.ofMC("repeater"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LEAD, 2)
                .input('F', BwtItems.hempFiberItem)
                .input('R', BwtItems.ropeItem)
                .pattern(" FF")
                .pattern(" FF")
                .pattern("R  ")
                .criterion("has_rope", conditionsFromItem(BwtItems.ropeItem))
                .offerTo(exporter, IdUtils.ofMC("lead"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                .input('R', Items.STICK)
                .input('S', ItemTags.STONE_TOOL_MATERIALS)
                .input('#', ConventionalItemTags.STRINGS)
                .pattern("SSS")
                .pattern("#R ")
                .pattern(" R ")
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, IdUtils.ofMC("stone_pickaxe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.IRON_AXE)
                .input('M', Items.IRON_INGOT)
                .input('I', Items.STICK)
                .pattern("M ")
                .pattern("MI")
                .pattern(" I")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, IdUtils.ofMC("iron_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.IRON_HOE)
                .input('M', Items.IRON_INGOT)
                .input('I', Items.STICK)
                .pattern("MI")
                .pattern(" I")
                .pattern(" I")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, IdUtils.ofMC("iron_hoe"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_AXE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern("M ")
                .pattern("MI")
                .pattern(" I")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern("MMM")
                .pattern(" I ")
                .pattern(" I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_pickaxe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_HOE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern("MI")
                .pattern(" I")
                .pattern(" I")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_hoe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern(" M ")
                .pattern(" I ")
                .pattern(" I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_shovel"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_SWORD)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern(" M ")
                .pattern(" M ")
                .pattern(" I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_sword"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_HELMET)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BTWR_Items.DIAMOND_PLATE)
                .pattern("III")
                .pattern("IPI")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_helmet"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BTWR_Items.DIAMOND_PLATE)
                .pattern("P P")
                .pattern("III")
                .pattern("III")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_chestplate"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BTWR_Items.DIAMOND_PLATE)
                .pattern("III")
                .pattern("P P")
                .pattern("P P")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_leggings"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .pattern("I I")
                .pattern("I I")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofMC("diamond_boots"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.SHIELD)
                .input('I', Items.STICK)
                .input('N', Items.IRON_NUGGET)
                .pattern("NIN")
                .pattern("III")
                .pattern("NIN")
                .criterion("has_iron_nugget", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, IdUtils.ofMC("shield"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.SOUL_TORCH, 1)
                .input('C', BwtItems.nethercoalItem)
                .input('I', Items.STICK)
                .input('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .pattern("C")
                .pattern("I")
                .pattern("S")
                .criterion("has_nethercoal", conditionsFromItem(BwtItems.nethercoalItem))
                .offerTo(exporter, IdUtils.ofMC("soul_torch"));

        this.overrideRecipesForVanillaBeds(exporter);

        // Self Sustainable
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.OVEN_BRICK)
                .input('S', SLAB_BRICKS_LOOSE)
                .pattern("SS")
                .pattern("SS")
                .criterion("has_slab_bricks_loose", conditionsFromItem(SLAB_BRICKS_LOOSE))
                .offerTo(exporter, IdUtils.ofSS("oven_brick"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRUDE_TORCH_UNLIT, 1)
                .input('C', ItemTags.COALS)
                .input('I', Items.STICK)
                .pattern("C")
                .pattern("I")
                .criterion("has_coal", conditionsFromTag(ItemTags.COALS))
                .offerTo(exporter, IdUtils.ofSS("crude_torch_unlit"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TORCH_UNLIT, 1)
                .input('C', BwtItems.nethercoalItem)
                .input('I', Items.STICK)
                .pattern("C")
                .pattern("I")
                .criterion("has_nethercoal", conditionsFromItem(BwtItems.nethercoalItem))
                .offerTo(exporter, IdUtils.ofSS("torch_unlit"));

        // Better With Time

        // Create the recipe for the blood wood pressure plate
        Block bloodWoodSiding = Registries.BLOCK.get(IdUtils.ofBWT("blood_wood_planks_siding"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.bloodWoodBlocks.pressurePlateBlock)
                .input('S', bloodWoodSiding)
                .input('R', Items.REDSTONE)
                .pattern("S")
                .pattern("R")
                .criterion("has_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, IdUtils.ofBWT("blood_wood_pressure_plate"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.grateBlock)
                .input('S', Items.STICK)
                .input('F', ConventionalItemTags.STRINGS)
                .pattern("FSF")
                .pattern("SSS")
                .pattern("FSF")
                .criterion("has_strings", conditionsFromTag(ConventionalItemTags.STRINGS))
                .offerTo(exporter, IdUtils.ofBWT("grate"));

        // Items
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.sailItem)
                .input('F', BwtItems.fabricItem)
                .input('W', ItemTags.PLANKS)
                .pattern("FFF")
                .pattern("WWW")
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, IdUtils.ofBWT("sail"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.paddingItem)
                .input('F', BwtItems.fabricItem)
                .input('W', ItemTags.WOOL)
                .input('C', Items.FEATHER)
                .pattern(" F ")
                .pattern("CWC")
                .pattern(" F ")
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, IdUtils.ofBWT("padding"));

        // Blocks
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.gearBoxBlock)
                .input('W', ItemTags.PLANKS)
                .input('A', BwtBlocks.axleBlock)
                .input('G', BwtItems.gearItem)
                .pattern("WGW")
                .pattern("GAG")
                .pattern("WGW")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, IdUtils.ofBWT("gear_box"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.redstoneClutchBlock)
                .input('W', ItemTags.PLANKS)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .input('G', BwtItems.gearItem)
                .pattern("WGW")
                .pattern("GLG")
                .pattern("WGW")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, IdUtils.ofBWT("redstone_clutch"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.soilPlanterBlock)
                .input('D', DIRT_LOOSE)
                .input('P', BwtBlocks.planterBlock)
                .pattern("D")
                .pattern("P")
                .criterion("has_planter", conditionsFromItem(BwtBlocks.planterBlock))
                .offerTo(exporter, IdUtils.ofBWT("soil_planter"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.grassPlanterBlock)
                .input('D', Blocks.GRASS_BLOCK)
                .input('P', BwtBlocks.planterBlock)
                .pattern("D")
                .pattern("P")
                .criterion("has_planter", conditionsFromItem(BwtBlocks.planterBlock))
                .offerTo(exporter, IdUtils.ofBWT("grass_planter"));


        /**
         // Adding trapdoor recipes
         for (String woodType : vanillaWoodTypes) {
         Identifier resultId = IdUtils.ofBWT("he_" + woodType + "_trapdoor");
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
                .input('B', STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .input('S', Items.STICK)
                .pattern("  S")
                .pattern(" S ")
                .pattern("BGB")
                .criterion("has_gear", conditionsFromItem(BwtItems.gearItem))
                .offerTo(exporter, IdUtils.ofBWT("hand_crank"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BwtBlocks.millStoneBlock)
                .input('B', STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BGB")
                .criterion("has_stone_brick", conditionsFromItem(STONE_BRICK))
                .offerTo(exporter, IdUtils.ofBWT("mill_stone"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.hibachiBlock)
                .input('H', BwtItems.concentratedHellfireItem)
                .input('E', BTWRDS_Items.ELEMENT)
                .input('B', STONE_BRICK)
                .input('R', Items.REDSTONE)
                .pattern("HHH")
                .pattern("BEB")
                .pattern("BRB")
                .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, IdUtils.ofBWT("hibachi"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.bellowsBlock)
                .input('L', BTWRTags.Items.TANNED_LEATHERS)
                .input('B', BwtItems.beltItem)
                .input('S', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('G', BwtItems.gearItem)
                .pattern("SSS")
                .pattern("LLL")
                .pattern("GBG")
                .criterion("has_wooden_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, IdUtils.ofBWT("bellows"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.anchorBlock)
                .input('I', Items.IRON_NUGGET)
                .input('B', STONE_BRICK)
                .pattern("   ")
                .pattern(" I ")
                .pattern("BBB")
                .criterion("has_stone_brick", conditionsFromItem(STONE_BRICK))
                .offerTo(exporter, IdUtils.ofBWT("anchor"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.turntableBlock)
                .input('C', Items.CLOCK)
                .input('S', BwtItemTags.WOODEN_SIDING_BLOCKS)
                .input('B', STONE_BRICK)
                .input('G', BwtItems.gearItem)
                .pattern("SSS")
                .pattern("BCB")
                .pattern("BGB")
                .criterion("has_wooden_siding", conditionsFromTag(BwtItemTags.WOODEN_SIDING_BLOCKS))
                .offerTo(exporter, IdUtils.ofBWT("turntable"));

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.detectorBlock)
                .input('B', STONE_BRICK)
                .input('E', BwtItems.redstoneEyeItem)
                .input('T', Items.REDSTONE_TORCH)
                .input('R', Items.REDSTONE)
                .pattern("BBBB")
                .pattern("ETTE")
                .pattern("BRRB")
                .pattern("BRRB")
                .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, IdUtils.ofBWT("detector_block"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.pulleyBlock)
                .input('W', STONE_BRICK)
                .input('I', Items.IRON_INGOT)
                .input('G', BwtItems.gearItem)
                .input('L', BTWRDS_Items.REDSTONE_LATCH)
                .pattern("WIW")
                .pattern("GLG")
                .pattern("WIW")
                .criterion("has_redstone_latch", conditionsFromItem(BTWRDS_Items.REDSTONE_LATCH))
                .offerTo(exporter, IdUtils.ofBWT("pulley"));

        // Overwritten cauldron recipes
        CauldronRecipe.JsonBuilder.createFood().result(BwtItems.donutItem,2)
                .ingredient(BwtItems.flourItem)
                .ingredient(Items.SUGAR)
                .criterion("has_flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, IdUtils.ofBWT("donut_from_cauldron"));

        CauldronRecipe.JsonBuilder.createFood().result(BwtItems.nethercoalItem)
                .ingredient(BwtItems.coalDustItem)
                .ingredient(BwtItems.hellfireDustItem)
                .criterion("has_hellfire_dust", conditionsFromItem(BwtItems.hellfireDustItem))
                .offerTo(exporter, IdUtils.ofBWT("nethercoal_from_cauldron"));


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
                .offerTo(exporter, IdUtils.ofBWT("composite_bow"));

        // BTWR: Core
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.MUSHROOM_OMELETTE_RAW)
                .input('E', BwtItems.rawEggItem)
                .input('M', Items.BROWN_MUSHROOM)
                .pattern("EM")
                .pattern("MM")
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, IdUtils.ofBTWR("mushroom_omelette_raw"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.DIAMOND_PLATE)
                .input('S', BwtItems.strapItem)
                .input('I', BTWR_Items.DIAMOND_INGOT)
                .input('P', BwtItems.paddingItem)
                .pattern("SIS")
                .pattern(" P ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofBTWR("diamond_plate"));

        // Vegehenna
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, PASTRY_UNCOOKED_CAKE)
                .input('E', BwtItems.rawEggItem)
                .input('M', Items.MILK_BUCKET)
                .input('F', BwtItems.flourItem)
                .input('S', Items.SUGAR)
                .pattern("SSS")
                .pattern("MEM")
                .pattern("FFF")
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, IdUtils.ofVG("pastry_uncooked_cake"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, BREAD_DOUGH)
                .input('F', BwtItems.flourItem)
                .pattern("F ")
                .pattern("FF")
                .criterion("flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, IdUtils.ofVG("bread_dough"));

        // Self-Sustainable
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.HAMPER)
                .input('#', ModItems.WICKER)
                .input('P', ItemTags.PLANKS)
                .pattern("###")
                .pattern("#P#")
                .pattern("###")
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, IdUtils.ofSS("hamper"));

        // DS
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.STICK,2)
                .input('P', ItemTags.PLANKS)
                .pattern("P")
                .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
                .offerTo(exporter, IdUtils.ofDS("stick_from_single_planks"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BTWRDS_Items.REDSTONE_LATCH)
                .input('G', Items.GOLD_NUGGET)
                .input('R', Items.REDSTONE)
                .pattern("GGG")
                .pattern(" R ")
                .criterion("has_redstone", conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, IdUtils.ofDS("redstone_latch"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                .input('R', Items.STICK)
                .input('S', ItemTags.STONE_TOOL_MATERIALS)
                .input('#', ConventionalItemTags.STRINGS)
                .pattern("SSS")
                .pattern(" R#")
                .pattern(" R ")
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, IdUtils.ofDS("stone_pickaxe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.IRON_AXE)
                .input('M', Items.IRON_INGOT)
                .input('I', Items.STICK)
                .pattern(" M")
                .pattern("IM")
                .pattern("I ")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, IdUtils.ofDS("iron_axe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.IRON_HOE)
                .input('M', Items.IRON_INGOT)
                .input('I', Items.STICK)
                .pattern("IM")
                .pattern("I ")
                .pattern("I ")
                .criterion("has_iron_ingot", conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, IdUtils.ofDS("iron_hoe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_AXE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern(" M")
                .pattern("IM")
                .pattern("I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofDS("diamond_axe_right"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.DIAMOND_HOE)
                .input('M', BTWR_Items.DIAMOND_INGOT)
                .input('I', Items.STICK)
                .pattern("IM")
                .pattern("I ")
                .pattern("I ")
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofDS("diamond_hoe_right"));

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