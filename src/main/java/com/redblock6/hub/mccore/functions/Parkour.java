package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class Parkour {
    private static final Main plugin = Main.getInstance();
    public static HashMap<Player, Parkour> statuses = new HashMap<>();
    public static ArrayList<Player> playersInParkour = new ArrayList<>();

    public Player p;
    public boolean inParkour = false;
    public int time = 0;


    private Parkour(Player p) {
        this.p = p;
        statuses.put(p, this);
    }

    public static Parkour getParkourStatus(Player p) {
        if (statuses.containsKey(p)) {
            return statuses.get(p);
        }
        return new Parkour(p);
    }


    public void enterParkour() {
        if (!(p.getGameMode().equals(GameMode.CREATIVE)) && !(p.getGameMode().equals(GameMode.SPECTATOR))) {
            CreateScoreboard.setScoreboard(p, "Parkour", true);
            p.sendMessage(CreateGameMenu.translate("&6&l> &fYou joined the parkour"));
            time = 0;
            inParkour = true;
            p.sendTitle(CreateGameMenu.translate("&6&lPARKOUR"), ChatColor.WHITE + "You joined the parkour", 10, 20, 10);
            Fireworks.spawnFirework1(p.getLocation());
            otherSound(p);
            p.setFlying(false);
            p.setAllowFlight(false);
            p.getInventory().clear();

            ItemStack carpet = new ItemStack(Material.RED_CARPET, 1);

            ItemMeta meta = carpet.getItemMeta();
            meta.setDisplayName(CreateGameMenu.translate("&4&lBACK TO START"));
            carpet.setItemMeta(meta);
            NBTItem nbti = new NBTItem(carpet);
            nbti.setString("item", "start");
            nbti.applyNBT(carpet);

            ItemStack bed = new ItemStack(Material.RED_BED, 1);

            ItemMeta meta2 = bed.getItemMeta();
            meta2.setDisplayName(CreateGameMenu.translate("&4&lEXIT"));
            bed.setItemMeta(meta2);
            nbti = new NBTItem(bed);
            nbti.setString("item", "exit");
            nbti.applyNBT(bed);

            p.getInventory().setItem(8, bed);
            p.getInventory().setItem(0, carpet);

            //make sure they can be collided with
            p.setCollidable(true);

            playersInParkour.add(p);
            inParkour = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (inParkour()) {
                        if (p.isOnline()) {
                            time++;

                            CreateScoreboard.setScoreboard(p, "Parkour", false);
                        }
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 0, 20);
        }
    }

    public static ArrayList<Player> getOtherPlayers() {
        return playersInParkour;
    }

    public void exitParkour() {
        CreateScoreboard.setScoreboard(p, "Normal", true);
        p.sendMessage(CreateGameMenu.translate("&6&l> &fYou left the parkour"));
        inParkour = false;
        playersInParkour.remove(p);

        p.getInventory().clear();

        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lGAME MENU"));
        item.setItemMeta(meta);

        //get the players inv & give them the gamemenu
        NBTItem nbti = new NBTItem(item);
        nbti.setString("item", "gameMenu");
        nbti.applyNBT(item);
        p.getInventory().setItem(4, item);
    }

    public void finishParkour() {
        p.sendTitle(CreateGameMenu.translate("&b&lPARKOUR"), ChatColor.WHITE + "You finished the parkour", 10, 20, 10);
        otherSound(p);
        p.sendMessage(CreateGameMenu.translate("&b&l> &fYou finished the parkour in &b" +  getTime() + " seconds"));
        Fireworks.spawnFirework2(p.getLocation());
        Location loc = new Location(plugin.getServer().getWorld("Hub"), plugin.getServer().getWorld("Hub").getSpawnLocation().getX(), plugin.getServer().getWorld("Hub").getSpawnLocation().getY(), plugin.getServer().getWorld("Hub").getSpawnLocation().getZ(), (float) -179.9, (float) -1.5);
        p.teleport(loc);
        playersInParkour.remove(p);
        CreateScoreboard.setScoreboard(p, "Normal", true);
        inParkour = false;
    }

    //idk ig

    public int getTime() {
        return time;
    }

    public int resetTime() {
        time = 0;
        return time;
    }

    public boolean inParkour() {
        return inParkour;
    }

    public int addTime() {
        return time++;
    }

    public static void otherSound(Player p) {
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 2);
            }
        }.runTaskLaterAsynchronously(plugin, 2);
    }
}
