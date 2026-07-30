package org.btwr.data_suite.mixin.vanilla;

import com.google.gson.JsonElement;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(RecipeManager.class)
public abstract class RecipeManagerMixin {

    // Disable certain recipe types
    //@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("TAIL"))
    private void removeSmeltingRecipes(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        RecipeManager self = (RecipeManager) (Object) this;

        Set<RecipeType<?>> disabledTypes = Set.of(
                RecipeType.SMELTING,
                RecipeType.SMOKING,
                RecipeType.BLASTING
        );

        List<RecipeEntry<?>> filtered = self.values().stream()
                .filter(entry -> !disabledTypes.contains(entry.value().getType()))
                .toList();

        self.setRecipes(filtered);
    }
}
