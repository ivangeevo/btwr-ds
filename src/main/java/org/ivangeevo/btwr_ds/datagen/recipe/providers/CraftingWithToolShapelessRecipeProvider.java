package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.recipe.CraftingWithToolShapelessRecipe;
import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.item.BTWR_Items;
import com.bwt.items.BwtItems;
import ivangeevo.sturdy_trees.SturdyTreesMod;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class CraftingWithToolShapelessRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public CraftingWithToolShapelessRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        this.createSticksWithAxe(exporter);
        this.createPlanksWithAxe(exporter);

        CraftingWithToolShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_SCOURED_CUT,2)
                .withToolDamage()
                .input(BwtItems.scouredLeatherItem)
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .criterion("has_scoured_leather", conditionsFromItem(BwtItems.scouredLeatherItem))
                .offerTo(exporter, ID.ofDS("leather_scoured_cut"));

        CraftingWithToolShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_TANNED_CUT,2)
                .withToolDamage()
                .input(BwtItems.tannedLeatherItem)
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .criterion("has_tanned_leather", conditionsFromItem(BwtItems.tannedLeatherItem))
                .offerTo(exporter, ID.ofDS("leather_tanned_cut"));
    }

    private void createPlanksWithAxe(RecipeExporter exporter) {
        planksWithAxe(Items.OAK_PLANKS, Items.OAK_LOG, Items.STRIPPED_OAK_LOG, SturdyTreesItems.BARK_OAK, exporter);
        planksWithAxe(Items.BIRCH_PLANKS, Items.BIRCH_LOG, Items.STRIPPED_BIRCH_LOG, SturdyTreesItems.BARK_BIRCH, exporter);
        planksWithAxe(Items.SPRUCE_PLANKS, Items.SPRUCE_LOG, Items.STRIPPED_SPRUCE_LOG, SturdyTreesItems.BARK_SPRUCE, exporter);
        planksWithAxe(Items.JUNGLE_PLANKS, Items.JUNGLE_LOG, Items.STRIPPED_JUNGLE_LOG, SturdyTreesItems.BARK_JUNGLE, exporter);
        planksWithAxe(Items.ACACIA_PLANKS, Items.ACACIA_LOG, Items.STRIPPED_ACACIA_LOG, SturdyTreesItems.BARK_ACACIA, exporter);
        planksWithAxe(Items.DARK_OAK_PLANKS, Items.DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_LOG, SturdyTreesItems.BARK_DARK_OAK, exporter);
        planksWithAxe(Items.MANGROVE_PLANKS, Items.MANGROVE_LOG, Items.STRIPPED_MANGROVE_LOG, SturdyTreesItems.BARK_MANGROVE, exporter);
        planksWithAxe(Items.CHERRY_PLANKS, Items.CHERRY_LOG, Items.STRIPPED_CHERRY_LOG, SturdyTreesItems.BARK_SPRUCE, exporter);
    }

    private void createSticksWithAxe(RecipeExporter exporter) {
        sticksWithAxes(Items.OAK_LOG, Items.STRIPPED_OAK_LOG, SturdyTreesItems.BARK_OAK, exporter);
        sticksWithAxes(Items.BIRCH_LOG, Items.STRIPPED_BIRCH_LOG, SturdyTreesItems.BARK_BIRCH, exporter);
        sticksWithAxes(Items.SPRUCE_LOG, Items.STRIPPED_SPRUCE_LOG, SturdyTreesItems.BARK_SPRUCE, exporter);
        sticksWithAxes(Items.JUNGLE_LOG, Items.STRIPPED_JUNGLE_LOG, SturdyTreesItems.BARK_JUNGLE, exporter);
        sticksWithAxes(Items.ACACIA_LOG, Items.STRIPPED_ACACIA_LOG, SturdyTreesItems.BARK_ACACIA, exporter);
        sticksWithAxes(Items.DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_LOG, SturdyTreesItems.BARK_DARK_OAK, exporter);
        sticksWithAxes(Items.MANGROVE_LOG, Items.STRIPPED_MANGROVE_LOG, SturdyTreesItems.BARK_MANGROVE, exporter);
        sticksWithAxes(Items.CHERRY_LOG, Items.STRIPPED_CHERRY_LOG, SturdyTreesItems.BARK_CHERRY, exporter);
    }

    private void sticksWithAxes(Item log, Item strippedLog, Item barkItem, RecipeExporter exporter) {
        String logType = Registries.ITEM.getId(log).getPath();

        // From log
        CraftingWithToolShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, Items.STICK, 2)
                .additionalDrop(barkItem)
                .additionalDrop(BwtItems.sawDustItem)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.PRIMITIVE_AXES),1)
                .input(log)
                .criterion("has_" + logType, conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(SturdyTreesMod.MOD_ID,"sticks_from_tool_crafting_from_" + logType));

        // From stripped log
        CraftingWithToolShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, Items.STICK, 2)
                .additionalDrop(BwtItems.sawDustItem, 2)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.PRIMITIVE_AXES),1)
                .input(strippedLog)
                .criterion("has_" + strippedLog, conditionsFromItem(strippedLog))
                .offerTo(exporter, Identifier.of(SturdyTreesMod.MOD_ID,"sticks_from_tool_crafting_from_stripped_" + logType));
    }

    private void planksWithAxe(Item planks, Item log, Item strippedLog, Item bark, RecipeExporter exporter) {
        String logType = Registries.ITEM.getId(log).getPath();
        String planksType = Registries.ITEM.getId(planks).getPath();
        String strippedType = Registries.ITEM.getId(strippedLog).getPath();

        // From log
        CraftingWithToolShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, planks, 4)
                .additionalDrop(bark)
                .additionalDrop(BwtItems.sawDustItem)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS),1)
                .input(log)
                .criterion("has_" + logType, conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(SturdyTreesMod.MOD_ID, planksType + "_from_" + logType + "_tool_crafting"));

        // From stripped log
        CraftingWithToolShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, planks, 4)
                .additionalDrop(BwtItems.sawDustItem, 2)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS),1)
                .input(strippedLog)
                .criterion("has_" + strippedType, conditionsFromItem(strippedLog))
                .offerTo(exporter, Identifier.of(SturdyTreesMod.MOD_ID, planksType + "_from_" + strippedType + "_tool_crafting"));
    }
}
