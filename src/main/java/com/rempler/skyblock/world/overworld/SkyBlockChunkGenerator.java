package com.rempler.skyblock.world.overworld;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SkyBlockChunkGenerator extends OverworldChunkGenerator {
    public static final ChunkGeneratorType<OverworldGenSettings, SkyBlockChunkGenerator> TYPE = new ChunkGeneratorType<>(SkyBlockChunkGenerator::new, false, OverworldGenSettings::new);

    public SkyBlockChunkGenerator(IWorld worldIn, BiomeProvider provider, OverworldGenSettings settingsIn) {
        super(worldIn, provider, settingsIn);
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
