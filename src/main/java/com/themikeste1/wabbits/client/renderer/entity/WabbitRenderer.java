package com.themikeste1.wabbits.client.renderer.entity;

import com.themikeste1.wabbits.client.renderer.entity.model.WabbitModel;
import com.themikeste1.wabbits.core.entities.WabbitEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class WabbitRenderer extends MobRenderer<WabbitEntity, WabbitModel<WabbitEntity>> {
    private static final ResourceLocation SKIN = new ResourceLocation("wabbits:textures/entity/wabbit.png");

    public WabbitRenderer(EntityRendererManager renderManager) {
        //   (renderManager,               model,     shadowSize);
        super(renderManager, new WabbitModel<>(),1.0F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(WabbitEntity entity) {
        return SKIN;
    }
}
