package io.riftancient.client;

import io.riftancient.client.entity.RiftEntityRenderers;
import io.riftancient.client.entity.RiftModelLayers;
import io.riftancient.client.entity.RiftStalkerModel;
import io.riftancient.client.entity.VorathModel;
import io.riftancient.entity.RiftEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RiftAncientClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("riftancient-client");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(RiftModelLayers.VORATH, VorathModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(RiftModelLayers.RIFT_STALKER, RiftStalkerModel::createBodyLayer);
        EntityRendererRegistry.register(RiftEntities.VORATH, RiftEntityRenderers.VorathRenderer::new);
        EntityRendererRegistry.register(RiftEntities.RIFT_STALKER, RiftEntityRenderers.RiftStalkerRenderer::new);
        LOGGER.info("RiftAncient custom entity renderers online — Vorath and Rift Stalker awakened");
    }
}
