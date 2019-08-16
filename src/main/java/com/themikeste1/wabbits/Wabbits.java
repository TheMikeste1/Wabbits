package com.themikeste1.wabbits;

//META
import com.themikeste1.wabbits.atlas.color.BlockColors;
import com.themikeste1.wabbits.atlas.color.BlockItemColors;
import com.themikeste1.wabbits.atlas.color.ItemColors;
import com.themikeste1.wabbits.client.renderer.tileentity.RendererChestChangingRainbowTileEntity;
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.tileentities.ChestChangingRainbowTileEntity;

//Forge
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

//Java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.stream.Collectors;


/**
 * Sets up the mod.
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 */
@Mod(Constants.MOD_ID)
public class Wabbits {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    public Wabbits() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    } //Wabbits()


    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM " + Constants.MOD_NAME + " " + Constants.VERSION);
    } //setup()

    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

        //Register IBlockColors
        BlockColors.registerColors();
        //Register IItemColors
        ItemColors.registerColors();
        BlockItemColors.registerColors();

        ClientRegistry.bindTileEntitySpecialRenderer(ChestChangingRainbowTileEntity.class, new RendererChestChangingRainbowTileEntity());
    } //doClientStuff()

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(Constants.MOD_ID, "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    } //enqueueIMC()

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    } //processIMC()
} //class Wabbits
