package org.btwr.data_suite.mixin.vanilla.block;

import net.minecraft.block.AbstractBlock;
import org.btwr.vegehenna.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ModBlocks.class)
public abstract class VGModBlocksMixin {

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lorg/btwr/vegehenna/block/blocks/SugarCaneRootsBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static AbstractBlock.Settings initSugarCaneRoots(AbstractBlock.Settings settings) {
        return settings.strength(0.01f);
    }

}