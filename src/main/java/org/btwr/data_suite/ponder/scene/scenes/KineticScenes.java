package org.btwr.data_suite.ponder.scene.scenes;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.btwr.data_suite.ponder.scene.BTWRSceneBuilder;

public class KineticScenes {
    /**
    public static void axleAsRelay(SceneBuilder builder, SceneBuildingUtil util) {
        BTWRSceneBuilder scene = new BTWRSceneBuilder(builder);
        scene.title("axle", "Relaying rotational force using Axles");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);

        BlockPos gaugePos = util.grid().at(0, 1, 2);
        Selection gauge = util.select().position(gaugePos);
        scene.world().showSection(gauge, Direction.UP);
        //scene.world().setKineticSpeed(gauge, 0);

        scene.idle(5);
        scene.world().showSection(util.select().position(5, 1, 2), Direction.DOWN);
        scene.idle(10);

        for (int i = 4; i >= 1; i--) {
            if (i == 2) {
                scene.rotateCameraY(70);
            }
            scene.idle(5);
            scene.world().showSection(util.select().position(i, 1, 2), Direction.DOWN);
        }

        //scene.world().setKineticSpeed(gauge, 64);
        scene.effects().indicateSuccess(gaugePos);
        scene.idle(10);
        scene.overlay().showText(1000)
                .placeNearTarget()
                .text("Axles will relay rotation in a straight line ")
                .pointAt(util.vector().of(3, 1.5, 2.5));

        scene.idle(20);
        scene.markAsFinished();
    }
     **/

    public static void axleAsRelay(SceneBuilder builder, SceneBuildingUtil util) {
        BTWRSceneBuilder scene = new BTWRSceneBuilder(builder);
        scene.title("axle", "Relaying rotational force using Axles");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);

        // Place the first axle as the source
        BlockPos axlePos = util.grid().at(0, 1, 2);
        Selection axle = util.select().position(axlePos);
        scene.world().showSection(axle, Direction.UP);
        //scene.world().setKineticSpeed(axle, 0);

        scene.idle(5);

        // Show the far end
        scene.world().showSection(util.select().position(5, 1, 2), Direction.DOWN);
        scene.idle(10);

        // Show intermediate axles
        for (int i = 4; i >= 1; i--) {
            if (i == 2)
                scene.rotateCameraY(70);
            scene.idle(5);
            scene.world().showSection(util.select().position(i, 1, 2), Direction.DOWN);
        }

        // Start rotation
        //scene.world().setKineticSpeed(axle, 64);
        scene.effects().indicateSuccess(axlePos);
        scene.idle(10);

        scene.overlay().showText(1000)
                .placeNearTarget()
                .text("Axles will relay rotation in a straight line.")
                .pointAt(util.vector().of(3, 1.5, 2.5));

        scene.idle(20);
        scene.markAsFinished();
    }
}