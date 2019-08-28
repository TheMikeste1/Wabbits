package com.themikeste1.wabbits.core.config;

//Forge
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.themikeste1.wabbits.core.Constants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;


@Mod.EventBusSubscriber
public class Config {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;



    public static final String CATEGORY_GENERAL = "general";

    public static final String SUBCATEGORY_GENERATOR_RAINBOW_SHARD = "generator_rainbow_shard";
    public static ForgeConfigSpec.IntValue GENERATOR_RAINBOW_SHARD_MAXCAP;
    private static int GENERATOR_RAINBOW_SHARD_MAXCAP_DEFAULT = 100000;


    public static final String SUBCATEGORY_WABBIT = "wabbit";
    public static ForgeConfigSpec.IntValue WABBIT_SPAWN_WEIGHT;
    private static int WABBIT_SPAWN_WEIGHT_DEFAULT = 2;
    public static ForgeConfigSpec.IntValue WABBIT_MIN_SPAWN_AMOUNT;
    private static int WABBIT_MIN_SPAWN_AMOUNT_DEFAULT = 1;
    public static ForgeConfigSpec.IntValue WABBIT_MAX_SPAWN_AMOUNT;
    private static int WABBIT_MAX_SPAWN_AMOUNT_DEFAULT = 5;


    static {
        COMMON_BUILDER.comment("General Settings").push(CATEGORY_GENERAL);

        setupGeneratorRainbowShardConfig();
        setupWabbitConfig();

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupGeneratorRainbowShardConfig() {
        COMMON_BUILDER.comment("Rainbow Shard Generator Settings").push(SUBCATEGORY_GENERATOR_RAINBOW_SHARD);
        GENERATOR_RAINBOW_SHARD_MAXCAP = COMMON_BUILDER
                .comment("Rainbow Shard Generator Max Capacity\n" + "Default: " + GENERATOR_RAINBOW_SHARD_MAXCAP_DEFAULT)
                .defineInRange("maxPower", GENERATOR_RAINBOW_SHARD_MAXCAP_DEFAULT, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
    }

    private static void setupWabbitConfig() {
        COMMON_BUILDER.comment("Wabbit Entity Settings").push(SUBCATEGORY_WABBIT);

        WABBIT_SPAWN_WEIGHT = COMMON_BUILDER
                .comment("Spawn Weight\n" + "Default: " + WABBIT_SPAWN_WEIGHT_DEFAULT)
                .defineInRange("wabbitSpawnWeight", WABBIT_SPAWN_WEIGHT_DEFAULT, 0, Integer.MAX_VALUE);

        WABBIT_MIN_SPAWN_AMOUNT = COMMON_BUILDER
                .comment("Minimum Spawn Group Size\n" + "Default: " + WABBIT_MIN_SPAWN_AMOUNT_DEFAULT)
                .defineInRange("wabbitMinSpawnAmount", WABBIT_MIN_SPAWN_AMOUNT_DEFAULT, 0, Integer.MAX_VALUE);

        WABBIT_MAX_SPAWN_AMOUNT = COMMON_BUILDER
                .comment("Maximum Spawn Group Size\n" + "Default: " + WABBIT_MAX_SPAWN_AMOUNT_DEFAULT)
                .defineInRange("wabbitMaxSpawnAmount", WABBIT_MAX_SPAWN_AMOUNT_DEFAULT, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
    }


    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    /* ***********************************************************************
     * configLoaded()
     * Use this function to make any calculations necessary based off of config
     * variables.
     ************************************************************************/
    @SubscribeEvent
    public static void configLoaded(final ModConfig.Loading event) {
        LOGGER.debug("Loaded {} config file {}", Constants.MOD_ID, event.getConfig().getFileName());
    }

    /* ***********************************************************************
     * configFileReload()
     * Use this function to make any calculations necessary based off of config
     * variables. This is used when a config file is reloaded.
     ************************************************************************/
    @SubscribeEvent
    public static void configFileReload(final ModConfig.ConfigReloading event) {

    }
}
