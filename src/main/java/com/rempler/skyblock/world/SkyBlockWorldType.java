package com.rempler.skyblock.world;

import com.rempler.skyblock.config.ConfigOptions;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.*;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.EndGenerationSettings;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class SkyBlockWorldType extends WorldType {
    public SkyBlockWorldType() {
        super("skyblock-type");
    }

    public static boolean isWorldSkyblock(World world) {
        return world.getWorldInfo().getGenerator() instanceof SkyBlockWorldType;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean hasInfoNotice() {
        return true;
    }

    @Override
    public float getCloudHeight() {
        try {
            float cloudHeight = ConfigOptions.Common.cloudLevel.get();
            return cloudHeight;
        } catch (NullPointerException e){
            return 188f;
        }
    }

    @Nonnull
    @Override
    public ChunkGenerator<?> createChunkGenerator(World world){
        if (world.dimension.getType() == DimensionType.OVERWORLD) {
            OverworldGenSettings genSettings = SkyBlockChunkGenerator.TYPE.createSettings();
            OverworldBiomeProviderSettings biomeSettings = BiomeProviderType.VANILLA_LAYERED.createSettings(world.getWorldInfo()).setGeneratorSettings(genSettings);
            return SkyBlockChunkGenerator.TYPE.create(world, BiomeProviderType.VANILLA_LAYERED.create(biomeSettings), genSettings);
        } else if (world.dimension.getType() == DimensionType.THE_END) {
            EndGenerationSettings genSettings = EndVoidChunkGenerator.TYPE.createSettings();
            EndBiomeProviderSettings biomeSettings = BiomeProviderType.THE_END.createSettings(world.getWorldInfo());
            return EndVoidChunkGenerator.TYPE.create(world, new EndBiomeProvider(biomeSettings), genSettings);
        }
        //TODO make NetherVoid
        /*else if (world.dimension.getType() == DimensionType.THE_NETHER) {
            NetherGenSettings genSettings = NetherVoidChunkGenerator.TYPE.createSettings();
            return NetherVoidChunkGenerator.TYPE.create(world, new NetherBiome(), genSettings);
        }*/
        return super.createChunkGenerator(world);
    }

    /* In skyblock worlds, do not darken the sky until player hits y=0 */
    @Override
    public double getHorizon(World world) {
        return 0.0D;
    }

    @Override
    public double voidFadeMagnitude() {
        return 1.0D;
    }
}
