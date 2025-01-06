package org.ivangeevo.btwr_ds.datagen.recipe.provider;

import btwr.btwr_sl.lib.util.utils.RecipeProviderUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class DisabledRecipeProvider extends FabricRecipeProvider implements RecipeProviderUtils {

    public DisabledRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    private static final String[] vanillaWoodTypes = new String[]
            {"oak", "birch", "spruce", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo", "crimson", "warped"};

    @Override
    public void generate(RecipeExporter exporter) {
        this.removeForVanilla(exporter);
        this.removeForTE(exporter);
        this.removeForBWT(exporter);
        this.removeForBTWR(exporter);

        /** Vegehenna recipes to remove **/
        disableVG(exporter, "flour");

        /** Animageddon recipes to remove **/
        //disableAG(exporter, "gunpowder");
        disableRecipe(exporter, "animageddon", "gunpowder");

        /** Sturdy Trees recipes to remove **/

    }

    protected void removeForVanilla(RecipeExporter exporter) {
        /** Vanilla recipes to remove **/
        // Remove item recipes
        disableVanilla(exporter, "bone_meal");

        // Food item recipes
        disableVanilla(exporter, "bread");
        disableVanilla(exporter, "cookie");
        disableVanilla(exporter, "sugar_from_sugar_cane");

        disableVanilla(exporter, "blaze_powder");

        // Remove blocks recipes
        disableVanilla(exporter, "crafting_table");
        disableVanilla(exporter, "chest");
        disableVanilla(exporter, "furnace");


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

        disableBWT(exporter, "grate");
        disableBWT(exporter, "fried_egg_from_campfire_cooking");

        // Cauldron
        disableBWT(exporter, "tanned_leather_from_cauldron");
        disableBWT(exporter, "nether_sludge_from_cauldron");

        // Crucible
        disableBWT(exporter, "redstone_synthesis_from_gold_ingots");
        disableBWT(exporter, "redstone_synthesis_from_gold_nuggets");

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
        disableBTWR(exporter, "boiled_potato_from_smoking");
        disableBTWR(exporter, "egg_scrambled_cooked_from_campfire_cooking");
        disableBTWR(exporter, "mushroom_omelette_cooked_from_campfire_cooking");
        disableBTWR(exporter, "chicken_soup");
        disableBTWR(exporter, "hearty_stew");

        // Remove leather recipes
        String[] leathersToRemove = new String[]{"scoured", "tanned", "scoured_cut", "tanned_cut"};
        for (String leatherType : leathersToRemove) {
            disableBTWR(exporter, "leather_" + leatherType);
        }

    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
    }
}
