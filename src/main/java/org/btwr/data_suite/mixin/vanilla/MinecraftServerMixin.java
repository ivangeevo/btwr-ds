package org.btwr.data_suite.mixin.vanilla;

import net.minecraft.resource.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import org.btwr.data_suite.data.DatapackEnforcer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(value = MinecraftServer.class, priority = 1001)
public abstract class MinecraftServerMixin {

    //@Inject(method = "loadDataPacks(Lnet/minecraft/resource/ResourcePackManager;Ljava/util/Collection;Lnet/minecraft/resource/featuretoggle/FeatureSet;Z)Lnet/minecraft/resource/DataConfiguration;", at = @At("HEAD"))
    private static void onLoadDataPacks(ResourcePackManager manager, Collection<String> defaultPacks,
            FeatureSet features, boolean allowCustom, CallbackInfoReturnable<DataConfiguration> cir
    ) {
        DatapackEnforcer.getInstance().onLoadDataPack(manager);
    }

    //@Inject(method = "createDataPackSettings", at = @At("HEAD"))
    private static void enableCustomPack(ResourcePackManager manager, boolean allowCustom, CallbackInfoReturnable<DataPackSettings> cir) {
        DatapackEnforcer.getInstance().enableCustomPack(manager);
    }

}