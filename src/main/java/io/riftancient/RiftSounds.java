package io.riftancient;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public final class RiftSounds {
    private RiftSounds() {}

    public static final SoundEvent AETHEL_AMBIENT = register("aethel_ambient");

    private static SoundEvent register(String name) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, RiftAncient.id(name), SoundEvent.createVariableRangeEvent(RiftAncient.id(name)));
    }

    public static void register() {
        RiftAncient.LOGGER.info("Registered Aethel-Ruinium ambient sound");
    }
}
