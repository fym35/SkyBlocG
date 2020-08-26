package com.rempler.skyblock.api;

import com.rempler.skyblock.config.ConfigOptions;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IslandPos {
    private final int posX;
    private final int posZ;


    public IslandPos(int x, int z) {
        posX = x;
        posZ = z;
    }

    public BlockPos getCenter() {
        return new BlockPos(posX << 8, ConfigOptions.Common.islandYLevel.get(), posZ << 8);
    }

    public static IslandPos fromTag(CompoundNBT tag) {
        return new IslandPos(tag.getInt("IslandX"), tag.getInt("IslandZ"));
    }

    public CompoundNBT toTag() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("IslandX", posX);
        tag.putInt("IslandZ", posZ);
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof IslandPos)) {
            return false;
        }
        IslandPos islandPos = (IslandPos) o;
        return this.posX == islandPos.posX && this.posZ == islandPos.posZ;
    }

    @Override
    public int hashCode() {
        int result = posX;
        result = 31 * result + posZ;
        return result;
    }

}