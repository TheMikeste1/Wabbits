package themikeste1.wabbits.core.blocks;

//META
import themikeste1.wabbits.Constants;
import themikeste1.wabbits.core.Functions;

//Minecraft
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//Java
import javax.annotation.Nullable;


/**
 * Creates a basic {@link net.minecraft.block.Block} that will face towards the
 * {@link net.minecraft.entity.Entity} that places it, or away if they're
 * sneaking.
 *
 * @see HorizontalFacedBlock
 */
public class FacedBlock extends Block {
    /**
     * Basic constructor for the {@link net.minecraft.block.Block}. It can't
     * be much simpler than this. It literally just calls the constructor for
     * {@link Block}.
     *
     * @param registryName The to-be registry name.
     * @param properties   The to-be {@link net.minecraft.block.Block.Properties}.
     * @author TheMikeste1
     * @since 0.0.0.0
     */
    public FacedBlock(String registryName, Properties properties) {
        super(properties);
        setRegistryName(Constants.MOD_ID, registryName);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (placer == null || worldIn.isRemote)
            return;

        Direction direction;
        if (placer.isSneaking()) {
            direction = Functions.getDirectionAwayFromEntity(pos, placer);
        } else {
            direction = Functions.getDirectionTowardsEntity(pos, placer);
        }

        worldIn.setBlockState(
                pos,
                state.with(BlockStateProperties.FACING, direction),
                2
        );
    }


}
