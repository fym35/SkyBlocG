package com.rempler.skyblock.world;

import java.util.ArrayList;

public class IslandManager {

    public static ArrayList<IslandPos> CurrentIslandsList = new ArrayList<>();
    public static IslandPos getNextIsland() {
        int size = (int) Math.floor(Math.sqrt(CurrentIslandsList.size()));
        if (size % 2 == 0 && size > 0)
            size--;

        size = (size + 1) / 2;
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                return new IslandPos(x, z);
            }
        }
        return null;
    }
}
