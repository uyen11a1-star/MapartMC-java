package io.riftancient.world;

import io.riftancient.block.RiftBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

public final class RuinGenerator {
    private RuinGenerator() {}

    public static void ensureTemple(ServerLevel level) {
        BlockPos spawn = level.getRespawnData().pos();
        BlockPos center = new BlockPos(spawn.getX(), Math.max(70, spawn.getY()), spawn.getZ());
        if (!level.getBlockState(center).isAir() && !level.getBlockState(center).is(RiftBlocks.TEMPLE_ALTAR)) return;
        buildTemple(level, center);
    }

    public static void buildTemple(ServerLevel level, BlockPos center) {
        for (int x = -9; x <= 9; x++) {
            for (int z = -9; z <= 9; z++) {
                double distance = Math.sqrt(x * x + z * z);
                if (distance <= 9) level.setBlock(center.offset(x, -1, z), RiftBlocks.RUNIC_BRICKS.defaultBlockState(), 3);
                if (distance <= 7) level.setBlock(center.offset(x, 0, z), RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
            }
        }
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                if (Math.abs(x) >= 4 || Math.abs(z) >= 4) level.setBlock(center.offset(x, 1, z), RiftBlocks.RUNIC_BRICKS.defaultBlockState(), 3);
            }
        }
        level.setBlock(center.above(1), RiftBlocks.TEMPLE_ALTAR.defaultBlockState(), 3);
        int[][] pillars = {{-6, -6}, {-6, 6}, {6, -6}, {6, 6}, {-6, 0}, {6, 0}, {0, -6}, {0, 6}};
        for (int[] pillar : pillars) {
            for (int y = 0; y < 6; y++) level.setBlock(center.offset(pillar[0], y, pillar[1]), RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
            level.setBlock(center.offset(pillar[0], 6, pillar[1]), RiftBlocks.AETHERITE_BLOCK.defaultBlockState(), 3);
            for (int dx = -1; dx <= 1; dx++) for (int dz = -1; dz <= 1; dz++) {
                if (Math.abs(dx) + Math.abs(dz) == 1) level.setBlock(center.offset(pillar[0] + dx, 0, pillar[1] + dz), RiftBlocks.RUNIC_BRICKS.defaultBlockState(), 3);
            }
        }
        for (int y = 2; y <= 5; y++) {
            for (int x = -7; x <= 7; x++) {
                level.setBlock(center.offset(x, y, -7), RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
                level.setBlock(center.offset(x, y, 7), RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
            }
            for (int z = -7; z <= 7; z++) {
                level.setBlock(center.offset(-7, y, z), RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
                level.setBlock(center.offset(7, y, z), RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
            }
        }
        level.setBlock(center.offset(0, 5, -7), Blocks.GLOWSTONE.defaultBlockState(), 3);
        level.setBlock(center.offset(0, 5, 7), Blocks.GLOWSTONE.defaultBlockState(), 3);
    }
}
