package com.rempler.skyblock.world.skyoverworld;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraftforge.common.world.ForgeWorldType;

import javax.annotation.Nonnull;

public class SkyBlockWorldType extends ForgeWorldType {
    public static final SkyBlockWorldType INSTANCE = new SkyBlockWorldType();

    public SkyBlockWorldType() {
        super(SkyBlockWorldType::getChunkGenerator);
    }

    @Nonnull
    private static ChunkGenerator getChunkGenerator(@Nonnull Registry<Biome> biomeRegistry, @Nonnull Registry<DimensionSettings> dimensionSettingsRegistry, long seed) {
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

    @Override
    public String getTranslationKey() {
        return "generator.skyblock-type";
    }
}
