package org.ivangeevo.btwr_ds.world;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.*;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class SpawnChunksLoader {

    public static void init() {
        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {
            if (!serverWorld.getRegistryKey().equals(World.OVERWORLD)) return;

            MinecraftServer server = serverWorld.getServer();

            // Ensure logic only runs once per tick, not per dimension
            if (!server.getOverworld().equals(serverWorld)) return;

            BlockPos spawn = serverWorld.getSpawnPos();
            forceSpawnChunks(serverWorld, spawn);
        });
    }

    private static void forceSpawnChunks(ServerWorld world, BlockPos spawn) {
        ChunkPos center = new ChunkPos(spawn);
        ServerChunkManager chunkManager = world.getChunkManager();

        int radius = 6; // 13x13 area

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                ChunkPos pos = new ChunkPos(center.x + x, center.z + z);
                chunkManager.addTicket(ChunkTicketType.START, pos, 0, Unit.INSTANCE);
            }
        }
    }


}
