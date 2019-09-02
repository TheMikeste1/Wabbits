package themikeste1.wabbits.core.tileentities;

import themikeste1.wabbits.Config;
import themikeste1.wabbits.atlas.RecipeTypes;
import themikeste1.wabbits.atlas.TileEntityTypes;
import themikeste1.wabbits.core.gui.container.GrinderContainer;
import themikeste1.wabbits.core.tools.EnergyStorageWabbits;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


//TODO: Add ticking


@SuppressWarnings("DuplicatedCode")
public class GrinderTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createItemHandler);
    private LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(this::createEnergyHandler);
    private int counter = 0;

    public GrinderTileEntity() {
        super(TileEntityTypes.grinder);
    }

    @Override
    public void tick() {
        if (world.isRemote)
            return;

        //Nothing to do if we have no energy
        AtomicBoolean hasEnergy = new AtomicBoolean();
        energyHandler.ifPresent( e -> hasEnergy.set(e.getEnergyStored() > 0) );
        if (!hasEnergy.get())
            return;

        //Nothing to do if there isn't an item.
        AtomicReference<ItemStack> stack = new AtomicReference<>();
        itemHandler.ifPresent( h -> stack.set(h.getStackInSlot(0).copy()) );
        if(stack.get().isEmpty())
            return;

        AtomicReference<RecipeWrapper> inventory = new AtomicReference<>();
        itemHandler.ifPresent( h -> inventory.set(new RecipeWrapper((ItemStackHandler) h)) );

        IRecipe<?> recipe = world.getRecipeManager()
                .getRecipe(RecipeTypes.GRINDING, inventory.get(), world)
                .orElse(null);
    }










    private IItemHandler createItemHandler() {
        return new ItemStackHandler(2) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return ItemTags.getCollection()
                        .get(ResourceLocation.tryCreate("forge:ores"))
                        .contains(stack.getItem())
                        && slot == 0;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot != 0
                        || !ItemTags.getCollection()
                        .get(ResourceLocation.tryCreate("forge:ores"))
                        .contains(stack.getItem())
                ) {
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
        return new EnergyStorageWabbits(Config.GRINDER_MAXCAP.get());
    }

    @Override
    public ITextComponent getDisplayName() { return new StringTextComponent(getType().getRegistryName().getPath()); }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new GrinderContainer(id, playerInventory, world, pos);
    }

    public ItemStack getStackInSlot(int index) {
        AtomicReference<ItemStack> stack = new AtomicReference<ItemStack>();
        itemHandler.ifPresent( (h -> stack.set(h.getStackInSlot(index))) );
        return stack.get();
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();

        if (cap == CapabilityEnergy.ENERGY)
            return energyHandler.cast();

        return super.getCapability(cap, side);
    }
}
