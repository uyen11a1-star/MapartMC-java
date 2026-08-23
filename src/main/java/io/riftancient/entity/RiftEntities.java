package io.riftancient.entity;

import io.riftancient.RiftAncient;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class RiftEntities {
    private RiftEntities() {}

    public static final EntityType<VorathEntity> VORATH = register(
            "vorath",
            EntityType.Builder.<VorathEntity>of(VorathEntity::new, MobCategory.MONSTER)
                    .sized(1.9F, 3.8F)
                    .clientTrackingRange(12)
                    .updateInterval(2)
    );

    public static final EntityType<RiftStalkerEntity> RIFT_STALKER = register(
            "rift_stalker",
            EntityType.Builder.<RiftStalkerEntity>of(RiftStalkerEntity::new, MobCategory.MONSTER)
                    .sized(0.9F, 1.35F)
                    .clientTrackingRange(10)
                    .updateInterval(2)
    );

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, RiftAncient.id(name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void register() {
        FabricDefaultAttributeRegistry.register(VORATH, VorathEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(RIFT_STALKER, RiftStalkerEntity.createAttributes());
        RiftAncient.LOGGER.info("Registered custom Aethel-Ruinium entities");
    }
}
