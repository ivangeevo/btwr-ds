package org.btwr.data_suite.datagen;

import com.bwt.blocks.BwtBlocks;
import com.bwt.tags.BwtBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import org.btwr.shared_library.api.tag.BTWRConventionalTags;

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
                .add(BwtBlocks.gearBoxBlock);

        //this.getOrCreateTagBuilder(BTWRConventionalTags.Blocks.TURNED_TO_FALLING_BLOCKS)
                //.add(Blocks.RAW_COPPER_BLOCK)
                //.add(Blocks.RAW_IRON_BLOCK)
                //.add(Blocks.RAW_GOLD_BLOCK)
        //.add(Blocks.NETHERRACK)
        ;
    }
}