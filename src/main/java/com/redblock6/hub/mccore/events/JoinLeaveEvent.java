package com.redblock6.hub.mccore.events;

import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.functions.NMS.NPC;
import com.redblock6.hub.mccore.functions.*;
import de.tr7zw.nbtapi.NBTItem;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import java.util.Random;

//import static com.redblock6.hub.Main.maincollection;
import static com.redblock6.hub.Main.pool;

public class JoinLeaveEvent implements Listener {
    private static final Main plugin = Main.getInstance();
    public Bar bar = new Bar(plugin);
    private static EntityPlayer npc;

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        Jedis j = null;
        try {
            j = pool.getResource();
            //update the player count
            j.set("HUB-" + plugin.getConfig().getInt("hub-identifier") + "Count", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
            plugin.getServer().getLogger().info("> Updated the redis player count!");
        } catch (Exception exception) {
            exception.printStackTrace();
            plugin.getServer().getLogger().info("> Failed to update the redis player count, you know what to do.");
        }
        //clear player inventory
        p.getInventory().clear();

        //set their food bar
        p.setFoodLevel(20);

        //set gamemode
        p.setGameMode(GameMode.ADVENTURE);

        // flight
        p.setAllowFlight(true);
        p.setFlying(false);
        p.setInvisible(false);

        //testing bro lololoo
        /*
        World spawningworld = p.getWorld();
        Sheep sheep = (Sheep) spawningworld.spawnEntity(p.getLocation(), EntityType.SHEEP);
        sheep.setCustomName(ChatColor.translateAlternateColorCodes('&', "&4&liuhawsoiludhdfiuehiruo"));
        sheep.setColor(DyeColor.RED);
        p.addPassenger(sheep);

        World spawningworld = p.getWorld();
        ArmorStand hologram = (ArmorStand) spawningworld.spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setInvulnerable(false);
        hologram.setBasePlate(false);
        hologram.setArms(false);
        hologram.setCustomNameVisible(true);
        hologram.setCustomName(ChatColor.translateAlternateColorCodes('&', "&4&lICE IS VERY VERY POG"));
        p.addPassenger(hologram); */

        //create the gamemenu itme
        String player = p.getDisplayName();
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lGAME MENU"));
        item.setItemMeta(meta);

        //get the players inv & give them the gamemenu
        NBTItem nbti = new NBTItem(item);
        nbti.setString("item", "gameMenu");
        nbti.applyNBT(item);
        p.getInventory().setItem(4, item);
        e.setJoinMessage(null);

        //get that amazing location :D
        Location statsloc = new Location(Bukkit.getWorld("Hub"), (1364 + 0.5), 73, (-47 + 0.5), (float) -179.4, (float) 0.7);
        //create that npc
        npc = NPC.addNpcPacket(NPC.createPlayerNpc(Bukkit.getWorld("Hub"), statsloc, p.getName(), p, ""), p, false);
        npc.setNoGravity(true);
        npc.setInvulnerable(true);
        npc.setCustomNameVisible(false);

        //create a scoreboard for the player
        CreateScoreboard.setScoreboard(p, "Normal", true);

        //create a bossbar for the player
        Bar.createBossBar();
        if (!bar.getBar().getPlayers().contains(e.getPlayer())) bar.addPlayer(p);

        //get a world and teleport the player to it
        Location loc = new Location(plugin.getServer().getWorld("Hub"), plugin.getServer().getWorld("Hub").getSpawnLocation().getX() + 0.5, plugin.getServer().getWorld("Hub").getSpawnLocation().getY() + 0.5, plugin.getServer().getWorld("Hub").getSpawnLocation().getZ() + 0.5, (float) -179, (float) -1);
        p.teleport(loc);

        //send the player a title
        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4&lHUB"), ChatColor.translateAlternateColorCodes('&', "&fmc.redblock6.com"), 5, 20, 5);

        //set message strings
        String line = ChatColor.translateAlternateColorCodes('&', "&4&m---------------------------------");
        String welcome = ChatColor.translateAlternateColorCodes('&', "&fWelcome to &4&lMCREDBLOCK");
        String blank = "";
        String name = ChatColor.translateAlternateColorCodes('&', "&4&lNAME &c" + player);
        String store = ChatColor.translateAlternateColorCodes('&', "&4&lSTORE &chttps://store.redblock6.com");
        String forums = ChatColor.translateAlternateColorCodes('&', "&4&lFORUMS &chttps://forums.redblock6.com");
        String discord = ChatColor.translateAlternateColorCodes('&', "&4&lDISCORD &chttps://discord.com/invite/wcdMgBBhWy");
        String ip = ChatColor.translateAlternateColorCodes('&', "&4&lmc.redblock6.com");

        if (plugin.getServer().getOnlinePlayers().size() == 1 || plugin.getServer().getOnlinePlayers().size() == 0) {
            new BukkitRunnable() {
                int timesran = 0;
                Location loc;

                @Override
                public void run() {
                    if (timesran == 0) {
                        loc = new Location(plugin.getServer().getWorld("Hub"), (1371 + 0.5), (79 + 0.3), (-88 + 0.5));
                        Holograms.createGameHologram(loc, "KITPVP");
                    } else if (timesran == 1) {
                        loc = new Location(plugin.getServer().getWorld("Hub"), (1374 + 0.5), (79 + 0.3), (-89 + 0.5));
                        Holograms.createGameHologram(loc, "DR");
                    } else if (timesran == 2) {
                        loc = new Location(plugin.getServer().getWorld("Hub"), (1377 + 0.5), (79 + 0.3), (-90 + 0.5));
                        Holograms.createGameHologram(loc, "OITQ");
                    } else if (timesran == 3) {
                        loc = new Location(plugin.getServer().getWorld("Hub"), (1381 + 0.5), (79 + 0.3), (-90 + 0.5));
                        Holograms.createGameHologram(loc, "PKR");
                    } else if (timesran == 4) {
                        cancel();
                    }

                    timesran++;
                }
            }.runTaskTimer(plugin, 2, 2);
        }


        //check if the player has joined before
        if (!p.hasPlayedBefore() || j.get(p.getUniqueId() + "Coins") == null) {
            /*DBObject stats = new BasicDBObject("_name", p.getUniqueId()).append("MagicDust", "0").append("OITQCoins", "0").append("KITPVPCoins", "0").append("DRCoins", "0").append("PKRCoins", "0");
            maincollection.findAndRemove(stats);
            maincollection.insert(stats);
             */

            j = pool.getResource();
            j.set(p.getUniqueId() + "Coins", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "Exp", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "Level", String.valueOf(Integer.parseInt("1")));
            j.set(p.getUniqueId() + "ExpMax", String.valueOf(Integer.parseInt("100")));
            j.set(p.getUniqueId() + "KitKills", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "OITQWS", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "DRWS", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "PKRWS", String.valueOf(Integer.parseInt("0")));

            j.close();

            CreateScoreboard.setScoreboard(p, "Normal", false);
            String achline = CreateGameMenu.translate("&2&m---------------------------------");
            String completed = CreateGameMenu.translate("&2&lACHEIVEMENT COMPLETED &a&lOUR ADVENTURE BEGINS");
            String coinplus = CreateGameMenu.translate("&5&l+ &d100 Magic Dust");
            String tutorial = CreateGameMenu.translate("&fUse the &aTutorial NPC&f, or click the");
            String tutorial2 = CreateGameMenu.translate("&f&aGame Menu &fto get started");

            //send the messages
            p.sendMessage(achline);
            p.sendMessage(completed);
            p.sendMessage(blank);
            p.sendMessage(coinplus);
            p.sendMessage(blank);
            p.sendMessage(tutorial);
            p.sendMessage(tutorial2);
            p.sendMessage(achline);

            //and those holograms
            Location hololoc = new Location(p.getWorld(), (1364 + 0.5), 76.7, (-47 + 0.5));
            Holograms.createStatsHologram(hololoc, p);

            //play teh acheivement sound thingy wingy
            new BukkitRunnable() {
                @Override
                public void run() {
                    Parkour.otherSound(p);
                    p.sendTitle(CreateGameMenu.translate("&2&l✔ ACHEIVEMENT COMPLETED ✔"), CreateGameMenu.translate("&aOur Adventure Begins"), 10, 20, 0);
                    GiveCoinsXP.GivePlayerCoins(p, 100);
                }
            }.runTaskLaterAsynchronously(plugin, 20);

            //send the welcome message later
            new BukkitRunnable() {
                @Override
                public void run() {
                    //send the messages
                    p.sendMessage(line);
                    p.sendMessage(welcome);
                    p.sendMessage(blank);
                    p.sendMessage(name);
                    p.sendMessage(store);
                    p.sendMessage(forums);
                    p.sendMessage(discord);
                    p.sendMessage(blank);
                    p.sendMessage(ip);
                    p.sendMessage(line);
                    //play a sound
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
                }
            }.runTaskLaterAsynchronously(plugin, 360);
        } else {
            //send the messages
            p.sendMessage(line);
            p.sendMessage(welcome);
            p.sendMessage(blank);
            p.sendMessage(name);
            p.sendMessage(store);
            p.sendMessage(forums);
            p.sendMessage(discord);
            p.sendMessage(blank);
            p.sendMessage(ip);
            p.sendMessage(line);

            //and those holograms
            Location hololoc = new Location(p.getWorld(), (1364 + 0.5), 76.7, (-47 + 0.5));
            Holograms.createStatsHologram(hololoc, p);

            //play a sound
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
        }


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Jedis j = null;

        e.setQuitMessage(null);
        Parkour park = Parkour.getParkourStatus(e.getPlayer());
        if (park.inParkour) {
            park.exitParkour();
        }

        NPC.removeNpcPacket(npc, e.getPlayer());
        Holograms.removeHologramPacket();

        try {
            j = pool.getResource();
            //update the player count
            j.set("HUB-" + plugin.getConfig().getInt("hub-identifier") + "Count", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
            plugin.getServer().getLogger().info("> Updated the redis player count!");

            j.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            plugin.getServer().getLogger().info("> Failed to update the redis player count, you know what to do.");
        }

        if (plugin.getServer().getOnlinePlayers().size() == 0 || plugin.getServer().getOnlinePlayers().size() == 1) {
            Holograms.removeGameHolograms();
        }
    }
}
