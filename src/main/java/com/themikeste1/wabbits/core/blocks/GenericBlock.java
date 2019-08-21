package com.themikeste1.wabbits.core.blocks;

//Minecraft
import net.minecraft.block.Block;


/**
 * Generic {@link Block} so I can set up blocks the way I like to.
 *
 * @version 1.0
 * @since 0.0.0.0
 * @author TheMikeste1
 */
public class GenericBlock extends Block {
    /**
     * Basic constructor for the {@link Block}. It can't be much simpler than
     * this.
     *
     * @param registryName The to-be registry name.
     * @param properties The to-be {@link Block.Properties}.
     *
     * @since 0.0.0.0
     * @author TheMikeste1
     */
    public GenericBlock(String registryName, Properties properties) { super(properties); setup(registryName); }

    /**
     * Does the set up for the {@link Block}. Anything that can be generalized
     * from the constructors goes here. That way you don't have to type it for
     * every constructor.
     *
     * @param registryName The {@link Block}'s to-be registry name.
     *
     * @since 0.0.0.0
     * @author TheMikeste1
     */
    private void setup(String registryName) {
        setRegistryName(registryName);
    }
}
