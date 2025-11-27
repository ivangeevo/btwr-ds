package org.btwr.data_suite.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.block.BlockState;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import org.btwr.data_suite.BTWRDSMod;

import java.util.*;

public class BlockSpeedRegistry {

    private static final Map<Identifier, Double> BLOCK_SPEEDS = new HashMap<>();
    private static final Map<TagKey<Block>, Double> TAG_SPEEDS = new HashMap<>();

    public static void init() {
        // Initialize all entries first
        initializeEntries();

        ServerTickEvents.END_WORLD_TICK.register(world -> {

            for (ServerPlayerEntity player : world.getPlayers()) {
                BlockPos pos = player.getBlockPos().down();
                Block block = player.getWorld().getBlockState(pos).getBlock();

                var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                if (attr == null) return;

                // Clear old block-based modifiers
                attr.getModifiers().stream()
                        .filter(mod -> mod.id().getNamespace().equals(BTWRDSMod.MOD_ID))
                        .forEach(mod -> attr.removeModifier(mod.id()));

                BlockSpeedRegistry.get(block.getDefaultState()).ifPresent(mod -> {
                    attr.addTemporaryModifier(mod.toAttributeModifier());
                });
            }

        });
    }

    private static void initializeEntries() {
        register("minecraft:stone", 1.20);
        register("minecraft:andesite", 1.20);
        register("minecraft:diorite", 1.20);
        register("minecraft:granite", 1.20);
        register("minecraft:deepslate", 1.20);
        register("minecraft:cobblestone", 1.20);
        register("minecraft:cobbled_deepslate", 1.20);
        register("minecraft:gravel", 1.20);
        registerTag("minecraft:logs", 1.20);
        registerTag("c:stump_blocks", 1.20);
        registerTag("minecraft:wooden_slabs", 1.20);
        registerTag("minecraft:planks", 1.20);
        registerTag("minecraft:stairs", 1.20);
        registerTag("minecraft:stone_ores", 1.20);
        registerTag("minecraft:deepslate_ores", 1.20);
        register("tough_environment:stone_converting", 1.20);
        register("tough_environment:andesite_converting", 1.20);
        register("tough_environment:diorite_converting", 1.20);
        register("tough_environment:granite_converting", 1.20);
        register("tough_environment:deepslate_converting", 1.20);
        register("tough_environment:slab_dirt_packed", 1.20);
        register("tough_environment:slab_gravel", 1.20);
        register("tough_environment:slab_cobblestone_loose", 1.20);
        register("tough_environment:slab_cobbled_deepslate_loose", 1.20);
        register("tough_environment:dirt_packed", 1.20);
        register("tough_environment:cobblestone_loose", 1.20);
        register("tough_environment:cobbled_deepslate_loose", 1.20);
        register("tough_environment:andesite_loose", 1.20);
        register("tough_environment:diorite_loose", 1.20);
        register("tough_environment:granite_loose", 1.20);
        register("minecraft:dirt_path", 1.10);
        register("tough_environment:slab_sand", 0.80);
        register("tough_environment:slab_red_sand", 0.80);
        register("minecraft:sand", 0.80);
        register("minecraft:red_sand", 0.80);
        registerTag("minecraft:leaves", 0.50);
    }

    private static void register(String id, double speed) {
        BLOCK_SPEEDS.put(Identifier.of(id), speed);
    }

    private static void registerTag(String tagId, double speed) {
        TAG_SPEEDS.put(TagKey.of(Registries.BLOCK.getKey(), Identifier.of(tagId)), speed);
    }

    public static Optional<BlockSpeedModifier> get(BlockState state) {
        Identifier blockId = Registries.BLOCK.getId(state.getBlock());

        // Check direct block match
        if (BLOCK_SPEEDS.containsKey(blockId)) {
            double value = BLOCK_SPEEDS.get(blockId) - 1.0;
            return Optional.of(new BlockSpeedModifier(
                    Identifier.of(BTWRDSMod.MOD_ID, "speed_" + blockId.getPath()),
                    value,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        }

        // Check tags
        for (var entry : TAG_SPEEDS.entrySet()) {
            if (state.isIn(entry.getKey())) {
                double value = entry.getValue() - 1.0;
                return Optional.of(new BlockSpeedModifier(
                        Identifier.of(BTWRDSMod.MOD_ID, "speed_" + entry.getKey().id().getPath()),
                        value,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ));
            }
        }

        return Optional.empty();
    }

    public static Optional<BlockSpeedModifier> get(BlockView world, BlockPos pos) {
        return get(world.getBlockState(pos));
    }

}