package org.ivangeevo.btwr_ds.world;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

import java.util.Comparator;

public class SpawnChunksLoader {

    private static final ChunkTicketType<ChunkPos> SPAWN_CHUNK_TICKET =
            ChunkTicketType.create("spawn_chunk", Comparator.comparingLong(ChunkPos::toLong), 0);

    private static boolean spawnChunksLoaded = false;

    public static void init() {
        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {
            if (!serverWorld.getRegistryKey().equals(World.OVERWORLD)) return;

            MinecraftServer server = serverWorld.getServer();

            // This ensures logic only runs once per tick, not per dimension
            if (!server.getOverworld().equals(serverWorld)) return;


            // Load and force ticking of chunks around the spawn
            if (!spawnChunksLoaded) {
                BlockPos spawn = serverWorld.getSpawnPos();
                forceSpawnChunks(serverWorld, spawn);
                spawnChunksLoaded = true;
            }

        });
    }

    private static void forceSpawnChunks(ServerWorld world, BlockPos spawn) {
        ChunkPos center = new ChunkPos(spawn);
        ServerChunkManager chunkManager = world.getChunkManager();
        ChunkTicketManager ticketManager = chunkManager.chunkLoadingManager.getTicketManager();

        // ±6 chunks = 13×13 area
        int radius = 6;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                ChunkPos pos = new ChunkPos(center.x + dx, center.z + dz);
                ticketManager.addTicket(SPAWN_CHUNK_TICKET, pos, 1, pos);
            }
        }
    }
}
