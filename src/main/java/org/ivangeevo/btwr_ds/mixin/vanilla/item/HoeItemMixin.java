package org.ivangeevo.btwr_ds.mixin.vanilla.item;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.ivangeevo.btwr_ds.block.BlockTillingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin
{

    // Removes right-clicking for hoes.
    //@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void injectedUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        BlockTillingManager.MixinMod.getInstance().onUseOnBlock(context, cir);
    }
}
