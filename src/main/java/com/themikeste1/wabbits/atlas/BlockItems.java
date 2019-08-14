package com.themikeste1.wabbits.atlas;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

//META
import com.themikeste1.wabbits.core.Constants;

//Minecraft
import net.minecraft.item.Item;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//Java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockItems {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        LOGGER.debug("Wabbits: Registering item blocks...");
        event.getRegistry().registerAll(
                ((IModHasBlockItem) Blocks.test_block).generateModBlockItem(),
                ((IModHasBlockItem) Blocks.rainbow_bricks).generateModBlockItem()
        );
    } //registerBlockItems()
}
