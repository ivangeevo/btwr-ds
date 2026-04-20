package org.btwr.data_suite.ponder.scene.scenes;

import com.bwt.blocks.GearBoxBlock;
import com.bwt.blocks.axles.AxleBlock;
import com.bwt.entities.BwtEntities;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.btwr.data_suite.ponder.scene.BTWRPonderSceneBuilder;

public class KineticScenes {

    public static void axleAsRelay(SceneBuilder builder, SceneBuildingUtil util) {
        BTWRPonderSceneBuilder scene = new BTWRPonderSceneBuilder(builder);
        BTWRPonderSceneBuilder.BTWRPonderWorldInstructions sceneWorld = scene.getWorld();

        scene.title("axle", "Relaying rotational force using Axles");
        scene.configureBasePlate(0, 0, 6);
        scene.rotateCameraY(180);

        // 1 Show water column at Z=0
        sceneWorld.showSection(util.select().position(0, 1, 0), Direction.UP);
        sceneWorld.showSection(util.select().position(1, 1, 0), Direction.UP);
        sceneWorld.showSection(util.select().position(2, 1, 0), Direction.UP);
        sceneWorld.showSection(util.select().position(3, 1, 0), Direction.UP);
        sceneWorld.showSection(util.select().position(4, 1, 0), Direction.UP);
        sceneWorld.showSection(util.select().position(5, 1, 0), Direction.UP);
        sceneWorld.showSection(util.select().position(0, 2, 0), Direction.UP);
        sceneWorld.showSection(util.select().position(1, 2, 0), Direction.UP);
        scene.idle(20);

        // 2. Show an axle power source
        sceneWorld.showSection(util.select().position(3, 3, 0), Direction.DOWN);
        scene.idle(20);

        // 3. Spawn the water-wheel entity
        sceneWorld.spawnMechanicalSource(
                BwtEntities.waterWheelEntity, new Vec3d(3.5, 3.5, 0.5), 180f, -0.70710677f
        );

        scene.idle(10);

        // 4. Show gearbox
        sceneWorld.showSelectionWithPropertyState(
                3, 3, 1, GearBoxBlock.MECH_POWERED, true, false, Direction.DOWN, util
        );

        scene.idle(20);

        // 5. Show axles one by one
        sceneWorld.showSelectionWithPropertyState(
                3, 3, 2, AxleBlock.MECH_POWER, 3, false, Direction.DOWN, util
        );
        scene.idle(5);

        sceneWorld.showSelectionWithPropertyState(
                3, 3, 3, AxleBlock.MECH_POWER, 2, false, Direction.DOWN, util
        );
        scene.idle(5);

        sceneWorld.showSelectionWithPropertyState(
                3, 3, 4, AxleBlock.MECH_POWER, 1, false, Direction.DOWN, util
        );
        scene.idle(5);

        scene.overlay().showText(80)
                .text("Axles relay rotation in a straight line, losing power over distance")
                .placeNearTarget()
                .pointAt(util.vector().of(3.5, 4.5, 3.5));

        scene.idle(90);
        scene.markAsFinished();
    }
}