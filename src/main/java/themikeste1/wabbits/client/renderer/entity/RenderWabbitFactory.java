package themikeste1.wabbits.client.renderer.entity;

import themikeste1.wabbits.core.entities.WabbitEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class RenderWabbitFactory implements IRenderFactory<WabbitEntity> {
    public static final RenderWabbitFactory INSTANCE = new RenderWabbitFactory();

    @Override
    public EntityRenderer<? super WabbitEntity> createRenderFor(EntityRendererManager manager) {
        if (FMLEnvironment.dist.isDedicatedServer())
            throw new IllegalStateException("Only run this on client!");

        return new WabbitRenderer(manager);
    }
}
