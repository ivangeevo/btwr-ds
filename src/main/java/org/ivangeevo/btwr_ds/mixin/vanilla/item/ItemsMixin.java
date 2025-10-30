package org.ivangeevo.btwr_ds.mixin.vanilla.item;

import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Items.class)
public abstract class ItemsMixin
{
    @ModifyArg(method = "<clinit>", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/AxeItem;createAttributeModifiers(Lnet/minecraft/item/ToolMaterial;FF)Lnet/minecraft/component/type/AttributeModifiersComponent;"
    ), index = 1)
    private static float setAxesBaseDamage(float par2) {
        return 2;
    }


}
