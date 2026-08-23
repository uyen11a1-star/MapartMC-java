package io.riftancient.client.entity;

import io.riftancient.RiftAncient;
import io.riftancient.entity.RiftStalkerEntity;
import io.riftancient.entity.VorathEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class RiftEntityRenderers {
    private RiftEntityRenderers() {}

    public static final class VorathRenderer extends MobRenderer<VorathEntity, VorathRenderState, VorathModel> {
        private static final ResourceLocation TEXTURE = RiftAncient.id("textures/entity/vorath.png");

        public VorathRenderer(EntityRendererProvider.Context context) {
            super(context, new VorathModel(context.bakeLayer(RiftModelLayers.VORATH)), 0.9F);
        }

        @Override
        public VorathRenderState createRenderState() {
            return new VorathRenderState();
        }

        @Override
        public void extractRenderState(VorathEntity entity, VorathRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            state.phase = entity.getPhase();
            state.awakeningProgress = entity.getAnimationProgress(partialTick);
            state.entityAge = entity.tickCount + partialTick;
        }

        @Override
        public ResourceLocation getTextureLocation(VorathRenderState state) {
            return TEXTURE;
        }
    }

    public static final class RiftStalkerRenderer extends MobRenderer<RiftStalkerEntity, RiftStalkerRenderState, RiftStalkerModel> {
        private static final ResourceLocation TEXTURE = RiftAncient.id("textures/entity/rift_stalker.png");

        public RiftStalkerRenderer(EntityRendererProvider.Context context) {
            super(context, new RiftStalkerModel(context.bakeLayer(RiftModelLayers.RIFT_STALKER)), 0.35F);
        }

        @Override
        public RiftStalkerRenderState createRenderState() {
            return new RiftStalkerRenderState();
        }

        @Override
        public void extractRenderState(RiftStalkerEntity entity, RiftStalkerRenderState state, float partialTick) {
            super.extractRenderState(entity, state, partialTick);
            state.entityAge = entity.tickCount + partialTick;
        }

        @Override
        public ResourceLocation getTextureLocation(RiftStalkerRenderState state) {
            return TEXTURE;
        }
    }
}
