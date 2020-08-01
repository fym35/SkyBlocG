package com.rempler.skyblock;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.rempler.skyblock.config.ConfigOptions;
import com.rempler.skyblock.world.*;
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
import org.apache.logging.log4j.Logger;
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
    //Just copied run and command register from Botania cuz they are private in mod
    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        if (ModList.get().isLoaded("gardenofglass")) {
            WorldType worldType = new WorldTypeSkyblock();
            if (worldType.equals(ServerLifecycleHooks.getCurrentServer().getWorld(DimensionType.OVERWORLD).getWorldType())) {
                LiteralArgumentBuilder<CommandSource> cmdIsland = Commands.literal("botania-skyblock-spread")
                        .requires(s -> s.hasPermissionLevel(2))
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("range", IntegerArgumentType.integer(250, 1000000))
                                        .executes(ctx -> runGoG(ctx, IntegerArgumentType.getInteger(ctx, "range"))))
                                .executes(ctx -> runGoG(ctx, 10000)));
                dispatcher.register(cmdIsland);
            }
        } else {
            LiteralArgumentBuilder<CommandSource> cmdIsland = Commands.literal("island")
                    .requires(s -> s.hasPermissionLevel(0))
                    .then(Commands.literal("create")
                            .then(Commands.argument("range", IntegerArgumentType.integer(1000, 1000))
                                    .executes(ctx -> run(ctx, IntegerArgumentType.getInteger(ctx, "range")))));
            LiteralCommandNode<CommandSource> cmdMainCommand = dispatcher.register(cmdIsland);
            dispatcher.register(Commands.literal("is").redirect(cmdMainCommand));
            dispatcher.register(Commands.literal(SkyBlock.MODID).redirect(cmdMainCommand));
            dispatcher.register(Commands.literal("sky").redirect(cmdMainCommand));
        }
    }

    private static int runGoG(CommandContext<CommandSource> ctx, int range) throws CommandSyntaxException {
        PlayerEntity player = EntityArgument.getPlayer(ctx, "player");
        int minDist = 1000;
        BlockPos spawn = player.world.getSpawnPoint();

        int x;
        int z;
        do {
            x = player.world.rand.nextInt(range) - range / 2 + spawn.getX();
            z = player.world.rand.nextInt(range) - range / 2 + spawn.getZ();
        } while(MathHelper.pointDistancePlane(x, z, spawn.getX(), spawn.getZ()) < (float)minDist);

        SkyBlockWorldEvents.spawnPlayer(player, new BlockPos(x, ConfigOptions.islandYLevel.get(), z), true);
        return 1;
    }

    private static int run(CommandContext<CommandSource> ctx, int range) throws CommandSyntaxException {
        PlayerEntity player = EntityArgument.getPlayer(ctx, "player");
        BlockPos spawn = player.world.getSpawnPoint();

        IslandPos position = IslandManager.getNextIsland();
        assert position != null;

        int x = position.getX() * ConfigOptions.islandDistance.get();
        int z = position.getZ() * ConfigOptions.islandDistance.get();
        MathHelper.pointDistancePlane(x, z, spawn.getX(), spawn.getZ());

        SkyBlockWorldEvents.spawnPlayer(player, new BlockPos(x, ConfigOptions.islandYLevel.get(), z), true);
        return 1;
    }

    public static ResourceLocation location(String name){
        return new ResourceLocation(SkyBlock.MODID, name);
    }
}
