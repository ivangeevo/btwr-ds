package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import btwr.btwr_sl.tag.BTWRConventionalTags;
import btwr.core.item.BTWR_Items;
import com.bwt.items.BwtItems;
import ivangeevo.sturdy_trees.item.SturdyTreesItems;
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
import org.ivangeevo.vegehenna.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ShapelessRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils
{
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
         .offerTo(exporter, ID.ofBWT("blood_wood_planks"));
         **/

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.ARROW, 2)
                .input(Items.STICK)
                .input(Items.FEATHER)
                .input(Items.FLINT)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_stick", conditionsFromItem(Items.STICK))
                .offerTo(exporter, ID.ofMC("arrow"));

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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.FISHING_ROD)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STRING)
                .input(Items.IRON_NUGGET)
                .criterion("has_fishing_hook_material", conditionsFromItem(Items.IRON_NUGGET))
                .offerTo(exporter, ID.ofMC("fishing_rod"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.EGG_SCRAMBLED_RAW, 2)
                .input(BwtItems.rawEggItem)
                .input(Items.MILK_BUCKET)
                .criterion("has_raw_egg", conditionsFromItem(BwtItems.rawEggItem))
                .offerTo(exporter, ID.ofBTWR("egg_scrambled_raw"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.STEAK_DINNER,3)
                .input(Items.COOKED_BEEF)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(ModItems.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(ModItems.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("steak_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.PORK_DINNER,3)
                .input(Items.COOKED_PORKCHOP)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(ModItems.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(ModItems.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("pork_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, BTWR_Items.WOLF_DINNER,3)
                .input(BwtItems.cookedWolfChopItem)
                .input(BTWRConventionalTags.Items.COOKED_POTATO_FOODS)
                .input(ModItems.COOKED_CARROT)
                .criterion("has_cooked_carrot", RecipeProvider.conditionsFromItem(ModItems.COOKED_CARROT))
                .offerTo(exporter, ID.ofBTWR("wolf_dinner"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.strapItem,4)
                .input(ConventionalItemTags.SHEAR_TOOLS)
                .input(BTWR_Items.LEATHER_TANNED_CUT)
                .criterion("has_leather_tanned_cut", conditionsFromItem(BTWR_Items.LEATHER_TANNED_CUT))
                .offerTo(exporter, ID.ofBWT("strap"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, BwtItems.broadheadArrowItem,4)
                .input(Items.STICK)
                .input(BwtItems.broadheadItem)
                .input(Items.FEATHER)
                .input(ConventionalItemTags.STRINGS)
                .criterion("has_broadhead", conditionsFromItem(BwtItems.broadheadItem))
                .offerTo(exporter, ID.ofBWT("broadhead_arrow"));


        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PASTRY_UNCOOKED_PUMPKIN_PIE)
                .input(BwtItems.rawEggItem)
                .input(Items.SUGAR)
                .input(Items.PUMPKIN)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .criterion("flour", conditionsFromItem(BwtItems.flourItem))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_pumpkin_pie"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.PASTRY_UNCOOKED_COOKIES,4)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(BwtItems.flourItem)
                .input(ModItems.CHOCOLATE)
                .criterion("has_chocolate", conditionsFromItem(ModItems.CHOCOLATE))
                .offerTo(exporter, ID.ofVG("pastry_uncooked_cookies"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, SturdyTreesItems.STUMP_REMOVER,2)
                .input(Items.ROTTEN_FLESH)
                .input(Items.RED_MUSHROOM)
                .input(BTWR_Items.CREEPER_OYSTERS)
                .criterion("has_creeper_oysters", conditionsFromItem(BTWR_Items.CREEPER_OYSTERS))
                .offerTo(exporter, ID.ofST("stump_remover"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, org.tough_environment.item.ModItems.CHISEL_DIAMOND)
                .input(BTWR_Items.DIAMOND_INGOT)
                .criterion("has_diamond_ingot", conditionsFromItem(BTWR_Items.DIAMOND_INGOT))
                .offerTo(exporter, ID.ofTE("chisel_diamond"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.MELON_SLICE, 5)
                .input(Items.MELON)
                .input(ItemTags.AXES)
                .criterion("has_melon", RecipeProvider.conditionsFromItem(Items.MELON))
                .offerTo(exporter);

        this.createConvertToSawDustToolRecipes(exporter);
    }

    private void addModExclusive(RecipeExporter exporter) {

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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.COAL)
                .input(BwtItems.coalDustItem)
                .input(BwtItems.coalDustItem)
                .criterion("has_coal_dust", conditionsFromItem(BwtItems.coalDustItem))
                .offerTo(exporter, ID.ofDS("coal_from_coal_dust"));

    }

    private void createConvertToSawDustToolRecipes(RecipeExporter exporter) {
        convertToSawdustRecipeBuilder(exporter, net.ivangeevo.self_sustainable.item.ModItems.FIRESTARTER_STICKS, "has_firestarter_sticks");
        convertToSawdustRecipeBuilder(exporter, net.ivangeevo.self_sustainable.item.ModItems.FIRESTARTER_BOW, "has_firestarter_bow");
        convertToSawdustRecipeBuilder(exporter, BTWR_Items.CLUB_WOOD, "has_club_wood");
    }

    private void convertToSawdustRecipeBuilder(RecipeExporter exporter, Item tool, String criterion) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, BwtItems.sawDustItem)
                .input(tool)
                .criterion(criterion, conditionsFromItem(tool))
                .offerTo(exporter, ID.ofDS("saw_dust_from_converting_" + extractName(tool)));
    }

}
