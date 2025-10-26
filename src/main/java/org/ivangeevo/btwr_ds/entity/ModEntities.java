package org.ivangeevo.btwr_ds.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.ivangeevo.btwr_ds.BTWRDSMod;
import org.ivangeevo.btwr_ds.entity.entities.BeastEntity;

public class ModEntities {

    public static final EntityType<BeastEntity> BEAST = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(BTWRDSMod.MOD_ID, "beast"),
            EntityType.Builder.create(BeastEntity::new, SpawnGroup.MONSTER).dimensions(0.9f, 1.2f).build("beast")
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(BEAST, BeastEntity.createBeastAttributes());
    }
}
