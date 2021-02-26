package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import static com.redblock6.hub.Main.pool;

public class GiveCoinsXP {
    private static final Main plugin = Main.getInstance();

    public static void GivePlayerCoins(Player p, int amount) {
        p.sendTitle(CreateGameMenu.translate("&2&lHANG ON"), CreateGameMenu.translate("&fContacting RYGB services..."), 0, 120, 0);

        //get the pool
        try {
            Jedis j = pool.getResource();

            //play the giving coins sound thingy
            new BukkitRunnable() {
                int coinsgiven = 0;

                @Override
                public void run() {
                    if (coinsgiven == amount) {
                        cancel();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                        p.sendMessage(CreateGameMenu.translate("&6&l> &fYou now have &e" + j.get(p.getUniqueId() + "Coins") + " coins"));
                        j.close();

                        p.sendTitle(CreateGameMenu.translate("&6&l☆ &e0 &6&l☆"), CreateGameMenu.translate("&fspend these coins wisely"), 0, 40, 10);
                        // CreateScoreboard.setScoreboard(p, "Normal", true);
                    } else {
                        coinsgiven++;
                        p.sendTitle(CreateGameMenu.translate("&6&l★ &e" + (amount - coinsgiven) + " &6&l★"), CreateGameMenu.translate(j.get(p.getUniqueId() + "Coins")), 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                        j.incrBy(p.getUniqueId() + "Coins", 1);
                        CreateScoreboard.setScoreboard(p, "Normal", false);
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, 2);

            plugin.getServer().getLogger().info("> Added " + amount + " coins for " + p.getUniqueId() + " in redis.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD..."), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 40, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p + " " + amount + " coins with redis.");
        }
    }

    public static void GivePlayerEXP(Player p, int amount) {
        p.sendTitle(CreateGameMenu.translate("&2&lHANG ON"), CreateGameMenu.translate("&fContacting RYGB services..."), 0, 120, 0);
        try {
            Jedis j = pool.getResource();

            //play the giving coins sound thingy
            new BukkitRunnable() {
                int expgiven = 0;

                @Override
                public void run() {
                    if (expgiven == amount) {
                        cancel();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 2);
                        p.sendMessage(CreateGameMenu.translate("&4&l> &fYou now have &c" + j.get(p.getUniqueId() + "Exp") + "&7/&c" + j.get(p.getUniqueId() + "ExpMax")));
                        j.close();

                        p.sendTitle(CreateGameMenu.translate("&4&l⬞ &c0 &4&l⬞"), "", 0, 40, 10);
                        // CreateScoreboard.setScoreboard(p, "Normal", true);
                    } else {
                        expgiven++;
                        p.sendTitle(CreateGameMenu.translate("&4&l⬝ &c" + (amount - expgiven) + " &4&l⬝"), CreateGameMenu.translate(j.get(p.getUniqueId() + "Exp") + "&7/&c" + j.get(p.getUniqueId() + "ExpMax")), 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 1);
                        j.incrBy(p.getUniqueId() + "Exp", 1);
                        CreateScoreboard.setScoreboard(p, "Normal", false);
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, 2);

            plugin.getServer().getLogger().info("> Added " + amount + " coins for " + p.getUniqueId() + " in redis.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD..."), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 40, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p + " " + amount + " exp with redis.");
        }
    }

    public static void GivePlayerBoth(Player p, int amountcoins, int amountexp) {
        p.sendTitle(CreateGameMenu.translate("&2&lHANG ON"), CreateGameMenu.translate("&fContacting RYGB services..."), 0, 120, 0);
        try {
            Jedis j = pool.getResource();

            //play the giving coins sound thingy
            new BukkitRunnable() {
                int coinsgiven = 0;
                int expgiven = 0;

                @Override
                public void run() {
                    if (coinsgiven == amountcoins && expgiven == amountexp) {
                        cancel();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                        p.sendMessage(CreateGameMenu.translate("&4&l> &fYou now have &e" + j.get(p.getUniqueId() + "Coins") + " coins &fand &c" + j.get(p.getUniqueId() + "Exp") + "&7&l/&c" + j.get(p.getUniqueId() + "ExpMax") + " &fexperience."));
                        j.close();

                        p.sendTitle(CreateGameMenu.translate("&6&l★ &e" + "0" + " &6&l★ &7&l- &4&l⬝ &c" + "0" + " &4&l⬝"), "All exp and coins have been given.", 0, 40, 10);
                        // CreateScoreboard.setScoreboard(p, "Normal", true);
                    } else {
                        p.sendTitle(CreateGameMenu.translate("&6&l★ &e" + (amountcoins - coinsgiven) + " &6&l★ &7&l- &4&l⬝ &c" + (amountexp - expgiven) + " &4&l⬝"), CreateGameMenu.translate("&fYou now have &e" + j.get(p.getUniqueId() + "Coins") + " coins &fand &c" + j.get(p.getUniqueId() + "Exp") + "&7&l/&c" + j.get(p.getUniqueId() + "ExpMax") + " &fexperience."), 0, 20, 0);
                        if (coinsgiven != amountcoins) {
                            coinsgiven++;
                            j.incrBy(p.getUniqueId() + "Coins", 1);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                            CreateScoreboard.setScoreboard(p, "Normal", false);
                        }
                        if (expgiven != amountexp) {
                            expgiven++;
                            j.incrBy(p.getUniqueId() + "Exp", 1);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 1);
                            CreateScoreboard.setScoreboard(p, "Normal", false);
                            if (Integer.parseInt(j.get(p.getUniqueId() + "Exp")) >= (Integer.parseInt(j.get(p.getUniqueId() + "ExpMax")) - 1)) {
                                levelUp(p);
                                coinsgiven -= 50;
                            }
                        }
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, 2);

            plugin.getServer().getLogger().info("> Added " + amountcoins + " coins and " + amountexp + " exp for " + p.getUniqueId() + " in redis.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD..."), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 40, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p + " " + amountcoins + " coins and " + amountexp + " exp with redis.");
        }
    }

    public static void levelUp(Player p) {
        Jedis j = pool.getResource();

        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
        j.set(p.getUniqueId() + "Exp", String.valueOf(Integer.parseInt("0")));
        j.incrBy(p.getUniqueId() + "ExpMax", 100);
        j.incrBy(p.getUniqueId() + "Level", 1);
        p.sendTitle(CreateGameMenu.translate("&2&lLEVEL UP"), CreateGameMenu.translate("&fYou are now level &a" + j.get(p.getUniqueId() + "Exp") + " &fand need &a" + j.get(p.getUniqueId() + "ExpMax") + " &fmore exp to level up."), 0, 20, 10);

        String line = CreateGameMenu.translate("&2&m---------------------------------");
        String levelup = CreateGameMenu.translate("&2&lLEVEL UP");
        String blank = "";
        String reward = CreateGameMenu.translate("&6&l+ &e50 coins");
        String newchallenge = CreateGameMenu.translate("&fNow you have to get &a" + j.get(p.getUniqueId() + "ExpMax"));
        String newchallenge2 = CreateGameMenu.translate("&fmore exp to levelup");
        String nowlevel = CreateGameMenu.translate("&fYou are now &2&lLEVEL &a" + j.get(p.getUniqueId() + "Level"));

        p.sendMessage(line);
        p.sendMessage(levelup);
        p.sendMessage(blank);
        p.sendMessage(reward);
        p.sendMessage(blank);
        p.sendMessage(nowlevel);
        p.sendMessage(newchallenge);
        p.sendMessage(newchallenge2);
        p.sendMessage(line);

        j.close();
    }
}
