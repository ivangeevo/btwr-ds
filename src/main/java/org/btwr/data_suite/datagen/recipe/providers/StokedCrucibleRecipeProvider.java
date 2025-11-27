package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.cooking_pots.StokedCrucibleRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.core.item.BTWR_Items;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.shared_library.util.utils.RecipeUtils;
import org.btwr.data_suite.item.BTWRDS_Items;
import org.tough_environment.block.ModBlocks;
import org.tough_environment.item.ModItems;

import java.util.concurrent.CompletableFuture;

import static org.tough_environment.item.ModItems.CHISEL_DIAMOND;
import static org.tough_environment.item.ModItems.CHISEL_IRON;

public class StokedCrucibleRecipeProvider extends FabricRecipeProvider implements RecipeUtils {

    public StokedCrucibleRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        this.generateResmeltingRecipes(exporter);

        StokedCrucibleRecipe.JsonBuilder.create().result(ModBlocks.WHITE_STONE.asItem())
                .ingredient(ModBlocks.WHITE_COBBLESTONE.asItem())
                .criterion("has_white_cobblestone", conditionsFromItem(ModBlocks.WHITE_COBBLESTONE.asItem()))
                .offerTo(exporter, IdUtils.ofDS("white_stone_from_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(BTWR_Items.DIAMOND_INGOT,2)
                .ingredient(BTWR_Items.DIAMOND_SHEARS)
                .criterion("has_diamond_shears", conditionsFromItem(BTWR_Items.DIAMOND_SHEARS))
                .offerTo(exporter, IdUtils.ofDS("smelt_diamond_shears_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(BTWR_Items.DIAMOND_INGOT)
                .ingredient(CHISEL_DIAMOND)
                .criterion("has_chisel_diamond", conditionsFromItem(CHISEL_DIAMOND))
                .offerTo(exporter, IdUtils.ofDS("smelt_chisel_diamond_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(Items.IRON_NUGGET)
                .ingredient(CHISEL_IRON)
                .criterion("has_chisel_iron", conditionsFromItem(CHISEL_IRON))
                .offerTo(exporter, IdUtils.ofDS("smelt_chisel_iron_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(Items.IRON_NUGGET,3)
                .ingredient(Items.SHIELD)
                .criterion("has_shield", conditionsFromItem(Items.SHIELD))
                .offerTo(exporter, IdUtils.ofDS("smelt_shield_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create().result(Items.GOLD_NUGGET,2)
                .ingredient(BTWRDS_Items.REDSTONE_LATCH)
                .criterion("has_redstone_latch", conditionsFromItem(BTWRDS_Items.REDSTONE_LATCH))
                .offerTo(exporter, IdUtils.ofDS("smelt_redstone_latch_in_crucible"));

        StokedCrucibleRecipe.JsonBuilder.create()
                .ingredient(Items.IRON_INGOT)
                .ingredient(Items.GOLD_INGOT)
                .ingredient(BwtItems.coalDustItem)
                .ingredient(BwtItems.soulUrnItem)
                .ingredient(BTWRDS_Items.SOUL_FLUX)
                .result(Items.NETHERITE_INGOT)
                .markDefault()
                .offerTo(exporter, IdUtils.ofBWT("netherite_ingot_smelting"));

        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NETHERITE_SCRAP, 4)
                .ingredient(Items.GOLD_INGOT, 4)
                .ingredient(BTWRDS_Items.SOUL_FLUX)
                .result(Items.NETHERITE_INGOT)
                .offerTo(exporter, IdUtils.ofBWT("netherite_ingot_from_scrap"));
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
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.RAIL, 8).result(Items.IRON_NUGGET, 18).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.POWERED_RAIL).result(Items.GOLD_NUGGET, 6).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.DETECTOR_RAIL).result(Items.IRON_NUGGET, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.IRON_DOOR).result(Items.IRON_INGOT, 4).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(BwtBlocks.stoneDetectorRailBlock.asItem()).result(Items.IRON_NUGGET, 1).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(BwtBlocks.obsidianDetectorRailBlock.asItem()).result(Items.IRON_NUGGET, 1).result(ModItems.NETHERITE_NUGGET, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.COMPASS).result(Items.IRON_NUGGET, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.CLOCK).result(Items.GOLD_NUGGET, 3).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.MINECART).result(Items.IRON_NUGGET, 30).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.SHEARS).result(Items.IRON_NUGGET, 12).offerTo(exporter);
        StokedCrucibleRecipe.JsonBuilder.create().ingredient(Items.NOTE_BLOCK).result(Items.GOLD_NUGGET, 2).offerTo(exporter);
    }

}