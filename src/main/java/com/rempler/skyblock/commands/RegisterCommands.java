package com.rempler.skyblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.rempler.skyblock.SkyBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraftforge.fml.ModList;

public class RegisterCommands {
    //Just copied command register from Botania 1.16 cuz it's working just as intended
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
                                .executes(ctx -> SkyBlockCommand.teleportToIsland(ctx, EntityArgument.getPlayer(ctx, "player")))
                        )
                )
                .then(Commands.literal("regen-island")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> SkyBlockCommand.rebuildIsland(ctx, EntityArgument.getPlayer(ctx, "player")))
                        )
                );
        LiteralCommandNode<CommandSource> cmdMainCommand = dispatcher.register(cmdIsland);
        dispatcher.register(Commands.literal("is").redirect(cmdMainCommand));
        dispatcher.register(Commands.literal(SkyBlock.MODID).redirect(cmdMainCommand));
        dispatcher.register(Commands.literal("sky").redirect(cmdMainCommand));
        if (ModList.get().isLoaded("gardenofglass")) {
            dispatcher.register(Commands.literal("gog").redirect(cmdMainCommand));
            dispatcher.register(Commands.literal("gardenofglass").redirect(cmdMainCommand));
            dispatcher.register(Commands.literal("botania-skyblock").redirect(cmdMainCommand));
        }
    }
}
