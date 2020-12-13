package com.rempler.skyblock.world;

import com.rempler.skyblock.SkyBlock;
import com.rempler.skyblock.helpers.IslandPos;
import com.rempler.skyblock.helpers.PacketHandler;
import com.rempler.skyblock.helpers.PacketWorld;
import com.rempler.skyblock.world.overworld.SkyBlockChunkGenerator;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkyBlockWorldEvents {

    private SkyBlockWorldEvents() {}

    public static void syncStatus(EntityJoinWorldEvent evt) {
        if (evt.getEntity() instanceof ServerPlayerEntity) {
            boolean isSkyblock = SkyBlockChunkGenerator.isWorldSkyblock(evt.getWorld());
            if (isSkyblock) {
                PacketHandler.sendTo((ServerPlayerEntity) evt.getEntity(), new PacketWorld());
            }
        }
    }

    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        World world = event.getPlayer().world;
        if (SkyBlockChunkGenerator.isWorldSkyblock(world)) {
            SkyBlockSavedData data = SkyBlockSavedData.get((ServerWorld) world);
            if (!data.skyblocks.containsValue(Util.DUMMY_UUID)) {
                IslandPos islandPos = data.getSpawn();
                ((ServerWorld) world).func_241124_a__(islandPos.getCenter(), 0);
                spawnPlayer(event.getPlayer(), islandPos);
                SkyBlock.LOGGER.info("Created the spawn island");
            }
        }
    }

    public static void spawnPlayer(PlayerEntity player, IslandPos islandPos) {
        BlockPos pos = islandPos.getCenter();
        createSkyblock(player.world, pos);

        if (player instanceof ServerPlayerEntity) {
            ServerPlayerEntity pmp = (ServerPlayerEntity) player;
            pmp.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1.6, pos.getZ() + 0.5);
            pmp.func_242111_a(pmp.world.getDimensionKey(), pos, 0, true, false);
        }
    }

    public static void createSkyblock(World world, BlockPos pos) {
        ResourceLocation biome = world.getBiome(pos).getRegistryName();
        BlockState wood = Blocks.OAK_LOG.getDefaultState();
        BlockState leaves = Blocks.OAK_LEAVES.getDefaultState();
        if (biome == Biomes.DARK_FOREST.getRegistryName() || biome == Biomes.DARK_FOREST_HILLS.getRegistryName()){
            wood = Blocks.DARK_OAK_LOG.getDefaultState();
            leaves = Blocks.DARK_OAK_LEAVES.getDefaultState();
        }
        else if (biome == Biomes.BIRCH_FOREST.getRegistryName() || biome == Biomes.BIRCH_FOREST_HILLS.getRegistryName() || biome == Biomes.TALL_BIRCH_FOREST.getRegistryName() || biome == Biomes.TALL_BIRCH_HILLS.getRegistryName()){
            wood = Blocks.BIRCH_LOG.getDefaultState();
            leaves = Blocks.BIRCH_LEAVES.getDefaultState();
        }
        else if (biome == Biomes.DESERT_LAKES.getRegistryName() || biome == Biomes.JUNGLE.getRegistryName() || biome == Biomes.JUNGLE_EDGE.getRegistryName() || biome == Biomes.JUNGLE_HILLS.getRegistryName() || biome == Biomes.MODIFIED_JUNGLE.getRegistryName() || biome == Biomes.MODIFIED_JUNGLE_EDGE.getRegistryName()){
            wood = Blocks.JUNGLE_LOG.getDefaultState();
            leaves = Blocks.JUNGLE_LEAVES.getDefaultState();
        }
        else if (biome == Biomes.DESERT.getRegistryName() || biome == Biomes.DESERT_HILLS.getRegistryName() || biome == Biomes.SAVANNA.getRegistryName() || biome == Biomes.SAVANNA_PLATEAU.getRegistryName() || biome == Biomes.SHATTERED_SAVANNA.getRegistryName() || biome == Biomes.SHATTERED_SAVANNA_PLATEAU.getRegistryName()){
            wood = Blocks.ACACIA_LOG.getDefaultState();
            leaves = Blocks.ACACIA_LEAVES.getDefaultState();
        }
        else if (biome == Biomes.SNOWY_TUNDRA.getRegistryName() || biome == Biomes.MOUNTAINS.getRegistryName() || biome == Biomes.WOODED_MOUNTAINS.getRegistryName() || biome == Biomes.GIANT_SPRUCE_TAIGA.getRegistryName() || biome == Biomes.GIANT_SPRUCE_TAIGA_HILLS.getRegistryName() || biome == Biomes.TAIGA.getRegistryName()){
            wood = Blocks.SPRUCE_LOG.getDefaultState();
            leaves = Blocks.SPRUCE_LEAVES.getDefaultState();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    world.setBlockState(pos.add(-1 + i, -1 - j, -1 + k), j == 0 ? Blocks.GRASS_BLOCK.getDefaultState() : j == 3 ? Blocks.BEDROCK.getDefaultState() : Blocks.DIRT.getDefaultState());
                }
            }
        }
        for(int j = 0; j < 4; j++) {
            world.setBlockState(pos.add(0, j, 0), wood);
        }
        for (int i = 0; i < 5; i++) {
            for(int j = 0; j < 2; j++) {
                for(int k = 0; k < 5; k++) {
                    world.setBlockState(pos.add(-2 + i, 3 + j, -2 + k), leaves);
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for(int j = 2; j < 4; j++) {
                for(int k = 0; k < 3; k++) {
                    world.setBlockState(pos.add(-1 + i, 3 + j, -1 + k), leaves);
                }
            }
        }
        for (int i = 3; i < 5; i++) {
            world.setBlockState(pos.up(i), wood);
        }

    }
}
