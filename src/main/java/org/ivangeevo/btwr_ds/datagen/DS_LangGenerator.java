package org.ivangeevo.btwr_ds.datagen;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public class DS_LangGenerator extends FabricLanguageProvider {

    public DS_LangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(BTWRDS_Items.BRIMSTONE, "Brimstone");
        translationBuilder.add(BTWRDS_Items.ENDER_SLAG, "Ender Slag");
        translationBuilder.add(BTWRDS_Items.SOUL_FLUX, "Soul Flux");
        translationBuilder.add(BTWRDS_Items.BARK_BLOOD_WOOD, "Blood Wood Bark");
        translationBuilder.add(BTWRDS_Items.ELEMENT, "Element");
        translationBuilder.add(BTWRDS_Items.REDSTONE_LATCH, "Redstone Latch");
    }


}
