package io.riftancient.client.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public final class RiftStalkerModel extends EntityModel<RiftStalkerRenderState> {
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart core;
    private final ModelPart legFrontLeft;
    private final ModelPart legFrontRight;
    private final ModelPart legBackLeft;
    private final ModelPart legBackRight;
    private final ModelPart tail;

    public RiftStalkerModel(ModelPart root) {
        super(root);
        body = root.getChild("body");
        head = body.getChild("head");
        core = body.getChild("core");
        legFrontLeft = body.getChild("leg_front_left");
        legFrontRight = body.getChild("leg_front_right");
        legBackLeft = body.getChild("leg_back_left");
        legBackRight = body.getChild("leg_back_right");
        tail = body.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-5, -4, -8, 10, 8, 16)
                .texOffs(0, 26).addBox(-4, -2, -10, 8, 5, 4), PartPose.offset(0, 13, 0));
        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 26)
                .addBox(-4, -4, -5, 8, 7, 6), PartPose.offset(0, -1, -8));
        body.addOrReplaceChild("core", CubeListBuilder.create().texOffs(48, 0)
                .addBox(-2, -2, -2, 4, 4, 4), PartPose.offset(0, 0, -1));
        body.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(0, 38)
                .addBox(-2, 0, -2, 4, 11, 4), PartPose.offset(-4, 1, -5));
        body.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(16, 38)
                .addBox(-2, 0, -2, 4, 11, 4), PartPose.offset(4, 1, -5));
        body.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(32, 38)
                .addBox(-2, 0, -2, 4, 11, 4), PartPose.offset(-4, 1, 5));
        body.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(48, 38)
                .addBox(-2, 0, -2, 4, 11, 4), PartPose.offset(4, 1, 5));
        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 16)
                .addBox(-2, -2, 0, 4, 4, 10), PartPose.offset(0, 0, 7));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(RiftStalkerRenderState state) {
        super.setupAnim(state);
        float age = state.entityAge;
        float walk = state.walkAnimationPos * .55F;
        float speed = Math.max(.18F, state.walkAnimationSpeed * 1.8F);
        head.yRot = state.yRot;
        head.xRot = state.xRot;
        body.y = 13.0F + Mth.sin(age * .12F) * .5F;
        core.xScale = core.yScale = core.zScale = 1.0F + Mth.sin(age * .22F) * .14F;
        legFrontLeft.xRot = Mth.cos(walk) * speed;
        legBackRight.xRot = Mth.cos(walk) * speed;
        legFrontRight.xRot = Mth.cos(walk + Mth.PI) * speed;
        legBackLeft.xRot = Mth.cos(walk + Mth.PI) * speed;
        tail.xRot = Mth.sin(age * .1F) * .08F;
    }
}
