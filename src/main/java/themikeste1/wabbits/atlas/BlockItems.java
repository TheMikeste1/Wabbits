package themikeste1.wabbits.atlas;

//FalconAthenaeum
import themikeste1.falconathenaeum.core.blocks.IModHasBlockItem;

//META
import themikeste1.wabbits.Constants;

//Minecraft
import themikeste1.wabbits.core.blocks.ConduitBlock;
import themikeste1.wabbits.core.blocks.GeneratorRainbowShardBlock;
import themikeste1.wabbits.core.blocks.GrinderBlock;
import themikeste1.wabbits.core.blocks.TestBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//Java
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockItems {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    @ObjectHolder(TestBlock.regName)
    public static final BlockItem test_block = null;
    @ObjectHolder("rainbow_bricks")
    public static final BlockItem rainbow_bricks = null;
    @ObjectHolder("rainbow_glass")
    public static final BlockItem rainbow_glass = null;
    @ObjectHolder("stained_rainbow_glass")
    public static final BlockItem stained_rainbow_glass = null;
    @ObjectHolder("rainbow_chest")
    public static final BlockItem rainbow_chest = null;
    @ObjectHolder(GeneratorRainbowShardBlock.regName)
    public static final BlockItem generator_rainbow_shard = null;
    @ObjectHolder(ConduitBlock.regName)
    public static final BlockItem conduit = null;
    @ObjectHolder(GrinderBlock.regName)
    public static final BlockItem grinder = null;


    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        LOGGER.debug("Wabbits: Registering item blocks...");
        event.getRegistry().registerAll(
                generateModBlockItem(Blocks.test_block),
                generateModBlockItem(Blocks.rainbow_bricks),
                generateModBlockItem(Blocks.rainbow_chest),
                generateModBlockItem(Blocks.rainbow_glass),
                generateModBlockItem(Blocks.stained_rainbow_glass),
                generateModBlockItem(Blocks.generator_rainbow_shard),
                generateModBlockItem(Blocks.conduit),
                generateModBlockItem(Blocks.grinder)
        );
    } //registerBlockItems()

    private static BlockItem generateModBlockItem(Block block) {
        if (!(block instanceof IModHasBlockItem))
            throw new IllegalArgumentException("Block " + block.toString() + " does not implement IModHasBlockItem!");

        return ((IModHasBlockItem) block).generateModBlockItem();
    }
}
