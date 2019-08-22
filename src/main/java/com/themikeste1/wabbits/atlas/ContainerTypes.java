package com.themikeste1.wabbits.atlas;


import com.themikeste1.wabbits.core.Constants;
import com.themikeste1.wabbits.core.gui.container.GeneratorRainbowShardContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerTypes {

    @ObjectHolder(Constants.MOD_ID + ":generator_rainbow_shard")
    public static ContainerType<GeneratorRainbowShardContainer> generator_rainbow_shard = null;


    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                TypeGenerator.generateGeneratorRainbowShard()
        );
    }



    private static class TypeGenerator {
        static ContainerType generateGeneratorRainbowShard() {
            return IForgeContainerType
                    .create(
                            (windowId, inv, data) -> {
                                World world = Minecraft.getInstance().world;
                                if (!world.isRemote) {
                                    throw new IllegalStateException("Should only be run on client!");
                                }
                                BlockPos pos = data.readBlockPos();
                                return new GeneratorRainbowShardContainer(windowId, inv, world, pos);
                            }
                    )
                    .setRegistryName(Constants.MOD_ID, "generator_rainbow_shard");
        }
    }
}