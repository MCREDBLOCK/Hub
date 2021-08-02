package com.redblock6.hub.mccore.functions;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.achievements.AchDatabase;
import com.redblock6.hub.mccore.achievements.HAchType;
import com.redblock6.hub.mccore.events.JoinLeaveEvent;
import com.redblock6.hub.mccore.events.MoveEvent;
import com.redblock6.hub.mccore.extensions.McPlayer;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;

import static com.redblock6.hub.Main.pool;
import static com.redblock6.hub.mccore.functions.CreateGameMenu.translate;

public class TourGuide implements Listener {

    private static final Main plugin = Main.getInstance();
    public static ArrayList<Player> playersInTutorial = new ArrayList<>();
    private static final MySQLSetterGetter mysql = new MySQLSetterGetter();

    public static void startTutorial(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
        p.setGameMode(GameMode.SPECTATOR);
        Location loc = new Location(p.getWorld(), (1379 + 0.5), 106, (-85 + 0.5), -178, -1);
        p.teleport(loc);
        playersInTutorial.add(p);

        new BukkitRunnable() {
            int timesran = 0;

            @Override
            public void run() {
                if (!(p.isOnline())) {
                    cancel();
                }
                if (timesran == 0) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HAT, 100, 1);
                    McPlayer.sendTitle(p, translate("&4&lWELCOME!"), translate("&fWe'll try to keep this tour quick so you can start playing."), 10, 80, 10);

                    p.sendMessage(translate("&4&m---------------------------------"));
                    p.sendMessage(translate("&4&lWELCOME TO MCREDBLOCK!"));
                    p.sendMessage("");
                    p.sendMessage(translate("&fWe'll try and keep this tour short"));
                    p.sendMessage(translate("&fso that you can collect your reward"));
                    p.sendMessage(translate("&fand get straight to playing."));
                    p.sendMessage(translate("&4&m---------------------------------"));

                } else if (timesran == 1) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HAT, 100, 1);
                    McPlayer.sendTitle(p, "&4&lSELECT A GAME", translate("&fUse the NPC's or the nether star in your inventory to select a game"), 10, 80, 10);

                    p.sendMessage(translate("&4&m---------------------------------"));
                    p.sendMessage(translate("&4&lSELECT A GAME"));
                    p.sendMessage("");
                    p.sendMessage(translate("&fUse the NPC's or the "));
                    p.sendMessage(translate("&fnether stars in your inventory"));
                    p.sendMessage(translate("&fto select a game."));
                    p.sendMessage(translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1379 + 0.5), 76, (-76 + 0.5), -178, -1);
                    p.teleport(loc);
                } /*
                else if (timesran == 2) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HAT, 100, 1);
                    McPlayer.sendTitle(p, "", CreateGameMenu.translate("&4&lCHECK YOUR STATS &fYou can quickly check your stats overview right at the hub."), 10, 80, 10);

                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));
                    p.sendMessage(CreateGameMenu.translate("&4&lCHECK YOUR STATS"));
                    p.sendMessage("");
                    p.sendMessage(CreateGameMenu.translate("&fCheck your stats overview"));
                    p.sendMessage(CreateGameMenu.translate("&fright in the hub."));
                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1364 + 0.5), 73, (-51 + 0.5), (long) -0.5, (long) -0.3);
                    p.teleport(loc);
                }
                */else if (timesran == 2) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HAT, 100, 1);
                    McPlayer.sendTitle(p,translate("&4&lUP FOR A CHALLENGE?"), translate("&fTry to beat the parkour in the fastest time"), 10, 80, 10);

                    p.sendMessage(translate("&4&m---------------------------------"));
                    p.sendMessage(translate("&4&lUP FOR A CHALLENGE?"));
                    p.sendMessage("");
                    p.sendMessage(translate("&fTry to beat the parkour challenge"));
                    p.sendMessage(translate("&fin the fastest time."));
                    //p.sendMessage(translate("&fwhen you finish the parkour for the first time &7Coming Soon&f."));
                    p.sendMessage(translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1379 + 0.5), 74, (-46 + 0.5), -1, -0);
                    p.teleport(loc);
                } else if (timesran == 4) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_HAT, 100, 1);
                    McPlayer.sendTitle(p, "", translate("&4&lTHAT'S IT"), 10, 80, 10);

                    p.sendMessage(translate("&4&m---------------------------------"));
                    p.sendMessage(translate("&4&lTHAT'S IT"));
                    p.sendMessage(translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1379 + 0.5), 117, (-73 + 0.5), (long) -179.8, -90);
                    p.teleport(loc);
                } else if (timesran == 5) {
                    Location loc = new Location(plugin.getServer().getWorld("Hub"), (1383 + 0.5), 74, (-57 + 0.5), (long) -135.9, (long) -3.6);
                    //play teh acheivement sound thingy wingy
                    AchDatabase database = new AchDatabase(p);
                    if (mysql.getTutorial(p.getUniqueId()).equals("Incomplete") || !database.getHubAch().contains(HAchType.Tour_Guide)) {
                        CreateScoreboard.setScoreboard(p, "Normal", false);
                        database.addHAch(HAchType.Tour_Guide);
                        String achline = translate("&2&m---------------------------------");
                        String completed = translate("&2&l✔ &aTour Guide");
                        String expplus = translate("&4&l+ &c10 EXP");
                        String coinplus = translate("&5&l+ &d5 Magic Dust");
                        String tutorial = translate("&fComplete the tutorial");
                        p.sendMessage(achline);
                        p.sendMessage(completed);
                        p.sendMessage("");
                        p.sendMessage(expplus);
                        p.sendMessage(coinplus);
                        p.sendMessage("");
                        p.sendMessage(tutorial);
                        p.sendMessage(achline);
                        Parkour.otherSound(p);
                        mysql.completedTutorial(p.getUniqueId());
                        McPlayer.sendTitle(p, translate("&2&l✔"), translate("&aTour Guide"), 10, 80, 0);
                        GiveCoinsXP.GivePlayerBoth(p, 5, 10);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (JoinLeaveEvent.playerEffect.get(p) != null) {
                                    JoinLeaveEvent.playerEffect.get(p).cancel();
                                }
                                hologramAnimation(p);
                                JoinLeaveEvent.playerHologram.get(p).delete();
                            }
                        }.runTaskLater(plugin, 40);
                    }
                    p.teleport(loc);
                } else if (timesran == 7) {
                    cancel();
                    p.setFlying(false);
                    p.setGameMode(GameMode.ADVENTURE);
                    playersInTutorial.remove(p);
                }

                timesran++;
            }
        }.runTaskTimer(plugin, 20, 80);
    }

    public static ArrayList<Player> getPlayersInTutorialArrayList() {
        return playersInTutorial;
    }

    public static void hologramAnimation(Player p) {
        new BukkitRunnable() {
            int timesran = 0;
            @Override
            public void run() {
                if (timesran != 50) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                    McPlayer.spawnParticle(p, EnumParticle.CLOUD, new Location(Bukkit.getWorld("Hub"), 1386 + 0.5, 76, -60 + 0.5));
                    timesran++;
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 2);

        new BukkitRunnable() {
            @Override
            public void run() {
                //create all of the locations
                Location loc1 = new Location(Bukkit.getWorld("Hub"), 1375, 79, -67);
                Location loc2 = new Location(Bukkit.getWorld("Hub"), 1372 + 0.5, 79, -61 + 0.5);
                Location loc3 = new Location(Bukkit.getWorld("Hub"), 1376 + 0.5, 79, -55 + 0.5);
                Location loc4 = new Location(Bukkit.getWorld("Hub"), 1378 + 0.5, 78, -53 + 0.5);
                Location loc5 = new Location(Bukkit.getWorld("Hub"), 1381 + 0.5, 78, -51 + 0.5);
                Location loc6 = new Location(Bukkit.getWorld("Hub"), 1387 + 0.5, 78, -53 + 0.5);
                Location massLoc = new Location(Bukkit.getWorld("Hub"), 1386.500, 76.000, -59.500);

                //create all of the holograms
                Hologram holo1 = HologramsAPI.createHologram(plugin, loc1);
                Hologram holo2 = HologramsAPI.createHologram(plugin, loc2);
                Hologram holo3 = HologramsAPI.createHologram(plugin, loc3);
                Hologram holo4 = HologramsAPI.createHologram(plugin, loc4);
                Hologram holo5 = HologramsAPI.createHologram(plugin, loc5);
                Hologram holo6 = HologramsAPI.createHologram(plugin, loc6);

                //get all of the visibility managers
                VisibilityManager vis1 = holo1.getVisibilityManager();
                VisibilityManager vis2 = holo2.getVisibilityManager();
                VisibilityManager vis3 = holo3.getVisibilityManager();
                VisibilityManager vis4 = holo4.getVisibilityManager();
                VisibilityManager vis5 = holo5.getVisibilityManager();
                VisibilityManager vis6 = holo6.getVisibilityManager();
                vis1.setVisibleByDefault(false);
                vis2.setVisibleByDefault(false);
                vis3.setVisibleByDefault(false);
                vis4.setVisibleByDefault(false);
                vis5.setVisibleByDefault(false);
                vis6.setVisibleByDefault(false);
                vis1.showTo(p);
                vis2.showTo(p);
                vis3.showTo(p);
                vis4.showTo(p);
                vis5.showTo(p);
                vis6.showTo(p);

                //add lines
                holo1.appendTextLine(translate("&4&l★"));
                holo2.appendTextLine(translate("&4&l★"));
                holo3.appendTextLine(translate("&c&l★"));
                holo4.appendTextLine(translate("&c&l★"));
                holo5.appendTextLine(translate("&f&l★"));
                holo6.appendTextLine(translate("&f&l★"));

                //teleport
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo1.teleport(massLoc);
                    }
                }.runTaskLater(plugin, 5);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo2.teleport(massLoc);
                    }
                }.runTaskLater(plugin, 10);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo3.teleport(massLoc);
                    }
                }.runTaskLater(plugin, 15);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo4.teleport(massLoc);
                    }
                }.runTaskLater(plugin, 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo5.teleport(massLoc);
                    }
                }.runTaskLater(plugin, 25);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo6.teleport(massLoc);
                    }
                }.runTaskLater(plugin, 30);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo1.teleport(loc1);
                        holo2.teleport(loc2);
                        holo3.teleport(loc3);
                        holo4.teleport(loc4);
                        holo5.teleport(loc5);
                        holo6.teleport(loc6);
                        p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 100, 1);
                        if (JoinLeaveEvent.playerTutorialNPC.get(p) != null) {
                            JoinLeaveEvent.playerTutorialNPC.get(p).despawn();
                            JoinLeaveEvent.playerTutorialNPC.get(p).destroy();
                            CitizensAPI.getNPCRegistry().deregister(JoinLeaveEvent.playerTutorialNPC.get(p));
                        }
                    }
                }.runTaskLater(plugin, 40);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        McPlayer.spawnParticle(p, EnumParticle.CLOUD, new Location(Bukkit.getWorld("Hub"), 1386.500,76.000,-59.500));
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 0);
                        McPlayer.sendTitle(p, translate("&2&lNPC UNLOCKED"), translate("&fYou unlocked the &aAchievements Npc"),5, 15, 5);
                        Location tutnpcloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 75.000, -59.500, 46, (float) 0.3);
                        Location tutloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 78.400, -59.500);
                        NPC quests = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, "");
                        quests.spawn(tutnpcloc);
                        quests.getEntity().setCustomNameVisible(false);
                        JoinLeaveEvent.npcArrayList.add(quests);
                        JoinLeaveEvent.playerTutorialNPC.put(p,quests);

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

                        for (Player loopplayer : Bukkit.getOnlinePlayers()) {
                            if (loopplayer != p) {
                                PlayerConnection connection = ((CraftPlayer) loopplayer).getHandle().playerConnection;
                                connection.sendPacket(new PacketPlayOutEntityDestroy(JoinLeaveEvent.playerTutorialNPC.get(p).getEntity().getEntityId()));
                            }
                        }
                    }
                }.runTaskLater(plugin, 45);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo2.delete();
                        holo3.delete();
                        holo4.delete();
                        holo5.delete();
                        holo6.delete();

                        Location tutloc = new Location(Bukkit.getWorld("Hub"), 1386.500, 79.500, -59.500);

                        holo1.teleport(tutloc);
                        holo1.clearLines();
                        holo1.appendTextLine(translate("&4&l+ &c5 EXP"));

                        ItemStack item = new ItemStack(Material.WOOL, 1, DyeColor.RED.getWoolData());
                        holo1.appendItemLine(item);
                        GiveCoinsXP.GivePlayerEXP(p, 5);
                    }
                }.runTaskLater(plugin, 50);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo1.teleport(p.getLocation());
                    }
                }.runTaskLater(plugin, 80);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        holo1.delete();
                    }
                }.runTaskLater(plugin, 90);
            }
        }.runTaskLater(plugin, 100);
    }
}