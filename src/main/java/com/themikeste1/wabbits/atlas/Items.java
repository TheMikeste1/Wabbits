package com.themikeste1.wabbits.atlas;

//Minecraft
import net.minecraft.item.Item;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

//Meta
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.items.ItemTest;


/**
 * Stores all the Items in the mod. Also contains methods for registering them.
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 * @see Blocks
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Items {
    @ObjectHolder(Constants.MOD_ID + ":test_item")
    public static final Item test_item = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Constants.LOGGER.debug("Wabbits: Registering items...");
        event.getRegistry().registerAll(
                new ItemTest()
        );
    } //registerItems()
} //class Items
