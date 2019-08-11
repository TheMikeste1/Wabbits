package com.themikeste1.wabbits.items;

//Minecraft
import net.minecraft.item.Item;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

//Meta
import com.themikeste1.wabbits.core.Constants;


/**
 * Stores all the Items in the mod. Also contains methods for registering them.
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 * @see com.themikeste1.wabbits.blocks.Blocks
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Constants.MOD_ID)
public class Items {
    public static final Item test_item = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Constants.LOGGER.debug("Wabbits: Registering items...");
        event.getRegistry().registerAll(
                new ItemTest()
        );
    } //registerItems()
} //class Items
