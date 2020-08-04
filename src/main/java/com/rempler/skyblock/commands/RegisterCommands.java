package com.rempler.skyblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.rempler.skyblock.SkyBlock;
import com.rempler.skyblock.config.ConfigOptions;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import vazkii.botania.common.world.WorldTypeSkyblock;

import static com.rempler.skyblock.commands.SkyBlockCommand.run;

public class RegisterCommands {
    //Just copied run and command register from Botania cuz they are private in mod
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        if (ModList.get().isLoaded("gardenofglass")) {
            WorldType worldType = new WorldTypeSkyblock();
            if (worldType.equals(ServerLifecycleHooks.getCurrentServer().getWorld(DimensionType.OVERWORLD).getWorldType())) {
                LiteralArgumentBuilder<CommandSource> cmdIsland = Commands.literal("botania-skyblock-spread")
                        .requires(s -> s.hasPermissionLevel(2))
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("range", IntegerArgumentType.integer(250, 1000000))
                                        .executes(ctx -> GoGCommand.runGoG(ctx, IntegerArgumentType.getInteger(ctx, "range"))))
                                .executes(ctx -> GoGCommand.runGoG(ctx, 10000)));
                dispatcher.register(cmdIsland);
            }
        } else {
            LiteralArgumentBuilder<CommandSource> cmdIsland =
                    Commands.literal("island")
                            .requires(s -> s.hasPermissionLevel(0))
                            .then(Commands.literal("create")
                                    .then(Commands.argument("player", EntityArgument.player())
                                            .then(Commands.argument("range", IntegerArgumentType.integer(1000, 1000))
                                                    .executes(ctx -> run(ctx, IntegerArgumentType.getInteger(ctx, "range"))))
                                            .executes(ctx -> run(ctx, ConfigOptions.Common.islandDistance.get()))));
            LiteralCommandNode<CommandSource> cmdMainCommand = dispatcher.register(cmdIsland);
            dispatcher.register(Commands.literal("is").redirect(cmdMainCommand));
            dispatcher.register(Commands.literal(SkyBlock.MODID).redirect(cmdMainCommand));
            dispatcher.register(Commands.literal("sky").redirect(cmdMainCommand));
        }
    }
}
