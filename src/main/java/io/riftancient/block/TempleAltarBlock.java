package io.riftancient.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TempleAltarBlock extends Block {
    public TempleAltarBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(3) == 0) {
            level.addParticle(ParticleTypes.END_ROD, pos.getX() + .5 + (random.nextDouble() - .5) * .8, pos.getY() + 1.1, pos.getZ() + .5 + (random.nextDouble() - .5) * .8, 0, .02, 0);
        }
        if (random.nextInt(8) == 0) {
            level.addParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + .5, pos.getY() + 1.05, pos.getZ() + .5, 0, .04, 0);
        }
    }
}
