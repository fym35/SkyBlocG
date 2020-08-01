package com.rempler.skyblock.world;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;

public class SkyBlockBiomes {
    public static Biome skyblock;
    public static Biome nether;
    public static Biome end;

    public static void registerBiomes(){
        registerBiome(skyblock, Type.PLAINS, Type.BEACH, Type.FOREST, Type.HILLS, Type.OCEAN, Type.OVERWORLD, Type.SNOWY, Type.WATER, Type.SAVANNA);
        registerBiome(nether, Type.NETHER);
        registerBiome(end, Type.END);
    }

    public static void registerBiome(Biome biome, Type... types){
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }
}
