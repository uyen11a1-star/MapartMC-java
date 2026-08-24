package io.riftancient;

import io.riftancient.block.RiftBlocks;
import io.riftancient.block.RiftPortalBlock;
import io.riftancient.entity.RiftEntities;
import io.riftancient.entity.RiftStalkerEntity;
import io.riftancient.entity.VorathEntity;
import io.riftancient.item.RiftSeveranceItem;
import io.riftancient.world.RuinGenerator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;

import net.minecraft.world.entity.EquipmentSlot;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RiftAncient implements ModInitializer {
    public static final String MOD_ID = "riftancient";
    public static final String DISPLAY_NAME = "RiftAncient.mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ResourceKey<Level> AETHEL_RUINIUM = ResourceKey.create(Registries.DIMENSION, id("aethel_ruinium"));

    public static final Item RIFT_SIGIL = registerItem("rift_sigil", new Item(itemProperties("rift_sigil").stacksTo(16)));
    public static final Item AETHERITE_SHARD = registerItem("aetherite_shard", new Item(itemProperties("aetherite_shard")));
    public static final Item AETHERITE_INGOT = registerItem("aetherite_ingot", new Item(itemProperties("aetherite_ingot")));
    public static final Item DAWNWAKE_BLADE = registerItem("dawnwake_blade", new Item(itemProperties("dawnwake_blade").stacksTo(1).sword(ToolMaterial.NETHERITE, 5.0F, -2.4F)));
    public static final Item RUIN_HEART = registerItem("ruin_heart", new Item(itemProperties("ruin_heart").stacksTo(1).fireResistant()));
    public static final Item SEVERANCE = registerItem("severance", new RiftSeveranceItem(itemProperties("severance").stacksTo(1).fireResistant().sword(ToolMaterial.NETHERITE, 14.0F, -2.1F)));

    public static final Item AETHERITE_HELMET = registerItem("aetherite_helmet", new Item(itemProperties("aetherite_helmet").fireResistant().humanoidArmor(ArmorMaterials.NETHERITE, ArmorType.HELMET)));
    public static final Item AETHERITE_CHESTPLATE = registerItem("aetherite_chestplate", new Item(itemProperties("aetherite_chestplate").fireResistant().humanoidArmor(ArmorMaterials.NETHERITE, ArmorType.CHESTPLATE)));
    public static final Item AETHERITE_LEGGINGS = registerItem("aetherite_leggings", new Item(itemProperties("aetherite_leggings").fireResistant().humanoidArmor(ArmorMaterials.NETHERITE, ArmorType.LEGGINGS)));
    public static final Item AETHERITE_BOOTS = registerItem("aetherite_boots", new Item(itemProperties("aetherite_boots").fireResistant().humanoidArmor(ArmorMaterials.NETHERITE, ArmorType.BOOTS)));
    public static final Item AETHERITE_PICKAXE = registerItem("aetherite_pickaxe", new Item(itemProperties("aetherite_pickaxe").pickaxe(ToolMaterial.NETHERITE, 4.0F, -2.6F)));
    public static final Item AETHERITE_AXE = registerItem("aetherite_axe", new Item(itemProperties("aetherite_axe").axe(ToolMaterial.NETHERITE, 7.0F, -2.8F)));
    public static final Item AETHERITE_SHOVEL = registerItem("aetherite_shovel", new Item(itemProperties("aetherite_shovel").shovel(ToolMaterial.NETHERITE, 3.0F, -3.0F)));
    public static final Item AETHERITE_HOE = registerItem("aetherite_hoe", new Item(itemProperties("aetherite_hoe").hoe(ToolMaterial.NETHERITE, 0.0F, -3.0F)));

    public static final CreativeModeTab RIFT_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id("rift_tab"), CreativeModeTab.builder(CreativeModeTab.Row.TOP, 7)
            .title(Component.translatable("itemGroup.riftancient"))
            .icon(() -> new ItemStack(RIFT_SIGIL))
            .displayItems((parameters, output) -> {
                output.accept(RIFT_SIGIL);
                output.accept(AETHERITE_SHARD);
                output.accept(AETHERITE_INGOT);
                output.accept(DAWNWAKE_BLADE);
                output.accept(RUIN_HEART);
                output.accept(SEVERANCE);
                output.accept(AETHERITE_HELMET);
                output.accept(AETHERITE_CHESTPLATE);
                output.accept(AETHERITE_LEGGINGS);
                output.accept(AETHERITE_BOOTS);
                output.accept(AETHERITE_PICKAXE);
                output.accept(AETHERITE_AXE);
                output.accept(AETHERITE_SHOVEL);
                output.accept(AETHERITE_HOE);
                output.accept(RiftBlocks.ANCIENT_RIFTSTONE.asItem());
                output.accept(RiftBlocks.RUNIC_BRICKS.asItem());
                output.accept(RiftBlocks.RIFT_PORTAL.asItem());
                output.accept(RiftBlocks.AETHERITE_ORE.asItem());
                output.accept(RiftBlocks.AETHERITE_BLOCK.asItem());
                output.accept(RiftBlocks.TEMPLE_ALTAR.asItem());
            })
            .build());

    private static Item.Properties itemProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id(name)));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, id(name), item);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        RiftEntities.register();
        RiftSounds.register();
        RiftBlocks.register();

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClientSide()) return InteractionResult.PASS;
            BlockPos clicked = hitResult.getBlockPos();
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(RIFT_SIGIL) && world.getBlockState(clicked).is(RiftBlocks.ANCIENT_RIFTSTONE)) {
                if (RiftPortalBlock.createFrame(world, clicked)) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    world.levelEvent(2001, clicked, 0);
                    return InteractionResult.SUCCESS;
                }
            }
            if (stack.is(DAWNWAKE_BLADE) && world.getBlockState(clicked).is(RiftBlocks.TEMPLE_ALTAR)) {
                if (world instanceof ServerLevel serverLevel && serverLevel.getEntitiesOfClass(VorathEntity.class, new net.minecraft.world.phys.AABB(clicked).inflate(48.0D)).isEmpty()) {
                    if (player instanceof ServerPlayer serverPlayer) awakenVorath(serverLevel, clicked, serverPlayer);
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if (!(entity.level() instanceof ServerLevel level) || !level.dimension().equals(AETHEL_RUINIUM)) return;
            if (entity instanceof VorathEntity) {
                entity.spawnAtLocation(level, new ItemStack(RUIN_HEART, 1), 0.0F);
                level.explode(null, entity.getX(), entity.getY(), entity.getZ(), 2.5F, Level.ExplosionInteraction.TNT);
                return;
            }
            if (entity instanceof RiftStalkerEntity || entity.getType() == EntityType.HUSK || entity.getType() == EntityType.ENDERMAN || entity.getType() == EntityType.WITHER_SKELETON) {
                if (level.random.nextFloat() < 0.35F) entity.spawnAtLocation(level, new ItemStack(AETHERITE_SHARD, 1 + level.random.nextInt(2)), 0.0F);
            }
        });

        ServerTickEvents.END_WORLD_TICK.register(level -> {
            if (!level.dimension().equals(AETHEL_RUINIUM)) return;
            if (level.getGameTime() % 200L == 0L) {
                RuinGenerator.ensureTemple(level);
                for (ServerPlayer player : level.players()) RuinGenerator.ensureNearbyRuin(level, player.blockPosition());
            }
            if (level.getGameTime() % 100L == 0L) spawnRiftHostiles(level);
            for (ServerPlayer player : level.players()) {
                if (level.getGameTime() % 40L == 0L) {
                    boolean fullAetherite = player.getItemBySlot(EquipmentSlot.HEAD).is(AETHERITE_HELMET) && player.getItemBySlot(EquipmentSlot.CHEST).is(AETHERITE_CHESTPLATE) && player.getItemBySlot(EquipmentSlot.LEGS).is(AETHERITE_LEGGINGS) && player.getItemBySlot(EquipmentSlot.FEET).is(AETHERITE_BOOTS);
                    if (fullAetherite) {
                        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.RESISTANCE, 60, 0, true, false));
                        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.NIGHT_VISION, 220, 0, true, false));
                    }
                    if (isAetheriteTool(player.getMainHandItem())) {
                        player.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.HASTE, 60, 0, true, false));
                    }
                }
                if (level.getGameTime() % 5L == 0L && player.getMainHandItem().is(SEVERANCE)) {
                    level.sendParticles(net.minecraft.core.particles.ParticleTypes.PORTAL, player.getX(), player.getY() + 1.0, player.getZ(), 3, .25, .5, .25, .02);
                }
            }
        });

        LOGGER.info("{} initialized — welcome to Aethel-Ruinium", DISPLAY_NAME);
    }

    private static boolean isAetheriteTool(ItemStack stack) {
        return stack.is(AETHERITE_PICKAXE) || stack.is(AETHERITE_AXE) || stack.is(AETHERITE_SHOVEL) || stack.is(AETHERITE_HOE);
    }

    private static void spawnRiftHostiles(ServerLevel level) {
        for (ServerPlayer player : level.players()) {
            if (level.random.nextFloat() > 0.22F) continue;
            BlockPos origin = player.blockPosition().offset(level.random.nextInt(17) - 8, 0, level.random.nextInt(17) - 8);
            if (!level.getBlockState(origin).isAir()) origin = origin.above();
            if (!level.getBlockState(origin).isAir()) continue;
            EntityType<?> type = level.random.nextBoolean() ? EntityType.HUSK : RiftEntities.RIFT_STALKER;
            Mob mob = (Mob) type.create(level, EntitySpawnReason.NATURAL);
            if (mob == null) continue;
            mob.setPos(origin.getX() + .5, origin.getY(), origin.getZ() + .5);
            mob.setCustomName(Component.literal(type == EntityType.HUSK ? "Ashen Sentinel" : "Rift Stalker"));
            mob.setCustomNameVisible(false);
            mob.setPersistenceRequired();
            level.addFreshEntity(mob);
        }
    }

    private static void awakenVorath(ServerLevel level, BlockPos altar, ServerPlayer player) {
        VorathEntity boss = RiftEntities.VORATH.create(level, EntitySpawnReason.TRIGGERED);
        if (boss == null) return;
        boss.setPos(altar.getX() + .5, altar.getY() + 2.5, altar.getZ() + .5);
        boss.setYRot(player.getYRot());
        boss.setXRot(0);
        boss.setPersistenceRequired();
        boss.beginAwakening();
        level.addFreshEntity(boss);
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.EXPLOSION, boss.getX(), boss.getY() + 1.5, boss.getZ(), 12, 1.5, 1.5, 1.5, .1);
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.REVERSE_PORTAL, boss.getX(), boss.getY(), boss.getZ(), 80, 2.5, 2.5, 2.5, .25);
        level.playSound(null, altar, net.minecraft.sounds.SoundEvents.RESPAWN_ANCHOR_CHARGE, net.minecraft.sounds.SoundSource.HOSTILE, 2.0F, .5F);
    }
}
