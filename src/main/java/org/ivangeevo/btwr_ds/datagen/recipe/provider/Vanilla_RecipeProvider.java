package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwrsl.lib.util.utils.RecipeProviderUtils;
import btwr.core.item.BTWR_Items;
import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.tags.BwtItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;


public class Vanilla_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    public Vanilla_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private static final String MC = "minecraft";

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};


    @Override
    public void generate(RecipeExporter exporter) {
        // Minecraft
        this.overrideForVanilla(exporter);
    }

    private void overrideForVanilla(RecipeExporter exporter) {

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

        // Adding trapdoor recipes
        for (String woodType : vanillaWoodTypes)
        {
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
                .input('B', ModItems.STONE_BRICK)
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
                .input('B', ModItems.STONE_BRICK)
                .pattern("RCR")
                .pattern("BBB")
                .criterion("has_redstone_torch", conditionsFromItem(Items.REDSTONE_TORCH))
                .offerTo(exporter, ID.ofMC("repeater"));


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

        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(BTWR_Items.BRICK_UNFIRED), RecipeCategory.BUILDING_BLOCKS,
                Items.BRICK, 0.10F, 10000).criterion("has_brick_unfired", conditionsFromItem(BTWR_Items.BRICK_UNFIRED)).offerTo(exporter, ID.ofMC("brick"));

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

    private void createNuggetRecipes(RecipeExporter exporter) {
        // prefix should be the name of the recipe you want to replace; in vanilla's case it's the ingot ones

        offerOreCookingRecipe("copper_ingot", Items.RAW_COPPER, Items.COPPER_ORE, Items.DEEPSLATE_COPPER_ORE,
                ModItems.COPPER_NUGGET, 10000, 5000, ItemTags.COPPER_ORES, MC, exporter);

        offerOreCookingRecipe("gold_ingot", Items.RAW_GOLD, Items.GOLD_ORE, Items.DEEPSLATE_GOLD_ORE,
                Items.GOLD_NUGGET, 10000, 5000, ItemTags.GOLD_ORES, MC,exporter);

        offerOreCookingRecipe("iron_ingot", Items.RAW_IRON, Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE,
                Items.IRON_NUGGET, 12000, 6000, ItemTags.IRON_ORES, MC, exporter);

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
        oreCookingRecipeBuilder((ingredient, category, experience, time) ->
                        CookingRecipeJsonBuilder.createSmelting(ingredient, category, smeltedItem, experience, time),
                prefix, "smelting", rawItem, oreItem, deepslateOreItem, new float[] {0.35F, 0.45F, 0.55F},
                smeltTime, conditionTag, namespace, exporter);

        // Blasting recipes
        oreCookingRecipeBuilder((ingredient, category, experience, time) ->
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

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

}
