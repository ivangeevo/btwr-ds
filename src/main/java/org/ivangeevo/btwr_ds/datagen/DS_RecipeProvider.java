package org.ivangeevo.btwr_ds.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.FabricIngredient;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.tag.ModConventionalTags;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DS_RecipeProvider extends FabricRecipeProvider
{

    private static final String TE = "tough_environment";

    public DS_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    Registry<Item> itemRegistry = Registries.ITEM;    /** quick method for getting item registries from item id **/

    private Item grabRaw(Identifier id)
    {
        return itemRegistry.get(id);
    }

    private Item grabRaw(String namespace, String itemID)
    {
      return itemRegistry.get(namespace == null ? ofVanilla(itemID) : Identifier.of(namespace, itemID) );
    }

    private Item grabRawUnreg(Identifier id)
    {
        return itemRegistry.get(id);
    }

    private Item grabRaw(String itemID)
    {
       return itemRegistry.get(ofVanilla(itemID));
    }
    private Identifier ofVanilla(String itemID)
    {
        return Identifier.ofVanilla(itemID);
    }
    private Identifier ofBTWR(String itemID)
    {
        return Identifier.of("btwr", itemID);
    }
    private Identifier ofTE(String itemID)
    {
        return Identifier.of("tough_environment", itemID);
    }
    private Identifier ofSS(String itemID)
    {
        return Identifier.of("self_sustainable", itemID);
    }
    private Identifier ofST(String itemID)
    {
        return Identifier.of("sturdy_trees", itemID);
    }


    @Override
    public void generate(RecipeExporter exporter) {
        //generateForVanilla(exporter);
        generateForMod(exporter);
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BONE_BLOCK)
                .input('S', Items.BONE)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .criterion("has_bone", conditionsFromItem(Items.BONE))
                .offerTo(exporter, Identifier.ofVanilla("bone_block"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.LADDER, 2)
                .input('S', Items.STICK)
                // TODO: Fix to use the STRING TOOL MATERIALS Tags from BTWR Conventional tags.
                // maybe the ConventionalItemTags STRING is ok?
                .input('F', ConventionalItemTags.STRINGS)
                .pattern("SFS")
                .pattern("SSS")
                .pattern("SFS")
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, Identifier.ofVanilla( "ladder"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.HOPPER)
                .input('S', ItemTags.WOODEN_SLABS)
                .input('G', ModConventionalTags.Items.GEARS)
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
            ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Registries.ITEM.get(resultId))
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
            ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Registries.ITEM.get(resultId))
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
                // TODO: Fix to use the STRING TOOL MATERIALS Tags from BTWR Conventional tags.
                // maybe the ConventionalItemTags STRING is ok?
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, Identifier.ofVanilla("arrow"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BONE, 9)
                .input(Items.BONE_BLOCK)
                .criterion("has_bone_block", conditionsFromItem(Items.BONE_BLOCK))
                .offerTo(exporter, Identifier.ofVanilla("bone"));
    }

    private void generateForMod(RecipeExporter exporter)
    {


            // TODO: fix non-vanilla mod namespaces give "item array empty" error for the inputs & criterion of this recipe
            Identifier resultId = ofTE("slab_cobblestone_loose");
            ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.FURNACE)
                    .input('S', grabRaw(resultId))
                    .pattern("SS")
                    .pattern("SS")
                    .criterion("has_slab_cobblestone_loose", conditionsFromItem(grabRaw(TE, "slab_cobblestone_loose")))
                    .offerTo(exporter);

    }

}
