package io.riftancient.world;

import io.riftancient.block.RiftBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class RuinGenerator {
    private RuinGenerator() {}

    public static void ensureTemple(ServerLevel level) {
        BlockPos spawn = level.getRespawnData().pos();
        int baseY = Math.max(5, spawn.getY());
        BlockPos center = new BlockPos(spawn.getX(), baseY, spawn.getZ());
        if (!level.getBlockState(center).isAir() && !level.getBlockState(center).is(RiftBlocks.TEMPLE_ALTAR)) return;
        buildTemple(level, center);
        buildObelisk(level, center.offset(28, 7, -18), 9);
        buildObelisk(level, center.offset(-31, 3, 24), 7);
        buildFallenArch(level, center.offset(24, 0, 27));
        buildFallenArch(level, center.offset(-26, 0, -28));
        buildAetheriteShrine(level, center.offset(0, 9, 42));
    }

    public static void ensureNearbyRuin(ServerLevel level, BlockPos playerPos) {
        int gridX = Math.floorDiv(playerPos.getX(), 64);
        int gridZ = Math.floorDiv(playerPos.getZ(), 64);
        for (int gx = gridX - 1; gx <= gridX + 1; gx++) {
            for (int gz = gridZ - 1; gz <= gridZ + 1; gz++) {
                if (gx == 0 && gz == 0) continue;
                BlockPos base = new BlockPos(gx * 64 + 16, 5, gz * 64 + 16);
                if (!level.getBlockState(base).isAir()) continue;
                int type = Math.floorMod(gx * 31 + gz * 17, 3);
                level.setBlock(base, RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
                if (type == 0) buildObelisk(level, base, 7 + Math.floorMod(gx + gz, 4));
                else if (type == 1) buildFallenArch(level, base);
                else buildAetheriteShrine(level, base);
            }
        }
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

    private static void buildObelisk(ServerLevel level, BlockPos base, int height) {
        for (int x = -2; x <= 2; x++) for (int z = -2; z <= 2; z++) {
            if (Math.abs(x) + Math.abs(z) <= 3) level.setBlock(base.offset(x, -1, z), RiftBlocks.RUNIC_BRICKS.defaultBlockState(), 3);
        }
        for (int y = 0; y < height; y++) {
            Block block = y == height - 1 ? RiftBlocks.AETHERITE_BLOCK : RiftBlocks.ANCIENT_RIFTSTONE;
            level.setBlock(base.above(y), block.defaultBlockState(), 3);
            if (y > 1 && y % 2 == 0) {
                level.setBlock(base.offset(1, y, 0), RiftBlocks.RUNIC_BRICKS.defaultBlockState(), 3);
                level.setBlock(base.offset(-1, y, 0), RiftBlocks.RUNIC_BRICKS.defaultBlockState(), 3);
            }
        }
    }

    private static void buildFallenArch(ServerLevel level, BlockPos base) {
        for (int x = -5; x <= 5; x++) {
            int height = 2 + (int) Math.round(3.5D - Math.abs(x) * .55D);
            for (int y = 0; y <= height; y++) {
                if (y == height || Math.abs(x) == 5) level.setBlock(base.offset(x, y, 0), RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
            }
        }
        level.setBlock(base.offset(0, 1, 0), RiftBlocks.AETHERITE_BLOCK.defaultBlockState(), 3);
    }

    private static void buildAetheriteShrine(ServerLevel level, BlockPos base) {
        for (int x = -4; x <= 4; x++) for (int z = -4; z <= 4; z++) {
            if (Math.abs(x) + Math.abs(z) <= 5) level.setBlock(base.offset(x, -1, z), RiftBlocks.RUNIC_BRICKS.defaultBlockState(), 3);
        }
        for (int x = -2; x <= 2; x++) for (int z = -2; z <= 2; z++) {
            if (Math.abs(x) == 2 || Math.abs(z) == 2) level.setBlock(base.offset(x, 0, z), RiftBlocks.ANCIENT_RIFTSTONE.defaultBlockState(), 3);
        }
        level.setBlock(base, RiftBlocks.AETHERITE_BLOCK.defaultBlockState(), 3);
        level.setBlock(base.above(), RiftBlocks.TEMPLE_ALTAR.defaultBlockState(), 3);
    }
}
