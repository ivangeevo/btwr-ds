package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.blocks.BwtBlocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.btwr.core.item.BTWR_Items;
import org.btwr.self_sustainable.item.ModItems;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;
import org.btwr.shared_library.util.utils.IdUtils;
import com.bwt.items.BwtItems;
import org.btwr.sturdy_trees.item.SturdyTreesItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.btwr.shared_library.util.utils.RecipeUtils;

import java.util.concurrent.CompletableFuture;

import static org.btwr.self_sustainable.item.ModItems.FIRESTARTER_BOW;
import static org.btwr.self_sustainable.item.ModItems.FIRESTARTER_STICKS;
import static org.btwr.tough_environment.item.ModItems.CHISEL_WOOD;
import static org.btwr.vegehenna.item.ModItems.*;

public class ShapelessRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};

    public ShapelessRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        this.addModExclusive(exporter);

        // TODO: figure out why .additionalDrop() builder is not working on datagen
        /**
         ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.bloodWoodBlocks.planksBlock)
         .input(BwtBlocks.bloodWoodBlocks.logBlock)
         .input(BTWRConventionalTags.Items.AXES_MAKE_PLANKS)
         .additionalDrop(BTWRDS_Items.BARK_BLOOD_WOOD.getDefaultStack())
         .additionalDrop(SturdyTreesItems.DUST_SAW.getDefaultStack())
         .criterion("has_blood_wood_log", conditionsFromItem(BwtBlocks.bloodWoodBlocks.logBlock))
         .offerTo(exporter, IdUtils.ofBWT("blood_wood_planks"));
         **/

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ARROW, 2)
                .input(Items.STICK)
                .input(Items.FEATHER)
                .input(Items.FLINT)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, IdUtils.ofMC("arrow"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_SHOVEL)
                .input(Items.STICK)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, IdUtils.ofMC("stone_shovel"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_AXE)
                .input(Items.STICK)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ItemTags.STONE_TOOL_MATERIALS)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_cobblestone", RecipeProvider.conditionsFromTag(ItemTags.STONE_TOOL_MATERIALS))
                .offerTo(exporter, IdUtils.ofMC("stone_axe"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.FISHING_ROD)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(Items.IRON_NUGGET)
                .criterion("has_fishing_hook_material", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, IdUtils.ofMC("fishing_rod"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_RAW, 2)
                .input(BwtItems.rawEggItem)
                .input(Items.MILK_BUCKET)
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, IdUtils.ofBTWR("egg_scrambled_raw"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.STEAK_DINNER,3)
                .input(Items.COOKED_BEEF)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(COOKED_CARROT))
                .offerTo(exporter, IdUtils.ofBTWR("steak_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.PORK_DINNER,3)
                .input(Items.COOKED_PORKCHOP)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(COOKED_CARROT))
                .offerTo(exporter, IdUtils.ofBTWR("pork_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.WOLF_DINNER,3)
                .input(BwtItems.cookedWolfChopItem)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(COOKED_CARROT))
                .offerTo(exporter, IdUtils.ofBTWR("wolf_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, BwtItems.broadheadArrowItem,4)
                .input(Items.STICK)
                .input(BwtItems.broadheadItem)
                .input(Items.FEATHER)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_broadhead", conditionsFromItem(BwtItems.broadheadItem))
                .offerTo(exporter, IdUtils.ofBWT("broadhead_arrow"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, BwtBlocks.wickerPaneBlock)
                .input(BwtBlocks.grateBlock)
                .input(ModItems.WICKER)
                .criterion(hasItem(BwtBlocks.grateBlock), conditionsFromItem(BwtBlocks.grateBlock))
                .offerTo(exporter, IdUtils.ofBWT("wicker"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, PASTRY_UNCOOKED_PUMPKIN_PIE)
                .input(BwtItems.rawEggItem)
                .input(Items.SUGAR)
                .input(Items.PUMPKIN)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .criterion("flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, IdUtils.ofVG("pastry_uncooked_pumpkin_pie"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, PASTRY_UNCOOKED_COOKIES,4)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(CHOCOLATE)
                .criterion("has_chocolate", conditionsFromItem(CHOCOLATE))
                .offerTo(exporter, IdUtils.ofVG("pastry_uncooked_cookies"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, SturdyTreesItems.STUMP_REMOVER,2)
                .input(Items.ROTTEN_FLESH)
                .input(Items.RED_MUSHROOM)
                .input(BTWR_Items.CREEPER_OYSTERS)
                .criterion("has_creeper_oysters", conditionsFromItem(BTWR_Items.CREEPER_OYSTERS))
                .offerTo(exporter, IdUtils.ofST("stump_remover"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, org.btwr.tough_environment.item.ModItems.CHISEL_DIAMOND)
                .input(BTWR_Items.DIAMOND_INGOT)
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, IdUtils.ofTE("chisel_diamond"));

        // Self-Sustainable
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.KNITTING_NEEDLES)
                .input(CHISEL_WOOD)
                .input(CHISEL_WOOD)
                .criterion(hasItem(CHISEL_WOOD), conditionsFromItem(CHISEL_WOOD))
                .offerTo(exporter, IdUtils.ofSS("knitting_needles"));

        // Nomad's Rest
        for (DyeColor color : DyeColor.values()) {
            Item woolKnitItem = Registries.ITEM.get(IdUtils.ofSS(color.getName() + "_wool_knit"));
            Identifier bedrollId = Identifier.of("nomads_rest", color.getName() + "_bedroll");
            Item bedrollItem = Registries.ITEM.get(bedrollId);

            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, bedrollItem)
                    .input(woolKnitItem)
                    .input(woolKnitItem)
                    .input(BTWRConventionalTags.Items.STRING_TOOL_MATERIALS)
                    .criterion(hasItem(woolKnitItem), conditionsFromItem(woolKnitItem))
                    .offerTo(exporter, bedrollId);
        }

        // Enable this recipe when Groth is added in BWT
        /**
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BTWR_Items.NETHER_GROTH_SPORES)
                .input(Items.RED_MUSHROOM)
                .input(Items.BROWN_MUSHROOM)
                .input(Items.NETHER_WART)
                .input(Items.MYCELIUM)
                .input(BwtItems.dungItem)
                .input(BwtItems.soulUrnItem)
                .criterion("has_mycelium", conditionsFromItem(Items.MYCELIUM))
                .offerTo(exporter, IdUtils.ofBTWR("nether_groth_spores"));
         **/

        this.createConvertToSawDustToolRecipes(exporter);
    }

    private void addModExclusive(RecipeExporter exporter) {

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.hempFiberItem,9)
                .input(BwtItems.fabricItem)
                .criterion("has_fabric", conditionsFromItem(BwtItems.fabricItem))
                .offerTo(exporter, IdUtils.ofDS("hemp_fiber_from_fabric"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.COAL)
                .input(BwtItems.coalDustItem)
                .input(BwtItems.coalDustItem)
                .criterion("has_coal_dust", conditionsFromItem(BwtItems.coalDustItem))
                .offerTo(exporter, IdUtils.ofDS("coal_from_coal_dust"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, BwtItems.sawDustItem, 2)
                .input(ModItems.KNITTING_NEEDLES)
                .criterion(hasItem(ModItems.KNITTING_NEEDLES), conditionsFromItem(ModItems.KNITTING_NEEDLES))
                .offerTo(exporter, IdUtils.ofDS("saw_dust_from_knitting_needles"));

    }

    private void createConvertToSawDustToolRecipes(RecipeExporter exporter) {
        convertToSawdustRecipeBuilder(exporter, FIRESTARTER_STICKS, "has_firestarter_sticks");
        convertToSawdustRecipeBuilder(exporter, FIRESTARTER_BOW, "has_firestarter_bow");
        convertToSawdustRecipeBuilder(exporter, BTWR_Items.CLUB_WOOD, "has_club_wood");
    }

    private void convertToSawdustRecipeBuilder(RecipeExporter exporter, Item tool, String criterion) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.sawDustItem)
                .input(tool)
                .criterion(criterion, conditionsFromItem(tool))
                .offerTo(exporter, IdUtils.ofDS("saw_dust_from_converting_" + extractName(tool)));
    }

}