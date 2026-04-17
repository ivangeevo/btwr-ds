package org.btwr.data_suite.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.btwr.data_suite.BTWRDSMod;
import org.btwr.data_suite.entity.entities.BeastEntity;

public class ModEntityTypes {
    public static final EntityType<BeastEntity> BEAST = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(BTWRDSMod.MOD_ID, "beast"),
            EntityType.Builder.create(BeastEntity::new, SpawnGroup.MONSTER).dimensions(0.9f, 1.2f).build("beast")
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(BEAST, BeastEntity.createBeastAttributes());
    }
}