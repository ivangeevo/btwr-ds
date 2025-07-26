package org.ivangeevo.btwr_ds.world;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

public class OGSpawnChunksLoader {

    private static final int CHUNK_RADIUS = 6;

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ServerWorld overworld = server.getOverworld();
            forceLoadSpawnChunks(overworld);
        });
    }

    private static void forceLoadSpawnChunks(ServerWorld world) {
        ServerChunkManager chunkManager = world.getChunkManager();
        ChunkPos center = new ChunkPos(world.getSpawnPos());


        // Add tickets for all chunks in the square around spawn
        for (int dx = -CHUNK_RADIUS; dx <= CHUNK_RADIUS; dx++) {
            for (int dz = -CHUNK_RADIUS; dz <= CHUNK_RADIUS; dz++) {
                ChunkPos chunkPos = new ChunkPos(center.x + dx, center.z + dz);
                chunkManager.addTicket(ChunkTicketType.PLAYER, chunkPos, 31, chunkPos);
            }
        }
    }
}
