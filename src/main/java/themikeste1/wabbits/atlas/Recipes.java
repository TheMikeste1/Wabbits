package themikeste1.wabbits.atlas;

import themikeste1.wabbits.Constants;
import themikeste1.wabbits.core.recipe.GrindRecipe;
import themikeste1.wabbits.core.recipe.serializers.GrindSerializer;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Recipes {

    @ObjectHolder("grind")
    public static final IRecipeSerializer<GrindRecipe> grind = null;

    @SubscribeEvent
    public static void registerRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().registerAll(
                new GrindSerializer()
                        .setRegistryName(Constants.MOD_ID, "grind")
        );
    }
}
