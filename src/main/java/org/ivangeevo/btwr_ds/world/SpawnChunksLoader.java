package org.ivangeevo.btwr_ds.world;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

public class SpawnChunksLoader {

    private static final int CHUNK_RADIUS = 6;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ServerWorld overworld = server.getOverworld();
            // Ensure fake player exists and is in spawn chunks
            FakePlayer fake = FakePlayer.get(overworld);
            fake.teleport(overworld,overworld.getSpawnPos().getX() + 0.5,
                    overworld.getSpawnPos().getY(),
                    overworld.getSpawnPos().getZ() + 0.5,
                    0f, 0f);

            // Force load surrounding spawn chunks using FORCED tickets
            forceLoadSpawnChunks(overworld);
        });
    }

    private static void forceLoadSpawnChunks(ServerWorld world) {
        var cm = world.getChunkManager();
        var center = world.getSpawnPos();
        var centerChunk = new net.minecraft.util.math.ChunkPos(center);

        for (int dx = -CHUNK_RADIUS; dx <= CHUNK_RADIUS; dx++) {
            for (int dz = -CHUNK_RADIUS; dz <= CHUNK_RADIUS; dz++) {
                var chunkPos = new ChunkPos(centerChunk.x + dx, centerChunk.z + dz);
                cm.addTicket(ChunkTicketType.FORCED, chunkPos, 31, chunkPos);
            }
        }
    }
}
