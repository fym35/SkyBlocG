package com.rempler.skyblock;

import com.rempler.skyblock.world.SkyBlockWorldType;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.botania.common.Botania;
import vazkii.botania.common.core.command.CommandSkyblockSpread;
import vazkii.botania.common.world.WorldTypeSkyblock;

@Mod(SkyBlock.MODID)
public class SkyBlock
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "skyblock";
    public static final WorldType THE_VOIDS_TYPE = new SkyBlockWorldType();

    public SkyBlock() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void serverStarting(FMLServerStartingEvent event) {
        WorldType worldType = event.getServer().getWorld(DimensionType.OVERWORLD).getWorldType();
        if (Botania.gardenOfGlassLoaded && worldType == WorldTypeSkyblock.byName("botania-skyblock")) {
            CommandSkyblockSpread.register(event.getCommandDispatcher());
        }
        if (Botania.gardenOfGlassLoaded && worldType == SkyBlockWorldType.byName("skyblock")) {
            RegistryEvents.registerCommands(event.getCommandDispatcher());
        }
    }
}
