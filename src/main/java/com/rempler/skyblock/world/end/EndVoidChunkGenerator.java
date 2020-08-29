package com.rempler.skyblock.world.end;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EndVoidChunkGenerator extends EndChunkGenerator {
    public static final ChunkGeneratorType<EndGenerationSettings, EndVoidChunkGenerator> TYPE = new ChunkGeneratorType<>(EndVoidChunkGenerator::new, false, EndGenerationSettings::new);


    public EndVoidChunkGenerator(IWorld worldIn, BiomeProvider biomeProviderIn, EndGenerationSettings settingsIn) {
        super(worldIn, biomeProviderIn, settingsIn);
    }

    @Override
    public void makeBase(IWorld worldIn, IChunk chunkIn) {}

    @Override
    public void generateSurface(WorldGenRegion region, IChunk chunkIn) {}

    @Override
    public void decorate(WorldGenRegion region) {}

    @Override
    public void func_225550_a_(BiomeManager biomeManager, IChunk chunkIn, GenerationStage.Carving carvingSettings) {}
}
