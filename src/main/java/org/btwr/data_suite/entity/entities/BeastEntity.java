package org.btwr.data_suite.entity.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class BeastEntity extends PathAwareEntity {

    private static final float MOVE_SPEED_AGGRESSIVE = 0.45F;
    private static final float MOVE_SPEED_PASSIVE = 0.3F;

    public int howlingCountdown = 0;
    public int heardHowlCountdown = 0;

    private boolean furWet;

    private static final Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER = difficulty -> difficulty == Difficulty.HARD;
    private final BreakDoorGoal breakDoorsGoal = new BreakDoorGoal(this, DOOR_BREAK_DIFFICULTY_CHECKER);

    public BeastEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createBeastAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVE_SPEED_AGGRESSIVE)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                ;
    }

    @Override
    protected void initGoals() {

        //getNavigator().setBreakDoors( true );

        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        // TODO: Add this goal from BTW
        //tasks.addTask( 1, new ZombieBreakBarricadeBehavior( this ) );
        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, MOVE_SPEED_PASSIVE / MOVE_SPEED_AGGRESSIVE));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        this.targetSelector.add(2, new AttackGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());

        //TODO: Make the follow range for the targetPredicate in ActiveTargetGoal to be set differently for each target
        // This might mean making a new target class that accepts a custom follow range
        // Make the range 32 for players & llamas and 16 for all other target animals

        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, VillagerEntity.class, false));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, ChickenEntity.class, false));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, CowEntity.class, false));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PigEntity.class, false));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, SheepEntity.class, false));

        // Made them "hate" llamas, because a wolf usually flees from them
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, LlamaEntity.class, false));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WOLF_GROWL;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_GROWL;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 3.0F;
    }

    @Override
    public float getSoundPitch() {
        return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.05F + 0.55F;
    }

    @Override
    protected void knockback(LivingEntity target) {
        // override to remove knockback on the beast
    }

    @Override
    public double getEyeY() {
        return this.getY() + this.getHeight() * 0.85f; // tweak 0.8–0.85 until suffocation stops
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.5F - this.getWorld().getLightLevel(pos);
    }

    @Override
    public void tick() {
        if (this.getWorld().isClient) {
            howlingCountdown = Math.max(0, howlingCountdown -1);
        }
        else {
            heardHowlCountdown = Math.max(0, heardHowlCountdown - 1);

            if (this.getWorld().isDay()) {
                float brightness = getBrightness();

                BlockPos posToCheck = new BlockPos(
                        MathHelper.floor(this.getPos().getX()),
                        MathHelper.floor(this.getPos().getY()),
                        MathHelper.floor(this.getPos().getZ())
                );

                if (brightness > 0.5F && this.getRandom().nextFloat() * 30F < (brightness - 0.4F) * 2.0F &&
                        this.getWorld().isSkyVisible(posToCheck))
                {
                    this.setOnFireFor(8);
                }
            }
        }

        super.tick();
    }

    public float getBrightness() {
        World world = this.getWorld();

        // Optional: sample a bit above the entity's mid-height, like the old 0.66D offset
        double sampleY = this.getY() + this.getHeight() * 0.66D;
        BlockPos samplePos = BlockPos.ofFloored(this.getX(), sampleY, this.getZ());

        if (!world.isChunkLoaded(samplePos)) {
            return 0.0F;
        }

        // Get combined brightness (sky and block light)
        int blockLight = world.getLightLevel(LightType.BLOCK, samplePos);
        int skyLight = world.getLightLevel(LightType.SKY, samplePos);

        // Vanilla combines them into a 0.0–1.0 brightness value
        return (Math.max(blockLight, skyLight)) / 15.0F;
    }


    //------------- Class Specific Methods ------------//


    public float getTailRotation() {
        return 1.5393804F;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 10) {
            //howlingCountdown = WolfHowlBehavior.HOWL_DURATION;
            // 80 is the same as in the WolfHowlBehavior(goal), we set it separately for now
            howlingCountdown = 80;
        }
        else {
            super.handleStatus(status);
        }
    }

    /**
    public float getHeadRotationPointOffset(float par1)
    {
        if (howlingCountdown > 0 )
        {
            float fTiltFraction = 1F;

            if (howlingCountdown < 5 )
            {
                fTiltFraction = (float) howlingCountdown / 5F;
            }
            else if (howlingCountdown > WolfHowlBehavior.HOWL_DURATION - 10 )
            {
                fTiltFraction = (float)(WolfHowlBehavior.HOWL_DURATION + 1 - howlingCountdown) / 10F;
            }

            return fTiltFraction * -0.5F;
        }

        return 0F;
    }

    public float getHeadRotation(float par1)
    {
        if (howlingCountdown > 0 )
        {
            float fTiltFraction = 1F;

            if (howlingCountdown < 5 )
            {
                fTiltFraction = (float) howlingCountdown / 5F;
            }
            else if (howlingCountdown > WolfHowlBehavior.HOWL_DURATION - 10 )
            {
                fTiltFraction = (float)(WolfHowlBehavior.HOWL_DURATION + 1 - howlingCountdown) / 10F;
            }

            return fTiltFraction * -((float)Math.PI / 5F);
        }
        else
        {
            return rotationPitch / (180F / (float)Math.PI);
        }
    }
     **/

    //----------- Client Side Functionality -----------//


    @Override
    public void onKickedByCow(CowEntity cowEntity) {

    }

}