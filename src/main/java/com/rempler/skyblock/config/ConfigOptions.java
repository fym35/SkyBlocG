package com.rempler.skyblock.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.rempler.skyblock.SkyBlock;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.util.Arrays;

@Mod.EventBusSubscriber
public class ConfigOptions {
    //Builder
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG;
    //Other Options
    public static ForgeConfigSpec.BooleanValue hideToasts;
    //Void Options
    public static ForgeConfigSpec.BooleanValue netherVoid;
    public static ForgeConfigSpec.BooleanValue netherVoidStructures;
    public static ForgeConfigSpec.BooleanValue endVoid;
    public static ForgeConfigSpec.BooleanValue endVoidStructures;
    public static ForgeConfigSpec.EnumValue<WorldGenType> worldGenType;
    public static ForgeConfigSpec.ConfigValue<String> worldGenSpecialParameters;
    public static ForgeConfigSpec.IntValue worldBiomeID;
    public static ForgeConfigSpec.ConfigValue<DimensionType> baseDimension;
    public static ForgeConfigSpec.IntValue cloudLevel;
    public static ForgeConfigSpec.IntValue horizonLevel;
    //Common Island Options
    public static ForgeConfigSpec.ConfigValue<String> islandMainSpawnType;
    public static ForgeConfigSpec.ConfigValue<String> islandSpawnType;
    public static ForgeConfigSpec.IntValue islandDistance;
    public static ForgeConfigSpec.IntValue protectionBuildRange;
    public static ForgeConfigSpec.BooleanValue spawnProtection;
    public static ForgeConfigSpec.BooleanValue islandProtection;
    public static ForgeConfigSpec.IntValue islandSize;
    public static ForgeConfigSpec.BooleanValue spawnChest;
    public static ForgeConfigSpec.BooleanValue oneChunk;
    public static ForgeConfigSpec.ConfigValue<String[]> startingItems;
    public static ForgeConfigSpec.IntValue islandBiomeID;
    public static ForgeConfigSpec.IntValue islandBiomeRange;
    public static ForgeConfigSpec.IntValue islandYLevel;
    public static ForgeConfigSpec.EnumValue<BottomBlockType> bottomBlockType;
    public static ForgeConfigSpec.BooleanValue autoCreate;
    public static ForgeConfigSpec.BooleanValue autoCreateServersOnly;
    public static ForgeConfigSpec.BooleanValue allowIslandCreation;
    public static ForgeConfigSpec.BooleanValue resetInventory;
    public static ForgeConfigSpec.ConfigValue<String[]> customIslands;
    public static ForgeConfigSpec.BooleanValue forceSpawn;
    public static ForgeConfigSpec.IntValue buffTimer;
    //Grass Island
    public static ForgeConfigSpec.BooleanValue enableGrassIsland;
    public static ForgeConfigSpec.BooleanValue spawnTree;
    public static ForgeConfigSpec.EnumValue<GrassBlockType> grassBlockType;
    //Sand Island
    public static ForgeConfigSpec.BooleanValue enableSandIsland;
    public static ForgeConfigSpec.BooleanValue spawnCactus;
    public static ForgeConfigSpec.EnumValue<SandBlockType> sandBlockType;
    //Snow Island
    public static ForgeConfigSpec.BooleanValue enableSnowIsland;
    public static ForgeConfigSpec.BooleanValue spawnPumpkins;
    public static ForgeConfigSpec.BooleanValue spawnIgloo;
    //Wood Island
    public static ForgeConfigSpec.BooleanValue enableWoodIsland;
    public static ForgeConfigSpec.BooleanValue spawnWater;
    public static ForgeConfigSpec.BooleanValue spawnString;
    public static ForgeConfigSpec.EnumValue<WoodBlockType> woodBlockType;
    //GoG Island
    public static ForgeConfigSpec.BooleanValue enableGoGIsland;
    //Advanced World Generation
    public static ForgeConfigSpec.BooleanValue oneChunkCommandAllowed;
    public static ForgeConfigSpec.IntValue x;
    public static ForgeConfigSpec.IntValue y;
    public static ForgeConfigSpec.IntValue z;
    public static ForgeConfigSpec.EnumValue<CommandBlockType> commandBlockType;
    public static ForgeConfigSpec.BooleanValue commandBlockAuto;
    public static ForgeConfigSpec.BooleanValue allowVisitCommand;
    public static ForgeConfigSpec.BooleanValue allowHomeCommand;
    public static ForgeConfigSpec.ConfigValue<String> commandBlockCommand;
    public static ForgeConfigSpec.ConfigValue<String[]> worldLoadCommands;

    static{
        CONFIG = BUILDER.build();
    }

    public enum WorldGenType {
        VOID, OVERWORLD, SUPERFLAT, WORLDTYPE, CUSTOMIZED
    }
    public enum BottomBlockType {
        BEDROCK, SECONDARYBLOCK
    }
    public enum GrassBlockType {
        GRASS, DIRT, COARSEDIRT
    }
    public enum SandBlockType {
        NORMAL, RED
    }
    public enum WoodBlockType {
        OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARKOAK
    }
    public enum CommandBlockType {
        NONE, IMPULSE, REPEATING, CHAIN
    }
    public static String[] emptyFilledArray(int length) {
        String[] array = new String[length];
        Arrays.fill(array, "");
        return array;
    }

    public static void loadConfig(ForgeConfigSpec config, String path){
        SkyBlock.LOGGER.info("Loading config: " + path);
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        SkyBlock.LOGGER.info("Build config: " + path);
        file.load();
        SkyBlock.LOGGER.info("Loaded config: " + path);
        config.setConfig(file);
    }

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
        server.comment("Config Settings for the world generation");
        hideToasts = server.comment("Hide the toasts when at spawn").define("hideToasts", false);
        server.comment("");
        netherVoid = server.comment("Nether dimension will be a void world")
                .define("netherVoid", true);
        netherVoidStructures = server.comment("End dimension will generate structures (Only takes effect if nether is a void world")
                .define("netherVoidStructures", true);
        endVoid = server.comment("Nether dimension will be a void world")
                .define("netherVoid", true);
        endVoidStructures = server.comment("End dimension will generate structures (Only takes effect if nether is a void world")
                .define("netherVoidStructures", true);
        worldGenType = server.comment("Overworld generation type").defineEnum("worldGenType", WorldGenType.VOID);
        worldGenSpecialParameters = server.comment("VOID-NOT USED, OVERWORLD-NOT USED, SUPERFLAT-Use the string as used for normal flat worlds, WORLDTYPE-world type to be used (set like server level-types), CUSTOMIZED-NOT USED")
                .define("worldGenSpecialParameters", "");
        worldBiomeID = server.comment("Biome used for entire world").defineInRange("worldBiomeID", -1, -10, 10);
        baseDimension = server.comment("Dimension for island management to occur in").define("baseDimension", DimensionType.OVERWORLD);
        cloudLevel = server.comment("Level where clouds appear").defineInRange("cloudLevel", 96, 1, 255);
        horizonLevel = server.comment("Level where the horizon appears").defineInRange("horizonLevel", 1, 1, 255);
        server.comment("");
        server.comment("Config Settings for the world generation");
        server.comment("");
        islandMainSpawnType = server.comment("This is for the island at 0,0! Valids are random, bedrock, sand, snow, wood, grass, gog, or others added by addons/custom islands")
                .define("islandMainSpawnType", "bedrock");
        islandSpawnType = server.comment("Valids are random, sand, snow, wood, grass, gog, or others added by addons/custom islands")
                .define("islandSpawnType", "grass");
        islandDistance = server.comment("Distance between islands").defineInRange("islandDistance", 1000, 100, 1000000);
        protectionBuildRange = server.comment("Range where players from other islands are not allowed and the range furthest players of an island can go. Affects spawn too (Max of islandDistance/2)")
                .defineInRange("protectionBuildRange", 500, 50, 500000);
        spawnProtection = server.comment("Protect spawn from building, destroying, interactions with blocks and machines, etc. Those in creative are ignored")
                .define("spawnProtection", true);
        islandProtection = server.comment("Disables effect of protectionBuildRange").define("islandProtection", true);
        islandSize = server.comment("Width of islands").defineInRange("islandSize", 3, 1, 15);
        spawnChest = server.comment("Spawn a chest on the island").define("spawnChest", false);
        oneChunk = server.comment("Start the world in one chunk mode").define("oneChunk", false);
        startingItems = server.comment("Starting items given to new players. Use the /startingInv command in game")
                .define("startingItems", emptyFilledArray(36));
        islandBiomeID = server.comment("Biome used for islands").defineInRange("islandBiomeID", -1, -1, 99999);
        islandBiomeRange = server.comment("Biome range (width) used for islands").defineInRange("islandBiomeRange", 0, 0, 99999);
        islandYLevel = server.comment("Y Level to spawn islands at (Set to 2 above where you want the ground block)").defineInRange("islandYLevel", 72, 1, 240);
        bottomBlockType = server.comment("Type of block to spawn under islands").defineEnum("bottomBlockType", BottomBlockType.BEDROCK);
        autoCreate = server.comment("Automatically give new players islands").define("autoCreate", false);
        autoCreateServersOnly = server.comment("ONLY TAKES EFFECT ON DEDICATED SERVERS-Automatically give new players islands").define("autoCreateServersOnly", false);
        allowIslandCreation = server.comment("Allow players to create or reset their islands").define("allowIslandCreation", true);
        resetInventory = server.comment("Reset players inventory with the starting inventory").define("resetInventory", true);
        customIslands = server.comment("Custom islands using the structure block data. Files are to be placed in the " + SkyBlock.MODID + "structures config folder. The names in this list should be the same as the structure names. "
                + "These names are the ids for the island type as well").define("customIslands", emptyFilledArray(0));
        forceSpawn = server.comment("Forces players to spawn at the spawn position. By default, the player will be teleported to a safe spot above it if spawning fails. This config disables that")
                .define("forceSpawn", false);
        buffTimer = server.comment("Sets how long the buffs are given when spawning on an island in ticks (I think)").defineInRange("buffTimer", 1200, 0, 9999999);
        server.comment("");
        server.comment("Settings for the grass island");
        server.comment("");
        enableGrassIsland = server.comment("Allow grass island to be used").define("enableGrassIsland", true);
        spawnTree = server.comment("Spawn a tree").define("spawnTree", true);
        grassBlockType = server.comment("Type of grass/dirt").defineEnum("grassBlockType", GrassBlockType.GRASS);
        server.comment("");
        server.comment("Settings for the sand island");
        server.comment("");
        enableSandIsland = server.comment("Allow sand island to be used").define("enableSandIsland", true);
        spawnCactus = server.comment("Spawn a cactus").define("spawnCactus", true);
        sandBlockType = server.comment("Type of sand").defineEnum("sandBlockType", SandBlockType.RED);
        server.comment("");
        server.comment("Settings for the snow island");
        server.comment("");
        enableSnowIsland = server.comment("Allow snow island to be used").define("enableSnowIsland", true);
        spawnPumpkins = server.comment("Spawn pumpkins").define("spawnPumpkins", true);
        spawnIgloo = server.comment("Spawn an igloo").define("spawnIgloo", false);
        server.comment("");
        server.comment("Settings for the wood island");
        server.comment("");
        enableWoodIsland = server.comment("Allow wood island to be used").define("enableWoodIsland", true);
        spawnWater = server.comment("Spawn water").define("spawnWater", true);
        spawnString = server.comment("Spawn string").define("spawnString", true);
        woodBlockType = server.comment("Type of wood").defineEnum("woodBlockType", WoodBlockType.DARKOAK);
        server.comment("");
        server.comment("Settings for the Garden of Glass island (Requires Botania and Garden of Glass!)");
        server.comment("");
        enableGoGIsland = server.comment("Allow garden of glass island to be used").define("enableGoGIsland", false);
        server.comment("");
        server.comment("Config Settings for more advanced world generation");
        server.comment("");
        oneChunkCommandAllowed = server.comment("Allow the one chunk command to be used").define("oneChunkCommandAllowed", false);
        server.comment("Offset position for command block from the center block above the bedrock");
        x = server.comment("The x coordinate (Offset from the center block above the bedrock)").defineInRange("x", 0, -15, 15);
        y = server.comment("The y coordinate (Offset from the center block above the bedrock)").defineInRange("y", 0, -15, 15);
        z = server.comment("The z coordinate (Offset from the center block above the bedrock)").defineInRange("z", 0, -15, 15);
        commandBlockType = server.comment("Type of command block to spawn").defineEnum("commandBlockType", CommandBlockType.NONE);
        commandBlockAuto = server.comment("Run always or require redstone").define("commandBlockAuto", false);
        allowVisitCommand = server.comment("Allows the visit command to be used").define("allowVisitCommand", true);
        allowHomeCommand = server.comment("Allows the home command to be used").define("allowHomeCommand", true);
        commandBlockCommand = server.comment("Command for the command block to run").define("spawnWater", "");
        worldLoadCommands = server.comment("Commands to run when the world loads").define("worldLoadCommands", emptyFilledArray(0));
    }
}
