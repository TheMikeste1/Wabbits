package com.themikeste1.wabbits.core.entities;

import com.themikeste1.wabbits.core.entities.ai.goal.WabbitAttackGoal;
import net.minecraft.block.Block;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;


import javax.annotation.Nullable;
import java.util.Random;

public class WabbitEntity extends CreatureEntity {
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    
    public WabbitEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        jumpController = new JumpHelperController(this);
        moveController = new MoveHelperController(this);
        setMovementSpeed(0.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        applyEntityAI();
    }

    protected void applyEntityAI() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new WabbitAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp(WabbitEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WolfEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
       // this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
    }

    @Override
    public void updateAITasks() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            if (this.currentMoveTypeDuration == 0) {
                LivingEntity livingentity = this.getAttackTarget();
                if (livingentity != null && this.getDistanceSq(livingentity) < 16.0D) {
                    this.calculateRotationYaw(livingentity.posX, livingentity.posZ);
                    this.moveController.setMoveTo(livingentity.posX, livingentity.posY, livingentity.posZ, this.moveController.getSpeed());
                    this.startJumping();
                    this.wasOnGround = true;
                }
            }

            WabbitEntity.JumpHelperController jumpHelperController = (WabbitEntity.JumpHelperController)this.jumpController;
            if (!jumpHelperController.getIsJumping()) {
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vec3d vec3d = new Vec3d(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ());
                    if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                        vec3d = path.getPosition(this);
                    }

                    this.calculateRotationYaw(vec3d.x, vec3d.z);
                    this.startJumping();
                }
            } else if (!jumpHelperController.canJump()) {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * (double)(180F / (float)Math.PI)) - 90.0F;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.ENTITY_RABBIT_JUMP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RABBIT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_RABBIT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RABBIT_DEATH;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    //Combat
    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 2.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return super.attackEntityFrom(source, amount);
    }

    /*-------------------------------Movement-------------------------------*/
    public void setMovementSpeed(double newSpeed) {
        this.getNavigator().setSpeed(newSpeed);
        this.moveController.setMoveTo(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ(), newSpeed);
    }

    private void updateMoveTypeDuration() {
        if (this.moveController.getSpeed() < 2.2D) {
            this.currentMoveTypeDuration = 10;
        } else {
            this.currentMoveTypeDuration = 1;
        }

    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    @Override
    protected void jump() {
        super.jump();
        double d0 = this.moveController.getSpeed();
        if (d0 > 0.0D) {
            double d1 = func_213296_b(this.getMotion());
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vec3d(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
        }

    }

    public float getJumpCompletion(float baseline) {
        if (FMLEnvironment.dist.isDedicatedServer())
            throw new IllegalStateException("Only run this on client!");

        return jumpDuration == 0 ? 0.0F : ((float)jumpTicks + baseline) / (float)jumpDuration;
    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    private void enableJumpControl() {
        ((WabbitEntity.JumpHelperController)this.jumpController).setCanJump(true);
    }

    private void disableJumpControl() {
        ((WabbitEntity.JumpHelperController)this.jumpController).setCanJump(false);
    }

    @Override
    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @Override
    protected float getJumpUpwardsMotion() {
        if (!this.collidedHorizontally && (!this.moveController.isUpdating() || !(this.moveController.getY() > this.posY + 0.5D))) {
            Path path = this.navigator.getPath();
            if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                Vec3d vec3d = path.getPosition(this);
                if (vec3d.y > this.posY + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveController.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    //Spawning
    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        //In RabbitEntity, this function sets up the rabbit type (desert/snow)
        //as well as whether or not its a baby spawned by breeding.
        //
        //I'm not using it for anything right now, but I'm leaving it in case
        //I want it later.
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        return spawnDataIn;
    }

    /**
     * Determines whether or not a place is valid for spawning.
     *
     * @param wabbit
     * @param world
     * @param spawnReason
     * @param pos
     * @param random
     * @return True if it can spawn on that location.
     */
    public static boolean isValidSpawnPlacement(EntityType<WabbitEntity> wabbit, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        LOGGER.debug("Spawning Wabbit...");
        Block block = world.getBlockState(pos.down()).getBlock();
        return (block == Blocks.GRASS_BLOCK || block == Blocks.SNOW || block == Blocks.SAND) && world.getLightSubtracted(pos, 0) > 5;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            this.createRunningParticles();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleStatusUpdate(id);
        }

    }


    public class JumpHelperController extends JumpController {
        private final WabbitEntity wabbit;
        private boolean canJump;

        public JumpHelperController(WabbitEntity wabbit) {
            super(wabbit);
            this.wabbit = wabbit;
        }

        public boolean getIsJumping() {
            return this.isJumping;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        /**
         * Called to actually make the entity jump if isJumping is true.
         */
        public void tick() {
            if (this.isJumping) {
                this.wabbit.startJumping();
                this.isJumping = false;
            }

        }
    }

    static class MoveHelperController extends MovementController {
        private final WabbitEntity wabbit;
        private double nextJumpSpeed;

        public MoveHelperController(WabbitEntity wabbit) {
            super(wabbit);
            this.wabbit = wabbit;
        }

        public void tick() {
            if (this.wabbit.onGround && !this.wabbit.isJumping && !((WabbitEntity.JumpHelperController)this.wabbit.jumpController).getIsJumping()) {
                this.wabbit.setMovementSpeed(0.0D);
            } else if (this.isUpdating()) {
                this.wabbit.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        /**
         * Sets the speed and location to move to
         */
        public void setMoveTo(double x, double y, double z, double speedIn) {
            if (this.wabbit.isInWater()) {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }

        }
    }
}
