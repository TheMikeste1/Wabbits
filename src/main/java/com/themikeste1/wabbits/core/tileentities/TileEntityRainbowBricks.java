package com.themikeste1.wabbits.core.tileentities;

//META
import com.themikeste1.wabbits.api.state.properties.BlockStateProperties;
import com.themikeste1.wabbits.atlas.TileEntities;

//Minecraft
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @see com.themikeste1.wabbits.core.blocks.BlockRainbowBricks
 * @see com.themikeste1.wabbits.core.blockitems.BlockItemRainbowBricks
 * @see com.themikeste1.wabbits.atlas.Blocks
 */
public class TileEntityRainbowBricks extends TileEntity implements ITickableTileEntity {

    private int colorChangeCounter = 0;

    public TileEntityRainbowBricks() {
        super(TileEntities.rainbow_bricks);
    }

    @Override
    public void tick() {
        if (colorChangeCounter > 0)
            colorChangeCounter--;
    }

    public void walked(BlockState state, World worldIn, BlockPos pos) {
        if (colorChangeCounter <= 0) {
            colorChangeCounter = 20;
            updateColor(state, worldIn, pos);
        }
    }

    public void clicked(BlockState state, World worldIn, BlockPos pos) {
        updateColor(state, worldIn, pos);
    }

    private void updateColor(BlockState state, World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos,
                state.cycle(BlockStateProperties.RAINBOW_COLORS));
    }
}
