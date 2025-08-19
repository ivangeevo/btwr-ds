package org.ivangeevo.btwr_ds.ponder;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;
import org.ivangeevo.btwr_ds.ponder.scene.BTWRPonderScenes;
import org.ivangeevo.btwr_ds.ponder.tag.BTWRPonderTags;

public class BTWRPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return BTWRDSMod.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<Identifier> helper) {
        BTWRPonderScenes.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<Identifier> helper) {
        BTWRPonderTags.register(helper);
    }
}
