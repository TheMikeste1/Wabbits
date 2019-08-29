package com.themikeste1.wabbits.atlas;

import com.themikeste1.wabbits.Constants;
import com.themikeste1.wabbits.core.world.biome.RainbowBrickBiome;

import net.minecraft.world.biome.Biome;

import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Biomes {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();


    @ObjectHolder("rainbow_brick_biome")
    public static final Biome rainbow_brick_biome = null;


    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        LOGGER.debug("Wabbits: Registering biomes...");
        event.getRegistry().registerAll(
                new RainbowBrickBiome()
        );
    } //registerItems()

    public static void addBiomes() {
        BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(rainbow_brick_biome, 10));
    }
}
