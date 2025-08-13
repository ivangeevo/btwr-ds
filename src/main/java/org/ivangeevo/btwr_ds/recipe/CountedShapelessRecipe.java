package org.ivangeevo.btwr_ds.recipe;

import com.bwt.recipes.IngredientWithCount;
import com.mojang.serialization.MapCodec;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountedShapelessRecipe implements CraftingRecipe {
	private final String group;
	private final CraftingRecipeCategory category;
	private final ItemStack result;
	private final DefaultedList<IngredientWithCount> ingredients;

	public CountedShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<IngredientWithCount> ingredients) {
		this.group = group;
		this.category = category;
		this.result = result;
		this.ingredients = ingredients;
	}

	@Override
	public boolean matches(CraftingRecipeInput input, World world) {
		List<IngredientWithCount> remaining = new ArrayList<>(ingredients);

		for (int i = 0; i < input.getSize(); i++) {
			ItemStack stack = input.getStackInSlot(i);
			if (stack.isEmpty()) continue;

			boolean matched = false;
			for (Iterator<IngredientWithCount> iter = remaining.iterator(); iter.hasNext(); ) {
				IngredientWithCount required = iter.next();
				if (required.test(stack)) {
					iter.remove();
					matched = true;
					break;
				}
			}
			if (!matched) return false;
		}

		return remaining.isEmpty();
	}

	@Override
	public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
		return result.copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return width * height >= ingredients.size();
	}

	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registries) {
		return result;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		// Return basic Ingredient list for REI / book compatibility
		DefaultedList<Ingredient> flat = DefaultedList.ofSize(ingredients.size(), Ingredient.EMPTY);
		for (int i = 0; i < ingredients.size(); i++) {
			flat.set(i, ingredients.get(i).ingredient());
		}
		return flat;
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public CraftingRecipeCategory getCategory() {
		return category;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return BTWRDSRecipes.SHAPELESS_WITH_COUNTS_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<CountedShapelessRecipe> {
		/**
		private static final MapCodec<ShapelessRecipeWithCounts> CODEC = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
								Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
								CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
								ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
						IngredientWithCount.Serializer.DISALLOW_EMPTY_CODEC
								.codec()      // convert MapCodec<IngredientWithCount> to Codec<IngredientWithCount>
								.listOf()     // now you can get Codec<List<IngredientWithCount>>
								.fieldOf("ingredients")
								.flatXmap(
										ingredients -> {
											List<IngredientWithCount> filtered = ingredients.stream()
													.filter(iwc -> !iwc.ingredient().isEmpty())
													.toList();

											if (filtered.isEmpty()) {
												return DataResult.error(() -> "No ingredients for shapeless recipe");
											} else if (filtered.size() > 9) {
												return DataResult.error(() -> "Too many ingredients for shapeless recipe");
											} else {
												return DataResult.success(DefaultedList.<IngredientWithCount>copyOf(IngredientWithCount.EMPTY, filtered)
												);
											}
										},
										DataResult::success
								)
								.forGetter(recipe -> recipe.ingredients)

								.apply(instance, ShapelessRecipeWithCounts::new)
		);
		 **/

		public static final PacketCodec<RegistryByteBuf, CountedShapelessRecipe> PACKET_CODEC = PacketCodec.ofStatic(
				Serializer::write,
				Serializer::read
		);

		@Override
		public MapCodec<CountedShapelessRecipe> codec() {
			return null;
		}

		@Override
		public PacketCodec<RegistryByteBuf, CountedShapelessRecipe> packetCodec() {
			return PACKET_CODEC;
		}

		private static CountedShapelessRecipe read(RegistryByteBuf buf) {
			String group = buf.readString();
			CraftingRecipeCategory category = buf.readEnumConstant(CraftingRecipeCategory.class);
			int size = buf.readVarInt();

			DefaultedList<IngredientWithCount> ingredients = DefaultedList.ofSize(size, IngredientWithCount.EMPTY);
			for (int i = 0; i < size; i++) {
				ingredients.set(i, IngredientWithCount.Serializer.PACKET_CODEC.decode(buf));
			}

			ItemStack result = ItemStack.PACKET_CODEC.decode(buf);
			return new CountedShapelessRecipe(group, category, result, ingredients);
		}

		private static void write(RegistryByteBuf buf, CountedShapelessRecipe recipe) {
			buf.writeString(recipe.group);
			buf.writeEnumConstant(recipe.category);
			buf.writeVarInt(recipe.ingredients.size());

			for (IngredientWithCount ingredient : recipe.ingredients) {
				IngredientWithCount.Serializer.PACKET_CODEC.encode(buf, ingredient);
			}

			ItemStack.PACKET_CODEC.encode(buf, recipe.result);
		}
	}

}
