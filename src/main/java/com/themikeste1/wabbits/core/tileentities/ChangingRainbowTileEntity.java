package com.themikeste1.wabbits.core.tileentities;

//META
import com.themikeste1.wabbits.atlas.TileEntitiesTypes;

//Minecraft
import com.themikeste1.wabbits.core.blockitems.ChangingRainbowBlockItem;
import com.themikeste1.wabbits.core.blocks.ChangingRainbowBlock;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;


//TODO: Find way to reduce ticks when not used for a while.
// For example, create a counter, when counter reaches 100 ticks, time until
// next tick increases. Resets when next used. Use  Block#tickrate()?
// Also, look at how crops to their ticking.

/**
 *
 * @see ChangingRainbowBlock
 * @see ChangingRainbowBlockItem
 * @see com.themikeste1.wabbits.atlas.Blocks
 */
public class ChangingRainbowTileEntity extends TileEntity implements ITickableTileEntity {
    private int colorChangeCounter = 0;
    private int counterStart = -1;

    public ChangingRainbowTileEntity()                                      { super(TileEntitiesTypes.changing_rainbow); setup(counterStart); }
    public ChangingRainbowTileEntity(                     int counterStart) { super(TileEntitiesTypes.changing_rainbow); setup(counterStart); }
    public ChangingRainbowTileEntity(TileEntityType type)                   { super(type);                               setup(counterStart); }
    public ChangingRainbowTileEntity(TileEntityType type, int counterStart) { super(type);                               setup(counterStart); }

    private void setup(int counterStart) { this.counterStart = counterStart >= 0 ? counterStart : 20; }

    @Override
    public void tick() {
        if (!world.isRemote && !canChange())
            colorChangeCounter--;
    }

    public boolean canChange() { return colorChangeCounter <= 0; }
    public void resetCounter() { colorChangeCounter = counterStart; }
    public void setCounter(int counter) { colorChangeCounter = counter >= 0 ? counter : counterStart; }
}
