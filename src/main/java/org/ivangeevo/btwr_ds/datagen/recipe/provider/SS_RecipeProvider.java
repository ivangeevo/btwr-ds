package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import com.bwt.items.BwtItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.ivangeevo.self_sustainable.item.ModItems;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;


public class SS_RecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public SS_RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }

    @Override
    public void generate(RecipeExporter exporter) {
        this.generateShaped(exporter);
        this.generateShapeless(exporter);
    }

    private void generateShapeless(RecipeExporter exporter) {
    }


    private void generateShaped(RecipeExporter exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CRUDE_TORCH_UNLIT, 1)
                .input('C', ItemTags.COALS)
                .input('I', Items.STICK)
                .pattern("C")
                .pattern("I")
                .criterion("has_coal", conditionsFromTag(ItemTags.COALS))
                .offerTo(exporter, ID.ofSS("crude_torch_unlit"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.TORCH_UNLIT, 1)
                .input('C', BwtItems.nethercoalItem)
                .input('I', Items.STICK)
                .pattern("C")
                .pattern("I")
                .criterion("has_nethercoal", conditionsFromItem(BwtItems.nethercoalItem))
                .offerTo(exporter, ID.ofSS("torch_unlit"));
    }



}
