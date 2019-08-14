package com.themikeste1.wabbits.atlas;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModHasTileEntity;

//META
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.tileentities.TileEntityRainbowBricks;

//Minecraft
import net.minecraft.tileentity.TileEntityType;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

//Java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntities {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    @ObjectHolder(Constants.MOD_ID + ":rainbow_bricks")
    public static final TileEntityType<TileEntityRainbowBricks> rainbow_bricks = null;

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        LOGGER.debug("Wabbits: Registering TileEntities...");
        event.getRegistry().registerAll(
                ((IModHasTileEntity) Blocks.rainbow_bricks).generateModTileEntityType()
        );
    }
}
