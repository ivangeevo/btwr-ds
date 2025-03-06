package org.ivangeevo.btwr_ds.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.ivangeevo.btwr_ds.BTWRDSMod;
import org.jetbrains.annotations.Nullable;

public class ReplaceCraftingTableProcessor extends StructureProcessor {

    public static final ReplaceCraftingTableProcessor INSTANCE = new ReplaceCraftingTableProcessor();
    public static final MapCodec<ReplaceCraftingTableProcessor> CODEC = MapCodec.unit(ReplaceCraftingTableProcessor::new);

    public static final StructureProcessorType<ReplaceCraftingTableProcessor> TYPE =
            () -> CODEC;

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot,
                                                        StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo currentBlockInfo,
                                                        StructurePlacementData data) {
        // Replace crafting tables with air
        if (currentBlockInfo.state().isOf(Blocks.CRAFTING_TABLE)) {
            return new StructureTemplate.StructureBlockInfo(currentBlockInfo.pos(), Blocks.AIR.getDefaultState(), currentBlockInfo.nbt());
        }
        return currentBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }

    // Register your processor during mod initialization
    public static void register() {
        Registry.register(Registries.STRUCTURE_PROCESSOR, Identifier.of(BTWRDSMod.MOD_ID, "replace_crafting_table"), TYPE);
    }
}
