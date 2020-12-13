package com.rempler.skyblock;

import com.rempler.skyblock.commands.SkyBlockCommand;
import com.rempler.skyblock.config.ConfigOptions;
import com.rempler.skyblock.helpers.PacketHandler;
import com.rempler.skyblock.helpers.Registry;
import com.rempler.skyblock.world.SkyBlockWorldEvents;
import com.rempler.skyblock.world.SkyBlockWorldType;
import com.rempler.skyblock.world.overworld.SkyBlockChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.rempler.skyblock.helpers.ResourceLocationHelper.prefix;

@Mod(SkyBlock.MODID)
public class SkyBlock
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "skyblock";

    public SkyBlock() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigOptions.COMMON_SPEC);

        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        MinecraftForge.EVENT_BUS.register(this);

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ForgeWorldType.class, this::registerWorldtype);

    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(SkyBlockWorldEvents::syncStatus);
        MinecraftForge.EVENT_BUS.addListener(SkyBlockWorldEvents::onPlayerJoin);

        PacketHandler.init();

        event.enqueueWork(SkyBlockChunkGenerator::init);
    }

    private void registerWorldtype(RegistryEvent.Register<ForgeWorldType> event) {
        Registry.register(event.getRegistry(), prefix("skyblock-type"), SkyBlockWorldType.INSTANCE);
    }
	    
    private void registerCommands(RegisterCommandsEvent event) {
        SkyBlockCommand.register(event.getDispatcher());
    }
}
