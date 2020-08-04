package com.rempler.skyblock.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rempler.skyblock.config.ConfigOptions;
import com.rempler.skyblock.world.SkyBlockWorldEvents;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import static com.rempler.skyblock.config.MathHelper.pointDistancePlane;

public class SkyBlockCommand {
    public static int run(CommandContext<CommandSource> ctx, int range) throws CommandSyntaxException {
        PlayerEntity player = EntityArgument.getPlayer(ctx, "player");
        int minDist = 1000;
        BlockPos spawn = player.world.getSpawnPoint();

        int x;
        int z;
        do {
            x = player.world.rand.nextInt(range) - range / 2 + spawn.getX();
            z = player.world.rand.nextInt(range) - range / 2 + spawn.getZ();
        } while(pointDistancePlane(x, z, spawn.getX(), spawn.getZ()) < (float)minDist);

        SkyBlockWorldEvents.spawnPlayer(player, new BlockPos(x, ConfigOptions.Common.islandYLevel.get(), z), true);
        return 1;
    }
}
