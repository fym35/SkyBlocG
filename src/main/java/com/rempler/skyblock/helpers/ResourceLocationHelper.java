package com.rempler.skyblock.helpers;

import com.rempler.skyblock.SkyBlock;
import net.minecraft.util.ResourceLocation;

public class ResourceLocationHelper {
    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(SkyBlock.MODID, path);
    }
}
