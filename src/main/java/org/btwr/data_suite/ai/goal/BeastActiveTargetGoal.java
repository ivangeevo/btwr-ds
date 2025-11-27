package org.btwr.data_suite.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;

public class BeastActiveTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {

    protected double targetFollowRange;

    public BeastActiveTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, double targetFollowRange) {
        super(mob, targetClass, checkVisibility);
        this.targetFollowRange = targetFollowRange;
    }

}