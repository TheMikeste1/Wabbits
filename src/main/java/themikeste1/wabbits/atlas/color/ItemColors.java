package themikeste1.wabbits.atlas.color;

//Minecraft
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemColors {
    private static final net.minecraft.client.renderer.color.ItemColors itemColors = Minecraft.getInstance().getItemColors();

    public static void registerColors() {

    }
}
