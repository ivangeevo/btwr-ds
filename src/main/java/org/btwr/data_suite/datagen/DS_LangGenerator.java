package org.btwr.data_suite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.data_suite.BTWRDSMod;
import org.btwr.data_suite.item.BTWRDS_Items;

import java.util.concurrent.CompletableFuture;

public class DS_LangGenerator extends FabricLanguageProvider {

    public DS_LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder tb) {
        this.generateItemTranslations(tb);
        this.generateStatusEffectsTranslations(tb);
    }

    private void generateItemTranslations(TranslationBuilder tb) {
        tb.add(BTWRDS_Items.BRIMSTONE, "Brimstone");
        tb.add(BTWRDS_Items.ENDER_SLAG, "Ender Slag");
        tb.add(BTWRDS_Items.SOUL_FLUX, "Soul Flux");
        tb.add(BTWRDS_Items.BARK_BLOOD_WOOD, "Blood Wood Bark");
        tb.add(BTWRDS_Items.ELEMENT, "Element");
        tb.add(BTWRDS_Items.REDSTONE_LATCH, "Redstone Latch");

        // Potions
        //tb.add(potionItem("potion", "reduced_hunger"), "Potion of Reduced Hunger");
        //tb.add(potionItem("splash_potion", "reduced_hunger"), "Splash Potion of Reduced Hunger");
        //tb.add(potionItem("lingering_potion","reduced_hunger"), "Lingering Potion of Reduced Hunger");
    }

    private void generateStatusEffectsTranslations(TranslationBuilder tb) {
        tb.add(statusEffect("fortune"), "Fortune");
        tb.add(statusEffect("looting"), "Looting");
        tb.add(statusEffect("true_sight"), "True Sight");

        //tb.add(statusEffect("reduced_hunger"), "Reduced Hunger");
    }

    private String statusEffect(String effect) {
        return "effect." + BTWRDSMod.MOD_ID + "." + effect;
    }

    // Potion type can be "potion", "splash_potion", "lingering_potion
    private String potionItem(String potionType, String name) {
        return "item.minecraft." + potionType + "." + name;
    }
}