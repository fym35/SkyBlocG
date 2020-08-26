package com.rempler.skyblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.rempler.skyblock.SkyBlock;
import com.rempler.skyblock.api.IslandPos;
import com.rempler.skyblock.world.SkyBlockSavedData;
import com.rempler.skyblock.world.overworld.SkyBlockChunkGenerator;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class RegisterCommands {
    //Just copied run and command register from Botania cuz they are private in mod
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> cmdIsland = Commands.literal("island")
                .requires(s -> s.hasPermissionLevel(2))
                .then(Commands.literal("help")
                        .executes(SkyBlockCommand::printHelp))
                .then(Commands.literal("create")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(SkyBlockCommand::createIsland)))
                .then(Commands.literal("spawn")
                        .executes(SkyBlockCommand::teleportToSpawn))
                .then(Commands.literal("visit")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> SkyBlockCommand.teleportToIsland(ctx, EntityArgument.getPlayer(ctx, "player"))))
                        //TODO 1.16.2 Argument Type
                        /*.then(Commands.argument("playerUuid", UUIDArgument.func_239194_a_())
                                .suggests((ctx, builder) -> ISuggestionProvider.suggest(
                                        SkyBlockSavedData.get(ctx.getSource().getWorld()).skyblocks.values().stream().map(UUID::toString),builder))
                                .executes(ctx -> SkyBlockCommand.teleportToIsland(ctx, UUIDArgument.func_239195_a_(ctx, "playerUuid")))
                        )*/
                )
                .then(Commands.literal("regen-island")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> SkyBlockCommand.rebuildIsland(ctx, EntityArgument.getPlayer(ctx, "player")))
                        )
                        //TODO 1.16.2 Argument Type
                        /*.then(Commands.argument("playerUuid", UUIDArgument.func_239194_a_())
                                .suggests((ctx, builder) -> ISuggestionProvider.suggest(
                                        SkyBlockSavedData.get(ctx.getSource().getWorld()).skyblocks.values().stream().map(UUID::toString),builder))
                                .executes(ctx -> SkyBlockCommand.teleportToIsland(ctx, UUIDArgument.func_239195_a_(ctx, "playerUuid")))
                        )*/
                );
        LiteralCommandNode<CommandSource> cmdMainCommand = dispatcher.register(cmdIsland);
        dispatcher.register(Commands.literal("is").redirect(cmdMainCommand));
        dispatcher.register(Commands.literal(SkyBlock.MODID).redirect(cmdMainCommand));
        dispatcher.register(Commands.literal("sky").redirect(cmdMainCommand));
    }
    static IslandPos getIslandForUUID(UUID player, SkyBlockSavedData data) throws CommandSyntaxException {
        IslandPos pos = data.skyblocks.inverse().get(player);
        if (pos == null) {
            throw SkyBlockCommand.NO_ISLAND.create();
        }
        return pos;
    }

    private static ServerWorld getSkyblockWorld(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerWorld world = ctx.getSource().getWorld();
        if (!SkyBlockChunkGenerator.isWorldSkyblock(world)) {
            throw SkyBlockCommand.NOT_SKYBLOCK_WORLD.create();
        }
        return world;
    }
}
