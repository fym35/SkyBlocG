package com.rempler.skyblock.world.nether;

import net.minecraft.world.World;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.NetherChunkGenerator;
import net.minecraft.world.gen.NetherGenSettings;

public class NetherVoidChunkGenerator extends NetherChunkGenerator {
    public static final ChunkGeneratorType<NetherGenSettings, NetherVoidChunkGenerator> TYPE = new ChunkGeneratorType<>(NetherVoidChunkGenerator::new, false, NetherGenSettings::new);


    public NetherVoidChunkGenerator(World world, BiomeProvider provider, NetherGenSettings settings) {
        super(world, provider, settings);
    }
}
