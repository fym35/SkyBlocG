package com.rempler.skyblock.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber
public final class ConfigOptions {
    public static class Common {
        //Common Island Options
        public static ForgeConfigSpec.IntValue islandDistance;
        public static ForgeConfigSpec.IntValue islandYLevel;
        public static ForgeConfigSpec.EnumValue<BottomBlockType> bottomBlockType;
        public static ForgeConfigSpec.BooleanValue allowIslandCreation;
        //GoG Island
        // public static ForgeConfigSpec.BooleanValue enableGoGIsland;
        //Advanced World Generation
        public static ForgeConfigSpec.BooleanValue allowVisitCommand;
        public static ForgeConfigSpec.BooleanValue allowHomeCommand;
        public static ForgeConfigSpec.BooleanValue allowIslandRegen;

        public enum BottomBlockType {
            BEDROCK, SECONDARYBLOCK
        }

        public Common(ForgeConfigSpec.Builder server) {
        //    cloudLevel = server.comment("Level where clouds appear").defineInRange("cloudLevel", 96, 1, 255);
            islandDistance = server.comment("The multiplier for island distances for multiplayer Garden of Glass worlds.\n" +
                    "Islands are placed on a grid with 256 blocks between points, with the spawn island always being placed on 256, 256.\n" +
                    "By default, the scale is 8, putting each island on points separated by 2048 blocks.\n" +
                    "You can't set the Value < 4 due to Nether portal collisions.")
                    .defineInRange("islandDistance", 8, 4, 512);
            islandYLevel = server.comment("Y Level to spawn islands at (Set to 2 above where you want the ground block)").defineInRange("islandYLevel", 72, 1, 244);
            bottomBlockType =
