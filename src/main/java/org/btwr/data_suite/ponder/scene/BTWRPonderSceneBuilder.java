package org.btwr.data_suite.ponder.scene;

import com.bwt.entities.HorizontalMechPowerSourceEntity;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BTWRPonderSceneBuilder extends PonderSceneBuilder {
    private final BTWRPonderWorldInstructions world;

    public BTWRPonderSceneBuilder(SceneBuilder baseSceneBuilder) {
        this(baseSceneBuilder.getScene());
    }

    private BTWRPonderSceneBuilder(PonderScene ponderScene) {
        super(ponderScene);
        world = new BTWRPonderWorldInstructions();
    }

    public BTWRPonderWorldInstructions getWorld() {
        return world;
    }

    public class BTWRPonderWorldInstructions extends PonderWorldInstructions {
        public void spawnMechanicalSource(
                EntityType<? extends HorizontalMechPowerSourceEntity> type,
                Vec3d pos, float yaw, float speed
        )
        {
            createEntity(world -> {
                HorizontalMechPowerSourceEntity e = type.create(world);
                if (e != null) {
                    e.setPos(pos.x, pos.y, pos.z);
                    e.setYaw(yaw);
                    e.setRotationSpeed(speed);
                }
                return e;
            });
        }

        public <T extends Comparable<T>> void showSelectionWithPropertyState(
                int x, int y, int z, Property<T> property, T value,
                boolean spawnParticles, Direction direction, SceneBuildingUtil util
        )
        {
            showSection(util.select().position(x, y, z), direction);
            modifyBlock(util.grid().at(x, y, z), s -> s.with(property, value), spawnParticles);
        }

    }
}