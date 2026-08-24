package io.riftancient.item;

import io.riftancient.block.RiftBlocks;
import io.riftancient.block.RiftPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class RiftSigilItem extends Item {
    public RiftSigilItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clicked = context.getClickedPos();
        if (level.isClientSide() || !level.getBlockState(clicked).is(RiftBlocks.ANCIENT_RIFTSTONE)) {
            return InteractionResult.PASS;
        }
        if (!RiftPortalBlock.createFrame(level, clicked)) {
            return InteractionResult.PASS;
        }
        if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }
        level.levelEvent(2001, clicked, 0);
        level.playSound(null, clicked, SoundEvents.PORTAL_TRIGGER, SoundSource.BLOCKS, 1.0F, 0.85F);
        return InteractionResult.SUCCESS;
    }
}
