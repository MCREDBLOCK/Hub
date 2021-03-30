package com.redblock6.hub.mccore.functions;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redblock6.hub.Main;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import redis.clients.jedis.Jedis;

import static com.redblock6.hub.Main.pool;

public class Holograms {

    private static final com.redblock6.hub.Main plugin = Main.getInstance();
    private static Hologram hologram;
    private static Hologram gamehologram1;
    private static Hologram gamehologram2;
    private static Hologram gamehologram3;
    private static Hologram gamehologram4;
    private static final MySQLSetterGetter mysql = new MySQLSetterGetter();

    public static Hologram createStatsHologram(Location loc, Player p) {
        Jedis j = pool.getResource();

        hologram = HologramsAPI.createHologram(plugin, loc);
        VisibilityManager visibilityManager = hologram.getVisibilityManager();

        visibilityManager.showTo(p);
        visibilityManager.setVisibleByDefault(false);

        hologram.appendTextLine(CreateGameMenu.translate("&4&lYOUR STATS OVERVIEW"));
        hologram.appendTextLine(CreateGameMenu.translate("&fKitPvP Kills: &c" + mysql.getKitKills(p.getUniqueId())));
        hologram.appendTextLine(CreateGameMenu.translate("&fDR Winstreak: &c" + j.get(p.getUniqueId() + "DRWS")));
        hologram.appendTextLine(CreateGameMenu.translate("&fOITQ Winstreak: &c" + j.get(p.getUniqueId() + "OITQWS")));
        hologram.appendTextLine(CreateGameMenu.translate("&fPKR Winstreak: &c" + j.get(p.getUniqueId() + "PKRWS")));

        ItemStack item = new ItemStack(Material.RED_WOOL, 1);
        hologram.appendItemLine(item);
        j.close();

        return hologram;
    }

    public static void createGameHologram(Location loc, String type) {
        if (type.equalsIgnoreCase("KitPvP")) {
            if (getMajorityOnline("KITPVP")) {
                gamehologram1 = HologramsAPI.createHologram(plugin, loc);
                VisibilityManager visibilityManager = gamehologram1.getVisibilityManager();

                visibilityManager.setVisibleByDefault(true);

                gamehologram1.appendTextLine(CreateGameMenu.translate("&2&lCLICK TO PLAY"));
                gamehologram1.appendTextLine(CreateGameMenu.translate("&fKitPvP"));
                gamehologram1.appendTextLine(CreateGameMenu.translate("&a" + ServerConnector.getPlayerCount("KITPVP") + " &2&lPLAYERS"));

                ItemStack item = new ItemStack(Material.DIAMOND_SWORD, 1);
                gamehologram1.appendItemLine(item);
            } else if (!getMajorityOnline("KITPVP")) {
                gamehologram1 = HologramsAPI.createHologram(plugin, loc);
                VisibilityManager visibilityManager = gamehologram1.getVisibilityManager();

                visibilityManager.setVisibleByDefault(true);

                gamehologram1.appendTextLine(CreateGameMenu.translate("&4&lCLICK TO PLAY"));
                gamehologram1.appendTextLine(CreateGameMenu.translate("&fKitPvP"));
                gamehologram1.appendTextLine(CreateGameMenu.translate("&4&lMAJORITY OFFLINE"));

                ItemStack item = new ItemStack(Material.RED_WOOL, 1);
                gamehologram1.appendItemLine(item);
            }
        } else if (type.equalsIgnoreCase("DR")) {
            if (getMajorityOnline("DR")) {
                gamehologram2 = HologramsAPI.createHologram(plugin, loc);
                VisibilityManager visibilityManager = gamehologram2.getVisibilityManager();

                visibilityManager.setVisibleByDefault(true);

                gamehologram2.appendTextLine(CreateGameMenu.translate("&2&lCLICK TO PLAY"));
                gamehologram2.appendTextLine(CreateGameMenu.translate("&fDeath Run"));
                gamehologram2.appendTextLine(CreateGameMenu.translate("&a" + ServerConnector.getPlayerCount("DR") + " &2&lPLAYERS"));

                ItemStack item = new ItemStack(Material.SKELETON_SKULL, 1);
                gamehologram2.appendItemLine(item);
            } else if (!getMajorityOnline("DR")) {
                gamehologram2 = HologramsAPI.createHologram(plugin, loc);
                VisibilityManager visibilityManager = gamehologram2.getVisibilityManager();

                visibilityManager.setVisibleByDefault(true);

                gamehologram2.appendTextLine(CreateGameMenu.translate("&4&lCLICK TO PLAY"));
                gamehologram2.appendTextLine(CreateGameMenu.translate("&fDeath Run"));
                gamehologram2.appendTextLine(CreateGameMenu.translate("&4&lMAJORITY OFFLINE"));

                ItemStack item = new ItemStack(Material.RED_WOOL, 1);
                gamehologram2.appendItemLine(item);
            }
        } else if (type.equalsIgnoreCase("OITQ")) {
            if (getMajorityOnline("OITQ")) {
                gamehologram3 = HologramsAPI.createHologram(plugin, loc);
                VisibilityManager visibilityManager = gamehologram3.getVisibilityManager();

                visibilityManager.setVisibleByDefault(true);

                gamehologram3.appendTextLine(CreateGameMenu.translate("&2&lCLICK TO PLAY"));
                gamehologram3.appendTextLine(CreateGameMenu.translate("&fOITQ"));
                gamehologram3.appendTextLine(CreateGameMenu.translate("&a" + ServerConnector.getPlayerCount("OITQ") + " &2&lPLAYERS"));

                ItemStack item = new ItemStack(Material.BOW, 1);
                gamehologram3.appendItemLine(item);
            } else if (!getMajorityOnline("OITQ")) {
                gamehologram3 = HologramsAPI.createHologram(plugin, loc);
                VisibilityManager visibilityManager = gamehologram3.getVisibilityManager();

                visibilityManager.setVisibleByDefault(true);

                gamehologram3.appendTextLine(CreateGameMenu.translate("&4&lCLICK TO PLAY"));
                gamehologram3.appendTextLine(CreateGameMenu.translate("&fOITQ"));
                gamehologram3.appendTextLine(CreateGameMenu.translate("&4&lMAJORITY OFFLINE"));

                ItemStack item = new ItemStack(Material.RED_WOOL, 1);
                gamehologram3.appendItemLine(item);
            }
        } else if (type.equalsIgnoreCase("PKR")) {
            if (getMajorityOnline("PKR")) {
                gamehologram4 = HologramsAPI.createHologram(plugin, loc);
                VisibilityManager visibilityManager = gamehologram4.getVisibilityManager();

                visibilityManager.setVisibleByDefault(true);

                gamehologram4.appendTextLine(CreateGameMenu.translate("&2&lCLICK TO PLAY"));
                gamehologram4.appendTextLine(CreateGameMenu.translate("&fParkour Run"));
                gamehologram4.appendTextLine(CreateGameMenu.translate("&a" + ServerConnector.getPlayerCount("PKR") + " &2&lPLAYERS"));

                ItemStack item = new ItemStack(Material.LEATHER_BOOTS, 1);
                gamehologram4.appendItemLine(item);
            } else if (!getMajorityOnline("PKR")) {
                gamehologram4 = HologramsAPI.createHologram(plugin, loc);
                VisibilityManager visibilityManager = gamehologram4.getVisibilityManager();

                visibilityManager.setVisibleByDefault(true);

                gamehologram4.appendTextLine(CreateGameMenu.translate("&4&lCLICK TO PLAY"));
                gamehologram4.appendTextLine(CreateGameMenu.translate("&fParkour Run"));
                gamehologram4.appendTextLine(CreateGameMenu.translate("&4&lMAJORITY OFFLINE"));

                ItemStack item = new ItemStack(Material.RED_WOOL, 1);
                gamehologram4.appendItemLine(item);
            }
        }
    }

    public static boolean getMajorityOnline(String type) {
        Jedis j = pool.getResource();
        int online = 0;

        if (j.get(type.toUpperCase() + "-1Status") != null && j.get(type.toUpperCase() + "-1Status").equals("ONLINE")) {
            online++;
        } if (j.get(type.toUpperCase() + "-2Status") != null && j.get(type.toUpperCase() + "-2Status").equals("ONLINE")) {
            online++;
        } if (j.get(type.toUpperCase() + "-3Status") != null && j.get(type.toUpperCase() + "-3Status").equals("ONLINE")) {
            online++;
        } if (j.get(type.toUpperCase() + "-4Status") != null && j.get(type.toUpperCase() + "-4Status").equals("ONLINE")) {
            online++;
        }

        j.close();
        return online > 2;
    }

    public static void removeGameHolograms() {
        if (gamehologram1 != null) {
            gamehologram1.delete();
        } if (gamehologram2 != null) {
            gamehologram2.delete();
        } if (gamehologram3 != null) {
            gamehologram3.delete();
        } if (gamehologram4 != null) {
            gamehologram4.delete();
        }
    }

    public static void removeHologramPacket(Hologram holo) {
        holo.delete();
    }
}
