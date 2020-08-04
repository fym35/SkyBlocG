package com.rempler.skyblock.world;

import com.rempler.skyblock.RegistryEvents;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class EndVoidBiome extends Biome {
    public EndVoidBiome() {
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

        DefaultBiomeFeatures.addEndCity(this);

        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 2, 8));

        this.setRegistryName(RegistryEvents.location("skyblock-end-biome"));
    }
}
