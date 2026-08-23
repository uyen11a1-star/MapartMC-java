package io.riftancient.block;

import io.riftancient.RiftAncient;
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

    public static boolean createFrame(Level level, BlockPos anchor) {
        int width = 4;
        int height = 5;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                boolean edge = x == 0 || x == width - 1 || y == 0 || y == height - 1;
                BlockPos pos = anchor.offset(x, y, 0);
                if (edge && !level.getBlockState(pos).is(RiftBlocks.ANCIENT_RIFTSTONE)) return false;
                if (!edge && !level.getBlockState(pos).isAir()) return false;
            }
        }
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                level.setBlock(anchor.offset(x, y, 0), RiftBlocks.RIFT_PORTAL.defaultBlockState(), 3);
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
        BlockPos spawn = destination.getRespawnData().pos().above(2);
        return new TeleportTransition(destination, Vec3.atBottomCenterOf(spawn), Vec3.ZERO, entity.getYRot(), entity.getXRot(), TeleportTransition.PLACE_PORTAL_TICKET);
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
