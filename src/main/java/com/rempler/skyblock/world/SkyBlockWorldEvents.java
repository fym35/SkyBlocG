package com.rempler.skyblock.world;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import vazkii.botania.common.world.SkyblockWorldEvents;
import vazkii.botania.common.world.WorldTypeSkyblock;

public class SkyBlockWorldEvents {

    private SkyBlockWorldEvents() {}

    private static final String MADE_ISLAND = "MadeIsland";
    private static final String HAS_ISLAND = "HasIsland";
    private static final String ISLAND_X = "IslandX";
    private static final String ISLAND_Y = "IslandY";
    private static final String ISLAND_Z = "IslandZ";
    public static double SPAWN_X = 0;
    public static double SPAWN_Y = 0;
    public static double SPAWN_Z = 0;

    public static void onPlayerUpdate(LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().world.isRemote) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            CompoundNBT data = player.getPersistentData();
            if (!data.contains(PlayerEntity.PERSISTED_NBT_TAG)) {
                data.put(PlayerEntity.PERSISTED_NBT_TAG, new CompoundNBT());
            }

            CompoundNBT persist = data.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            if (player.ticksExisted > 3 &&!persist.getBoolean(MADE_ISLAND)){
                World overworld = ServerLifecycleHooks.getCurrentServer().getWorld(DimensionType.OVERWORLD);
                World world = player.world;
                if (SkyBlockWorldType.isWorldSkyblock(world)) {
                    BlockPos coords = world.getSpawnPoint();
                    if (coords.getY() <= 0) {
                        coords = new BlockPos(coords.getX(), 82, coords.getZ());
                        world.setSpawnPoint(coords);
                        SPAWN_X = coords.getX();
                        SPAWN_Y = coords.getY();
                        SPAWN_Z = coords.getY();
                    }
                    if (world.getBlockState(coords.down(4)).getBlock() != Blocks.BEDROCK && world == overworld) {
                        spawnPlayer(player, coords, false);
                    }
                }
                persist.putBoolean(MADE_ISLAND, true);
            }
        }
    }

    public static void spawnPlayer(PlayerEntity player, BlockPos pos, boolean fabricated) {
        CompoundNBT data = player.getPersistentData();
        if (!data.contains(PlayerEntity.PERSISTED_NBT_TAG)) {
            data.put(PlayerEntity.PERSISTED_NBT_TAG, new CompoundNBT());
        }
        CompoundNBT persist = data.getCompound(PlayerEntity.PERSISTED_NBT_TAG);

        if (!persist.getBoolean(HAS_ISLAND)) {
            createSkyblock(player.world, pos);

            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity pmp = (ServerPlayerEntity) player;
                pmp.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1.6, pos.getZ() + 0.5);
                pmp.setSpawnPoint(pos, true, false, player.world.getDimension().getType());
            }
            if (fabricated) {
                persist.putBoolean(HAS_ISLAND, true);
                persist.putDouble(ISLAND_X, player.getPosX());
                persist.putDouble(ISLAND_Y, player.getPosY());
                persist.putDouble(ISLAND_Z, player.getPosZ());
            }
        }
        else {
            double posX = persist.getDouble(ISLAND_X);
            double posY = persist.getDouble(ISLAND_Y);
            double posZ = persist.getDouble(ISLAND_Z);

            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity pmp = (ServerPlayerEntity) player;
                pmp.setPositionAndUpdate(posX, posY, posZ);
            }
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
