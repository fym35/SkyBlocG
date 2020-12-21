package com.rempler.skyblock.world.skyoverworld;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.server.ServerChunkProvider;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

import static com.rempler.skyblock.helpers.ResourceLocationHelper.prefix;

@ParametersAreNonnullByDefault
public class SkyBlockChunkGenerator extends ChunkGenerator {
    // [VanillaCopy] skyoverworld chunk gen
    public static Codec<SkyBlockChunkGenerator> CODEC = RecordCodecBuilder.create(
            (instance) -> instance.group(
                    BiomeProvider.CODEC.fieldOf("biome_source").forGetter((gen) -> gen.biomeProvider),
                    Codec.LONG.fieldOf("seed").stable().forGetter((gen) -> gen.seed),
                    DimensionSettings.field_236098_b_.fieldOf("settings").forGetter((gen) -> gen.settings)
            ).apply(instance, instance.stable(SkyBlockChunkGenerator::new)));

    public static void init() {
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, prefix("skyblock-type"), SkyBlockChunkGenerator.CODEC);
    }

    private final long seed;
    private final Supplier<DimensionSettings> settings;

    public SkyBlockChunkGenerator(BiomeProvider provider, long seed, Supplier<DimensionSettings> settings) {
        super(provider, provider, settings.get().getStructures(), seed);
        this.seed = seed;
        this.settings = settings;
    }

    public static boolean isWorldSkyblock(World world) {
        return world.getChunkProvider() instanceof ServerChunkProvider
                && ((ServerChunkProvider) world.getChunkProvider()).getChunkGenerator() instanceof SkyBlockChunkGenerator;
    }

    @Nonnull
    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }

    @Nonnull
    @Override
    public ChunkGenerator func_230349_a_(long newSeed) {
        return new SkyBlockChunkGenerator(this.biomeProvider.getBiomeProvider(newSeed), newSeed, settings);
    }

    @Override
    public void generateSurface(WorldGenRegion region, IChunk chunkIn) {}

    @Override
    public void func_230352_b_(IWorld p_230352_1_, StructureManager p_230352_2_, IChunk p_230352_3_) {

    }

    @Override
    public void func_230350_a_(long seed, BiomeManager biomes, IChunk chunk, GenerationStage.Carving stage) {}

    @Override
    public void func_230351_a_(WorldGenRegion region, StructureManager structureManager) {}

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Nonnull
    @Override
    public IBlockReader func_230348_a_(int p_230348_1_, int p_230348_2_) {
        return new Blockreader(new BlockState[0]);
    }
}
