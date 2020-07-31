package com.rempler.skyblock;

import com.rempler.skyblock.world.SkyBlockBiome;
import com.rempler.skyblock.world.SkyBlockBiomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {
    public static final Logger LOGGER = SkyBlock.LOGGER;
    public static final String MODID = SkyBlock.MODID;

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        event.getRegistry().registerAll(
                SkyBlockBiomes.skyblock = new SkyBlockBiome()
        );
        SkyBlockBiomes.registerBiomes();
    }

    public static ResourceLocation location(String name){
        return new ResourceLocation(MODID, name);
    }
}
