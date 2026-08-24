package io.riftancient.block;

import io.riftancient.RiftAncient;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class RiftBlocks {
    private RiftBlocks() {}

    public static final Block ANCIENT_RIFTSTONE = register("ancient_riftstone", new Block(properties("ancient_riftstone").strength(7.0F, 1200.0F).sound(SoundType.STONE).requiresCorrectToolForDrops().lightLevel(state -> 4)));
    public static final Block RUNIC_BRICKS = register("runic_bricks", new Block(properties("runic_bricks").strength(6.0F, 1200.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()));
    public static final Block RIFT_PORTAL = register("rift_portal", new RiftPortalBlock(properties("rift_portal").noCollision().strength(-1.0F).lightLevel(state -> 12).sound(SoundType.GLASS)));
    public static final Block AETHERITE_ORE = register("aetherite_ore", new Block(properties("aetherite_ore").strength(5.0F, 30.0F).sound(SoundType.STONE).requiresCorrectToolForDrops().lightLevel(state -> 3)));
    public static final Block AETHERITE_BLOCK = register("aetherite_block", new Block(properties("aetherite_block").strength(6.0F, 1200.0F).sound(SoundType.METAL).requiresCorrectToolForDrops().lightLevel(state -> 5)));
    public static final Block TEMPLE_ALTAR = register("temple_altar", new TempleAltarBlock(properties("temple_altar").strength(8.0F, 1200.0F).sound(SoundType.STONE).requiresCorrectToolForDrops().lightLevel(state -> 7)));

    private static BlockBehaviour.Properties properties(String name) {
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, RiftAncient.id(name)));
    }

    private static Block register(String name, Block block) {
        Block registered = Registry.register(BuiltInRegistries.BLOCK, RiftAncient.id(name), block);
        if (!name.equals("rift_portal")) {
            Registry.register(BuiltInRegistries.ITEM, RiftAncient.id(name), new BlockItem(registered, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, RiftAncient.id(name)))));
        }
        return registered;
    }

    public static void register() {
        RiftAncient.LOGGER.info("Registered Aethel-Ruinium blocks");
    }
}
