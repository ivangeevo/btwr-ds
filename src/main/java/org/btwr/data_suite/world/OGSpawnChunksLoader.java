package org.btwr.data_suite.world;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

public class OGSpawnChunksLoader {

    private static final int CHUNK_RADIUS = 6;

    private static boolean loaded = false;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ServerWorld overworld = server.getOverworld();
            if (!loaded) {
                forceLoadSpawnChunks(overworld);
                loaded = true;
            }
        });
    }

    private static void forceLoadSpawnChunks(ServerWorld world) {
        var cm = world.getChunkManager();
        var center = world.getSpawnPos();
        var centerChunk = new net.minecraft.util.math.ChunkPos(center);

        for (int dx = -CHUNK_RADIUS; dx <= CHUNK_RADIUS; dx++) {
            for (int dz = -CHUNK_RADIUS; dz <= CHUNK_RADIUS; dz++) {
                var chunkPos = new ChunkPos(centerChunk.x + dx, centerChunk.z + dz);
                cm.addTicket(ChunkTicketType.PLAYER, chunkPos, 2, chunkPos);
            }
        }
    }

}