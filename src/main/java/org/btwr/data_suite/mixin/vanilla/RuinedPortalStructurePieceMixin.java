package org.btwr.data_suite.mixin.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RuinedPortalStructurePiece.class)
public abstract class RuinedPortalStructurePieceMixin extends SimpleStructurePiece {
    public RuinedPortalStructurePieceMixin(StructurePieceType type, int length, StructureTemplateManager structureTemplateManager, Identifier id, String template, StructurePlacementData placementData, BlockPos pos) {
        super(type, length, structureTemplateManager, id, template, placementData, pos);
    }

    /**
    @Inject(method = "createPlacementData(Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/structure/RuinedPortalStructurePiece$VerticalPlacement;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/RuinedPortalStructurePiece$Properties;)Lnet/minecraft/structure/StructurePlacementData;")
    private static void injectedCreatePlacementData(BlockMirror mirror, BlockRotation rotation, RuinedPortalStructurePiece.VerticalPlacement verticalPlacement, BlockPos pos, RuinedPortalStructurePiece.Properties properties, CallbackInfoReturnable<StructurePlacementData> cir) {
        BlockIgnoreStructureProcessor blockIgnoreStructureProcessor = properties.airPocket ? BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS : BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS;
        List<StructureProcessorRule> list = Lists.newArrayList();
        list.add(createReplacementRule(Blocks.GOLD_BLOCK, 0.3F, Blocks.AIR));
        list.add(createLavaReplacementRule(verticalPlacement, properties));
        if (!properties.cold) {
            list.add(createReplacementRule(Blocks.NETHERRACK, 0.07F, Blocks.MAGMA_BLOCK));
        }

        StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(rotation).setMirror(mirror).setPosition(pos).addProcessor(blockIgnoreStructureProcessor).addProcessor(new RuleStructureProcessor(list)).addProcessor(new BlockAgeStructureProcessor(properties.mossiness)).addProcessor(new ProtectedBlocksStructureProcessor(BlockTags.FEATURES_CANNOT_REPLACE)).addProcessor(new LavaSubmergedBlockStructureProcessor());
        if (properties.replaceWithBlackstone) {
            structurePlacementData.addProcessor(BlackstoneReplacementStructureProcessor.INSTANCE);
        }

        return structurePlacementData;

    }
    **/

    //@ModifyArg(method = "createPlacementData(Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/structure/RuinedPortalStructurePiece$VerticalPlacement;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/RuinedPortalStructurePiece$Properties;)Lnet/minecraft/structure/StructurePlacementData;", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/RuinedPortalStructurePiece;createReplacementRule(Lnet/minecraft/block/Block;FLnet/minecraft/block/Block;)Lnet/minecraft/structure/processor/StructureProcessorRule;", ordinal = 1), index = 0)
    private static Block replaceNetherrackPlacementData(Block old) {
        return Blocks.NETHER_BRICKS;
    }

    //@ModifyArg(method = "createLavaReplacementRule", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/RuinedPortalStructurePiece;createReplacementRule(Lnet/minecraft/block/Block;Lnet/minecraft/block/Block;)Lnet/minecraft/structure/processor/StructureProcessorRule;", ordinal = 1), index = 1)
    private static Block replaceNetherrackLavaReplacementRule(Block old) {
        return Blocks.NETHER_BRICKS;
    }

    //@Redirect(method = "updateNetherracksInBound", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean redirectIsOf(BlockState instance, Block block) {
        // treat original netherrack checks as nether bricks
        if (block == Blocks.NETHERRACK) {
            block = Blocks.NETHER_BRICKS;
        }
        return instance.isOf(block);
    }

    //@Redirect(method = "generateOvergrownLeaves", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;NETHERRACK:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC))
    private Block replaceNetherrackOvergrownLeaves() {
        return Blocks.NETHER_BRICKS;
    }

    //@Redirect(method = "placeNetherrackBottom", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;NETHERRACK:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC))
    private Block replaceNetherrackBottom() {
        return Blocks.NETHER_BRICKS;
    }
}