package com.themikeste1.wabbits.client.renderer.entity;

import com.themikeste1.wabbits.client.renderer.entity.model.WabbitModel;
import com.themikeste1.wabbits.core.entities.WabbitEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class WabbitRenderer extends MobRenderer<WabbitEntity, WabbitModel<WabbitEntity>> {
    public WabbitRenderer(EntityRendererManager p_i50961_1_, WabbitModel<WabbitEntity> p_i50961_2_, float p_i50961_3_) {
        super(p_i50961_1_, p_i50961_2_, p_i50961_3_);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(WabbitEntity entity) {
        return null;
    }
}
