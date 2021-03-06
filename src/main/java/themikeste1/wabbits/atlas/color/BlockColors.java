package themikeste1.wabbits.atlas.color;

//META
import themikeste1.wabbits.api.handlers.HandlerRainbowColor;

//Minecraft
import themikeste1.wabbits.atlas.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class BlockColors {
    private static final net.minecraft.client.renderer.color.BlockColors blockColors = Minecraft.getInstance().getBlockColors();

    public static void registerColors() {
        registerRainbowColors();
    }

    private static void registerRainbowColors() {
        blockColors.register(new HandlerRainbowColor(),
                Blocks.rainbow_bricks,
                Blocks.rainbow_glass,
                Blocks.stained_rainbow_glass,
                Blocks.rainbow_chest
        );
    }
}
