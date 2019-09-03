package themikeste1.wabbits.atlas;

import themikeste1.wabbits.Constants;

import themikeste1.wabbits.Config;
import themikeste1.wabbits.core.entities.WabbitEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypes {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    //Entities
    @ObjectHolder("wabbit")
    public static EntityType<WabbitEntity> wabbit = null;

    //SpawnEggs
    @ObjectHolder("spawn_wabbit")
    public static final Item spawn_wabbit = null;


    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        LOGGER.debug("Wabbits: Registering Entities...");
        event.getRegistry().registerAll(
                wabbit
        );
    } //registerEntities()

    @SubscribeEvent
    public static void registerSpawnEggs(final RegistryEvent.Register<Item> event) {
        generateEntityTypes();
        LOGGER.debug("Wabbits: Registering Spawn Eggs...");
        event.getRegistry().registerAll(
                new SpawnEggItem(wabbit, 0, 0,
                        new Item.Properties()
                                .group(ItemGroups.MAIN_GROUP_WABBITS)
                ).setRegistryName(Constants.MOD_ID, "spawn_wabbit")
        );
    } //registerSpawnEggs()


    private static void generateEntityTypes() {
        LOGGER.debug("Wabbits: Creating EntityTypes...");
        wabbit = EntityType.Builder
                .create(WabbitEntity::new, EntityClassification.CREATURE)
                .size(0.8F, 1F)
                .build("wabbit");
        wabbit.setRegistryName(Constants.MOD_ID, "wabbit");
    }


    public static void setWabbitSpawnBiomes() {
        EntitySpawnPlacementRegistry.register(wabbit,
                EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, spawnReason, pos, random) -> {
                    if (entityType != wabbit)
                        throw new IllegalArgumentException(wabbit.getRegistryName() + " only!");

                    return WabbitEntity.isValidSpawnPlacement(entityType, world, spawnReason, pos, random);
                }
        );

        //In reality, it would be better to take each biome ands set a custom weight
        //instead of using the generalEntry... But I'm lazy, so all biomes get
        //the same probability. ^_^
        Biome[] biomes = { Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.DESERT_LAKES,
                Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.WOODED_BADLANDS_PLATEAU,
                Biomes.ERODED_BADLANDS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
                Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS, Biomes.PLAINS,
                themikeste1.wabbits.atlas.Biomes.rainbow_brick_biome
        };
        Biome.SpawnListEntry generalEntry = new Biome.SpawnListEntry(wabbit,
                Config.WABBIT_SPAWN_WEIGHT.get(),
                Config.WABBIT_MIN_SPAWN_AMOUNT.get(),
                Config.WABBIT_MAX_SPAWN_AMOUNT.get()
        );
        for (Biome biome : biomes) {
            biome.getSpawns(EntityClassification.CREATURE).add(generalEntry);
        }
    }
} //class EntityTypes
