package themikeste1.wabbits.core.items;

import themikeste1.wabbits.atlas.ItemGroups;
import themikeste1.wabbits.Constants;
import themikeste1.wabbits.core.Functions;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrenchItem extends Item {

    public WrenchItem(String registryName) { this(registryName, new Item.Properties().group(ItemGroups.MAIN_GROUP_WABBITS)); }
    public WrenchItem(String registryName, Item.Properties properties) {
        super(properties);
        setRegistryName(Constants.MOD_ID, registryName);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);

        boolean changed = false;

        //We can use else-if here because no block should have more than one
        //of these.
        if (state.has(BlockStateProperties.HORIZONTAL_FACING)) {
            if (context.isPlacerSneaking()) {
                state = Functions.cycleBackwards(state, BlockStateProperties.HORIZONTAL_FACING);
            }
            else {
                state = state.cycle(BlockStateProperties.HORIZONTAL_FACING);
            }
            changed = true;
        }
        else if (state.has(BlockStateProperties.FACING)) {
            if (context.isPlacerSneaking()) {
                state = Functions.cycleBackwards(state, BlockStateProperties.FACING);
            }
            else {
                state = state.cycle(BlockStateProperties.FACING);
            }
            changed = true;
        }
        else if (state.has(BlockStateProperties.FACING_EXCEPT_UP)) {
            if (context.isPlacerSneaking()) {
                state = Functions.cycleBackwards(state, BlockStateProperties.FACING_EXCEPT_UP);
            }
            else {
                state = state.cycle(BlockStateProperties.FACING_EXCEPT_UP);
            }
            changed = true;
        }


        if (changed) {
            world.setBlockState(pos, state, 2);
        }

        return super.onItemUse(context);
    }
}
