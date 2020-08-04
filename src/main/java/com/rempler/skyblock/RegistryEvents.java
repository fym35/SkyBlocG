package com.rempler.skyblock;

import com.rempler.skyblock.config.ConfigOptions;
import com.rempler.skyblock.world.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import vazkii.botania.common.core.helper.MathHelper;
import vazkii.botania.common.world.WorldTypeSkyblock;

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
