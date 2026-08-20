package org.btwr.data_suite.event.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;

public class ModServerEvents {
    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register(ModServerEvents::modifyWolfHealth);
    }

    // Vanilla sets 8 health for untamed and 40 health for tamed wolves
    // Modify it to always be 20 no matter if tamed or untamed
    private static void modifyWolfHealth(Entity entity, World world) {
        if (entity instanceof WolfEntity wolf) {
            EntityAttributeInstance health = wolf.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (health != null) {
                health.setBaseValue(20.0);
                wolf.setHealth(20.0f); // Also update current HP
            }
        }
    }
}
