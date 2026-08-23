package io.riftancient.entity;

import io.riftancient.RiftAncient;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class VorathEntity extends PathfinderMob {
    public static final int SLEEPING = 0;
    public static final int AWAKENING = 1;
    public static final int ACTIVE = 2;
    public static final int ENRAGED = 3;

    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(VorathEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_TICKS = SynchedEntityData.defineId(VorathEntity.class, EntityDataSerializers.INT);
    private int runeCooldown;
    private ItemEntity ceremonialBlade;
    private final ServerBossEvent bossBar = new ServerBossEvent(net.minecraft.network.chat.Component.literal("Vorath, the Sleeping Ruin"), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_20);

    public VorathEntity(EntityType<? extends VorathEntity> type, Level level) {
        super(type, level);
        this.xpReward = 80;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 600.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.ATTACK_DAMAGE, 22.0D)
                .add(Attributes.FOLLOW_RANGE, 56.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 14.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.75D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PHASE, SLEEPING);
        builder.define(ANIMATION_TICKS, 0);
    }

    public int getPhase() {
        return this.entityData.get(PHASE);
    }

    public float getAnimationProgress(float partialTick) {
        int ticks = this.entityData.get(ANIMATION_TICKS);
        return Math.min(1.0F, Math.max(0.0F, (ticks + partialTick) / 100.0F));
    }

    public void beginAwakening() {
        this.entityData.set(PHASE, AWAKENING);
        this.entityData.set(ANIMATION_TICKS, 0);
        this.setNoAi(true);
        this.setInvulnerable(true);
        this.setCustomName(net.minecraft.network.chat.Component.literal("Vorath, the Sleeping Ruin"));
        this.setCustomNameVisible(true);
        bossBar.setVisible(true);
        ceremonialBlade = new ItemEntity(level(), getX(), getY() + 1.2D, getZ(), new ItemStack(RiftAncient.DAWNWAKE_BLADE));
        ceremonialBlade.setNoPickUpDelay();
        ceremonialBlade.setUnlimitedLifetime();
        level().addFreshEntity(ceremonialBlade);
    }

    @Override
    public void tick() {
        super.tick();
        if (getPhase() == AWAKENING) {
            int ticks = this.entityData.get(ANIMATION_TICKS) + 1;
            this.entityData.set(ANIMATION_TICKS, ticks);
            if (ceremonialBlade != null && !ceremonialBlade.isRemoved()) {
                double angle = ticks * 0.16D;
                ceremonialBlade.setPos(getX() + Math.cos(angle) * (1.5D - ticks * .008D), getY() + 1.1D + ticks * .018D, getZ() + Math.sin(angle) * (1.5D - ticks * .008D));
                ceremonialBlade.setYRot(ticks * 12.0F);
            }
            if (level() instanceof ServerLevel server) {
                float radius = 1.2F + ticks * 0.025F;
                for (int i = 0; i < 10; i++) {
                    double angle = (Math.PI * 2.0D * i / 10.0D) + ticks * 0.07D;
                    server.sendParticles(ParticleTypes.REVERSE_PORTAL, getX() + Math.cos(angle) * radius, getY() + 0.6D + ticks * 0.015D, getZ() + Math.sin(angle) * radius, 2, 0.02D, 0.02D, 0.02D, 0.02D);
                }
                if (ticks == 1 || ticks == 35 || ticks == 70) server.playSound(null, blockPosition(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.HOSTILE, 1.7F, 0.45F + ticks * 0.004F);
                if (ticks == 100) {
                    if (ceremonialBlade != null) ceremonialBlade.discard();
                    ceremonialBlade = null;
                    setInvulnerable(false);
                    setNoAi(false);
                    entityData.set(PHASE, ACTIVE);
                    server.sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY() + 1.4D, getZ(), 1, 0, 0, 0, 0);
                    server.sendParticles(ParticleTypes.END_ROD, getX(), getY() + 1.2D, getZ(), 80, 2.5D, 1.8D, 2.5D, 0.08D);
                    server.playSound(null, blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 2.2F, 0.65F);
                }
            }
            return;
        }
        if (getPhase() < ACTIVE) return;
        if (!level().isClientSide()) bossBar.setProgress(Math.max(0.0F, getHealth() / getMaxHealth()));
        if (getHealth() <= getMaxHealth() * 0.35F && getPhase() != ENRAGED) {
            entityData.set(PHASE, ENRAGED);
            if (level() instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.FLAME, getX(), getY() + 1.5D, getZ(), 50, 1.5D, 1.0D, 1.5D, 0.08D);
                server.playSound(null, blockPosition(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.HOSTILE, 1.8F, 0.7F);
            }
        }
        if (runeCooldown > 0) runeCooldown--;
        if (!level().isClientSide() && getTarget() != null && runeCooldown == 0 && distanceToSqr(getTarget()) > 20.0D) {
            castRiftBurst(getTarget());
            runeCooldown = getPhase() == ENRAGED ? 55 : 85;
        }
    }

    private void castRiftBurst(net.minecraft.world.entity.LivingEntity target) {
        if (!(level() instanceof ServerLevel server)) return;
        Vec3 origin = position().add(0, 1.6D, 0);
        Vec3 delta = target.position().add(0, target.getBbHeight() * 0.55D, 0).subtract(origin).normalize();
        for (int i = 1; i <= 18; i++) {
            Vec3 point = origin.add(delta.scale(i * 0.65D));
            server.sendParticles(ParticleTypes.REVERSE_PORTAL, point.x, point.y, point.z, 5, .1D, .1D, .1D, .03D);
        }
        target.hurt(damageSources().mobAttack(this), getPhase() == ENRAGED ? 18.0F : 12.0F);
        server.playSound(null, blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.HOSTILE, 1.3F, .55F);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossBar.removePlayer(player);
    }

    @Override
    public void remove(RemovalReason reason) {
        bossBar.removeAllPlayers();
        if (ceremonialBlade != null) ceremonialBlade.discard();
        super.remove(reason);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
}
