package org.ivangeevo.btwr_ds.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;

import java.util.concurrent.CompletableFuture;

public class DS_LangGenerator extends FabricLanguageProvider {

    public DS_LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        this.overrideForVanilla(translationBuilder);
        this.generateForMod(translationBuilder);
    }

    private void overrideForVanilla(TranslationBuilder tb) {
        tb.add(Items.MUSHROOM_STEW, "Cream of Mushroom");
    }

    private void generateForMod(TranslationBuilder tb) {
        tb.add(BTWRDS_Items.BRIMSTONE, "Brimstone");
        tb.add(BTWRDS_Items.ENDER_SLAG, "Ender Slag");
        tb.add(BTWRDS_Items.SOUL_FLUX, "Soul Flux");
        tb.add(BTWRDS_Items.BARK_BLOOD_WOOD, "Blood Wood Bark");
        tb.add(BTWRDS_Items.ELEMENT, "Element");
        tb.add(BTWRDS_Items.REDSTONE_LATCH, "Redstone Latch");
    }


}
