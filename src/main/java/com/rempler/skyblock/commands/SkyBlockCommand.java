package com.rempler.skyblock.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.rempler.skyblock.helpers.IslandPos;
import com.rempler.skyblock.config.ConfigOptions;
import com.rempler.skyblock.world.SkyBlockSavedData;
import com.rempler.skyblock.world.SkyBlockWorldEvents;
import com.rempler.skyblock.world.overworld.SkyBlockChunkGenerator;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;
import java.util.UUID;

public class SkyBlockCommand {

    protected static final SimpleCommandExceptionType NOT_SKYBLOCK_WORLD = new SimpleCommandExceptionType(
            new TranslationTextComponent("skyblock.command.world"));
    protected static final SimpleCommandExceptionType NO_ISLAND = new SimpleCommandExceptionType(
            new TranslationTextComponent("skyblock.command.noisland"));
    protected static final SimpleCommandExceptionType NOT_ENABLED = new SimpleCommandExceptionType(
            new TranslationTextComponent("skyblock.command.enabled"));

    protected static int printHelp(CommandContext<CommandSource> ctx) {
        for (int i = 0; i < 5; i++) {
            ctx.getSource().sendFeedback(new TranslationTextComponent("skyblock.command.help." + i), false);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int doTeleportToIsland(CommandContext<CommandSource> ctx, UUID owner, ITextComponent feedback) throws CommandSyntaxException {
        ServerWorld world = getSkyblockWorld(ctx);
        IslandPos pos = getIslandForUUID(owner, SkyBlockSavedData.get(world));

        ServerPlayerEntity player = ctx.getSource().asPlayer();
        BlockPos blockPos = pos.getCenter();

        player.teleport(world, blockPos.getX() + 0.5, blockPos.getY(),
                blockPos.getZ() + 0.5, player.rotationYaw, player.rotationPitch);
        ctx.getSource().sendFeedback(feedback, true);
        return Command.SINGLE_SUCCESS;
    }

    protected static int createIsland(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        PlayerEntity player = EntityArgument.getPlayer(ctx, "player");
        SkyBlockSavedData data = SkyBlockSavedData.get(getSkyblockWorld(ctx));
        UUID uuid = player.getUniqueID();

        if (data.skyblocks.containsValue(uuid)) {
            doTeleportToIsland(ctx, uuid, new TranslationTextComponent("skyblock.command.island.teleported",
                    ctx.getSource().getDisplayName()));
            return Command.SINGLE_SUCCESS;
        }

        SkyBlockWorldEvents.spawnPlayer(player, data.create(uuid));
        ctx.getSource().sendFeedback(new TranslationTextComponent("skyblock.command.island.success", player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    protected static int doRebuildIsland(CommandContext<CommandSource> ctx, UUID player, ITextComponent feedback) throws CommandSyntaxException {
        ServerWorld world = getSkyblockWorld(ctx);
        IslandPos pos = getIslandForUUID(player, SkyBlockSavedData.get(world));

        SkyBlockWorldEvents.createSkyblock(world, pos.getCenter());
        ctx.getSource().sendFeedback(feedback, true);
        return Command.SINGLE_SUCCESS;
    }

    private static IslandPos getIslandForUUID(UUID player, SkyBlockSavedData data) throws CommandSyntaxException {
        IslandPos pos = data.skyblocks.inverse().get(player);
        if (pos == null) {
            throw NO_ISLAND.create();
        }
        return pos;
    }

    private static ServerWorld getSkyblockWorld(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ServerWorld world = ctx.getSource().getWorld();
        if (!SkyBlockChunkGenerator.isWorldSkyblock(world)) {
            throw NOT_SKYBLOCK_WORLD.create();
        }
        return world;
    }
    static int teleportToIsland(CommandContext<CommandSource> ctx, PlayerEntity owner) throws CommandSyntaxException {
        if(ConfigOptions.Common.allowVisitCommand.get()) {
            return doTeleportToIsland(ctx, owner.getUniqueID(), new TranslationTextComponent("skyblock.command.teleport.success",
                    ctx.getSource().getDisplayName(), owner.getName()));
        }
        throw NOT_ENABLED.create();
    }

    static int teleportToSpawn(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        if (ConfigOptions.Common.allowHomeCommand.get()) {
            return doTeleportToIsland(ctx, Objects.requireNonNull(ctx.getSource().getEntity()).getUniqueID(), new TranslationTextComponent("skyblock.command.spawn.success",
                    ctx.getSource().getDisplayName()));
        }
        throw NOT_ENABLED.create();
    }

    static int rebuildIsland(CommandContext<CommandSource> ctx, ServerPlayerEntity owner) throws CommandSyntaxException {
        if (ConfigOptions.Common.allowIslandRegen.get()) {
            return doRebuildIsland(ctx, owner.getUniqueID(), new TranslationTextComponent("skyblock.command.regenisland.success", owner.getDisplayName()));
        }
        throw NOT_ENABLED.create();
    }
}
