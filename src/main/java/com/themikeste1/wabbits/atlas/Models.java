package com.themikeste1.wabbits.atlas;

import com.themikeste1.wabbits.Constants;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.BasicState;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Models {

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        try {
            // Try to load an OBJ model (placed in src/main/resources/assets/wabbits/models/)
            IUnbakedModel model = ModelLoaderRegistry.getModelOrMissing(new ResourceLocation("wabbits:block/test_block.obj"));

            if (model instanceof OBJModel) {
                IBakedModel bakedModel = model.bake(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new BasicState(model.getDefaultState(), false), DefaultVertexFormats.BLOCK);
                event.getModelRegistry().put(new ModelResourceLocation("wabbits:test_block", ""), bakedModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public static void onPreTextureStitch(TextureStitchEvent.Pre event) {
        event.addSprite(
          ResourceLocation.tryCreate("wabbits:flatcolors/yellow")
        );
    }

}
