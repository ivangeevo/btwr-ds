package org.btwr.data_suite.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.WolfEntity;
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
    }
}
