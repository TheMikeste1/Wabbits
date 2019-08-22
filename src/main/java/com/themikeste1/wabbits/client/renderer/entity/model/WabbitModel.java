package com.themikeste1.wabbits.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import com.themikeste1.wabbits.core.Functions;
import com.themikeste1.wabbits.core.entities.WabbitEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

public class WabbitModel<T extends WabbitEntity> extends EntityModel<T> {
    private final RendererModel wabbitLeftFoot = new RendererModel(this, 26, 24);
    private final RendererModel wabbitRightFoot = new RendererModel(this, 8, 24);
    private final RendererModel wabbitLeftThigh = new RendererModel(this, 30, 15);
    private final RendererModel wabbitRightThigh = new RendererModel(this, 16, 15);
    private final RendererModel wabbitBody = new RendererModel(this, 0, 0);
    private final RendererModel wabbitLeftArm = new RendererModel(this, 8, 15);
    private final RendererModel wabbitRightArm = new RendererModel(this, 0, 15);
    private final RendererModel wabbitHead = new RendererModel(this, 32, 0);
    private final RendererModel wabbitLeftEar = new RendererModel(this, 58, 0);
    private final RendererModel wabbitRightEar = new RendererModel(this, 52, 0);
    private final RendererModel wabbitTail = new RendererModel(this, 52, 6);
    private final RendererModel wabbitNose = new RendererModel(this, 32, 9);
    private float jumpRotation;

    public WabbitModel() {
        this.wabbitLeftFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
        this.wabbitLeftFoot.setRotationPoint(3.0F, 17.5F, 3.7F);
        this.wabbitLeftFoot.mirror = true;
        Functions.setRotationOffset(this.wabbitLeftFoot, 0.0F, 0.0F, 0.0F);

        this.wabbitRightFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
        this.wabbitRightFoot.setRotationPoint(-3.0F, 17.5F, 3.7F);
        this.wabbitRightFoot.mirror = true;
        Functions.setRotationOffset(this.wabbitRightFoot, 0.0F, 0.0F, 0.0F);

        this.wabbitLeftThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
        this.wabbitLeftThigh.setRotationPoint(3.0F, 17.5F, 3.7F);
        this.wabbitLeftThigh.mirror = true;
        Functions.setRotationOffset(this.wabbitLeftThigh, -0.34906584F, 0.0F, 0.0F);

        this.wabbitRightThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
        this.wabbitRightThigh.setRotationPoint(-3.0F, 17.5F, 3.7F);
        this.wabbitRightThigh.mirror = true;
        Functions.setRotationOffset(this.wabbitRightThigh, -0.34906584F, 0.0F, 0.0F);

        this.wabbitBody.addBox(-3.0F, -2.0F, -10.0F, 6, 5, 10);
        this.wabbitBody.setRotationPoint(0.0F, 19.0F, 8.0F);
        this.wabbitBody.mirror = true;
        Functions.setRotationOffset(this.wabbitBody, -0.34906584F, 0.0F, 0.0F);

        this.wabbitLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
        this.wabbitLeftArm.setRotationPoint(3.0F, 17.0F, -1.0F);
        this.wabbitLeftArm.mirror = true;
        Functions.setRotationOffset(this.wabbitLeftArm, -0.17453292F, 0.0F, 0.0F);

        this.wabbitRightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
        this.wabbitRightArm.setRotationPoint(-3.0F, 17.0F, -1.0F);
        this.wabbitRightArm.mirror = true;
        Functions.setRotationOffset(this.wabbitRightArm, -0.17453292F, 0.0F, 0.0F);

        this.wabbitHead.addBox(-2.5F, -4.0F, -5.0F, 5, 4, 5);
        this.wabbitHead.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.wabbitHead.mirror = true;
        Functions.setRotationOffset(this.wabbitHead, 0.0F, 0.0F, 0.0F);

        this.wabbitRightEar.addBox(-2.5F, -9.0F, -1.0F, 2, 5, 1);
        this.wabbitRightEar.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.wabbitRightEar.mirror = true;
        Functions.setRotationOffset(this.wabbitRightEar, 0.0F, -0.2617994F, 0.0F);

        this.wabbitLeftEar.addBox(0.5F, -9.0F, -1.0F, 2, 5, 1);
        this.wabbitLeftEar.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.wabbitLeftEar.mirror = true;
        Functions.setRotationOffset(this.wabbitLeftEar, 0.0F, 0.2617994F, 0.0F);

        this.wabbitTail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2);
        this.wabbitTail.setRotationPoint(0.0F, 20.0F, 7.0F);
        this.wabbitTail.mirror = true;
        Functions.setRotationOffset(this.wabbitTail, -0.3490659F, 0.0F, 0.0F);

        this.wabbitNose.addBox(-0.5F, -2.5F, -5.5F, 1, 1, 1);
        this.wabbitNose.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.wabbitNose.mirror = true;
        Functions.setRotationOffset(this.wabbitNose, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (this.isChild) {
            float f = 1.5F;
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.56666666F, 0.56666666F, 0.56666666F);
            GlStateManager.translatef(0.0F, 22.0F * scale, 2.0F * scale);
            renderHead(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.4F, 0.4F, 0.4F);
            GlStateManager.translatef(0.0F, 36.0F * scale, 0.0F);
            renderBody(scale);
            GlStateManager.popMatrix();
        } else {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.6F, 0.6F, 0.6F);
            GlStateManager.translatef(0.0F, 16.0F * scale, 0.0F);
            renderHead(scale);
            renderBody(scale);
            GlStateManager.popMatrix();
        }
    }

    private void renderBody(float scale) {
        this.wabbitLeftFoot.render(scale);
        this.wabbitRightFoot.render(scale);
        this.wabbitLeftThigh.render(scale);
        this.wabbitRightThigh.render(scale);
        this.wabbitBody.render(scale);
        this.wabbitLeftArm.render(scale);
        this.wabbitRightArm.render(scale);
        this.wabbitTail.render(scale);
    }

    private void renderHead (float scale) {
        this.wabbitHead.render(scale);
        this.wabbitLeftEar.render(scale);
        this.wabbitRightEar.render(scale);
        this.wabbitNose.render(scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float f = ageInTicks - (float)entityIn.ticksExisted;

        this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(f) * (float)Math.PI);

        this.wabbitNose.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.wabbitHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.wabbitRightEar.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.wabbitLeftEar.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.wabbitNose.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.wabbitHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.wabbitRightEar.rotateAngleY = this.wabbitNose.rotateAngleY - 0.2617994F;
        this.wabbitLeftEar.rotateAngleY = this.wabbitNose.rotateAngleY + 0.2617994F;
        this.wabbitLeftThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.wabbitRightThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.wabbitLeftFoot.rotateAngleX = this.jumpRotation * 50.0F * ((float)Math.PI / 180F);
        this.wabbitRightFoot.rotateAngleX = this.jumpRotation * 50.0F * ((float)Math.PI / 180F);
        this.wabbitLeftArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
        this.wabbitRightArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(partialTick) * (float)Math.PI);
    }
}
