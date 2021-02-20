package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Parkour {
    public static double time = 0.0;
    public static boolean isparkour = false;
    private static final Main plugin = Main.getInstance();

    public static void enterParkour(Player p) {
        CreateScoreboard.setScoreboard(p, "Parkour", true);
        p.sendMessage(CreateGameMenu.translate("&6&l> &fYou joined the parkour"));
        time = 0.0;
        p.sendTitle(CreateGameMenu.translate("&6&lPARKOUR"), ChatColor.WHITE + "You joined the parkour", 10, 20, 10);
        otherSound(p);
        isparkour = true;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (inParkour(p)) {
                    addTime(p);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public static void exitParkour(Player p) {
        CreateScoreboard.setScoreboard(p, "Normal", true);
        p.sendMessage(CreateGameMenu.translate("&6&l> &fYou left the parkour"));
        isparkour = false;
    }

    public static void finishParkour(Player p) {
        p.sendMessage(CreateGameMenu.translate("&b&l> &fYou completed the parkour"));
        p.sendTitle(CreateGameMenu.translate("&b&lPARKOUR"), ChatColor.WHITE + "You finished the parkour", 10, 20, 10);
        otherSound(p);
        Location loc = new Location(plugin.getServer().getWorld("Hub"), plugin.getServer().getWorld("Hub").getSpawnLocation().getX(), plugin.getServer().getWorld("Hub").getSpawnLocation().getY(), plugin.getServer().getWorld("Hub").getSpawnLocation().getZ(), (float) -179.9, (float) -1.5);
        p.teleport(loc);
        isparkour = false;
    }

    public static double getTime(Player p) {
        return time;
    }

    public static double resetTime(Player p) {
        time = 0;
        return time;
    }

    public static boolean inParkour(Player p) {
        return isparkour;
    }

    public static double addTime(Player p) {
        time++;
        return time;
    }

    public static void otherSound(Player p) {
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);
            }
        }.runTaskLaterAsynchronously(plugin, 20);
    }
}
