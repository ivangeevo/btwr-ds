package org.btwr.data_suite.data;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.storage.LevelStorage;
import org.btwr.data_suite.BTWRDSMod;

import java.nio.file.Path;
import java.util.*;
import java.util.function.UnaryOperator;

public class DatapackEnforcer {

    private static final ResourcePackSource RESOURCE_PACK_SOURCE = ResourcePackSource.create(
            getSourceTextSupplier("pack.source." + BTWRDSMod.MOD_ID), true
    );

    private static UnaryOperator<Text> getSourceTextSupplier(String translationKey) {
        Text text = Text.translatable(translationKey);
        return (name) -> Text.translatable("pack.nameAndSource", name, text).formatted(Formatting.GRAY);
    }

    private static final Path DATAPACKS_PATH = FabricLoader.getInstance().getGameDir().resolve("datapacks");

    private static final DatapackEnforcer INSTANCE = new DatapackEnforcer();

    private DatapackEnforcer() {}

    public static DatapackEnforcer getInstance() {
        return INSTANCE;
    }

    public void onLoadDataPack(ResourcePackManager manager) {
        Set<ResourcePackProvider> allProviders = new HashSet<>(manager.providers);

        Path symlinkConfig = FabricLoader.getInstance().getGameDir().resolve(LevelStorage.ALLOWED_SYMLINKS_FILE_NAME);
        ResourcePackProvider externalProvider = new FileResourcePackProvider(DATAPACKS_PATH, ResourceType.SERVER_DATA,
                RESOURCE_PACK_SOURCE, LevelStorage.createSymlinkFinder(symlinkConfig)
        );

        allProviders.add(externalProvider);
        manager.providers = ImmutableSet.copyOf(allProviders);
        manager.scanPacks();
    }

    public void enableCustomPack(ResourcePackManager manager)  {
        List<String> enabled = new ArrayList<>();

        // Always enable the "btwr_ds" datapack
        enabled.add(BTWRDSMod.MOD_ID);

        manager.setEnabledProfiles(enabled);
    }

}