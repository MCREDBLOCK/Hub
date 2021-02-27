package com.redblock6.hub.mccore.functions;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redblock6.hub.Main;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;

import static com.redblock6.hub.Main.pool;

public class Holograms {

    private static final com.redblock6.hub.Main plugin = Main.getInstance();
    private static Hologram hologram;

    public static void createStatsHologram(Location loc, Player p) {
        Jedis j = pool.getResource();

        hologram = HologramsAPI.createHologram(plugin, loc);
        VisibilityManager visibilityManager = hologram.getVisibilityManager();

        visibilityManager.showTo(p);
        visibilityManager.setVisibleByDefault(false);

        hologram.appendTextLine(CreateGameMenu.translate("&4&lYOUR STATS OVERVIEW"));
        hologram.appendTextLine(CreateGameMenu.translate("&fKitPvP Kills: &c" + j.get(p.getUniqueId() + "KitKills")));
        hologram.appendTextLine(CreateGameMenu.translate("&fDR Winstreak: &c" + j.get(p.getUniqueId() + "DRWS")));
        hologram.appendTextLine(CreateGameMenu.translate("&fOITQ Winstreak: &c" + j.get(p.getUniqueId() + "OITQWS")));
        hologram.appendTextLine(CreateGameMenu.translate("&fPKR Winstreak: &c" + j.get(p.getUniqueId() + "PKRWS")));

        ItemStack item = new ItemStack(Material.RED_WOOL, 1);
        hologram.appendItemLine(item);
    }

    public static void removeHologramPacket(Player p, int entID) {
        hologram.delete();
    }
}
