package com.themikeste1.wabbits.atlas;

//Minecraft
import net.minecraft.block.Block;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

//META
import com.themikeste1.wabbits.core.blocks.BlockRainbowBricks;
import com.themikeste1.wabbits.core.blocks.BlockTest;
import com.themikeste1.wabbits.core.Constants;

//Java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    @ObjectHolder(Constants.MOD_ID + ":test_block")
    public static final Block test_block = null;
    @ObjectHolder(Constants.MOD_ID + ":rainbow_bricks")
    public static final Block rainbow_bricks = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        LOGGER.debug("Wabbits: Registering blocks...");
        event.getRegistry().registerAll(
                new BlockTest(),
                new BlockRainbowBricks()
        );
    } //registerBlocks()
} //class Blocks
