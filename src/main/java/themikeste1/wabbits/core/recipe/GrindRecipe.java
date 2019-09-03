package themikeste1.wabbits.core.recipe;

import themikeste1.wabbits.atlas.BlockItems;
import themikeste1.wabbits.atlas.RecipeSerializers;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import static themikeste1.wabbits.atlas.RecipeTypes.GRINDING;

public class GrindRecipe implements IRecipe<IInventory> {
    protected final ResourceLocation id;
    protected final String group;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final int inputAmount;
    protected final int processTime;

    public GrindRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, int inputAmount, int outputAmount, int processTime) {
        this.id = id;
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.inputAmount = inputAmount;
        result.setCount(outputAmount);
        this.processTime = processTime;
    }

    public Ingredient getIngredient() { return ingredient; }
    public int getProcessTime() { return processTime; }
    public int getInputAmount() { return inputAmount; }
    public int getOutputAmount() { return result.getCount(); }

    @Override
    public String getGroup() { return group; }

    @Override
    public boolean matches(IInventory inv, World worldIn) { return ingredient.test(inv.getStackInSlot(0)); }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        //Needs to be #copy so we don't modify the result
        return result.copy();
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public ItemStack getRecipeOutput() { return this.result; }

    @Override
    public boolean canFit(int width, int height) { return true; }

    @Override
    public ItemStack getIcon() { return new ItemStack(BlockItems.grinder); }

    @Override
    public ResourceLocation getId() { return id; }

    @Override
    public IRecipeSerializer<?> getSerializer() { return RecipeSerializers.grind; }

    @Override
    public IRecipeType<?> getType() { return GRINDING; }
}
