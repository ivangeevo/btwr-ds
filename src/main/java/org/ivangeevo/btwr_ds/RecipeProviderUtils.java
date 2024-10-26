package org.ivangeevo.btwr_ds;

import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/** Helper interface containing extra shared/helper methods for all classes that implement it **/
public interface RecipeProviderUtils
{
    /** Call this to remove a recipe. The id must contain both the namespace and the item name "path" **/
    default void removeRecipe(RecipeExporter exporter, Identifier id)
    {
        recipeRemover().offerTo(exporter, id);
    }

    /** A recipe that cannot be obtained by normal means. Doesn't really remove it, but makes it unobtainable **/
    default ShapelessRecipeJsonBuilder recipeRemover()
    {
        return ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Blocks.BEDROCK)
                .input(Blocks.BEDROCK)
                .criterion("has_bedrock", RecipeProvider.conditionsFromItem(Blocks.BEDROCK));
    }

    default Item grabRaw(Identifier id)
{
    return Registries.ITEM.get(id);
}

    default Item grabRaw(String itemID)
    {
        return Registries.ITEM.get(Identifier.ofVanilla(itemID));
    }

    default Item grabRaw(String namespace, String itemID) {
        return Registries.ITEM.get(namespace == null ? Identifier.ofVanilla(itemID) : Identifier.of(namespace, itemID) );
    }
}
