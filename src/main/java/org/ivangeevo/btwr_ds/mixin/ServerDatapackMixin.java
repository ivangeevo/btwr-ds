package org.ivangeevo.btwr_ds.mixin;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.*;
import java.util.function.UnaryOperator;

@Mixin(value = MinecraftServer.class, priority = 1001)
public abstract class ServerDatapackMixin {

    @Unique
    private static final ResourcePackSource RESOURCE_PACK_SOURCE = ResourcePackSource.create(
            getSourceTextSupplier("pack.source.btwr_ds"), true
    );

    @Unique
    private static final Path DATAPACKS_PATH = FabricLoader.getInstance().getGameDir().resolve("datapacks");

    @Unique
    private static UnaryOperator<Text> getSourceTextSupplier(String translationKey) {
        Text text = Text.translatable(translationKey);
        return (name) -> Text.translatable("pack.nameAndSource", name, text).formatted(Formatting.GRAY);
    }

    @Inject(method = "loadDataPacks(Lnet/minecraft/resource/ResourcePackManager;Ljava/util/Collection;Lnet/minecraft/resource/featuretoggle/FeatureSet;Z)Lnet/minecraft/resource/DataConfiguration;", at = @At("HEAD"))
    private static void onLoadDataPacks(
            ResourcePackManager manager,
            Collection<String> defaultPacks,
            FeatureSet features,
            boolean allowCustom,
            CallbackInfoReturnable<DataConfiguration> cir
    ) {
        Set<ResourcePackProvider> allProviders = new HashSet<>(manager.providers);

        Path symlinkConfig = FabricLoader.getInstance().getGameDir().resolve(LevelStorage.ALLOWED_SYMLINKS_FILE_NAME);
        ResourcePackProvider externalProvider = new FileResourcePackProvider(
                DATAPACKS_PATH,
                ResourceType.SERVER_DATA,
                RESOURCE_PACK_SOURCE,
                LevelStorage.createSymlinkFinder(symlinkConfig)
        );

        allProviders.add(externalProvider);
        manager.providers = ImmutableSet.copyOf(allProviders);
        manager.scanPacks();
    }

    @Inject(method = "createDataPackSettings", at = @At("HEAD"))
    private static void enableCustomPack(ResourcePackManager manager, boolean allowCustom, CallbackInfoReturnable<DataPackSettings> cir) {
        List<String> enabled = new ArrayList<>();

        // Always enable "vanilla" and the "btwr_ds" datapack
        enabled.add("vanilla");
        enabled.add("btwr_ds");

        manager.setEnabledProfiles(enabled);
    }
}
