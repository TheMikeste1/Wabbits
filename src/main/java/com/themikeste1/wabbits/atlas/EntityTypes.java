package com.themikeste1.wabbits.atlas;

import com.themikeste1.wabbits.core.Constants;

import com.themikeste1.wabbits.core.entities.WabbitEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
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
                .create(WabbitEntity::new, EntityClassification.AMBIENT)
                .size(0.8F, 1F)
                .build("wabbit");
        wabbit.setRegistryName(Constants.MOD_ID, "wabbit");
    }
} //class EntityTypes
