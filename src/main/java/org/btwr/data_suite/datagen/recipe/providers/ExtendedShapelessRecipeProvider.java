package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.btwr.core.item.BTWR_Items;
import org.btwr.shared_library.recipe.ExtendedShapelessRecipe;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.shared_library.util.utils.RecipeUtils;
import org.btwr.data_suite.BTWRDSMod;
import org.btwr.data_suite.item.BTWRDS_Items;
import org.btwr.sturdy_trees.SturdyTreesMod;
import org.btwr.sturdy_trees.item.SturdyTreesItems;

import java.util.concurrent.CompletableFuture;

public class ExtendedShapelessRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    public ExtendedShapelessRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        this.createSticksWithAxe(exporter);
        this.createPlanksWithAxe(exporter);

        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_SCOURED_CUT,2)
                .withToolDamage()
                .input(BwtItems.scouredLeatherItem)
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .criterion("has_scoured_leather", conditionsFromItem(BwtItems.scouredLeatherItem))
                .offerTo(exporter, IdUtils.ofDS("leather_scoured_cut"));

        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Items.LEATHER_TANNED_CUT,2)
                .withToolDamage()
                .input(BwtItems.tannedLeatherItem)
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .criterion("has_tanned_leather", conditionsFromItem(BwtItems.tannedLeatherItem))
                .offerTo(exporter, IdUtils.ofDS("leather_tanned_cut"));

        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BwtItems.strapItem,4)
                .withToolDamage()
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .input(BTWR_Items.LEATHER_TANNED_CUT)
                .criterion("has_leather_tanned_cut", conditionsFromItem(BTWR_Items.LEATHER_TANNED_CUT))
                .offerTo(exporter, IdUtils.ofBWT("strap"));


        // Move this recipe into it's own provider when we figure out how to call recipes that have additional drops
        // and how to separate them from just tool crafting recipes.
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, Items.CARVED_PUMPKIN)
                .additionalDrop(Items.PUMPKIN_SEEDS, 4)
                .input(Items.PUMPKIN)
                .criterion("has_pumpkin", conditionsFromItem(Items.PUMPKIN))
                .offerTo(exporter, Identifier.ofVanilla("pumpkin_seeds"));
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

        // Register blood wood recipes as part of BTWRDS namespace.
        // From log
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BwtBlocks.bloodWoodBlocks.planksBlock.asItem(), 4)
                .additionalDrop(BTWRDS_Items.BARK_BLOOD_WOOD)
                .additionalDrop(BwtItems.sawDustItem)
                .additionalDrop(BwtItems.soulDustItem)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS),1)
                .input(BwtBlocks.bloodWoodBlocks.logBlock.asItem())
                .criterion("has_blood_wood_log", conditionsFromItem(BwtBlocks.bloodWoodBlocks.logBlock.asItem()))
                .offerTo(exporter, Identifier.of(BTWRDSMod.MOD_ID,"blood_wood_planks_from_blood_wood_log_from_tool_crafting"));

        // From stripped log
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, BwtBlocks.bloodWoodBlocks.planksBlock.asItem(), 4)
                .additionalDrop(BwtItems.soulDustItem)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS),1)
                .input(BwtBlocks.bloodWoodBlocks.strippedLogBlock.asItem())
                .criterion("has_stripped_blood_wood", conditionsFromItem(BwtBlocks.bloodWoodBlocks.strippedLogBlock.asItem()))
                .offerTo(exporter, Identifier.of(BTWRDSMod.MOD_ID, "blood_wood_planks_from_stripped_blood_wood_log_from_tool_crafting"));
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
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, Items.STICK, 2)
                .additionalDrop(barkItem)
                .additionalDrop(BwtItems.sawDustItem)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.PRIMITIVE_AXES),1)
                .input(log)
                .criterion("has_" + logType, conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(SturdyTreesMod.MOD_ID,"sticks_from_tool_crafting_from_" + logType));

        // From stripped log
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, Items.STICK, 2)
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
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, planks, 4)
                .additionalDrop(bark)
                .additionalDrop(BwtItems.sawDustItem)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS),1)
                .input(log)
                .criterion("has_" + logType, conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(SturdyTreesMod.MOD_ID, planksType + "_from_" + logType + "_tool_crafting"));

        // From stripped log
        ExtendedShapelessRecipe.JsonBuilder.create(RecipeCategory.MISC, planks, 4)
                .additionalDrop(BwtItems.sawDustItem, 2)
                .withToolDamage()
                .input(Ingredient.fromTag(BTWRConventionalTags.Items.AXES_MAKE_PLANKS),1)
                .input(strippedLog)
                .criterion("has_" + strippedType, conditionsFromItem(strippedLog))
                .offerTo(exporter, Identifier.of(SturdyTreesMod.MOD_ID, planksType + "_from_" + strippedType + "_tool_crafting"));
    }

}