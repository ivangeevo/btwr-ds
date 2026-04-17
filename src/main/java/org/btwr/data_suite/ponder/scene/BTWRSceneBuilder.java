package org.btwr.data_suite.ponder.scene;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.PonderSceneBuilder;

public class BTWRSceneBuilder extends PonderSceneBuilder {
    private final WorldInstructions world;

    public BTWRSceneBuilder(SceneBuilder baseSceneBuilder) {
        this(baseSceneBuilder.getScene());
    }

    private BTWRSceneBuilder(PonderScene ponderScene) {
        super(ponderScene);
        world = new WorldInstructions();
    }

    public class WorldInstructions extends PonderWorldInstructions {}
}