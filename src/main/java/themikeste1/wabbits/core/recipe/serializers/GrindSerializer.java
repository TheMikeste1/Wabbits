package themikeste1.wabbits.core.recipe.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import themikeste1.wabbits.core.recipe.GrindRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.ForgeRegistries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public class GrindSerializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<GrindRecipe> {
    //Logging
    private static final Logger LOGGER = LogManager.getLogger();

    public GrindSerializer() {
    }

    @Override
    public GrindRecipe read(ResourceLocation id, JsonObject json) {
        //Group
        String group = JSONUtils.getString(json, "group", "wabbits");

        //Ingredient
        JsonElement ingredientElement = JSONUtils.isJsonArray(json, "ingredient")
                ? JSONUtils.getJsonArray(json, "ingredient")
                : JSONUtils.getJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.deserialize(ingredientElement);

        //Result
        if (!json.has("result"))
            throw new JsonSyntaxException("Missing result, expected to find a string or object: " + id.toString());

        ItemStack result = ItemStack.EMPTY;
        if (json.get("result").isJsonObject())
            result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        else {
            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            try {
                result = new ItemStack(
                        ForgeRegistries.ITEMS.getValue(resourcelocation)
                );
            } catch (Exception e) {
                LOGGER.fatal(e.getMessage());
            }
        }
        int amount = JSONUtils.getInt(json, "amount", 1);
        int processTime = JSONUtils.getInt(json, "processtime", 100);

        return new GrindRecipe(id, group, ingredient, result, amount, processTime);
    }

    @Nullable
    @Override
    public GrindRecipe read(ResourceLocation id, PacketBuffer buffer) {
        String group = buffer.readString(32767); //Is this number relevant? //Yes, it's the max String size
        Ingredient ingredient = Ingredient.read(buffer);
        ItemStack result = buffer.readItemStack();
        int amount = buffer.readInt();
        int processTime = buffer.readInt();

        return new GrindRecipe(id, group, ingredient, result, amount, processTime);
    }

    @Override
    public void write(PacketBuffer buffer, GrindRecipe recipe) {
        buffer.writeString(recipe.getGroup());

        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.write(buffer);
        }

        buffer.writeString(recipe.getRecipeOutput().toString());
    }
}
