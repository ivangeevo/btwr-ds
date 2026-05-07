package org.btwr.data_suite.world;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.btwr.shared_library.util.utils.IdUtils;

public class ModDimensions {

    public static final RegistryKey<World> LIMINAL = RegistryKey.of(
            RegistryKeys.WORLD,
            IdUtils.ofDS("liminal")
    );

    public static void register() {}

}