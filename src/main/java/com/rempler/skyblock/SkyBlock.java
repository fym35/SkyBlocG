package com.rempler.skyblock;

import com.rempler.skyblock.commands.SkyBlockCommand;
import com.rempler.skyblock.config.ConfigOptions;
import com.rempler.skyblock.helpers.PacketHandler;
import com.rempler.skyblock.world.SkyBlockWorldEvents;
import com.rempler.skyblock.world.SkyBlockWorldType;
import net.minecraft.client.gui.screen.BiomeGeneratorTypeScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SkyBlock.MODID)
public class SkyBlock
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "skyblock";

    public SkyBlock() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigOptions.COMMON_SPEC);

        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        MinecraftForge.EVENT_BUS.addListener(SkyBlockWorldEvents::syncStatus);
        MinecraftForge.EVENT_BUS.addListener(SkyBlockWorldEvents::onPlayerJoin);
        MinecraftForge.EVENT_BUS.register(this);


    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        BiomeGeneratorTypeScreens.field_239068_c_.add(SkyBlockWorldType.INSTANCE);
    }

    private void registerCommands(RegisterCommandsEvent event) {
        /*if (ModList.get().isLoaded("gardenofglass")) {
            if (event.getServer().getWorld(World.OVERWORLD).equals(WorldTypeSkyblock.byName("botania-skyblock"))) {
                CommandSkyblockSpread.register(event.getCommandDispatcher());
            }
        } else {*/
        SkyBlockCommand.register(event.getDispatcher());
        //}
    }
}
