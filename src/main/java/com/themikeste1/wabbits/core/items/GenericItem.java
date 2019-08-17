package com.themikeste1.wabbits.core.items;

//META
import com.themikeste1.wabbits.atlas.ItemGroups;
import com.themikeste1.wabbits.core.Constants;

//Minecraft
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;



public class GenericItem extends Item {
    private boolean glows;

    public GenericItem(String registryName, Properties properties) {
        super(properties.group(ItemGroups.MAIN_GROUP_WABBITS));
        setup(registryName, false);
    }

    public GenericItem(String registryName, boolean glows, Properties properties) {
        super(properties.group(ItemGroups.MAIN_GROUP_WABBITS));
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
