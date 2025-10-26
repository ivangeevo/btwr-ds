package org.ivangeevo.btwr_ds.entity.entities;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BeastEntity extends PathAwareEntity {

    private static final float MOVE_SPEED_AGGRESSIVE = 0.45F;
    private static final float MOVE_SPEED_PASSIVE = 0.3F;

    public int howlingCountdown = 0;
    public int heardHowlCountdown = 0;

    private boolean furWet;

    public BeastEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.refreshPositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
        this.calculateDimensions(); // force update early
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        //this.goalSelector.add(1, new TameableEntity.TameableEscapeDangerGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        //this.goalSelector.add(2, new SitGoal(this));
        //this.goalSelector.add(3, new WolfEntity.AvoidLlamaGoal(this, LlamaEntity.class, 24.0F, 1.5, 1.5));
        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0, true));
        //this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        //this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        //this.goalSelector.add(9, new WolfBegGoal(this, 8.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
        //this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
        //this.targetSelector.add(4, new ActiveTargetGoal(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        //this.targetSelector.add(5, new UntamedActiveTargetGoal(this, AnimalEntity.class, false, FOLLOW_TAMED_PREDICATE));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
        this.targetSelector.add(7, new ActiveTargetGoal<>(this, AbstractSkeletonEntity.class, false));
        //this.targetSelector.add(8, new UniversalAngerGoal<>(this, true));
    }


    public static DefaultAttributeContainer.Builder createBeastAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVE_SPEED_PASSIVE)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        System.out.println("Beast took damage: " + amount + " from " + source.getName());
        return super.damage(source, amount);
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



    // What to add here?
    //@Override
    //public int getMeleeAttackStrength(Entity target) {
        //return 6;
    //}

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.5F - this.getWorld().getLightLevel(pos);
    }

    /**
    @Override
    public boolean isInsideWall() {
        if (this.noClip) return false;

        // Eye box like vanilla, but with a small epsilon shrink
        float f = this.getDimensions(this.getPose()).width() * 0.8F;
        double shrink = 0.002; // vanilla uses 1e-6, we relax slightly

        Box box = Box.of(this.getEyePos(), f - shrink, 1.0E-6, f - shrink);

        // Only count as inside wall if at least one *solid* voxel fully overlaps, not just grazes
        return BlockPos.stream(box).anyMatch(pos -> {
            var state = this.getWorld().getBlockState(pos);
            if (state.isAir() || !state.shouldSuffocate(this.getWorld(), pos)) return false;

            var shape = state.getCollisionShape(this.getWorld(), pos)
                    .offset(pos.getX(), pos.getY(), pos.getZ());

            // Require >99% intersection, not just any overlap

            return VoxelShapes.cuboid(box).getBoundingBoxes().stream()
                    .anyMatch(b -> b.intersects(shape.getBoundingBox()));
        });
    }
     **/

    private void debugInsideWall() {
        Vec3d eyePos = this.getEyePos();
        double eyeX = eyePos.x;
        double eyeY = eyePos.y;
        double eyeZ = eyePos.z;

        float f = this.getDimensions(this.getPose()).width() * 0.8F;
        Box eyeBox = Box.of(eyePos, f, 1.0E-6, f);

        System.out.println("DEBUG EyePos: " + eyePos + " eyeBox: " + eyeBox);

        BlockPos.stream(eyeBox).forEach(pos -> {
            var state = this.getWorld().getBlockState(pos);
            boolean suff = !state.isAir() && state.shouldSuffocate(this.getWorld(), pos);
            var shape = state.getCollisionShape(this.getWorld(), pos).offset(pos.getX(), pos.getY(), pos.getZ());
            boolean intersects = VoxelShapes.matchesAnywhere(shape, VoxelShapes.cuboid(eyeBox), BooleanBiFunction.AND);

            System.out.println("  pos=" + pos +
                    " block=" + state.getBlock().getTranslationKey() +
                    " suffocates=" + suff +
                    " intersects=" + intersects +
                    " collisionBoxes=" + shape.getBoundingBoxes().size());
        });
    }

    @Override
    public void tick() {

        if (this.age < 200 && this.getWorld() instanceof ServerWorld) { // limit spam
            debugInsideWall();
        }

        System.out.println("Beast BB: " + this.getBoundingBox());
        System.out.println("Beast dimensions: " + this.getDimensions(this.getPose()));

        if (this.getWorld().isClient) {
            howlingCountdown = Math.max(0, howlingCountdown -1);
        } else {
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
            // 80 is the same as in the goal, we set it separately for now
            howlingCountdown = 80;
        } else {
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
    public void setItemUseTime(int i) {

    }

    @Override
    public void onKickedByCow(CowEntity cowEntity) {

    }


}
