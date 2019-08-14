package com.themikeste1.wabbits.core.tileentities;

//META
import com.themikeste1.wabbits.atlas.TileEntities;

//Minecraft
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 *
 * @see com.themikeste1.wabbits.core.blocks.BlockRainbowBricks
 * @see com.themikeste1.wabbits.core.blockitems.BlockItemRainbowBricks
 * @see com.themikeste1.wabbits.atlas.Blocks
 */
public class TileEntityRainbowBricks extends TileEntity implements ITickableTileEntity {
    private int colorChangeCounter = 0;
    private final int counterStart;

    public TileEntityRainbowBricks() {
        super(TileEntities.rainbow_bricks);
        counterStart = 20;
    }
    public TileEntityRainbowBricks(int counterStart) {
        super(TileEntities.rainbow_bricks);
        this.counterStart = counterStart;
    }

    @Override
    public void tick() {
        if (!world.isRemote && !canChange())
            colorChangeCounter--;
    }

    public boolean canChange() { return colorChangeCounter <= 0; }
    public void resetCounter() { colorChangeCounter = counterStart; }
}
