package com.rempler.skyblock.world;

import com.rempler.skyblock.world.overworld.SkyBlockChunkGenerator;
import net.minecraft.client.gui.screen.BiomeGeneratorTypeScreens;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;

import javax.annotation.Nonnull;

public class SkyBlockWorldType extends BiomeGeneratorTypeScreens {
    public static final BiomeGeneratorTypeScreens INSTANCE = new SkyBlockWorldType();

    public SkyBlockWorldType() {
        super("skyblock-type");
    }

    @Nonnull
    @Override
    public ChunkGenerator func_241869_a(@Nonnull Registry<Biome> biomeRegistry, @Nonnull Registry<DimensionSettings> dimensionSettingsRegistry, long seed){
        //if (world.dimension.getType() == DimensionType.OVERWORLD) {
            return new SkyBlockChunkGenerator(new OverworldBiomeProvider(seed, false, false, biomeRegistry), seed,
                    () -> dimensionSettingsRegistry.getOrThrow(DimensionSettings.field_242734_c));
        /*}
        //TODO make End Voidable
        else if (world.dimension.getType() == DimensionType.THE_END && ConfigOptions.Common.endVoid.get()) {
            EndGenerationSettings genSettings = EndVoidChunkGenerator.TYPE.createSettings();
            EndBiomeProviderSettings biomeSettings = BiomeProviderType.THE_END.createSettings(world.getWorldInfo());
            return EndVoidChunkGenerator.TYPE.create(world, new EndBiomeProvider(biomeSettings), genSettings);
        }
        //TODO make NetherVoid
        else if (world.dimension.getType() == DimensionType.THE_NETHER) {
            NetherGenSettings genSettings = NetherVoidChunkGenerator.TYPE.createSettings();
            SingleBiomeProviderSettings single = new SingleBiomeProviderSettings(world.getWorldInfo());
            single.setBiome(SkyBlockBiomes.nether);
            return NetherVoidChunkGenerator.TYPE.create(world, new SingleBiomeProvider(single), genSettings);
        }
        return super.createChunkGenerator(world);*/
    }
}
