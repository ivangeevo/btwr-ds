package org.btwr.data_suite.world;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.GameRules;

public final class SpawnChunksLoader {

    private static boolean loaded = false;

    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(SpawnChunksLoader::onServerStarted);
    }

    private static void onServerStarted(MinecraftServer server) {
        if (loaded) return;

        ServerWorld overworld = server.getOverworld();
        if (overworld == null) return;

        forceLoadSpawnChunks(overworld);
        loaded = true;
    }

    private static void forceLoadSpawnChunks(ServerWorld world) {
        ChunkPos spawnChunk = new ChunkPos(world.getSpawnPos());

        int spawnRadius = world.getGameRules().getInt(GameRules.SPAWN_CHUNK_RADIUS);

        world.getChunkManager().addTicket(
                ChunkTicketType.PLAYER,
                spawnChunk,
                spawnRadius,
                spawnChunk
        );
    }
}
