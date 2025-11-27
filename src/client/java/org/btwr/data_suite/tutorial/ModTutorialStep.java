package org.btwr.data_suite.tutorial;

import net.minecraft.client.tutorial.TutorialManager;
import net.minecraft.client.tutorial.TutorialStepHandler;

import java.util.function.Function;

// Step enumeration, if you’re using a custom enum
public enum ModTutorialStep {
    NONE(null),
    //GATHER_FIREWOOD(manager -> new GatherFirewoodTutorial(manager)),
    //BUILD_SAFE_HOLE(manager -> new BuildSafeHoleTutorial(manager))
    ;

    private final Function<TutorialManager, TutorialStepHandler> factory;

    ModTutorialStep(Function<TutorialManager, TutorialStepHandler> factory) {
        this.factory = factory;
    }

    public TutorialStepHandler create(TutorialManager mgr) {
        return factory.apply(mgr);
    }

}