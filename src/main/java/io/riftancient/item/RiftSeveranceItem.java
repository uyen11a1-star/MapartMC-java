package io.riftancient.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class RiftSeveranceItem extends Item {
    public RiftSeveranceItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();
        if (!(level instanceof ServerLevel serverLevel)) return;
        Vec3 start = attacker.position().add(0, attacker.getBbHeight() * 0.55, 0);
        Vec3 direction = attacker.getLookAngle().normalize();
        for (int i = 1; i <= 12; i++) {
            Vec3 point = start.add(direction.scale(i * 0.45));
            serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, point.x, point.y, point.z, 4, .08, .08, .08, .03);
            if (i % 3 == 0) serverLevel.sendParticles(ParticleTypes.END_ROD, point.x, point.y, point.z, 1, 0, 0, 0, 0);
        }
        AABB slashBox = attacker.getBoundingBox().expandTowards(direction.scale(4.5)).inflate(1.0, .7, 1.0);
        for (LivingEntity nearby : level.getEntitiesOfClass(LivingEntity.class, slashBox)) {
            if (nearby == attacker || nearby == target || !nearby.isAlive()) continue;
            nearby.hurt(attacker instanceof Player player ? level.damageSources().playerAttack(player) : level.damageSources().mobAttack(attacker), 8.0F);
        }
        serverLevel.playSound(null, attacker.blockPosition(), net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_SWEEP, net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, .65F);
    }
}
