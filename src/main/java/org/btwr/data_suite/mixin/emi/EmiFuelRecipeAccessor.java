package org.btwr.data_suite.mixin.emi;

import dev.emi.emi.recipe.EmiFuelRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EmiFuelRecipe.class)
public interface EmiFuelRecipeAccessor {
    @Accessor("time") int getTime();
}
