package com.themikeste1.wabbits.core.tileentities;

//META
import com.themikeste1.wabbits.atlas.TileEntitiesTypes;

//Minecraft
import com.themikeste1.wabbits.core.blockitems.BlockItemChangingRainbow;
import com.themikeste1.wabbits.core.blocks.BlockChangingRainbow;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 *
 * @see BlockChangingRainbow
 * @see BlockItemChangingRainbow
 * @see com.themikeste1.wabbits.atlas.Blocks
 */
public class TileEntityChangingRainbow extends TileEntity implements ITickableTileEntity {
    private int colorChangeCounter = 0;
    private final int counterStart;

    public TileEntityChangingRainbow() {
        super(TileEntitiesTypes.changing_rainbow);
        counterStart = 20;
    }

    public TileEntityChangingRainbow(int counterStart) {
        super(TileEntitiesTypes.changing_rainbow);

        if (counterStart >= 0)
            this.counterStart = counterStart;
        else
            this.counterStart = 20;
    }

    @Override
    public void tick() {
        if (!world.isRemote && !canChange())
            colorChangeCounter--;
    }

    public boolean canChange() { return colorChangeCounter <= 0; }
    public void resetCounter() { colorChangeCounter = counterStart; }
}
