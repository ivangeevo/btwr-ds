package org.ivangeevo.btwr_ds.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;

import java.util.concurrent.CompletableFuture;

public class DS_LangGenerator extends FabricLanguageProvider {

    public DS_LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(BTWRDS_Items.COPPER_NUGGET, "Copper Nugget");
        translationBuilder.add(BTWRDS_Items.BRIMSTONE, "Brimstone");
        translationBuilder.add(BTWRDS_Items.ENDER_SLAG, "Ender Slag");
        translationBuilder.add(BTWRDS_Items.SOUL_FLUX, "Soul Flux");
        translationBuilder.add(BTWRDS_Items.BARK_BLOOD_WOOD, "Blood Wood Bark");
    }

}
