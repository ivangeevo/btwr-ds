package org.ivangeevo.btwr_ds.mixin;

import dev.emi.emi.recipe.EmiFuelRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EmiFuelRecipe.class)
public interface EmiFuelRecipeAccessor
{

    @Accessor("time")
    int getTime();

}
