package com.rempler.skyblock;

import com.rempler.skyblock.world.SkyBlockWorldEvents;
import com.rempler.skyblock.world.SkyBlockWorldType;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
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
    public static final WorldType THE_VOIDS_TYPE = new SkyBlockWorldType();

    public SkyBlock() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverStarting);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.addListener(SkyBlockWorldEvents::onPlayerUpdate);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void serverStarting(final FMLServerStartingEvent event) {
        if (ModList.get().isLoaded("gardenofglass")) {
            WorldType worldType = new WorldTypeSkyblock();
            if (worldType.equals(event.getServer().getWorld(DimensionType.OVERWORLD).getWorldType())) {
                CommandSkyblockSpread.register(event.getCommandDispatcher());
            }
        } else {
            WorldType skyblock = new SkyBlockWorldType();
            if (skyblock.equals(event.getServer().getWorld(DimensionType.OVERWORLD).getWorldType())) {
                RegistryEvents.registerCommands(event.getCommandDispatcher());
                LOGGER.info("Test: " + event.getCommandDispatcher());
            }
        }
    }
}
