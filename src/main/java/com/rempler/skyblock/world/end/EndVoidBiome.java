package com.rempler.skyblock.world.end;

import com.google.common.collect.ImmutableList;
import com.rempler.skyblock.RegistryEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndVoidBiome extends Biome {
    public EndVoidBiome() {
        super(new Biome.Builder()
                .surfaceBuilder(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState()))
                .precipitation(RainType.NONE)
                .category(Category.THEEND)
                .downfall(0.0f)
                .depth(0.5f)
                .scale(0.5f)
                .temperature(0.5f)
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .parent(null)
        );

        this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.END_SPIKE.withConfiguration(new EndSpikeFeatureConfig(false, ImmutableList.of(), null)).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        DefaultBiomeFeatures.addEndCity(this);
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 2, 8));

        this.setRegistryName(RegistryEvents.location("skyblock-end-biome"));
    }

    @OnlyIn(Dist.CLIENT)
    public int getSkyColor() {
        return 0;
    }

}
