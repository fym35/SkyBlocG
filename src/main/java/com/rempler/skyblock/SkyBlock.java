package com.rempler.skyblock;

import com.rempler.skyblock.helpers.PacketHandler;
import com.rempler.skyblock.commands.RegisterCommands;
import com.rempler.skyblock.config.ConfigOptions;
import com.rempler.skyblock.world.SkyBlockWorldEvents;
import com.rempler.skyblock.world.SkyBlockWorldType;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.botania.common.core.command.CommandSkyblockSpread;
import vazkii.botania.common.world.WorldTypeSkyblock;

@Mod(SkyBlock.MODID)
public class SkyBlock
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "skyblock";
    public static final WorldType SKYBLOCK_TYPE = new SkyBlockWorldType();

    public SkyBlock() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigOptions.COMMON_SPEC);

        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
        MinecraftForge.EVENT_BUS.addListener(SkyBlockWorldEvents::syncStatus);
        MinecraftForge.EVENT_BUS.addListener(SkyBlockWorldEvents::onPlayerJoin);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void serverStarting(final FMLServerStartingEvent event) {
        if (ModList.get().isLoaded("gardenofglass")) {
            if (event.getServer().getWorld(DimensionType.OVERWORLD).getWorldType().equals(WorldTypeSkyblock.byName("botania-skyblock"))) {
                CommandSkyblockSpread.register(event.getCommandDispatcher());
            }
        } else {
            if (event.getServer().getWorld(DimensionType.OVERWORLD).getWorldType().equals(SkyBlockWorldType.byName("skyblock-type"))) {
                RegisterCommands.register(event.getCommandDispatcher());
            }
        }
    }
}
