package com.themikeste1.wabbits.atlas;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModBlock;

//Meta
import com.themikeste1.wabbits.core.Constants;

//Minecraft
import net.minecraft.item.Item;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockItems {

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        Constants.LOGGER.debug("Wabbits: Registering item blocks...");
        event.getRegistry().registerAll(
                ((IModBlock) Blocks.test_block).getModBlockItem(),
                ((IModBlock) Blocks.rainbow_bricks).getModBlockItem()
        );
    } //registerBlockItems()
}
