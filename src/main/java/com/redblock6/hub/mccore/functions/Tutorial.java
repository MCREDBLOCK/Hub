package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import static com.redblock6.hub.Main.pool;

public class Tutorial implements Listener {

    private static final Main plugin = Main.getInstance();

    public static void startTutorial(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
        p.setInvisible(true);
        p.setFlying(true);
        p.setAllowFlight(true);
        p.setGameMode(GameMode.CREATIVE);

        new BukkitRunnable() {
            int timesran = 0;

            @Override
            public void run() {
                if (!(p.isOnline())) {
                    cancel();
                }
                if (timesran == 0) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 100, 1);
                    p.sendTitle(CreateGameMenu.translate("&4&lWELCOME!"), CreateGameMenu.translate("&fWe'll try to keep this tour quick so you can be on your way!"), 10, 80, 10);

                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));
                    p.sendMessage(CreateGameMenu.translate("&4&lWELCOME TO MCREDBLOCK!"));
                    p.sendMessage("");
                    p.sendMessage(CreateGameMenu.translate("&fWe will try and keep this tutorial"));
                    p.sendMessage(CreateGameMenu.translate("&fso that you can collect your reward"));
                    p.sendMessage(CreateGameMenu.translate("&fand get straight to gaming."));
                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1379 + 0.5), 106, (-85 + 0.5), -178, -1);
                    p.teleport(loc);
                } else if (timesran == 1) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 100, 1);
                    p.sendTitle(CreateGameMenu.translate("&4&lGAMES"), CreateGameMenu.translate("&fWe have a wide range of games to choose from"), 10, 80, 10);

                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));
                    p.sendMessage(CreateGameMenu.translate("&4&lVARIETY OF GAMES"));
                    p.sendMessage("");
                    p.sendMessage(CreateGameMenu.translate("&fWe have a range of games"));
                    p.sendMessage(CreateGameMenu.translate("&fto choose from, and we will keep"));
                    p.sendMessage(CreateGameMenu.translate("&fadding more games. Report bugs on the discord:"));
                    p.sendMessage(CreateGameMenu.translate("&chttps://discord.gg/3uWDrz4fnY"));
                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1379 + 0.5), 76, (-76 + 0.5), -178, -1);
                    p.teleport(loc);
                } else if (timesran == 2) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 100, 1);
                    p.sendTitle(CreateGameMenu.translate("&4&lSTATS CHECK"), CreateGameMenu.translate("&fYou can quickly check your stats overview right at the hub."), 10, 80, 10);

                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));
                    p.sendMessage(CreateGameMenu.translate("&4&lSTATS CHECK"));
                    p.sendMessage("");
                    p.sendMessage(CreateGameMenu.translate("&fCheck your stats overview"));
                    p.sendMessage(CreateGameMenu.translate("&fright in the hub."));
                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1364 + 0.5), 73, (-51 + 0.5), (long) -0.5, (long) -0.3);
                    p.teleport(loc);
                } else if (timesran == 3) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 100, 1);
                    p.sendTitle(CreateGameMenu.translate("&4&lUP FOR A CHALLENGE?"), CreateGameMenu.translate("&fTry to beat the parkour in the fastest time"), 10, 80, 10);

                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));
                    p.sendMessage(CreateGameMenu.translate("&4&lUP FOR A CHALLENGE?"));
                    p.sendMessage("");
                    p.sendMessage(CreateGameMenu.translate("&fTry to beat the parkour challenge"));
                    p.sendMessage(CreateGameMenu.translate("&fthe fastest. We'll give you a reward"));
                    p.sendMessage(CreateGameMenu.translate("&fwhen you finish the parkour for the first time."));
                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1379 + 0.5), 74, (-46 + 0.5), -1, -0);
                    p.teleport(loc);
                } else if (timesran == 4) {
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 100, 1);
                    p.sendTitle(CreateGameMenu.translate("&4&lTHAT'S IT"), "", 10, 80, 10);

                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));
                    p.sendMessage(CreateGameMenu.translate("&4&lTHAT'S IT"));
                    p.sendMessage(CreateGameMenu.translate("&4&m---------------------------------"));

                    Location loc = new Location(p.getWorld(), (1379 + 0.5), 117, (-73 + 0.5), (long) -179.8, -90);
                    p.teleport(loc);
                } else if (timesran == 5) {
                    Jedis j = pool.getResource();
                    //play teh acheivement sound thingy wingy
                    if (j.get(p.getUniqueId() + "Tutorial") == null) {
                        CreateScoreboard.setScoreboard(p, "Normal", false);
                        String achline = CreateGameMenu.translate("&2&m---------------------------------");
                        String completed = CreateGameMenu.translate("&2&lACHEIVEMENT COMPLETED &a&lTOUR GUIDE");
                        String expplus = CreateGameMenu.translate("&4&l+ &c10 EXP");
                        String coinplus = CreateGameMenu.translate("&5&l+ &d5 Magic Dust");
                        String tutorial = CreateGameMenu.translate("&fComplete the tutorial");
                        p.sendMessage(achline);
                        p.sendMessage(completed);
                        p.sendMessage("");
                        p.sendMessage(expplus);
                        p.sendMessage(coinplus);
                        p.sendMessage("");
                        p.sendMessage(tutorial);
                        p.sendMessage(achline);
                        Parkour.otherSound(p);
                        p.sendTitle(CreateGameMenu.translate("&2&l✔"), CreateGameMenu.translate("&aTour Guide"), 10, 80, 0);
                        GiveCoinsXP.GivePlayerBoth(p, 5, 10);
                        j.set(p.getUniqueId() + "Tutorial", "Completed");
                    }
                    j.close();
                } else if (timesran == 6) {
                    cancel();
                    p.setInvisible(false);
                    p.setFlying(false);
                    p.setGameMode(GameMode.ADVENTURE);
                    Location loc = new Location(plugin.getServer().getWorld("Hub"), plugin.getServer().getWorld("Hub").getSpawnLocation().getX() + 0.5, plugin.getServer().getWorld("Hub").getSpawnLocation().getY() + 0.5, plugin.getServer().getWorld("Hub").getSpawnLocation().getZ() + 0.5, (float) -179, (float) -1);
                    p.teleport(loc);
                }

                timesran++;
            }
        }.runTaskTimer(plugin, 20, 80);
    }
}
