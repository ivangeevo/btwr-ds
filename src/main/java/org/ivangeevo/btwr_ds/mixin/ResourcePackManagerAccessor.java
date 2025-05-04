package org.ivangeevo.btwr_ds.mixin;

import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(ResourcePackManager.class)
public abstract class ResourcePackManagerAccessor {

    @Shadow public Set<ResourcePackProvider> providers;

    @Accessor
    Set<ResourcePackProvider> getProviders() {
        return this.providers;
    }
}
