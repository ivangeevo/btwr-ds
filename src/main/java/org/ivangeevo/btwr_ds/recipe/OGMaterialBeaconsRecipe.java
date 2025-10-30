package org.ivangeevo.btwr_ds.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import issame.material_beacons.MaterialBeacons;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.ivangeevo.btwr_ds.data.BeaconDataRecipeInput;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OGMaterialBeaconsRecipe implements Recipe<BeaconDataRecipeInput> {

    protected final String group;
    protected final CraftingRecipeCategory category;
    protected final List<BlockOrTagIngredient> bases;
    protected final List<List<StatusEffectIngredient>> powers;

    public OGMaterialBeaconsRecipe(
            String group,
            CraftingRecipeCategory category,
            List<BlockOrTagIngredient> bases,
            List<List<StatusEffectIngredient>> powers
    ) {
        this.group = group;
        this.category = category;
        this.bases = bases;
        this.powers = powers;
    }

    @Override public ItemStack createIcon() {
        return new ItemStack(Blocks.BEACON);
    }

    @Override public RecipeSerializer<?> getSerializer() {
        return BTWRDSRecipes.MATERIAL_BEACONS_RECIPE_SERIALIZER;
    }

    @Override public boolean matches(BeaconDataRecipeInput input, World world) {
        return false;
    }

    @Override public boolean fits(int width, int height) {
        return true;
    }

    @Override public String getGroup() {
        return this.group;
    }

    @Override public RecipeType<?> getType() {
        return BTWRDSRecipes.MATERIAL_BEACONS_RECIPE_TYPE;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return Recipe.super.isIgnoredInRecipeBook();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public ItemStack craft(BeaconDataRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return ItemStack.EMPTY;
    }


    public List<BlockOrTagIngredient> getBeaconBases() {
        return bases;
    }

    public List<List<StatusEffectIngredient>> getBeaconPowers() {
        return powers;
    }

    public static class Serializer implements RecipeSerializer<OGMaterialBeaconsRecipe> {

        protected static final MapCodec<OGMaterialBeaconsRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance->instance.group(
                        Codec.STRING
                                .optionalFieldOf("group", "")
                                .forGetter(recipe -> recipe.group),
                        CraftingRecipeCategory.CODEC
                                .fieldOf("category")
                                .orElse(CraftingRecipeCategory.MISC)
                                .forGetter(recipe -> recipe.category),
                        BlockOrTagIngredient.Serializer.createCodec().codec()
                                .listOf()
                                .fieldOf("bases")
                                .forGetter(OGMaterialBeaconsRecipe::getBeaconBases),
                        StatusEffectIngredient.Serializer.createCodec().codec()
                                .listOf()
                                .listOf()
                                .fieldOf("powers")
                                .forGetter(OGMaterialBeaconsRecipe::getBeaconPowers)
                ).apply(instance, OGMaterialBeaconsRecipe::new)
        );

        public static final PacketCodec<RegistryByteBuf, OGMaterialBeaconsRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                Serializer::write, Serializer::read
        );

        public Serializer() {}

        @Override
        public MapCodec<OGMaterialBeaconsRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, OGMaterialBeaconsRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        protected static OGMaterialBeaconsRecipe read(RegistryByteBuf buf) {
            String group = buf.readString();
            CraftingRecipeCategory category = buf.readEnumConstant(CraftingRecipeCategory.class);
            List<BlockOrTagIngredient> bases = DefaultedList.of();
            bases.replaceAll(ignored -> BlockOrTagIngredient.Serializer.read(buf));
            List<List<StatusEffectIngredient>> powers = DefaultedList.of();
            powers.replaceAll(ignored -> Collections.singletonList(StatusEffectIngredient.Serializer.read(buf)));
            return new OGMaterialBeaconsRecipe(group, category, bases, powers);
        }

        protected static void write(RegistryByteBuf buf, OGMaterialBeaconsRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            for (BlockOrTagIngredient ingredient : recipe.getBeaconBases()) {
                BlockOrTagIngredient.Serializer.write(buf, ingredient);
            }
            for (List<StatusEffectIngredient> ingredient : recipe.getBeaconPowers()) {
                for (StatusEffectIngredient ingredient1 : ingredient) {
                    StatusEffectIngredient.Serializer.write(buf, ingredient1);
                }
            }
        }
    }

    public static class JsonBuilder implements CraftingRecipeJsonBuilder {
        protected CraftingRecipeCategory category = CraftingRecipeCategory.MISC;
        protected List<BlockOrTagIngredient> blockIngredients;
        protected List<List<StatusEffectIngredient>> powers;

        protected String beaconType;
        @Nullable
        protected String group;

        public static JsonBuilder create(Block input) {
            JsonBuilder obj = new JsonBuilder();
            DefaultedList<BlockOrTagIngredient> ingredients = DefaultedList.of();
            ingredients.replaceAll(ignored -> BlockOrTagIngredient.ofBlock(input));
            obj.blockIngredients = ingredients;
            obj.beaconType = Registries.BLOCK.getId(input).getPath();
            return obj;
        }

        public static JsonBuilder create(TagKey<Block> inputTag) {
            JsonBuilder obj = new JsonBuilder();
            DefaultedList<BlockOrTagIngredient> ingredients = DefaultedList.of();
            ingredients.replaceAll(ignored -> BlockOrTagIngredient.ofTag(inputTag));
            obj.blockIngredients = ingredients;
            obj.beaconType = inputTag.id().getPath();
            return obj;
        }

        public JsonBuilder addPowers(List<List<StatusEffectIngredient>> powers) {
            this.powers = powers;
            return this;
        }

        @Override
        public JsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
            return this;
        }

        @Override
        public JsonBuilder group(@Nullable String string) {
            return this;
        }


        @Override
        public Item getOutputItem() {
            return ItemStack.EMPTY.getItem();
        }

        @Override
        public void offerTo(RecipeExporter exporter) {
            this.offerTo(exporter, Identifier.of(MaterialBeacons.MOD_ID, beaconType));
        }

        @Override
        public void offerTo(RecipeExporter exporter, Identifier recipeId) {
            Advancement.Builder advancementBuilder = exporter.getAdvancementBuilder()
                    .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                    .rewards(AdvancementRewards.Builder.recipe(recipeId))
                    .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
            OGMaterialBeaconsRecipe kilnRecipe = new OGMaterialBeaconsRecipe(
                    Objects.requireNonNullElse(this.group, ""),
                    this.category,
                    this.blockIngredients,
                    this.powers
            );
            exporter.accept(recipeId, kilnRecipe, advancementBuilder.build(recipeId.withPrefixedPath("beacon/")));
        }
    }
}
