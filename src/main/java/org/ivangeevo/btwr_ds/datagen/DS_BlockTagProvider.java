package org.ivangeevo.btwr_ds.datagen;

import btwr.btwr_sl.tag.BTWRConventionalTags;
import com.bwt.blocks.BwtBlocks;
import com.bwt.tags.BwtBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DS_BlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public DS_BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BwtBlockTags.CROPS_CAN_PLANT_ON)
                .forceAddTag(BTWRConventionalTags.Blocks.FARMLAND_BLOCKS);

        getOrCreateTagBuilder(BTWRConventionalTags.Blocks.WOODEN_MISC_BLOCKS)
                .add(BwtBlocks.hopperBlock)
                .add(BwtBlocks.gearBoxBlock);

    }
}
