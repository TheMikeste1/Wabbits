package com.themikeste1.wabbits.atlas;

//Minecraft
import com.themikeste1.wabbits.atlas.Items;
import com.themikeste1.wabbits.core.blocks.BlockRainbowBricks;
import com.themikeste1.wabbits.core.blocks.BlockTest;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

//FalconAthenaeum
import com.themikeste1.falconathenaeum.core.blocks.IModBlock;

//Meta
import com.themikeste1.wabbits.core.Constants;


/**
 * Stores all the Blocks in the mod. Also contains methods for registering them.
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 * @see Items
 */
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Blocks {
    @ObjectHolder(Constants.MOD_ID + ":test_block")
    public static final Block test_block = null;
    @ObjectHolder(Constants.MOD_ID + ":rainbow_bricks")
    public static final Block rainbow_bricks = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        Constants.LOGGER.debug("Wabbits: Registering blocks...");
        event.getRegistry().registerAll(
                new BlockTest(),
                new BlockRainbowBricks()
        );
    } //registerBlocks()

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
        Constants.LOGGER.debug("Wabbits: Registering item blocks...");
        event.getRegistry().registerAll(
                ((IModBlock) test_block).getModBlockItem(),
                ((IModBlock) rainbow_bricks).getModBlockItem()
        );
    } //registerItemBlocks()
} //class Blocks
