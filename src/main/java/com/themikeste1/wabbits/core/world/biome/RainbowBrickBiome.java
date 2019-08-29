package com.themikeste1.wabbits.core.world.biome;

import com.themikeste1.wabbits.atlas.Blocks;
import com.themikeste1.wabbits.Constants;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;



public class RainbowBrickBiome extends Biome {
    public RainbowBrickBiome() {
        super(new Biome.Builder()
                .surfaceBuilder(
                        SurfaceBuilder.DEFAULT,
                        new SurfaceBuilderConfig(Blocks.rainbow_bricks.getDefaultState(), Blocks.rainbow_bricks.getDefaultState(), Blocks.rainbow_bricks.getDefaultState()))
                .precipitation(Biome.RainType.RAIN)
                .category(Category.DESERT)
                .depth(0.0F)
                .scale(0.3F)
                .temperature(1.0F)
                .downfall(0.2F)
                .waterColor(0)
                .waterFogColor(0)
                .category(Category.SAVANNA)
                .parent((String)null)
        );

        this.addStructure(Feature.PILLAGER_OUTPOST, new PillagerOutpostConfig(0.25D));
        this.addStructure(Feature.MINESHAFT, new MineshaftConfig(0.004D, MineshaftStructure.Type.MESA));
        this.addStructure(Feature.STRONGHOLD, IFeatureConfig.NO_FEATURE_CONFIG);
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addStructures(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.func_222348_W(this);
        DefaultBiomeFeatures.addMushrooms(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addFreezeTopLayer(this);
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.RABBIT, 4, 2, 3));
        this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));

        setRegistryName(Constants.MOD_ID, "rainbow_brick_biome");
    }
}
