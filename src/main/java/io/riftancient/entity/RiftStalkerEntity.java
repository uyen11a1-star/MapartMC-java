package io.riftancient.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public final class RiftStalkerEntity extends Monster {
    private int phasePulse;

    public RiftStalkerEntity(EntityType<? extends RiftStalkerEntity> type, Level level) {
        super(type, level);
        this.xpReward = 12;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 38.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.FOLLOW_RANGE, 28.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.42F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25D, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        phasePulse++;
        if (level() instanceof ServerLevel server && phasePulse % 12 == 0) {
            server.sendParticles(ParticleTypes.END_ROD, getX(), getY() + getBbHeight() * 0.7D, getZ(), 1, .12D, .12D, .12D, .01D);
        }
        if (!level().isClientSide() && getTarget() != null && tickCount % 100 == 0 && distanceToSqr(getTarget()) < 144.0D && random.nextFloat() < 0.35F) {
            double x = getTarget().getX() + (random.nextDouble() - .5D) * 8.0D;
            double y = getTarget().getY();
            double z = getTarget().getZ() + (random.nextDouble() - .5D) * 8.0D;
            if (level().noCollision(getBoundingBox().move(x - getX(), y - getY(), z - getZ()))) {
                teleportTo(x, y, z);
                if (level() instanceof ServerLevel server) server.sendParticles(ParticleTypes.REVERSE_PORTAL, x, y + .5D, z, 22, .3D, .5D, .3D, .06D);
            }
        }
    }
}
