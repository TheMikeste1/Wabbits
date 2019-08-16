package com.themikeste1.wabbits.atlas;

//META
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.items.GenericItem;
import com.themikeste1.wabbits.core.items.TestItem;

//Minecraft
import net.minecraft.item.Item;

//Forge
import net.minecraft.item.Rarity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

//Java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    @ObjectHolder(Constants.MOD_ID + ":test_item")
    public static final Item test_item = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        LOGGER.debug("Wabbits: Registering items...");
        event.getRegistry().registerAll(
                new TestItem(),
                new GenericItem("rainbow_shard", true, new Item.Properties()
                        .rarity(Rarity.EPIC)
                )
        );
    } //registerItems()
} //class Items
