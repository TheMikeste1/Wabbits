package com.themikeste1.wabbits.atlas;

import com.themikeste1.wabbits.core.blocks.ChestChangingRainbowBlock;
import com.themikeste1.wabbits.core.blocks.GlassChangingRainbowBlock;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

//META
import com.themikeste1.wabbits.core.blocks.ChangingRainbowBlock;
import com.themikeste1.wabbits.core.blocks.TestBlock;
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
    @ObjectHolder(Constants.MOD_ID + ":rainbow_glass")
    public static final Block rainbow_glass = null;
    @ObjectHolder(Constants.MOD_ID + ":rainbow_chest")
    public static final Block rainbow_chest = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        LOGGER.debug("Wabbits: Registering blocks...");
        event.getRegistry().registerAll(
                new TestBlock(),
                new ChangingRainbowBlock("rainbow_bricks"),
                new GlassChangingRainbowBlock("rainbow_glass", BlockRenderLayer.TRANSLUCENT),
                new ChestChangingRainbowBlock("rainbow_chest")
        );
    } //registerBlocks()
} //class Blocks
