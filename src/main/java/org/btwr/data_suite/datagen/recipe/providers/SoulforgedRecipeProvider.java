package org.btwr.data_suite.datagen.recipe.providers;

import com.bwt.blocks.BwtBlocks;
import com.bwt.items.BwtItems;
import com.bwt.recipes.soul_forge.SoulForgeShapedRecipe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.core.block.BTWR_Blocks;
import org.btwr.core.item.BTWR_Items;
import org.btwr.shared_library.util.utils.IdUtils;
import org.btwr.shared_library.util.utils.RecipeUtils;

import java.util.concurrent.CompletableFuture;

import static org.btwr.tough_environment.item.ModItems.NETHERITE_NUGGET;
import static org.btwr.tough_environment.item.ModItems.STONE_BRICK;

public class SoulforgedRecipeProvider extends FabricRecipeProvider implements RecipeUtils {
    public SoulforgedRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        // TODO: Add soulforged recipe for the chopping block when it's added to BTWR: Core
        /**
         SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BTWR_Blocks.CHOPPING_BLOCK)
         .input('B', BTWR_Items.STONE_BRICK)
         .pattern("B  B")
         .pattern("B  B")
         .pattern("BBBB")
         .criterion("has_stone_brick", conditionsFromItem(BTWR_Items.STONE_BRICK))
         .offerTo(exporter, ID.ofBTWR("chopping_block"));
         **/

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.MISC, BwtItems.broadheadItem, 6)
                .input('B', NETHERITE_NUGGET)
                .pattern(" B  ")
                .pattern(" B  ")
                .pattern("BBB ")
                .pattern(" B  ")
                .criterion("has_netherite_nugget", conditionsFromItem(NETHERITE_NUGGET))
                .offerTo(exporter, IdUtils.ofBWT("broadhead"));

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.blockDispenserBlock)
                .input('B', STONE_BRICK)
                .input('M', Blocks.MOSSY_COBBLESTONE)
                .input('U', BwtItems.soulUrnItem)
                .input('T', Items.REDSTONE_TORCH)
                .input('R', Items.REDSTONE)
                .pattern("MMMM")
                .pattern("MUUM")
                .pattern("BTTB")
                .pattern("BRRB")
                .criterion("has_soul_urn", conditionsFromItem(BwtItems.soulUrnItem))
                .offerTo(exporter, IdUtils.ofBWT("block_dispenser"));

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.REDSTONE, BwtBlocks.buddyBlock)
                .input('B', STONE_BRICK)
                .input('E', BwtItems.redstoneEyeItem)
                .input('T', Items.REDSTONE_TORCH)
                .pattern("BBEB")
                .pattern("ETTB")
                .pattern("BTTE")
                .pattern("BEBB")
                .criterion("has_soul_urn", conditionsFromItem(BwtItems.soulUrnItem))
                .offerTo(exporter, IdUtils.ofBWT("buddy_block"));

        //TODO: Make the creeper oyster block and the spider eye block a packing recipe instead when piston packing gets fixed
        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Blocks.CREEPER_OYSTER_BLOCK)
                .input('O', BTWR_Items.CREEPER_OYSTERS)
                .pattern("OOOO")
                .pattern("OOOO")
                .pattern("OOOO")
                .pattern("OOOO")
                .criterion(hasItem(BTWR_Items.CREEPER_OYSTERS), conditionsFromItem(BTWR_Items.CREEPER_OYSTERS))
                .offerTo(exporter, IdUtils.ofDS("creeper_oyster_block"));

        SoulForgeShapedRecipe.JsonBuilder.create(RecipeCategory.MISC, BTWR_Blocks.SPIDER_EYE_BLOCK)
                .input('O', Items.SPIDER_EYE)
                .pattern("OOOO")
                .pattern("OOOO")
                .pattern("OOOO")
                .pattern("OOOO")
                .criterion(hasItem(Items.SPIDER_EYE), conditionsFromItem(Items.SPIDER_EYE))
                .offerTo(exporter, IdUtils.ofDS("spider_eye_block"));
    }
}