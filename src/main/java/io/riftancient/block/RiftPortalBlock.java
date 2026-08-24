package io.riftancient.block;

import io.riftancient.RiftAncient;
import io.riftancient.world.RuinGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

public class RiftPortalBlock extends NetherPortalBlock {
    public RiftPortalBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public static boolean createFrame(Level level, BlockPos clicked) {
        for (int xOffset = -3; xOffset <= 0; xOffset++) {
            for (int yOffset = -4; yOffset <= 0; yOffset++) {
                if (tryCreateFrame(level, clicked.offset(xOffset, yOffset, 0), true)) return true;
                if (tryCreateFrame(level, clicked.offset(0, yOffset, xOffset), false)) return true;
            }
        }
        return false;
    }

    private static boolean tryCreateFrame(Level level, BlockPos anchor, boolean alongX) {
        int width = 4;
        int height = 5;
        int dx = alongX ? 1 : 0;
        int dz = alongX ? 0 : 1;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean edge = x == 0 || x == width - 1 || y == 0 || y == height - 1;
                BlockPos pos = anchor.offset(dx * x, y, dz * x);
                if (edge && !level.getBlockState(pos).is(RiftBlocks.ANCIENT_RIFTSTONE)) return false;
                if (!edge && !level.getBlockState(pos).isAir()) return false;
            }
        }
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                level.setBlock(anchor.offset(dx * x, y, dz * x), RiftBlocks.RIFT_PORTAL.defaultBlockState(), 3);
            }
        }
        return true;
    }

    @Override
    public int getPortalTransitionTime(ServerLevel level, Entity entity) {
        return 15;
    }

    @Override
    public TeleportTransition getPortalDestination(ServerLevel level, Entity entity, BlockPos pos) {
        ServerLevel destination = level.getServer().getLevel(level.dimension().equals(RiftAncient.AETHEL_RUINIUM) ? Level.OVERWORLD : RiftAncient.AETHEL_RUINIUM);
        if (destination == null) return null;
        BlockPos spawn = destination.getRespawnData().pos();
        if (destination.dimension().equals(RiftAncient.AETHEL_RUINIUM)) {
            spawn = new BlockPos(0, 5, 0);
            RuinGenerator.ensureSpawnPlatform(destination, spawn);
            RuinGenerator.ensureTemple(destination);
        }
        BlockPos safeBlock = destination.getWorldBorder().clampToBounds(spawn.getX(), spawn.getY(), spawn.getZ());
        Vec3 safeSpawn = Vec3.atBottomCenterOf(safeBlock.above());
        return new TeleportTransition(destination, safeSpawn, Vec3.ZERO, entity.getYRot(), entity.getXRot(), TeleportTransition.PLACE_PORTAL_TICKET);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(2) == 0) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            level.addParticle(ParticleTypes.PORTAL, x, y, z, random.nextGaussian() * .02, random.nextGaussian() * .02, random.nextGaussian() * .02);
            if (random.nextInt(20) == 0) level.playLocalSound(x, y, z, net.minecraft.sounds.SoundEvents.PORTAL_AMBIENT, net.minecraft.sounds.SoundSource.BLOCKS, .35F, .8F + random.nextFloat() * .4F, false);
        }
    }
}
