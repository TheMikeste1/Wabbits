package themikeste1.wabbits.core.tileentities;

import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import themikeste1.wabbits.Config;
import themikeste1.wabbits.atlas.RecipeTypes;
import themikeste1.wabbits.atlas.Recipes;
import themikeste1.wabbits.atlas.TileEntityTypes;
import themikeste1.wabbits.core.gui.container.GrinderContainer;
import themikeste1.wabbits.core.recipe.GrindRecipe;
import themikeste1.wabbits.core.tools.EnergyStorageWabbits;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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
    private ItemStack upcomingOutput = ItemStack.EMPTY;
    private int energyCostPerTick = 30;
    private int ticksUntilFinished = 0;

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
        if (!hasEnergy.get()) {
            updateBlockState();
            return;
        }

        //Nothing to do if there isn't an item.
        AtomicReference<ItemStack> inputStack = new AtomicReference<>();
        itemHandler.ifPresent( h -> inputStack.set(h.getStackInSlot(0).copy()) );
        if(inputStack.get().isEmpty()) {
            updateBlockState();
            ticksUntilFinished = 0;
            return;
        }

        AtomicReference<IItemHandler> inventory = new AtomicReference<>();
        itemHandler.ifPresent( h -> inventory.set(h) );

        GrindRecipe recipe = world.getRecipeManager()
                .getRecipe(RecipeTypes.GRINDING, new RecipeWrapper((ItemStackHandler) inventory.get()), world)
                .orElse(null);

        if (canGrind(recipe)) {
            if (!isGrinding()) {
                ticksUntilFinished = recipe.getProcessTime();
                inventory.get().extractItem(0, recipe.getInputAmount(), false);
                upcomingOutput = recipe.getRecipeOutput().copy();
                markDirty();
            }

            AtomicBoolean hasEnoughEnergy = new AtomicBoolean(false);
            energyHandler.ifPresent( e -> hasEnoughEnergy.set(e.getEnergyStored() >= energyCostPerTick) );

            if (hasEnoughEnergy.get()) {
                energyHandler.ifPresent( e -> ((EnergyStorageWabbits)e).addEnergy(-energyCostPerTick) );
                ticksUntilFinished--;
                markDirty();
            }

            if (doneGrinding()) {
                inventory.get().insertItem(1, upcomingOutput, false);

                if (canGrind(recipe)) { //If we can still grind, reset timer
                    ticksUntilFinished = recipe.getProcessTime();
                    inventory.get().extractItem(0, recipe.getInputAmount(), false);
                    upcomingOutput = recipe.getRecipeOutput().copy();
                } else
                    upcomingOutput = ItemStack.EMPTY;
                markDirty();
            }
        }
        else {
            updateBlockState();
        }

        BlockState state = getBlockState();
        if (state.get(BlockStateProperties.POWERED) != ticksUntilFinished > 0) {
            world.setBlockState(
                    pos,
                    state.with(BlockStateProperties.POWERED, ticksUntilFinished > 0),
                    3
            );
            markDirty(); //We've changed BlockState
        }
    }






    protected boolean canGrind(GrindRecipe recipe) {
        AtomicReference<ItemStack> inputSlotStack = new AtomicReference<>();
        itemHandler.ifPresent( h -> inputSlotStack.set( h.getStackInSlot(0)) );
        if (inputSlotStack.get().isEmpty() || recipe == null || inputSlotStack.get().getCount() < recipe.getInputAmount())
            return false;

        ItemStack recipeOutput = recipe.getRecipeOutput();
        if (recipeOutput.isEmpty())
            return false;

        AtomicReference<ItemStack> outputSlotStack = new AtomicReference<>();
        itemHandler.ifPresent( h -> outputSlotStack.set( h.getStackInSlot(1)) );
        if (!outputSlotStack.get().isEmpty()                                 //If the output stack is empty, we can process.
            && !(outputSlotStack.get().isItemEqual(recipeOutput)             //We're also good if the output slot and the recipe output are the same AND...
               && outputSlotStack.get().getCount() + recipeOutput.getCount() //... the current amount of items + the recipe output amount ...
               <= outputSlotStack.get().getMaxStackSize())) {                //... is less than the max stack size of the output.
                return false;
        }


        return true;
    }


    public boolean isGrinding() { return ticksUntilFinished > 0; }
    public boolean doneGrinding() { return !isGrinding(); }
    private void updateBlockState() {
        if (getBlockState().get(BlockStateProperties.POWERED)) {
            world.setBlockState(
                    pos,
                    getBlockState().with(BlockStateProperties.POWERED, false),
                    3
            );
            markDirty(); //We've changed BlockState
        }
    }


    private IItemHandler createItemHandler() {
        return new ItemStackHandler(2) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0) {
                    for (GrindRecipe recipe : Recipes.grindRecipes) {
                        if (recipe.getIngredient().test(stack))
                            return true;
                    }
                }
                else if (slot == 1) {
                    return true;
                }

                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (slot == 0) {
                    for (GrindRecipe recipe : Recipes.grindRecipes) {
                        if (recipe.getIngredient().test(stack))
                            return super.insertItem(slot, stack, simulate);
                    }
                }
                else if (slot == 1) {
                    return super.insertItem(slot, stack, simulate);
                }

                return stack;
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

        ticksUntilFinished = tag.getInt("ticksUntilFinished");

        upcomingOutput = new ItemStack(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryCreate(tag.getString("upcomingOutput"))));
        upcomingOutput.setCount(tag.getInt("upcomingOutputAmount"));
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

        tag.putInt("ticksUntilFinished", ticksUntilFinished);
        tag.putString("upcomingOutput", upcomingOutput.getItem().getRegistryName().toString());
        tag.putInt("upcomingOutputAmount", upcomingOutput.getCount());

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
