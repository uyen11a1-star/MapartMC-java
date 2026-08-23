package io.riftancient.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RiftAncientClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("riftancient-client");

    @Override
    public void onInitializeClient() {
        LOGGER.info("RiftAncient client effects online — Aethel-Ruinium awaits");
    }
}
