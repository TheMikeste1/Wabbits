package com.themikeste1.wabbits.blocks;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.item.Item;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

//FalconCore
import com.themikeste1.falconathenaeum.blocks.ModBlock;

//Meta
import com.themikeste1.wabbits.core.Constants;


/**
 * Stores all the Blocks in the mod. Also contains methods for registering them.
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 * @see com.themikeste1.wabbits.items.Items
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Constants.MOD_ID)
public class Blocks {
    public static final ModBlock test_block = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        Constants.LOGGER.debug("Wabbits: Registering blocks...");
        event.getRegistry().registerAll(
                new BlockTest()
        );
    } //registerBlocks()

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
        Constants.LOGGER.debug("Wabbits: Registering item blocks...");
        event.getRegistry().registerAll(
                test_block.getModBlockItem()
        );
    } //registerItemBlocks()
} //class Blocks
