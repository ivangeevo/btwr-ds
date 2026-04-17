package org.btwr.data_suite.ponder.scene;

import com.bwt.blocks.BwtBlocks;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.ponder.scene.scenes.KineticScenes;
import org.btwr.data_suite.ponder.tag.BTWRPonderTags;

public class BTWRPonderScenes {
    public static void register(PonderSceneRegistrationHelper<Identifier> helper) {

        PonderSceneRegistrationHelper<Block> HELPER =
                helper.withKeyFunction(Registries.BLOCK::getId);

        HELPER.forComponents(BwtBlocks.axleBlock)
                .addStoryBoard("axle/relay", KineticScenes::axleAsRelay, BTWRPonderTags.MECHANICAL_RELAYS);
    }
}