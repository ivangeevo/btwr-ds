package org.btwr.data_suite.ending;

import net.minecraft.server.network.ServerPlayerEntity;
import org.btwr.data_suite.data.ModDataAttachments;

public class LiminalStates {

    public static void activate(ServerPlayerEntity player) {
        player.setAttached(
                ModDataAttachments.LIMINAL_STATE,
                new LiminalPlayerState(
                        true,
                        false,
                        false,
                        false,
                        0
                )
        );
    }

    public static void markPlacedBlock(ServerPlayerEntity player) {
        LiminalPlayerState old =
                player.getAttached(ModDataAttachments.LIMINAL_STATE);

        player.setAttached(
                ModDataAttachments.LIMINAL_STATE,
                new LiminalPlayerState(
                        old.active(),
                        old.satInChair(),
                        true,
                        old.lookedOutside(),
                        old.progression() + 1
                )
        );
    }

    public static void markSatInChair(ServerPlayerEntity player) {
        LiminalPlayerState old =
                player.getAttached(ModDataAttachments.LIMINAL_STATE);

        player.setAttached(
                ModDataAttachments.LIMINAL_STATE,
                new LiminalPlayerState(
                        old.active(),
                        true,
                        old.placedBlock(),
                        old.lookedOutside(),
                        old.progression() + 1
                )
        );
    }
}