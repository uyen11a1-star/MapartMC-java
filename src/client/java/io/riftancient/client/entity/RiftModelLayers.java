package io.riftancient.client.entity;

import io.riftancient.RiftAncient;
import net.minecraft.client.model.geom.ModelLayerLocation;

public final class RiftModelLayers {
    private RiftModelLayers() {}

    public static final ModelLayerLocation VORATH = main("vorath");
    public static final ModelLayerLocation RIFT_STALKER = main("rift_stalker");

    private static ModelLayerLocation main(String name) {
        return new ModelLayerLocation(RiftAncient.id(name), "main");
    }
}
