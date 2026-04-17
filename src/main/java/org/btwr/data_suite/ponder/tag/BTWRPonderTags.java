package org.btwr.data_suite.ponder.tag;

import com.bwt.blocks.BwtBlocks;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;

public class BTWRPonderTags {
    public static final Identifier

    MECHANICAL_RELAYS = loc("mechanical_relays");

    private static Identifier loc(String id) {
        return Identifier.of(BTWRDSMod.MOD_ID, id);
    }

    public static void register(PonderTagRegistrationHelper<Identifier> helper) {
        PonderTagRegistrationHelper<Block> HELPER = helper.withKeyFunction(
                Registries.BLOCK::getId
        );

        PonderTagRegistrationHelper<ItemConvertible> itemHelper = helper.withKeyFunction(
                RegisteredObjectsHelper::getKeyOrThrow
        );

        helper.registerTag(MECHANICAL_RELAYS)
                .addToIndex()
                .item(BwtBlocks.gearBoxBlock, true, false)
                .title("Mechanical Blocks")
                .description("Components which help relaying Mechanical Power elsewhere")
                .register();

        HELPER.addToTag(MECHANICAL_RELAYS)
                .add(BwtBlocks.axleBlock)
                .add(BwtBlocks.gearBoxBlock);
    }
}