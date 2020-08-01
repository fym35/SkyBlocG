package com.rempler.skyblock.world;

import com.rempler.skyblock.RegistryEvents;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class NetherVoidBiome extends Biome {
    public NetherVoidBiome() {
        super(new Biome.Builder()
                .surfaceBuilder(SurfaceBuilder.NOPE, SurfaceBuilder.AIR_CONFIG)
                .precipitation(RainType.NONE)
                .category(Category.NETHER)
                .downfall(0.0f)
                .depth(0.5f)
                .scale(0.5f)
                .temperature(0.5f)
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .parent(null)
        );

        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.NETHER_BRIDGE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        this.addStructure(Feature.NETHER_BRIDGE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.GHAST, 50, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_PIGMAN, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 2, 4, 4));


        this.setRegistryName(RegistryEvents.location("skyblock-nether-biome"));
    }
}
