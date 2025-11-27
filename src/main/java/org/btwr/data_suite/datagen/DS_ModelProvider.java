package org.btwr.data_suite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import org.btwr.data_suite.item.BTWRDS_Items;

public class DS_ModelProvider extends FabricModelProvider {

    public DS_ModelProvider(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(BTWRDS_Items.BARK_BLOOD_WOOD, Models.GENERATED);
        itemModelGenerator.register(BTWRDS_Items.ENDER_SLAG, Models.GENERATED);
        itemModelGenerator.register(BTWRDS_Items.SOUL_FLUX, Models.GENERATED);
        itemModelGenerator.register(BTWRDS_Items.BRIMSTONE, Models.GENERATED);
        itemModelGenerator.register(BTWRDS_Items.ELEMENT, Models.GENERATED);
        itemModelGenerator.register(BTWRDS_Items.REDSTONE_LATCH, Models.GENERATED);
    }

}