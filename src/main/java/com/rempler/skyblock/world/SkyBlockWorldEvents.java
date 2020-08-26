package com.rempler.skyblock.world;

import com.rempler.skyblock.api.IslandPos;
import com.rempler.skyblock.world.overworld.SkyBlockChunkGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.ModList;

import vazkii.botania.common.world.SkyblockWorldEvents;
import vazkii.botania.common.world.WorldTypeSkyblock;

public class SkyBlockWorldEvents {

    private SkyBlockWorldEvents() {}

    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        World world = event.getPlayer().world;
        if (SkyBlockChunkGenerator.isWorldSkyblock(world)) {
            SkyBlockSavedData data = SkyBlockSavedData.get((ServerWorld) world);
            /*if (!data.skyblocks.containsValue(Util.DUMMY_UUID)) {
                IslandPos islandPos = data.getSpawn();
                ((ServerWorld) world).func_241124_a__(islandPos.getCenter());
                spawnPlayer(event.getPlayer(), islandPos);
                SkyBlock.LOGGER.info("Created the spawn island");
            }*/
        }
    }

    public static void spawnPlayer(PlayerEntity player, IslandPos islandPos) {
        BlockPos pos = islandPos.getCenter();
        createSkyblock(player.world, pos);
        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity pmp = (ServerPlayerEntity) player;
            pmp.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1.6, pos.getZ() + 0.5);
            pmp.teleport((ServerWorld) pmp.world.getWorld(), pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        }
    }

    public static void createSkyblock(World world, BlockPos pos) {
        if(ModList.get().isLoaded("gardenofglass") && world.getWorldType().equals(new WorldTypeSkyblock())) {
            SkyblockWorldEvents.createSkyblock(world, pos);
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 3; k++) {
                        world.setBlockState(pos.add(-1 + i, -1 - j, -1 + k), j == 0 ? Blocks.GRASS_BLOCK.getDefaultState() : j == 3 ? Blocks.BEDROCK.getDefaultState() : Blocks.DIRT.getDefaultState());
                    }
                }
            }
            for(int i = 0; i < 4; i++) {
                world.setBlockState(pos.add(0, i, 0), Blocks.OAK_LOG.getDefaultState());
            }
            for (int i = 0; i < 5; i++) {
                for(int j = 0; j < 2; j++) {
                    for(int k = 0; k < 5; k++) {
                        world.setBlockState(pos.add(-2 + i, 3 + j, -2 + k), Blocks.OAK_LEAVES.getDefaultState());
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                for(int j = 2; j < 4; j++) {
                    for(int k = 0; k < 3; k++) {
                        world.setBlockState(pos.add(-1 + i, 3 + j, -1 + k), Blocks.OAK_LEAVES.getDefaultState());
                    }
                }
            }
            world.setBlockState(pos.up(3), Blocks.OAK_LOG.getDefaultState());
        }
    }
}
