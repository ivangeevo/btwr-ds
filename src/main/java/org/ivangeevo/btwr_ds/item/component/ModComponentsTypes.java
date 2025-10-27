package org.ivangeevo.btwr_ds.item.component;

import net.minecraft.component.ComponentType;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.ivangeevo.btwr_ds.BTWRDSMod;

public class ModComponentsTypes {

    public static final ComponentType<MagneticPointTrackerComponent> MAGNETIC_POINT_TRACKER = ComponentType.<MagneticPointTrackerComponent>builder()
            .codec(MagneticPointTrackerComponent.CODEC)
            .packetCodec(MagneticPointTrackerComponent.PACKET_CODEC)
            .cache()
            .build();

    // Register method, to be called in the mod initialization
    public static void register() {
        registerDataComponent(MAGNETIC_POINT_TRACKER, "magnetic_point_tracker");
    }

    private static void registerDataComponent(ComponentType<?> componentType, String stringName) {
        register(Registries.DATA_COMPONENT_TYPE, componentType, stringName);
    }

    private static void register(Registry<ComponentType<?>> registryType, ComponentType<?> componentType, String stringName)
    {
        Registry.register(registryType, BTWRDSMod.MOD_ID + ":" + stringName, componentType);
    }


}
