package com.themikeste1.wabbits.atlas;

//FalconAthenaeum

//META
import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.tileentities.ChangingRainbowTileEntity;
import com.themikeste1.wabbits.core.tileentities.ChestChangingRainbowTileEntity;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

//Forge
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

//Java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.themikeste1.wabbits.atlas.Blocks.*;


@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntitiesTypes {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

   @ObjectHolder(Constants.MOD_ID + ":changing_rainbow")
    public static final TileEntityType<ChangingRainbowTileEntity> changing_rainbow = null;
    @ObjectHolder(Constants.MOD_ID + ":chest_changing_rainbow")
    public static final TileEntityType<ChangingRainbowTileEntity> chest_changing_rainbow = null;

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        LOGGER.debug("Wabbits: Registering TileEntities...");
        event.getRegistry().registerAll(
                TypeGenerator.generateChangingRainbowTileEntityType(
                        rainbow_bricks,
                        rainbow_glass,
                        stained_rainbow_glass
                ),
                TypeGenerator.generateChestChangingRainbowTileEntityType(
                        rainbow_chest
                )
        );
    }

    private static class TypeGenerator {
        static TileEntityType generateChangingRainbowTileEntityType(Block... blocks) {
            return TileEntityType.Builder
                    .create(ChangingRainbowTileEntity::new, blocks)
                    .build(null).setRegistryName("changing_rainbow");
        }

        static TileEntityType generateChestChangingRainbowTileEntityType(Block... blocks) {
            return TileEntityType.Builder
                    .create(ChestChangingRainbowTileEntity::new, blocks)
                    .build(null).setRegistryName("chest_changing_rainbow");
        }
    }
}