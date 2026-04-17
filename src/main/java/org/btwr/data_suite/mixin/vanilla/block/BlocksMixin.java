package org.btwr.data_suite.mixin.vanilla.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/SugarCaneBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static AbstractBlock.Settings initSugarCane(AbstractBlock.Settings settings) {
        return settings.strength(0.01f);
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/ShortPlantBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static AbstractBlock.Settings initShortGrassAndFern(AbstractBlock.Settings settings) {
        return settings.strength(0.01f);
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/TallPlantBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V", ordinal = 0))
    private static AbstractBlock.Settings initTallGrass(AbstractBlock.Settings settings) {
        return settings.strength(0.01f);
    }
}