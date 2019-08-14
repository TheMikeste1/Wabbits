package com.themikeste1.wabbits.atlas;

//Minecraft
import net.minecraft.tileentity.TileEntityType;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//Meta
import com.themikeste1.wabbits.core.Constants;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntities {

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        Constants.LOGGER.debug("Wabbits: Registering TileEntities...");
        event.getRegistry().registerAll(

        );
    }
}
