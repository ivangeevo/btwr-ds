package org.ivangeevo.btwr_ds.data;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class BTWRDatapackLoader {


    public static void register() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new BeaconDataReloadListener());
    }


}
