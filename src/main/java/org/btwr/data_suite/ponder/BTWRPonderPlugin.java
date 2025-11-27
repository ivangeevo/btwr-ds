package org.btwr.data_suite.ponder;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;
import org.btwr.data_suite.ponder.scene.BTWRPonderScenes;
import org.btwr.data_suite.ponder.tag.BTWRPonderTags;

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