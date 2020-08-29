package com.rempler.skyblock.world.nether;

import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class NetherVoidChunkGenerator extends NetherChunkGenerator {
    public static final ChunkGeneratorType<NetherGenSettings, NetherVoidChunkGenerator> TYPE = new ChunkGeneratorType<>(NetherVoidChunkGenerator::new, false, NetherGenSettings::new);


    public NetherVoidChunkGenerator(World world, BiomeProvider provider, NetherGenSettings settings) {
        super(world, provider, settings);
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
