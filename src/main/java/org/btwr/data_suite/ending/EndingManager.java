package org.btwr.data_suite.ending;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import org.btwr.data_suite.data.ModDataAttachments;
import org.btwr.data_suite.world.ModDimensions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EndingManager {

    private static final Map<GlobalPos, Integer> dissolvingBlocks = new HashMap<>();

    public static void tick(MinecraftServer server) {
        Iterator<Map.Entry<GlobalPos, Integer>> iterator = dissolvingBlocks.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<GlobalPos, Integer> entry = iterator.next();
            GlobalPos globalPos = entry.getKey();
            ServerWorld world = server.getWorld(globalPos.dimension());

            if (world == null) {
                iterator.remove();
                continue;
            }

            BlockPos pos = globalPos.pos();

            int ticks = entry.getValue();

            spawnDissolveAmbience(world, pos);

            ticks--;

            if (ticks <= 0) {
                dissolvePlacedBlock(world, pos);
                iterator.remove();
            } else {
                entry.setValue(ticks);
            }
        }
    }

    private static void spawnDissolveAmbience(ServerWorld world, BlockPos pos) {
        Vec3d center = pos.toCenterPos();

        world.spawnParticles(
                ParticleTypes.END_ROD,
                center.x,
                center.y + 0.2,
                center.z,
                2,
                0.1,
                0.1,
                0.1,
                0.002
        );

        if (world.getRandom().nextFloat() < 0.05f) {
            world.playSound(
                    null,
                    pos,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_HIT,
                    SoundCategory.AMBIENT,
                    0.15f,
                    0.5f + world.getRandom().nextFloat() + 0.3f
            );
        }
    }

    public static void begin(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            ServerWorld liminal = player.getServer().getWorld(ModDimensions.LIMINAL);

            if (liminal == null) return;

            buildRoom(liminal);

            player.teleport(liminal, 0.5, 2, 0.5, player.getYaw(), player.getPitch());

            LiminalStates.activate(player);
        }
    }

    private static void buildRoom(ServerWorld world) {
        // only build once
        BlockPos origin = new BlockPos(0, 0, 0);

        // walls
        for (int y = 1; y <= 4; y++) {
            for (int x = -6; x <= 6; x++) {
                world.setBlockState(origin.add(x, y, -6), Blocks.DARK_OAK_PLANKS.getDefaultState());
                world.setBlockState(origin.add(x, y, 6), Blocks.DARK_OAK_PLANKS.getDefaultState());
            }
            for (int z = -6; z <= 6; z++) {
                world.setBlockState(origin.add(-6, y, z), Blocks.DARK_OAK_PLANKS.getDefaultState());
                world.setBlockState(origin.add(6, y, z), Blocks.DARK_OAK_PLANKS.getDefaultState());
            }
        }

        // chair
        world.setBlockState(origin.add(0, 1, 2), Blocks.SPRUCE_STAIRS.getDefaultState());

        // pedestal
        world.setBlockState(origin.add(0, 1, -2), Blocks.POLISHED_BLACKSTONE.getDefaultState());
    }

    private static void dissolvePlacedBlock(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if (state.isAir()) {
            return;
        }

        Vec3d center = pos.toCenterPos();

        // block particles
        world.spawnParticles(
                new BlockStateParticleEffect(
                        ParticleTypes.BLOCK,
                        state
                ),
                center.x,
                center.y,
                center.z,
                40,
                0.3,
                0.3,
                0.3,
                0.02
        );

        // magical particles

        world.spawnParticles(
                ParticleTypes.END_ROD,
                center.x,
                center.y,
                center.z,
                30,
                0.2,
                0.4,
                0.2,
                0.01
        );

        // flash
        world.spawnParticles(
                ParticleTypes.FLASH,
                center.x,
                center.y,
                center.z,
                1,
                0,
                0,
                0,
                0
        );

        // sound
        world.playSound(
                null,
                pos,
                SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK,
                SoundCategory.AMBIENT,
                0.7f,
                0.6f
        );

        // remove block
        world.setBlockState(
                pos,
                Blocks.AIR.getDefaultState()
        );
    }

    public static void queueDissolve(GlobalPos pos) {
        // 40 ticks = 2 seconds
        dissolvingBlocks.put(pos, 40);
    }

    public static void triggerChairMoment(ServerPlayerEntity player) {
        ServerWorld world = player.getServerWorld();
        Vec3d center = player.getPos();

        // soft particles around player
        world.spawnParticles(
                ParticleTypes.END_ROD,
                center.x,
                center.y + 1.0,
                center.z,
                25,
                0.5,
                0.5,
                0.5,
                0.01
        );

        // subtle magical shimmer
        world.playSound(
                null,
                player.getBlockPos(),
                SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                SoundCategory.AMBIENT,
                0.4f,
                0.6f
        );

        // low ambient resonance
        world.playSound(
                null,
                player.getBlockPos(),
                SoundEvents.BLOCK_BEACON_AMBIENT,
                SoundCategory.AMBIENT,
                0.15f,
                0.5f
        );
    }

    public static void beginCredits(ServerPlayerEntity player) {
        LiminalPlayerState state = player.getAttached(ModDataAttachments.LIMINAL_STATE);

        // prevent retrigger
        if (!state.active()) {
            return;
        }

        // disable liminal progression
        player.setAttached(
                ModDataAttachments.LIMINAL_STATE,
                new LiminalPlayerState(
                        false,
                        state.satInChair(),
                        state.placedBlock(),
                        state.lookedOutside(),
                        state.progression()
                )
        );

        // atmospheric effect before credits
        ServerWorld world =
                player.getServerWorld();

        Vec3d pos =
                player.getPos();

        world.spawnParticles(
                ParticleTypes.REVERSE_PORTAL,
                pos.x,
                pos.y + 1.0,
                pos.z,
                100,
                1.0,
                1.0,
                1.0,
                0.05
        );

        world.playSound(
                null,
                player.getBlockPos(),
                SoundEvents.BLOCK_PORTAL_TRIGGER,
                SoundCategory.AMBIENT,
                0.7f,
                0.5f
        );

        // trigger vanilla credits
        player.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_WON, 1.0f));
    }

}