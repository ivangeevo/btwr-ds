package org.btwr.data_suite.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;

import java.util.function.Predicate;

public class BeastActiveTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {

    protected double targetFollowRange;

    public BeastActiveTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, double targetFollowRange) {
        super(mob, targetClass, checkVisibility);
        this.targetFollowRange = targetFollowRange;
    }

    public BeastActiveTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, Predicate<LivingEntity> targetPredicate, double targetFollowRange) {
        super(mob, targetClass, 10, checkVisibility, false, targetPredicate);
        this.targetFollowRange = targetFollowRange;
    }

    @Override
    protected Box getSearchBox(double distance) {
        return super.getSearchBox(targetFollowRange);
    }
}