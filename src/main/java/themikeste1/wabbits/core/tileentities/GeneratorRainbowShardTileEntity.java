package themikeste1.wabbits.core.tileentities;

//META
import themikeste1.wabbits.atlas.Items;
import themikeste1.wabbits.atlas.TileEntityTypes;
import themikeste1.wabbits.Config;
import themikeste1.wabbits.core.gui.container.GeneratorRainbowShardContainer;
import themikeste1.wabbits.core.tools.EnergyStorageWabbits;

//Minecraft
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

//Forge
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

//Java
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


@SuppressWarnings("unchecked")
public class GeneratorRainbowShardTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(this::createEnergyHandler);
    private int counter = 0;

    public GeneratorRainbowShardTileEntity() { super(TileEntityTypes.generator_rainbow_shard); }

    @Override
    public void tick() {
        if (world.isRemote)
            return;
        energyHandler.ifPresent(
                e -> ((EnergyStorageWabbits)e).pushPower(this));

        AtomicBoolean atMaxCap = new AtomicBoolean(false);
        energyHandler.ifPresent(e -> atMaxCap.set(((EnergyStorageWabbits) e).atMaxCapacity()));

        if (atMaxCap.get()) {
            if (getBlockState().get(BlockStateProperties.POWERED)) {
                world.setBlockState(
                        pos,
                        getBlockState().with(BlockStateProperties.POWERED, false),
                        3
                );
                markDirty(); //We've changed BlockState
            }
            return;
        }


        if (counter > 0) {
            counter--;
            energyHandler.ifPresent(e -> ((EnergyStorageWabbits) e).addEnergy(50));
            markDirty(); //We've added energy
        }
        //Using if here instead of else if so we can consume an item on the
        //same tick
        if (counter <= 0) {
            itemHandler.ifPresent(
                    h -> {
                        ItemStack stack = h.getStackInSlot(0);
                        if (stack.getItem() == Items.rainbow_shard) {
                            h.extractItem(0, 1, false);
                            counter = 20;
                            markDirty(); //We've consumed an item
                        }
                    }
            );
        }

        BlockState state = getBlockState();
        if (state.get(BlockStateProperties.POWERED) != counter > 0) {
            world.setBlockState(
                    pos,
                    state.with(BlockStateProperties.POWERED, counter > 0),
                    3
            );
            markDirty(); //We've changed BlockState
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        itemHandler.ifPresent( h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag) );
        CompoundNBT energyTag = tag.getCompound("energy");
        energyHandler.ifPresent( e -> ((INBTSerializable<CompoundNBT>) e).deserializeNBT(energyTag) );
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        itemHandler.ifPresent(
                h -> {
                    CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
                    tag.put("inv", compound);
                }
        );

        energyHandler.ifPresent(
                e -> {
                    CompoundNBT compound = ((INBTSerializable<CompoundNBT>) e).serializeNBT();
                    tag.put("energy", compound);
                }
        );

        return super.write(tag);
    }

    private IItemHandler createItemHandler() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == Items.rainbow_shard;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != Items.rainbow_shard) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }
        };
    }

    private IEnergyStorage createEnergyHandler() {
        return new EnergyStorageWabbits(
                Config.GENERATOR_RAINBOW_SHARD_MAXCAP.get(), //MaxCap
                0,                                //MaxReceive
                Config.GENERATOR_RAINBOW_SHARD_MAXCAP.get()  //MaxTransfer -- we have this twice so we use the correct constructor
        );
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();

        if (cap == CapabilityEnergy.ENERGY)
            return energyHandler.cast();

        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
        return new GeneratorRainbowShardContainer(id, playerInventory, world, pos);
    }

    @Override
    public ITextComponent getDisplayName() { return new StringTextComponent(getType().getRegistryName().getPath()); }

    public ItemStack getStackInSlot(int index) {
        AtomicReference<ItemStack> stack = new AtomicReference<ItemStack>();
        itemHandler.ifPresent( (h -> stack.set(h.getStackInSlot(index))) );
        return stack.get();
    }
}
