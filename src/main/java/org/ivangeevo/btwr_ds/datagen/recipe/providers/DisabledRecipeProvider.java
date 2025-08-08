package org.ivangeevo.btwr_ds.datagen.recipe.providers;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.ivangeevo.self_sustainable.SelfSustainableMod;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class DisabledRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public DisabledRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private static final String foc = "_from_oven_cooking";

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};

    @Override
    public void generate(RecipeExporter exporter) {

        // temporarily disable the BWT HCT millstone recipe to not clash with the BWT one
        disableRecipe(exporter, "bwt_hct", "modern_millstone");

        this.removeForVanilla(exporter);
        this.removeForTE(exporter);
        this.removeForBWT(exporter);
        this.removeForBTWR(exporter);
        this.removeForVegehenna(exporter);
        this.removeForSS(exporter);

        /** Animageddon recipes to remove **/
        //disableAG(exporter, "gunpowder");
        disableRecipe(exporter, "animageddon", "gunpowder");

    }

    protected void removeForVanilla(RecipeExporter exporter) {
        /** Vanilla recipes to remove **/
        // Remove item recipes
        disableVanilla(exporter, "bone_meal");
        disableVanilla(exporter, "brown_dye");
        disableVanilla(exporter, "red_dye_from_beetroot");
        disableVanilla(exporter, "torch");
        disableVanilla(exporter, "blaze_powder");
        disableVanilla(exporter, "mushroom_stew");
        disableVanilla(exporter, "bone_meal_from_bone_block");
        disableVanilla(exporter, "melon_slice");


        disableVanilla(exporter, "bread_from_smoking");

        // Food item recipes
        disableVanilla(exporter, "bread");
        disableVanilla(exporter, "cookie");
        disableVanilla(exporter, "sugar_from_sugar_cane");

        // cake recipe is only cookable in a kiln (and eventually pumpkin pie when made placeable)
        disableVanilla(exporter,"cake_from_smoking");
        //disableVanilla(exporter,"pumpkin_pie_from_smoking");


        // Remove blocks recipes
        disableVanilla(exporter, "crafting_table");
        //disableVanilla(exporter, "chest");
        disableVanilla(exporter, "furnace");
        disableVanilla(exporter, "blast_furnace");
        disableVanilla(exporter, "smoker");


        // Remove tool recipes
        String[] woodenToolsToRemove = new String[]{"sword", "pickaxe", "axe", "shovel", "hoe"};
        for (String tool : woodenToolsToRemove) {
            disableVanilla(exporter, "wooden_" + tool);
        }

        disableVanilla(exporter, "stone_sword");
        disableVanilla(exporter, "stone_hoe");

        // Remove cooking recipes
        disableVanilla(exporter, "charcoal");

        // Foods
        disableVanilla(exporter, "pumpkin_pie");

        this.disableVanillaOreCookingRecipes(exporter);
        // Remove vanilla 'reclaim' recipes for tools/metal armor
        disableVanilla(exporter, "iron_nugget_from_smelting");
        disableVanilla(exporter, "gold_nugget_from_smelting");

        // Remove the ability to repair items by combining them
        disableVanilla(exporter, "repair_item");

    }

    protected void removeForTE(RecipeExporter exporter) {
        disableTE(exporter, "furnace");
        disableTE(exporter, "white_cobblestone");
        disableTE(exporter, "white_cobblestone_from_blasting");
        disableTE(exporter, "nether_sludge");
    }

    protected void removeForBWT(RecipeExporter exporter) {
        // Food
        disableBWT(exporter, "bread");
        disableBWT(exporter, "baked_potato_from_cauldron");

        disableBWT(exporter, "fried_egg_from_campfire_cooking");

        // Millstone
        disableBWT(exporter, "brown_dye_from_milling_cocoa_beans");

        // Cauldron
        disableBWT(exporter, "tanned_leather_from_cauldron");
        disableBWT(exporter, "nether_sludge_from_cauldron");

        // Crucible
        disableBWT(exporter, "redstone_synthesis_from_gold_ingots");
        disableBWT(exporter, "redstone_synthesis_from_gold_nuggets");

        // Items
        disableBWT(exporter, "torch_from_nether_coal");

        // Blocks

        // Removing High efficiency button recipes
        for (String woodType : vanillaWoodTypes) {
            disableBWT(exporter, "he_" + woodType + "_button");
        }
        disableBWT(exporter, "he_blood_wood_button");

        // Removing High efficiency pressure plate recipes
        for (String woodType : vanillaWoodTypes)
        {
            disableBWT(exporter, "he_" + woodType + "_pressure_plate");
        }

        disableBWT(exporter, "he_blood_wood_pressure_plate");

        disableBWT(exporter, "smelt_flint_and_steel_in_crucible");

        // Remove kiln recipes
        String[] oresToRemove = new String[]{"coal_ores", "diamond_ores", "emerald_ores", "lapis_ores", "redstone_ores"};
        for (String oreType : oresToRemove) {
            disableBWT(exporter, "kiln_cook_" + oreType);
        }

    }

    protected void removeForBTWR(RecipeExporter exporter) {
        disableBTWR(exporter, "egg_scrambled_cooked");
        disableBTWR(exporter, "egg_scrambled_cooked_from_smoking");
        disableBTWR(exporter, "egg_scrambled_cooked_from_campfire_cooking");
        disableBTWR(exporter, "mushroom_omelette_cooked");
        disableBTWR(exporter, "mushroom_omelette_cooked_from_smoking");
        disableBTWR(exporter, "mushroom_omelette_cooked_from_campfire_cooking");
        disableBTWR(exporter, "chicken_soup");
        disableBTWR(exporter, "hearty_stew");

        // Remove leather recipes
        String[] leathersToRemove = new String[]{"scoured", "tanned", "scoured_cut", "tanned_cut"};
        for (String leatherType : leathersToRemove) {
            disableBTWR(exporter, "leather_" + leatherType);
        }

    }

    protected void removeForVegehenna(RecipeExporter exporter) {
        disableVG(exporter, "flour");
        disableVG(exporter, "cocoa_powder");
        disableVG(exporter, "chocolate");
        disableVG(exporter, "cooked_carrot_from_smelting");
    }

    protected void removeForSS(RecipeExporter exporter) {
        disableRecipe(exporter, SelfSustainableMod.MOD_ID, "copper_ingot" + foc);
        disableRecipe(exporter, SelfSustainableMod.MOD_ID, "iron_ingot" + foc);
        disableRecipe(exporter, SelfSustainableMod.MOD_ID, "gold_ingot" + foc);
    }

    private void disableVanillaOreCookingRecipes(RecipeExporter exporter) {
        // 2 recipes for each ore type
        // 3 metallic ore types
        // make a method that handles all 3 recipe types and call that 2 times
        disableVanilla(exporter, "iron_ingot_from_smelting_iron_ore");
        disableVanilla(exporter, "iron_ingot_from_smelting_deepslate_iron_ore");
        disableVanilla(exporter, "iron_ingot_from_smelting_raw_iron");
        disableVanilla(exporter, "iron_ingot_from_blasting_iron_ore");
        disableVanilla(exporter, "iron_ingot_from_blasting_deepslate_iron_ore");
        disableVanilla(exporter, "iron_ingot_from_blasting_raw_iron");

        disableVanilla(exporter, "gold_ingot_from_smelting_gold_ore");
        disableVanilla(exporter, "gold_ingot_from_smelting_deepslate_gold_ore");
        disableVanilla(exporter, "gold_ingot_from_smelting_nether_gold_ore");
        disableVanilla(exporter, "gold_ingot_from_smelting_raw_gold");
        disableVanilla(exporter, "gold_ingot_from_blasting_gold_ore");
        disableVanilla(exporter, "gold_ingot_from_blasting_deepslate_gold_ore");
        disableVanilla(exporter, "gold_ingot_from_blasting_nether_gold_ore");
        disableVanilla(exporter, "gold_ingot_from_blasting_raw_gold");

    }

    private void disableOreCookingRecipe(RecipeExporter exporter, String oreType) {
        String[] smeltingRecipeTypes = {"_from_smelting", "_from_blasting"};
        String[] oreTypes = {"deepslate"};

    }


    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }
}
