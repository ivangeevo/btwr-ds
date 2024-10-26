package org.ivangeevo.btwr_ds.datagen;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.RecipeProviderUtils;
import org.ivangeevo.btwr_ds.tag.BTWRConventionalTags;
import org.ivangeevo.btwr_ds.tag.BWTTags;

import java.util.concurrent.CompletableFuture;

public class DS_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{

    public DS_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    private static final ImmutableList<ItemConvertible> COAL_ORES = ImmutableList.of(Items.COAL_ORE, Items.DEEPSLATE_COAL_ORE);

    private static final ImmutableList<ItemConvertible> IRON_ORES = ImmutableList.of(Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE, Items.RAW_IRON);

    private static final ImmutableList<ItemConvertible> COPPER_ORES = ImmutableList.of(Items.COPPER_ORE, Items.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER);

    private static final ImmutableList<ItemConvertible> GOLD_ORES = ImmutableList.of(Items.GOLD_ORE, Items.DEEPSLATE_GOLD_ORE, Items.NETHER_GOLD_ORE, Items.RAW_GOLD);

    private static final ImmutableList<ItemConvertible> DIAMOND_ORES = ImmutableList.of(Items.DIAMOND_ORE, Items.DEEPSLATE_DIAMOND_ORE);



    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter)
    {
        // Minecraft
        this.generateForVanilla(exporter);

        // BTWR-DS
        this.generateForMod(exporter);

        // Better With Time
        this.generateForBWT(exporter);

        // Recipes that get removed
        this.generateRecipesToRemove(exporter);
    }


    private void generateForMod(RecipeExporter exporter)
    {
        // Tools
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                .input('R', Items.STICK)
                .input('S', ItemTags.STONE_TOOL_MATERIALS)
                .input('#', ConventionalItemTags.STRINGS)
                .pattern("SSS")
                .pattern(" R#")
                .pattern(" R ")
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, Identifier.of("btwr-ds","stone_pickaxe_right"));

        // Cooking recipes

        // TODO: Remove the smelting recipes for ores when we add the Brick oven from Self Sustainable
        //offerSmelting(exporter, IRON_ORES, RecipeCategory.MISC, Items.IRON_NUGGET, 0.35F, 200, "iron_nugget");

        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Items.RAW_IRON), RecipeCategory.MISC, Items.IRON_NUGGET, 0.35F, 1200)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES)).offerTo(exporter, Identifier.of("btwr-ds","iron_nugget_from_smelting_raw_iron"));
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Items.IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.45F, 1200)
        .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES)).offerTo(exporter, Identifier.of("btwr-ds", "iron_nugget_from_smelting_iron_ore"));
        CookingRecipeJsonBuilder.createSmelting(Ingredient.ofItems(Items.DEEPSLATE_IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.55F, 1200)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES)).offerTo(exporter, Identifier.of("btwr-ds", "iron_nugget_from_smelting_deepslate_iron_ore"));

        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(Items.RAW_IRON), RecipeCategory.MISC, Items.IRON_NUGGET, 0.45F, 600)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES)).offerTo(exporter, Identifier.of("btwr-ds","iron_nugget_from_blasting_raw_iron"));
        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(Items.IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.55F, 600)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES)).offerTo(exporter, Identifier.of("btwr-ds", "iron_nugget_from_blasting_iron_ore"));
        CookingRecipeJsonBuilder.createBlasting(Ingredient.ofItems(Items.DEEPSLATE_IRON_ORE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.65F, 600)
                .criterion("has_iron_ore", RecipeProvider.conditionsFromTag(ItemTags.IRON_ORES)).offerTo(exporter, Identifier.of("btwr-ds", "iron_nugget_from_blasting_deepslate_iron_ore"));

    }

    private void generateForVanilla(RecipeExporter exporter)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHEST)
                .input('S', ItemTags.WOODEN_SLABS)
                .pattern("SSS")
                .pattern("S S")
                .pattern("SSS")
                .criterion("has_slab", conditionsFromTag(ItemTags.WOODEN_SLABS))
                .offerTo(exporter, Identifier.ofVanilla("chest"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.BONE_BLOCK)
                .input('S', Items.BONE)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .criterion("has_bone", conditionsFromItem(Items.BONE))
                .offerTo(exporter, Identifier.ofVanilla("bone_block"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LADDER, 2)
                .input('S', Items.STICK)
                .input('F', ConventionalItemTags.STRINGS)
                .pattern("SFS")
                .pattern("SSS")
                .pattern("SFS")
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, Identifier.ofVanilla( "ladder"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Items.HOPPER)
                .input('S', ItemTags.WOODEN_SLABS)
                .input('G', BTWRConventionalTags.Items.GEARS)
                .input('P', ItemTags.WOODEN_PRESSURE_PLATES)
                .input('W', ItemTags.PLANKS)
                .pattern("S S")
                .pattern("GPG")
                .pattern(" W ")
                .criterion("has_slab", conditionsFromTag(ItemTags.WOODEN_SLABS))
                .offerTo(exporter);

        // Adding door recipes
        String[] doorTypes = { "oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry" };
        for (String woodType : doorTypes)
        {
            Identifier resultId = Identifier.ofVanilla(woodType + "_door");
            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(resultId))
                    .input('P', grabRaw(woodType + "_planks"))
                    .pattern("PP")
                    .pattern("PP")
                    .pattern("PP")
                    .criterion("has_planks", conditionsFromItem(Registries.ITEM.get(Identifier.ofVanilla(woodType + "_planks"))))
                    .offerTo(exporter, resultId);
        }

        // Adding pressure plate recipes
        String[] woodenPressurePlates = {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};
        for (String plateType : woodenPressurePlates)
        {
            Identifier resultId = Identifier.ofVanilla(plateType + "_pressure_plate");
            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, Registries.ITEM.get(resultId))
                    .input('S', grabRaw(plateType + "_slab"))
                    .input('R', Items.REDSTONE)
                    .pattern("S")
                    .pattern("R")
                    .criterion("has_slab", conditionsFromTag(ItemTags.WOODEN_SLABS))
                    .offerTo(exporter, resultId);
        }

        // Items
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ARROW, 2)
                .input(Items.STICK)
                .input(Items.FEATHER)
                .input(Items.FLINT)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, Identifier.ofVanilla("arrow"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BONE, 9)
                .input(Items.BONE_BLOCK)
                .criterion("has_bone_block", conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, Identifier.ofVanilla("bone"));

        // Tools

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_SHOVEL)
                .input(Items.STICK)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, Identifier.ofVanilla("stone_shovel"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_AXE)
                .input(Items.STICK)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, Identifier.ofVanilla("stone_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                .input('R', Items.STICK)
                .input('S', ItemTags.STONE_TOOL_MATERIALS)
                .input('#', ConventionalItemTags.STRINGS)
                .pattern("SSS")
                .pattern("#R ")
                .pattern(" R ")
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, Identifier.ofVanilla("stone_pickaxe"));

    }

    private void generateForBWT(RecipeExporter exporter)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LADDER)
                .input('P', BWTTags.Items.WOODEN_MOULDING_BLOCKS)
                .input('S', ConventionalItemTags.STRINGS)
                .pattern("PSP")
                .pattern("PPP")
                .pattern("PSP")
                .criterion("has_wooden_moulding", RecipeProvider.conditionsFromTag(BWTTags.Items.WOODEN_MOULDING_BLOCKS))
                .offerTo(exporter, Identifier.of("bwt", "he_ladder"));

    }

    private void generateRecipesToRemove(RecipeExporter exporter)
    {
        removeRecipe(exporter, Identifier.ofVanilla("iron_ingot_from_smelting_raw_iron"));
        removeRecipe(exporter, Identifier.ofVanilla("iron_ingot_from_smelting_iron_ore"));
        removeRecipe(exporter, Identifier.ofVanilla("iron_ingot_from_smelting_deepslate_iron_ore"));

        removeRecipe(exporter, Identifier.ofVanilla("iron_ingot_from_blasting_raw_iron"));
        removeRecipe(exporter, Identifier.ofVanilla("iron_ingot_from_blasting_iron_ore"));
        removeRecipe(exporter, Identifier.ofVanilla("iron_ingot_from_blasting_deepslate_iron_ore"));

        // Remove wooden tools
        removeRecipe(exporter, Identifier.ofVanilla("wooden_sword"));
        removeRecipe(exporter, Identifier.ofVanilla("wooden_pickaxe"));
        removeRecipe(exporter, Identifier.ofVanilla("wooden_axe"));
        removeRecipe(exporter, Identifier.ofVanilla("wooden_shovel"));
        removeRecipe(exporter, Identifier.ofVanilla("wooden_hoe"));

        // Remove the ability to repair items by combining them
        removeRecipe(exporter, Identifier.ofVanilla("repair_item"));

    }
}
