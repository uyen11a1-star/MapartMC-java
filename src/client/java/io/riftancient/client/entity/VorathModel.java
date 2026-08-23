package io.riftancient.client.entity;

import io.riftancient.entity.VorathEntity;
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
    private final ModelPart chestFront;
    private final ModelPart core;
    private final ModelPart headCenter;
    private final ModelPart headLeft;
    private final ModelPart headRight;
    private final ModelPart hornCenter;
    private final ModelPart hornLeft;
    private final ModelPart hornRight;
    private final ModelPart shoulderLeft;
    private final ModelPart shoulderRight;
    private final ModelPart armLeft;
    private final ModelPart armRight;
    private final ModelPart wingLeftUpper;
    private final ModelPart wingLeftLower;
    private final ModelPart wingRightUpper;
    private final ModelPart wingRightLower;
    private final ModelPart legLeft;
    private final ModelPart legRight;
    private final ModelPart runeRing;

    public VorathModel(ModelPart root) {
        super(root);
        body = root.getChild("body");
        chestFront = body.getChild("chest_front");
        core = body.getChild("core");
        headCenter = body.getChild("head_center");
        headLeft = body.getChild("head_left");
        headRight = body.getChild("head_right");
        hornCenter = body.getChild("horn_center");
        hornLeft = body.getChild("horn_left");
        hornRight = body.getChild("horn_right");
        shoulderLeft = body.getChild("shoulder_left");
        shoulderRight = body.getChild("shoulder_right");
        armLeft = body.getChild("arm_left");
        armRight = body.getChild("arm_right");
        wingLeftUpper = body.getChild("wing_left_upper");
        wingLeftLower = body.getChild("wing_left_lower");
        wingRightUpper = body.getChild("wing_right_upper");
        wingRightLower = body.getChild("wing_right_lower");
        legLeft = body.getChild("leg_left");
        legRight = body.getChild("leg_right");
        runeRing = body.getChild("rune_ring");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-8, -13, -5, 16, 19, 10)
                .texOffs(0, 30).addBox(-10, -4, -4, 20, 8, 8)
                .texOffs(0, 48).addBox(-7, 4, -4, 14, 5, 8), PartPose.offset(0, 11, 0));
        body.addOrReplaceChild("chest_front", CubeListBuilder.create().texOffs(36, 0)
                .addBox(-6, -9, -7, 12, 14, 3, new CubeDeformation(.25F))
                .texOffs(36, 18).addBox(-8, -5, -6, 16, 5, 2), PartPose.offset(0, 0, 0));
        body.addOrReplaceChild("core", CubeListBuilder.create().texOffs(40, 28)
                .addBox(-3, -4, -8, 6, 8, 3, new CubeDeformation(.45F))
                .texOffs(52, 28).addBox(-1, -2, -9, 2, 4, 2), PartPose.offset(0, -3, 0));
        body.addOrReplaceChild("head_center", CubeListBuilder.create().texOffs(0, 54)
                .addBox(-4, -5, -4, 8, 8, 8, new CubeDeformation(.15F)), PartPose.offset(0, -16, -1));
        body.addOrReplaceChild("head_left", CubeListBuilder.create().texOffs(32, 54)
                .addBox(-4, -4, -3, 7, 7, 6, new CubeDeformation(.1F)), PartPose.offset(-9, -14, 0));
        body.addOrReplaceChild("head_right", CubeListBuilder.create().texOffs(48, 54)
                .addBox(-3, -4, -3, 7, 7, 6, new CubeDeformation(.1F)), PartPose.offset(9, -14, 0));
        body.addOrReplaceChild("horn_center", CubeListBuilder.create().texOffs(0, 22)
                .addBox(-2, -7, -2, 4, 7, 4), PartPose.offset(0, -22, 0));
        body.addOrReplaceChild("horn_left", CubeListBuilder.create().texOffs(16, 22)
                .addBox(-2, -6, -2, 4, 6, 4), PartPose.offset(-10, -19, 1));
        body.addOrReplaceChild("horn_right", CubeListBuilder.create().texOffs(28, 22)
                .addBox(-2, -6, -2, 4, 6, 4), PartPose.offset(10, -19, 1));
        body.addOrReplaceChild("shoulder_left", CubeListBuilder.create().texOffs(0, 39)
                .addBox(-5, -3, -4, 9, 7, 8, new CubeDeformation(.18F)), PartPose.offset(-10, -1, 0));
        body.addOrReplaceChild("shoulder_right", CubeListBuilder.create().texOffs(22, 39)
                .addBox(-4, -3, -4, 9, 7, 8, new CubeDeformation(.18F)), PartPose.offset(10, -1, 0));
        body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(42, 39)
                .addBox(-3, 0, -3, 6, 13, 6)
                .texOffs(42, 48).addBox(-4, 11, -4, 8, 5, 8), PartPose.offset(-12, 2, 0));
        body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(42, 39)
                .addBox(-3, 0, -3, 6, 13, 6)
                .texOffs(42, 48).addBox(-4, 11, -4, 8, 5, 8), PartPose.offset(12, 2, 0));
        body.addOrReplaceChild("wing_left_upper", CubeListBuilder.create().texOffs(0, 63)
                .addBox(-15, -13, 0, 15, 8, 3), PartPose.offset(-8, -1, 3));
        body.addOrReplaceChild("wing_left_lower", CubeListBuilder.create().texOffs(0, 74)
                .addBox(-19, -7, 0, 19, 5, 3), PartPose.offset(-8, -1, 3));
        body.addOrReplaceChild("wing_right_upper", CubeListBuilder.create().texOffs(32, 63)
                .addBox(0, -13, 0, 15, 8, 3), PartPose.offset(8, -1, 3));
        body.addOrReplaceChild("wing_right_lower", CubeListBuilder.create().texOffs(32, 74)
                .addBox(0, -7, 0, 19, 5, 3), PartPose.offset(8, -1, 3));
        body.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 80)
                .addBox(-4, 0, -4, 8, 13, 8)
                .texOffs(16, 80).addBox(-5, 11, -6, 10, 4, 12), PartPose.offset(-5, 7, 0));
        body.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(28, 80)
                .addBox(-4, 0, -4, 8, 13, 8)
                .texOffs(44, 80).addBox(-5, 11, -6, 10, 4, 12), PartPose.offset(5, 7, 0));
        body.addOrReplaceChild("rune_ring", CubeListBuilder.create().texOffs(0, 94)
                .addBox(-15, -1, 5, 30, 1, 2), PartPose.offset(0, -2, 0));
        return LayerDefinition.create(mesh, 64, 96);
    }

    @Override
    public void setupAnim(VorathRenderState state) {
        super.setupAnim(state);
        float age = state.entityAge;
        float awakening = state.awakeningProgress;
        float pulse = Mth.sin(age * 0.16F) * 0.12F;
        float hover = Mth.sin(age * 0.08F) * .45F;
        body.y = 11.0F - awakening * 6.0F + hover;
        body.yRot = Mth.sin(age * 0.035F) * .04F;
        headCenter.yRot = state.yRot;
        headCenter.xRot = state.xRot;
        headLeft.yRot = state.yRot + .24F;
        headRight.yRot = state.yRot - .24F;
        hornCenter.yRot = age * .025F;
        hornLeft.zRot = -.08F + Mth.sin(age * .05F) * .03F;
        hornRight.zRot = .08F - Mth.sin(age * .05F) * .03F;
        core.zScale = 1.0F + pulse + awakening * .52F;
        core.xScale = 1.0F + pulse + awakening * .28F;
        core.yScale = 1.0F + pulse + awakening * .28F;
        chestFront.z = -awakening * 1.2F;
        shoulderLeft.zRot = -.08F - awakening * .35F;
        shoulderRight.zRot = .08F + awakening * .35F;
        float wingBeat = Mth.sin(age * .11F) * (.12F + awakening * .55F);
        wingLeftUpper.zRot = -.16F - wingBeat;
        wingRightUpper.zRot = .16F + wingBeat;
        wingLeftLower.zRot = -.24F - wingBeat * 1.2F;
        wingRightLower.zRot = .24F + wingBeat * 1.2F;
        armLeft.xRot = .18F + Mth.sin(age * .13F) * .08F;
        armRight.xRot = -.18F - Mth.sin(age * .13F) * .08F;
        legLeft.xRot = Mth.sin(age * .08F) * .04F;
        legRight.xRot = -Mth.sin(age * .08F) * .04F;
        runeRing.yRot = age * .08F;
        runeRing.xScale = 1.0F + awakening * .7F;
        if (state.phase == VorathEntity.ENRAGED) {
            wingLeftUpper.zRot -= .25F;
            wingRightUpper.zRot += .25F;
            wingLeftLower.zRot -= .2F;
            wingRightLower.zRot += .2F;
            core.yRot = age * .08F;
            runeRing.yScale = 1.35F;
        } else {
            runeRing.yScale = 1.0F;
        }
    }
}
