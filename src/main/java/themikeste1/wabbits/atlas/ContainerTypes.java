package themikeste1.wabbits.atlas;

import themikeste1.wabbits.Constants;
import themikeste1.wabbits.core.gui.container.GeneratorRainbowShardContainer;
import themikeste1.wabbits.core.gui.container.GrinderContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerTypes {

    @ObjectHolder("generator_rainbow_shard")
    public static final ContainerType<GeneratorRainbowShardContainer> generator_rainbow_shard = null;
    @ObjectHolder("grinder")
    public static final ContainerType<GrinderContainer> grinder = null;


    @SubscribeEvent
    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                TypeGenerator.generateGeneratorRainbowShard(),
                TypeGenerator.generateGrinder()
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

        static ContainerType generateGrinder() {
            return IForgeContainerType
                    .create(
                            (windowId, inv, data) -> {
                                World world = Minecraft.getInstance().world;
                                if (!world.isRemote) {
                                    throw new IllegalStateException("Should only be run on client!");
                                }
                                BlockPos pos = data.readBlockPos();
                                return new GrinderContainer(windowId, inv, world, pos);
                            }
                    )
                    .setRegistryName(Constants.MOD_ID, "grinder");
        }
    }
}
