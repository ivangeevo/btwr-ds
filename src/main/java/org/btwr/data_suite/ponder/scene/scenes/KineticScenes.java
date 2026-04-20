package org.btwr.data_suite.ponder.scene.scenes;

import com.bwt.entities.BwtEntities;
import com.bwt.entities.HorizontalMechPowerSourceEntity;
import com.bwt.entities.WaterWheelEntity;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.btwr.data_suite.ponder.scene.BTWRSceneBuilder;

public class KineticScenes {


    public static void axleAsRelay(SceneBuilder builder, SceneBuildingUtil util) {
        BTWRSceneBuilder scene = new BTWRSceneBuilder(builder);

        scene.title("axle", "Relaying rotational force using Axles");
        scene.configureBasePlate(0, 0, 6);
        scene.rotateCameraY(180);

        // 1. Show base layer
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);

        // 1.1 Show water column at Z=1
        scene.world().showSection(util.select().position(0, 0, 1), Direction.UP);
        scene.world().showSection(util.select().position(1, 0, 1), Direction.UP);
        scene.world().showSection(util.select().position(2, 0, 1), Direction.UP);
        scene.world().showSection(util.select().position(3, 0, 1), Direction.UP);
        scene.world().showSection(util.select().position(4, 0, 1), Direction.UP);
        scene.world().showSection(util.select().position(5, 0, 1), Direction.UP);
        scene.world().showSection(util.select().position(0, 1, 1), Direction.UP);
        scene.world().showSection(util.select().position(1, 1, 1), Direction.UP);
        scene.idle(20);

        // 2. Show axle at power source position
        scene.world().showSection(util.select().position(3, 3, 1), Direction.DOWN);
        scene.idle(20);

        // 3. Spawn the water wheel entity
        ElementLink<EntityElement> waterWheel = scene.world().createEntity(world -> {
            HorizontalMechPowerSourceEntity e = BwtEntities.waterWheelEntity.create(world);
            if (e != null) {
                e.setPos(3.5, 3.5, 1.5);
                e.setYaw(180.0f);
                e.setRotationSpeed(-0.70710677f);
            }
            return e;
        });
        scene.idle(10);

        // 4. Show gearbox
        scene.world().showSection(util.select().position(3, 3, 2), Direction.DOWN);
        scene.idle(20);

        // 5. Show axles one by one
        scene.world().showSection(util.select().position(3, 3, 3), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(3, 3, 4), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(3, 3, 5), Direction.DOWN);
        scene.idle(5);

        scene.idle(10);

        scene.overlay().showText(80)
                .text("Axles relay rotation in a straight line, losing power over distance")
                .placeNearTarget()
                .pointAt(util.vector().of(3.5, 3.5, 3.5));

        scene.idle(90);
        scene.markAsFinished();
    }

    /**
    public static void axleAsRelay(SceneBuilder builder, SceneBuildingUtil util) {
        BTWRSceneBuilder scene = new BTWRSceneBuilder(builder);

        scene.title("axle", "Relaying rotational force using Axles");
        // Structure is 6x4x6, base plate offset 0,0, size 6
        scene.configureBasePlate(0, 0, 6);

        // At the top of your scene method, after configureBasePlate:
        ElementLink<EntityElement> waterWheel = scene.world().createEntity(world -> {
            HorizontalMechPowerSourceEntity e = BwtEntities.waterWheelEntity.create(world);
            if (e != null) {
                e.setPos(3.5, 1.5, 1.5);
                e.setYaw(Direction.NORTH.asRotation());
                e.setRotationSpeed(1.0f);
            }
            return e;
        });


        //scene.world().showSection(util.select().everywhere(), Direction.UP);

        /**
        // Show base layer
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(10);

        // Show gearbox (power source) at (3, 1, 1)
        scene.world().showSection(util.select().position(3, 1, 1), Direction.DOWN);
        scene.idle(5);

        // Show axle power source at (3, 1, 2)
        scene.world().showSection(util.select().position(3, 1, 2), Direction.DOWN);
        scene.idle(5);

        // Show axles along Z axis one by one
        for (int z = 3; z <= 5; z++) {
            scene.world().showSection(util.select().position(3, 1, z), Direction.DOWN);
            scene.idle(5);
        }

        scene.idle(10);

        scene.overlay().showText(80)
                .text("Axles relay rotation in a straight line, losing power over distance")
                .placeNearTarget()
                .pointAt(util.vector().of(3.5, 1.5, 3.5));

        scene.idle(90);
        scene.markAsFinished();
    }
    **/
}