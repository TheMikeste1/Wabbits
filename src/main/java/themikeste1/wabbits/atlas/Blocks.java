package themikeste1.wabbits.atlas;

import themikeste1.wabbits.core.blocks.*;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

//META
import themikeste1.wabbits.Constants;

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
@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Blocks {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    @ObjectHolder(TestBlock.regName)
    public static final Block test_block = null;
    @ObjectHolder("rainbow_bricks")
    public static final Block rainbow_bricks = null;
    @ObjectHolder("rainbow_glass")
    public static final Block rainbow_glass = null;
    @ObjectHolder("stained_rainbow_glass")
    public static final Block stained_rainbow_glass = null;
    @ObjectHolder("rainbow_chest")
    public static final Block rainbow_chest = null;
    @ObjectHolder(GeneratorRainbowShardBlock.regName)
    public static final Block generator_rainbow_shard = null;
    @ObjectHolder(ConduitBlock.regName)
    public static final Block conduit = null;
    @ObjectHolder(GrinderBlock.regName)
    public static final Block grinder = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        LOGGER.debug("Wabbits: Registering blocks...");
        event.getRegistry().registerAll(
                new TestBlock(),
                new ChangingRainbowBlock("rainbow_bricks"),
                new GlassChangingRainbowBlock("rainbow_glass"),
                new GlassChangingRainbowBlock("stained_rainbow_glass", BlockRenderLayer.TRANSLUCENT),
                new ChestChangingRainbowBlock("rainbow_chest"),
                new GeneratorRainbowShardBlock(),
                new ConduitBlock(),
                new GrinderBlock()
        );
    } //registerBlocks()
} //class Blocks
