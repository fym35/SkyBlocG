package com.rempler.skyblock;

import com.rempler.skyblock.world.*;

import com.rempler.skyblock.world.end.EndVoidBiome;
import com.rempler.skyblock.world.nether.NetherVoidBiome;
import com.rempler.skyblock.world.overworld.SkyBlockBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(
                SkyBlockBiomes.skyblock = new SkyBlockBiome(),
                SkyBlockBiomes.nether = new NetherVoidBiome(),
                SkyBlockBiomes.end = new EndVoidBiome()
        );
        SkyBlockBiomes.registerBiomes();
    }

    public static ResourceLocation location(String name){
        return new ResourceLocation(SkyBlock.MODID, name);
    }
}
