package org.ivangeevo.btwr_ds.ponder.scene;

import com.bwt.blocks.BwtBlocks;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.ponder.scene.scenes.KineticScenes;
import org.ivangeevo.btwr_ds.ponder.tag.BTWRPonderTags;

public class BTWRPonderScenes {

    public static void register(PonderSceneRegistrationHelper<Identifier> helper) {

        PonderSceneRegistrationHelper<Block> HELPER =
                helper.withKeyFunction(Registries.BLOCK::getId);

        HELPER.forComponents(BwtBlocks.axleBlock)
                .addStoryBoard("axle/relay", KineticScenes::axleAsRelay, BTWRPonderTags.MECHANICAL_RELAYS);
    }
}
