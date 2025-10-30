package org.ivangeevo.btwr_ds.mixin.bwt;

import com.bwt.blocks.BwtBlocks;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BwtBlocks.class)
public abstract class BwtBlocksMixin {

    // Add random ticks to the Soil Planter block
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/bwt/blocks/SoilPlanterBlock;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static AbstractBlock.Settings bwt_hct$init3(AbstractBlock.Settings settings) {
        return settings.ticksRandomly();
    }

}
