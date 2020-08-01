package com.rempler.skyblock.world;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.EndChunkGenerator;
import net.minecraft.world.gen.EndGenerationSettings;

public class EndVoidChunkGenerator extends EndChunkGenerator {
    public static final ChunkGeneratorType<EndGenerationSettings, EndVoidChunkGenerator> TYPE = new ChunkGeneratorType<>(EndVoidChunkGenerator::new, false, EndGenerationSettings::new);


    public EndVoidChunkGenerator(IWorld worldIn, BiomeProvider biomeProviderIn, EndGenerationSettings settingsIn) {
        super(worldIn, biomeProviderIn, settingsIn);
    }
}
