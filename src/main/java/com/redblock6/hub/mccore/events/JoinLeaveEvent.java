package com.redblock6.hub.mccore.events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redblock6.hub.Main;
import com.redblock6.hub.Register;
import com.redblock6.hub.mccore.functions.*;
import com.redblock6.hub.mccore.functions.NMS.StatsNPC;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.effect.CircleEffect;
import de.tr7zw.nbtapi.NBTItem;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static com.redblock6.hub.Main.pool;

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

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void npcRightClick(NPCRightClickEvent e) {
        Jedis j = pool.getResource();
        if (e.getNPC().equals(playerTutorialNPC.get(e.getClicker()))) {
            if (j.get(e.getClicker().getUniqueId() + "Tutorial").equals("Incomplete")) {
                Tutorial.startTutorial(e.getClicker());
            } else {
                AchievementLibrary.openMenu(e.getClicker());
            }
        }
        j.close();
    }

    @EventHandler
    public void npcLeftClick(NPCLeftClickEvent e) {
        Jedis j = pool.getResource();
        if (e.getNPC().equals(playerTutorialNPC.get(e.getClicker()))) {
            if (j.get(e.getClicker().getUniqueId() + "Tutorial").equals("Incomplete")) {
                Tutorial.startTutorial(e.getClicker());
            } else {
                AchievementLibrary.openMenu(e.getClicker());
            }
        }
        j.close();
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
        npc = StatsNPC.addNpcPacket(StatsNPC.createPlayerNpc(Bukkit.getWorld("Hub"), statsloc, p.getName(), p, ""), p, false);
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

        //check if the player has joined before
        if (!p.hasPlayedBefore() || j.get(p.getUniqueId() + "Coins") == null) {
            j.set(p.getUniqueId() + "Coins", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "Exp", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "Level", String.valueOf(Integer.parseInt("1")));
            j.set(p.getUniqueId() + "ExpMax", String.valueOf(Integer.parseInt("100")));
            j.set(p.getUniqueId() + "KitKills", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "OITQWS", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "DRWS", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "PKRWS", String.valueOf(Integer.parseInt("0")));
            j.set(p.getUniqueId() + "Tutorial", "Incomplete");

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
            Hologram statsHolo = Holograms.createStatsHologram(hololoc, p);
            playerStatsHologram.put(p, statsHolo);

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
            Hologram holo = Holograms.createStatsHologram(hololoc, p);
            playerStatsHologram.put(p, holo);

            //play a sound
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
        }

        if (j.get(p.getUniqueId() + "Tutorial").equals("Incomplete") && !(playerHologram.containsValue(p)) && !(playerTutorialNPC.containsValue(p))) {
            Location tutloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 78.000, -59.500);

            Hologram holo = HologramsAPI.createHologram(plugin, tutloc);

            VisibilityManager visibilityManager = holo.getVisibilityManager();
            visibilityManager.setVisibleByDefault(false);
            visibilityManager.showTo(p);

            holo.appendTextLine(CreateGameMenu.translate("&4&lCLICK TO PLAY"));
            holo.appendTextLine(CreateGameMenu.translate("&fTutorial"));
            ItemStack holoitem = new ItemStack(Material.RED_WOOL);
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

            if (j.get(p.getUniqueId() + "Tutorial").equals("Incomplete")) {
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

            holo.appendTextLine(CreateGameMenu.translate("&4&lCLICK TO VIEW"));
            holo.appendTextLine(CreateGameMenu.translate("&fAchievements"));
            ItemStack holoitem = new ItemStack(Material.RED_WOOL);
            holo.appendItemLine(holoitem);
            JoinLeaveEvent.playerHologram.put(p, holo);
            JoinLeaveEvent.holograms.add(holo);
        }
        j.close();

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

        StatsNPC.removeNpcPacket(npc, e.getPlayer());
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
        playerEffect.get(e.getPlayer()).cancel();


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
                if (p.isOnline() && j.get(p.getUniqueId() + "Tutorial").equals("Incomplete")) {
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
                if (p.isOnline() && j.get(p.getUniqueId() + "Tutorial").equals("Incomplete")) {
                    PlayerConnection connection2 = ((CraftPlayer) p).getHandle().playerConnection;
                    connection2.sendPacket(new PacketPlayOutAnimation(((CraftEntity) tutNpc.getEntity()).getHandle(), 0));
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 25);
    }
}
