package com.redblock6.hub.mccore.events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redblock6.hub.Main;
import com.redblock6.hub.Register;
import com.redblock6.hub.mccore.achievements.AchDatabase;
import com.redblock6.hub.mccore.achievements.AchLibrary;
import com.redblock6.hub.mccore.achievements.HAchType;
import com.redblock6.hub.mccore.extensions.McPlayer;
import com.redblock6.hub.mccore.functions.*;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.CircleEffect;
import de.tr7zw.nbtapi.NBTItem;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;

import static com.redblock6.hub.Main.pool;
import static com.redblock6.hub.mccore.functions.CreateGameMenu.translate;

public class JoinLeaveEvent implements Listener {
    private static final Main plugin = Main.getInstance();
    public Bar bar = new Bar(plugin);
    private static EntityPlayer npc;
    private static EntityPlayer tutNpc;
    public static HashMap<Player, EntityPlayer> playerNPC = new HashMap<>();
    public static HashMap<Player, NPC> playerTutorialNPC = new HashMap<>();
    public static HashMap<Player, Hologram> playerHologram = new HashMap<>();
    public HashMap<Player, Hologram> playerStatsHologram = new HashMap<>();
    public static ArrayList<NPC> npcArrayList = new ArrayList<>();
    public static ArrayList<Hologram> holograms = new ArrayList<>();
    public static HashMap<Player, Effect> playerEffect = new HashMap<>();
    private static final MySQLSetterGetter mysql = new MySQLSetterGetter();

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void itemDropped(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void npcRightClick(NPCRightClickEvent e) {
        if (e.getNPC().equals(playerTutorialNPC.get(e.getClicker()))) {
            AchDatabase database = new AchDatabase(e.getClicker());
            if (mysql.getTutorial(e.getClicker().getUniqueId()).equals("Incomplete") || !database.getHubAch().contains(HAchType.Tour_Guide)) {
                TourGuide.startTutorial(e.getClicker());
            } else {
                AchLibrary.hub(e.getClicker());
            }
        }
    }

    @EventHandler
    public void npcLeftClick(NPCLeftClickEvent e) {
        AchDatabase database = new AchDatabase(e.getClicker());
        if (e.getNPC().equals(playerTutorialNPC.get(e.getClicker())) || !database.getHubAch().contains(HAchType.Tour_Guide)) {
            if (mysql.getTutorial(e.getClicker().getUniqueId()).equals("Incomplete")) {
                TourGuide.startTutorial(e.getClicker());
            } else {
                AchLibrary.hub(e.getClicker());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        Jedis j = pool.getResource();
        try {
            //update the player count
            j.set("HUB-" + plugin.getConfig().getInt("hub-identifier") + "Count", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
            plugin.getServer().getLogger().info("> Updated the redis player count!");
        } catch (Exception exception) {
            exception.printStackTrace();
            plugin.getServer().getLogger().info("> Failed to update the redis player count, you know what to do.");
        } finally {
            j.close();
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
        // p.setInvisible(false);

        mysql.createPlayer(p.getUniqueId(), p);

        //create the gamemenu itme
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lGAME MENU"));
        item.setItemMeta(meta);

        //create the hub selector itme
        ItemStack item2 = new ItemStack(Material.WATCH);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lHUB SELECTOR"));
        item2.setItemMeta(meta2);

        //get the players inv & give them the gamemenu
        NBTItem nbti = new NBTItem(item);
        nbti.setString("item", "gameMenu");
        nbti.applyNBT(item);
        p.getInventory().setItem(4, item);
        e.setJoinMessage(null);

        //get the players inv & give them the gamemenu
        NBTItem nbti2 = new NBTItem(item2);
        nbti2.setString("item", "hubSelector");
        nbti2.applyNBT(item2);
        p.getInventory().setItem(8, item2);

        //get that amazing location :D
        Location statsloc = new Location(Bukkit.getWorld("Hub"), (1364 + 0.5), 73, (-47 + 0.5), (float) -179.4, (float) 0.7);
        //create that npc
        /*
        npc = StatsNPC.addNpcPacket(StatsNPC.createPlayerNpc(Bukkit.getWorld("Hub"), statsloc, p.getName(), p, ""), p, false);
        npc.setNoGravity(true);
        npc.setInvulnerable(true);
        npc.setCustomNameVisible(false);

         */

        //create a scoreboard for the player
        CreateScoreboard.setScoreboard(p, "Normal", true);

        //create a bossbar for the player
        /*
        Bar.createBossBar();
        if (!bar.getBar().getPlayers().contains(e.getPlayer())) bar.addPlayer(p);
         */

        //get a world and teleport the player to it
        Location loc = new Location(plugin.getServer().getWorld("Hub"), plugin.getServer().getWorld("Hub").getSpawnLocation().getX() + 0.5, plugin.getServer().getWorld("Hub").getSpawnLocation().getY() + 0.5, plugin.getServer().getWorld("Hub").getSpawnLocation().getZ() + 0.5, (float) -179, (float) -1);
        p.teleport(loc);

        //send the player a title
        McPlayer.sendTitle(p, translate( "&4&lHUB"), ChatColor.translateAlternateColorCodes('&', "&fmc.redblock6.com"), 5, 20, 5);

        //set message strings
        String line = ChatColor.translateAlternateColorCodes('&', "&4&m---------------------------------");
        String welcome = ChatColor.translateAlternateColorCodes('&', "&fWelcome to &4&lMCREDBLOCK");
        String blank = "";
        String name = ChatColor.translateAlternateColorCodes('&', "&4&lNAME &c" + p.getName());
        String store = ChatColor.translateAlternateColorCodes('&', "&4&lSTORE &chttps://store.redblock6.com");
        String forums = ChatColor.translateAlternateColorCodes('&', "&4&lFORUMS &chttps://forums.redblock6.com");
        String discord = ChatColor.translateAlternateColorCodes('&', "&4&lDISCORD &chttps://discord.com/invite/wcdMgBBhWy");
        String ip = ChatColor.translateAlternateColorCodes('&', "&4&lmc.redblock6.com");

        //check if the player has joined before
        AchDatabase database = new AchDatabase(p);
        if (!p.hasPlayedBefore() || !database.getHubAch().contains(HAchType.Our_Adventure_Begins)) {
            /*
            j.set(p.getUniqueId() + "Coins", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "Exp", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "Level", String.valueOf(Integer.parseInt("1")));
            j.set(p.getUniqueId() + "ExpMax", String.valueOf(Integer.parseInt("100")));
            j.set(p.getUniqueId() + "KitKills", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "OITQWS", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "DRWS", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "PKRWS", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "Tutorial", "Incomplete");
             */

            MySQLSetterGetter player = new MySQLSetterGetter();
            player.createPlayer(p.getUniqueId(), p);

            CreateScoreboard.setScoreboard(p, "Normal", false);

            //and those holograms
            Location hololoc = new Location(p.getWorld(), (1364 + 0.5), 76.7, (-47 + 0.5));
            Hologram statsHolo = Holograms.createStatsHologram(hololoc, p);
            playerStatsHologram.put(p, statsHolo);

            //play teh acheivement sound thingy wingy
            new BukkitRunnable() {
                @Override
                public void run() {
                    AchLibrary.grantHubAchievement(p, HAchType.Our_Adventure_Begins);
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
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 0);
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
            Location hololoc = new Location(p.getWorld(), 1396 + 0.5, 76, -64 + 0.5);
            Hologram holo = Holograms.createStatsHologram(hololoc, p);
            playerStatsHologram.put(p, holo);

            //play a sound
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 0);
        }

        if (mysql.getTutorial(p.getUniqueId()).equals("Incomplete") || !database.getHubAch().contains(HAchType.Tour_Guide) && !(playerHologram.containsValue(p)) && !(playerTutorialNPC.containsValue(p))) {
            Location tutloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 78.000, -59.500);

            Hologram holo = HologramsAPI.createHologram(plugin, tutloc);

            VisibilityManager visibilityManager = holo.getVisibilityManager();
            visibilityManager.setVisibleByDefault(false);
            visibilityManager.showTo(p);

            holo.appendTextLine(translate("&4&lCLICK TO PLAY"));
            holo.appendTextLine(translate("&fTour Guide"));
            ItemStack holoitem = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
            holo.appendItemLine(holoitem);
            playerHologram.put(p, holo);
            holograms.add(holo);

            //create that npc
            Location tutnpcloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 75.000, -59.500, 46, (float) 0.3);
            Location tutparticleloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 76.000, -59.500, 46, (float) 0.3);

            NPC tutNpc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "3NR");
            tutNpc.spawn(tutnpcloc);
            tutNpc.getEntity().setCustomNameVisible(false);
            npcArrayList.add(tutNpc);
            playerTutorialNPC.put(p, tutNpc);

            tutNpc.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false);

            Effect effect = new CircleEffect(Register.getEffectManager());
            effect.setEntity(p);
            effect.setLocation(tutparticleloc);
            effect.iterations = 2400 * 20;
            effect.particleSize = 10;
            effect.start();
            playerEffect.put(p, effect);

            playerTutorialNPC.put(p, tutNpc);

            if (mysql.getTutorial(p.getUniqueId()).equals("Incomplete")) {
                startTutorialAnimation(p, j);
            }
        } else {
            Location tutnpcloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 75.000, -59.500, 46, (float) 0.3);
            Location tutloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 78.400, -59.500);
            NPC quests = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "");
            quests.spawn(tutnpcloc);
            quests.getEntity().setCustomNameVisible(false);
            npcArrayList.add(quests);
            playerTutorialNPC.put(p,quests);

            quests.data().setPersistent(NPC.NAMEPLATE_VISIBLE_METADATA, false);

            Hologram holo = HologramsAPI.createHologram(plugin, tutloc);

            VisibilityManager visibilityManager = holo.getVisibilityManager();
            visibilityManager.setVisibleByDefault(false);
            visibilityManager.showTo(p);

            holo.appendTextLine(translate("&4&lCLICK TO VIEW"));
            holo.appendTextLine(translate("&fAchievements"));
            ItemStack holoitem = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
            holo.appendItemLine(holoitem);
            JoinLeaveEvent.playerHologram.put(p, holo);
            JoinLeaveEvent.holograms.add(holo);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                removeUnneededNpcs(p);
                for (Player loopplayer : Bukkit.getOnlinePlayers()) {
                    if (loopplayer != p) {
                        removeUnneededNpcs(loopplayer);
                    }
                }
            }
        }.runTaskLater(plugin, 2);

    }

    public static void removeUnneededNpcs(Player p) {
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy();

        for (NPC npcToRemove : npcArrayList) {
            if (npcToRemove != playerTutorialNPC.get(p)) {
                PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
                // connection.sendPacket(new PacketPlayOutEntityDestroy(npcToRemove.getEntity().getUniqueId()));
                if (npcToRemove.getEntity().getType().equals(EntityType.PLAYER)) {
                    destroy = new PacketPlayOutEntityDestroy(((CraftPlayer) npcToRemove.getEntity()).getHandle().getId());
                } else if (npcToRemove.getEntity().getType().equals(EntityType.VILLAGER)) {
                    destroy = new PacketPlayOutEntityDestroy(((CraftVillager) npcToRemove.getEntity()).getHandle().getId());
                }
                connection.sendPacket(destroy);
                Bukkit.getLogger().info("341 Removed NPC" + npcToRemove.getId() + " for " + p.getName());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Parkour park = Parkour.getParkourStatus(e.getPlayer());
        if (park.inParkour) {
            park.exitParkour();
        }

        // StatsNPC.removeNpcPacket(npc, e.getPlayer());
        if (playerTutorialNPC.get(e.getPlayer()) != null) {
            playerTutorialNPC.get(e.getPlayer()).destroy();
        }
        if (playerTutorialNPC.get(e.getPlayer()) != null) {
            CitizensAPI.getNPCRegistry().deregister(playerTutorialNPC.get(e.getPlayer()));
        }
        playerHologram.get(e.getPlayer()).delete();
        playerHologram.remove(e.getPlayer());
        npcArrayList.remove(playerTutorialNPC.get(e.getPlayer()));
        playerTutorialNPC.remove(e.getPlayer());
        Holograms.removeHologramPacket(playerStatsHologram.get(e.getPlayer()));
        if (playerEffect.get(e.getPlayer()) != null) {
            playerEffect.get(e.getPlayer()).cancel();
        }


        try {
            Jedis j = pool.getResource();
            //update the player count
            j.set("HUB-" + plugin.getConfig().getInt("hub-identifier") + "Count", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));
            plugin.getServer().getLogger().info("> Updated the redis player count!");

            j.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            plugin.getServer().getLogger().info("> Failed to update the redis player count, you know what to do.");
        }
    }

    public void startTutorialAnimation(Player p, Jedis j) {
        NPC tutNpc = playerTutorialNPC.get(p);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isOnline() && mysql.getTutorial(p.getUniqueId()).equals("Incomplete")) {
                    PlayerConnection connection2 = ((CraftPlayer) p).getHandle().playerConnection;
                    connection2.sendPacket(new PacketPlayOutAnimation(((CraftEntity) tutNpc.getEntity()).getHandle(), 0));
                } else {
                    cancel();
                    playerEffect.get(p).cancel();
                    if (playerHologram.get(p) != null) {
                        playerHologram.get(p).delete();
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isOnline() && mysql.getTutorial(p.getUniqueId()).equals("Incomplete")) {
                    PlayerConnection connection2 = ((CraftPlayer) p).getHandle().playerConnection;
                    connection2.sendPacket(new PacketPlayOutAnimation(((CraftEntity) tutNpc.getEntity()).getHandle(), 0));
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 25);
    }
}
