package org.btwr.data_suite.mixin.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.RuinedPortalStructurePiece;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RuinedPortalStructurePiece.class)
public abstract class RuinedPortalStructurePieceMixin {

    @ModifyArg(
            method = "createPlacementData(Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/structure/RuinedPortalStructurePiece$VerticalPlacement;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/RuinedPortalStructurePiece$Properties;)Lnet/minecraft/structure/StructurePlacementData;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/RuinedPortalStructurePiece;createReplacementRule(Lnet/minecraft/block/Block;FLnet/minecraft/block/Block;)Lnet/minecraft/structure/processor/StructureProcessorRule;", ordinal = 1), index = 0)
    private static Block replaceNetherrackPlacementData(Block old) {
        return Blocks.NETHER_BRICKS;
    }

    @ModifyArg(
            method = "createLavaReplacementRule",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/RuinedPortalStructurePiece;createReplacementRule(Lnet/minecraft/block/Block;Lnet/minecraft/block/Block;)Lnet/minecraft/structure/processor/StructureProcessorRule;", ordinal = 1), index = 1)
    private static Block replaceNetherrackLavaReplacementRule(Block old) {
        return Blocks.NETHER_BRICKS;
    }

    @Redirect(
            method = "updateNetherracksInBound",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
            )
    )
    private boolean redirectIsOf(BlockState instance, Block block) {
        // treat original netherrack checks as nether bricks
        if (block == Blocks.NETHERRACK) {
            block = Blocks.NETHER_BRICKS;
        }
        return instance.isOf(block);
    }

    @Redirect(
            method = "generateOvergrownLeaves",
            at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;NETHERRACK:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC)
    )
    private Block replaceNetherrackOvergrownLeaves() {
        return Blocks.NETHER_BRICKS;
    }

    @Redirect(
            method = "placeNetherrackBottom",
            at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;NETHERRACK:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC)
    )
    private Block replaceNetherrackBottom() {
        return Blocks.NETHER_BRICKS;
    }


}