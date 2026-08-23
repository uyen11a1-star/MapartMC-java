package io.riftancient.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public final class RiftSeveranceItem extends Item {
    public RiftSeveranceItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND || player.getCooldowns().isOnCooldown(stack)) return InteractionResult.PASS;
        Vec3 origin = player.position().add(0, player.getBbHeight() * .55D, 0);
        Vec3 forward = player.getLookAngle().normalize();
        if (level instanceof ServerLevel server) {
            for (int i = 1; i <= 14; i++) {
                double arc = (i - 7.5D) * .11D;
                Vec3 point = origin.add(forward.scale(2.0D + i * .28D)).add(0, Math.sin(arc * 2.0D) * .45D, 0);
                server.sendParticles(ParticleTypes.REVERSE_PORTAL, point.x, point.y, point.z, 7, .08D, .08D, .08D, .02D);
                server.sendParticles(ParticleTypes.END_ROD, point.x, point.y, point.z, 1, 0, 0, 0, 0);
            }
            AABB slashBox = player.getBoundingBox().expandTowards(forward.scale(5.5D)).inflate(1.2D, .9D, 1.2D);
            List<LivingEntity> victims = level.getEntitiesOfClass(LivingEntity.class, slashBox);
            for (LivingEntity victim : victims) {
                if (victim == player || !victim.isAlive()) continue;
                victim.hurt(level.damageSources().playerAttack(player), 18.0F);
                victim.knockback(1.0D, player.getX() - victim.getX(), player.getZ() - victim.getZ());
            }
            server.playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_SWEEP, net.minecraft.sounds.SoundSource.PLAYERS, 1.8F, .45F);
        }
        player.getCooldowns().addCooldown(stack, 80);
        player.swing(hand);
        return InteractionResult.SUCCESS;
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
