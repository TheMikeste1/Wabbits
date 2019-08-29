package com.themikeste1.wabbits.atlas;

//META
import com.themikeste1.wabbits.Constants;
import com.themikeste1.wabbits.core.items.TestItem;

//Minecraft
import com.themikeste1.wabbits.core.items.WrenchItem;
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
@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Items {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    @ObjectHolder("test_item")
    public static final Item test_item = null;
    @ObjectHolder("rainbow_shard")
    public static final Item rainbow_shard = null;
    @ObjectHolder("wrench")
    public static final Item wrench = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        LOGGER.debug("Wabbits: Registering items...");
        event.getRegistry().registerAll(
                new TestItem(),
                new Item(new Item.Properties()
                        .rarity(Rarity.EPIC)
                        .group(ItemGroups.MAIN_GROUP_WABBITS)
                ).setRegistryName(Constants.MOD_ID, "rainbow_shard"),
                new WrenchItem("wrench")
        );
    } //registerItems()
} //class Items
