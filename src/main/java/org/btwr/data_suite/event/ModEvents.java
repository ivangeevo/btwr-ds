package org.btwr.data_suite.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import org.btwr.data_suite.data.ModDataAttachments;
import org.btwr.data_suite.ending.EndingManager;
import org.btwr.data_suite.ending.LiminalPlayerState;
import org.btwr.data_suite.ending.LiminalStates;
import org.btwr.data_suite.event.events.FoodComponentModifierEvents;
import org.btwr.data_suite.event.events.ItemCountModificationEvents;
import org.btwr.data_suite.event.events.ModLootTableEvents;

public class ModEvents {
    public static void register() {
        FoodComponentModifierEvents.register();
        ItemCountModificationEvents.register();
        ModLootTableEvents.register();
        //ModTillingLootTableEvents.register();

        // Modify Wolf health
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof WolfEntity wolf) {
                EntityAttributeInstance health = wolf.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                if (health != null) {
                    health.setBaseValue(20.0);
                    wolf.setHealth(20.0f); // Also update current HP
                }
            }
        });

        // Ending
        //registerLiminalEvents();
    }

    private static void registerLiminalEvents() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity instanceof EnderDragonEntity)) {
                return;
            }

            Entity attacker = damageSource.getAttacker();

            if (!(attacker instanceof ServerPlayerEntity player)) {
                return;
            }

            EndingManager.begin(player);
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            // tick dissolves
            EndingManager.tick(server);

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                LiminalPlayerState state = player.getAttached(ModDataAttachments.LIMINAL_STATE);

                assert state != null;
                if (!state.active()) {
                    continue;
                }

                // chair interaction
                BlockPos playerPos = player.getBlockPos();
                BlockPos chairPos = new BlockPos(0, 1, 2);

                if (!state.satInChair() && playerPos.equals(chairPos)) {
                    LiminalStates.markSatInChair(player);
                    EndingManager.triggerChairMoment(player);
                }

                // offering detection
                BlockPos pedestal = new BlockPos(0, 1, -2);
                BlockPos offeringPos = pedestal.up();
                BlockState offering = player.getServerWorld().getBlockState(offeringPos);

                if (!state.placedBlock() && !offering.isAir()) {
                    LiminalStates.markPlacedBlock(player);
                    EndingManager.queueDissolve(
                            GlobalPos.create(
                                    player.getServerWorld().getRegistryKey(),
                                    offeringPos
                            )
                    );
                }

                // progression complete
                LiminalPlayerState updated = player.getAttached(ModDataAttachments.LIMINAL_STATE);

                assert updated != null;
                if (updated.progression() >= 2) {
                    EndingManager.beginCredits(player);
                }
            }
        });
    }
}