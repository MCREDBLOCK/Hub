package com.redblock6.hub.mccore.extensions;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.redblock6.hub.mccore.functions.CreateGameMenu.translate;

public class McPlayer {

    public static void setInvisible(Player p, boolean flag, int duration) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, duration, 1, false, flag));
    }

    public static void sendTitle(Player p, String t, String s, int fade, int stay, int fade2) {
        PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, translateICBC(t), fade, stay, fade2));
        connection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, translateICBC(s), fade, stay, fade2));
        connection.sendPacket(new PacketPlayOutTitle(fade, stay, fade2));
    }

    public static IChatBaseComponent translateICBC(String s) {
        return IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + translate(s) + "\"}");
    }

    public static void spawnParticle(Player p, EnumParticle particle, Location loc) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, false, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 0, 0, 0, 0, 1);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }
}
