package themikeste1.wabbits.core.tileentities;

import themikeste1.wabbits.atlas.TileEntityTypes;

import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ChestChangingRainbowTileEntity extends ChestTileEntity{
    private int colorChangeCounter = 0;
    private final int counterStart;

    public ChestChangingRainbowTileEntity() {
        this(20);
    }

    public ChestChangingRainbowTileEntity(int counterStart) {
        super(TileEntityTypes.chest_changing_rainbow);

        if (counterStart >= 0)
            this.counterStart = counterStart;
        else
            this.counterStart = 20;


    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isRemote && !canChange())
            colorChangeCounter--;
    }

    public boolean canChange() { return colorChangeCounter <= 0; }
    public void resetCounter() { colorChangeCounter = counterStart; }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.wabbits.rainbow_chest");
    }
}
