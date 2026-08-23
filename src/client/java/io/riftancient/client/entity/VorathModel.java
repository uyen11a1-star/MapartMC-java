package io.riftancient.client.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public final class VorathModel extends EntityModel<VorathRenderState> {
    private final ModelPart body;
    private final ModelPart core;
    private final ModelPart headCenter;
    private final ModelPart headLeft;
    private final ModelPart headRight;
    private final ModelPart wingLeft;
    private final ModelPart wingRight;
    private final ModelPart armLeft;
    private final ModelPart armRight;

    public VorathModel(ModelPart root) {
        super(root);
        body = root.getChild("body");
        core = body.getChild("core");
        headCenter = body.getChild("head_center");
        headLeft = body.getChild("head_left");
        headRight = body.getChild("head_right");
        wingLeft = body.getChild("wing_left");
        wingRight = body.getChild("wing_right");
        armLeft = body.getChild("arm_left");
        armRight = body.getChild("arm_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8, -13, -5, 16, 19, 10)
                .texOffs(0, 30).addBox(-10, -4, -4, 20, 8, 8), PartPose.offset(0, 11, 0));
        body.addOrReplaceChild("core", CubeListBuilder.create().texOffs(40, 0)
                .addBox(-3, -4, -6, 6, 8, 3, new CubeDeformation(.35F)), PartPose.offset(0, -3, 0));
        body.addOrReplaceChild("head_center", CubeListBuilder.create().texOffs(36, 19)
                .addBox(-4, -5, -4, 8, 8, 8), PartPose.offset(0, -16, -1));
        body.addOrReplaceChild("head_left", CubeListBuilder.create().texOffs(0, 45)
                .addBox(-4, -4, -3, 7, 7, 6), PartPose.offset(-9, -14, 0));
        body.addOrReplaceChild("head_right", CubeListBuilder.create().texOffs(26, 45)
                .addBox(-3, -4, -3, 7, 7, 6), PartPose.offset(9, -14, 0));
        body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(0, 56)
                .addBox(-13, -12, 0, 13, 18, 3), PartPose.offset(-8, -1, 3));
        body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(32, 56)
                .addBox(0, -12, 0, 13, 18, 3), PartPose.offset(8, -1, 3));
        body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(48, 30)
                .addBox(-3, 0, -3, 6, 15, 6), PartPose.offset(-10, 1, 0));
        body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(48, 30)
                .addBox(-3, 0, -3, 6, 15, 6), PartPose.offset(10, 1, 0));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(VorathRenderState state) {
        super.setupAnim(state);
        float age = state.entityAge;
        float pulse = Mth.sin(age * 0.16F) * 0.12F;
        float awakening = state.awakeningProgress;
        body.y = 11.0F - awakening * 6.0F + Mth.sin(age * 0.08F) * .45F;
        body.yRot = Mth.sin(age * 0.035F) * .04F;
        headCenter.yRot = state.yRot;
        headCenter.xRot = state.xRot;
        headLeft.yRot = state.yRot + .22F;
        headRight.yRot = state.yRot - .22F;
        core.zScale = 1.0F + pulse + awakening * .45F;
        core.xScale = 1.0F + pulse + awakening * .25F;
        core.yScale = 1.0F + pulse + awakening * .25F;
        float wingBeat = Mth.sin(age * .11F) * (0.12F + awakening * .55F);
        wingLeft.zRot = -.12F - wingBeat;
        wingRight.zRot = .12F + wingBeat;
        armLeft.xRot = .18F + Mth.sin(age * .13F) * .08F;
        armRight.xRot = -.18F - Mth.sin(age * .13F) * .08F;
        if (state.phase == VorathEntityClientPhases.ENRAGED) {
            wingLeft.zRot -= .25F;
            wingRight.zRot += .25F;
            core.yRot = age * .08F;
        }
    }

    public static final class VorathEntityClientPhases {
        public static final int ENRAGED = 3;
        private VorathEntityClientPhases() {}
    }
}
