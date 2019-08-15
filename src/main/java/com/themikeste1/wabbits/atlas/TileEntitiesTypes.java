package com.themikeste1.wabbits.atlas;

//FalconAthenaeum

//META
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.tileentities.TileEntityChangingRainbow;

//Minecraft
import net.minecraft.block.Block;
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
public class TileEntitiesTypes {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

   @ObjectHolder(Constants.MOD_ID + ":changing_rainbow")
    public static final TileEntityType<TileEntityChangingRainbow> changing_rainbow = null;

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        LOGGER.debug("Wabbits: Registering TileEntities...");
        event.getRegistry().registerAll(
                TypeGenerator.generateRainbowTileEntityType(Blocks.rainbow_bricks)
        );
    }

    private static class TypeGenerator {
        static TileEntityType generateRainbowTileEntityType(Block... blocks) {
            return TileEntityType.Builder
                    .create(TileEntityChangingRainbow::new, blocks)
                    .build(null).setRegistryName("changing_rainbow");
        }
    }
}
