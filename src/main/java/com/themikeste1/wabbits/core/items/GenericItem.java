package com.themikeste1.wabbits.core.items;

//META
import com.themikeste1.wabbits.atlas.ItemGroups;
import com.themikeste1.wabbits.core.Constants;

//Minecraft
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;



public class GenericItem extends Item {
    private boolean glows;

    public GenericItem(String registryName) {
        super(new Item.Properties());
        setup(registryName, false);
    }

    public GenericItem(String registryName, boolean glows) {
        super(new Item.Properties());
        setup(registryName, glows);
    }

    public GenericItem(String registryName, Properties properties) {
        super(properties);
        setup(registryName, false);
    }

    public GenericItem(String registryName, boolean glows, Properties properties) {
        super(properties);
        setup(registryName, glows);
    }

    private void setup(String registryName, boolean glows) {
        setRegistryName(Constants.MOD_ID, registryName);

        this.glows = glows;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return glows;
    }
}
