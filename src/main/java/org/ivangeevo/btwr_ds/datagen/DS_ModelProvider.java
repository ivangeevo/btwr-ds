package org.ivangeevo.btwr_ds.datagen;

import com.bwt.blocks.*;
import com.bwt.blocks.abstract_cooking_pot.AbstractCookingPotBlock;
import com.bwt.blocks.lens.LensBeamBlock;
import com.bwt.blocks.turntable.TurntableBlock;
import com.bwt.items.BwtItems;
import com.bwt.utils.DyeUtils;
import com.bwt.utils.Id;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.data.client.*;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.ivangeevo.btwr_ds.item.BTWRDS_Items;

import java.util.List;
import java.util.Optional;

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
