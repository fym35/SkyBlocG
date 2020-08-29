package com.rempler.skyblock.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketWorld {
    public static void encode(PacketWorld pkt, PacketBuffer buf) {}

    public static PacketWorld decode(PacketBuffer buf) {
        return new PacketWorld();
    }

    public static void handle(PacketWorld pkt, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                WorldInfo info = Minecraft.getInstance().world.getWorldInfo();
                if (info instanceof SkyBlockWorldInfo) {
                    ((SkyBlockWorldInfo) info).markSkyblock();
                }
            });
        }
    }
}
