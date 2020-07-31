package com.rempler.skyblock.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IslandPos {
    private int posX;
    private int posZ;
    private String type;

    private ArrayList<String> playerUUIDs;

    public IslandPos(int x, int z, UUID... ids) {
        posX = x;
        posZ = z;
        playerUUIDs = new ArrayList<>();
        for (UUID id : ids) {
            playerUUIDs.add(id.toString());
        }

    }

    public IslandPos(String type, int x, int z, UUID... ids) {
        this.type = type;
        posX = x;
        posZ = z;
        playerUUIDs = new ArrayList<>();
        for (UUID id : ids) {
            playerUUIDs.add(id.toString());
        }

    }

    public void addNewPlayer(UUID playerUUID) {
        if (!playerUUIDs.contains(playerUUID.toString()))
            playerUUIDs.add(playerUUID.toString());
    }

    public void removePlayer(UUID playerUUID) {
        playerUUIDs.remove(playerUUID.toString());
    }

    public int getX() {
        return posX;
    }

    public int getZ() {
        return posZ;
    }

    public String getType() {
        return type;
    }

    public List<String> getPlayerUUIDs() {
        return playerUUIDs;
    }

    public void write(CompoundNBT nbt) {
        nbt.putInt("posX", posX);
        nbt.putInt("posZ", posZ);
        if (!StringUtils.isEmpty(type))
            nbt.putString("type", type);

        ListNBT list = new ListNBT();
        for (String playerUUID : playerUUIDs) {
            CompoundNBT stackTag = new CompoundNBT();

            stackTag.putString("playerUUID", playerUUID);

            list.add(stackTag);
        }
        nbt.put("UUIDs", list);
    }

    public void read(CompoundNBT nbt) {
        posX = nbt.getInt("posX");
        posZ = nbt.getInt("posZ");
        type = nbt.getString("type");

        playerUUIDs = new ArrayList<>();

        ListNBT list = nbt.getList("UUIDs", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.size(); ++i) {
            CompoundNBT stackTag = list.getCompound(i);

            String name = stackTag.getString("playerUUID");
            playerUUIDs.add(name);
        }
    }

}