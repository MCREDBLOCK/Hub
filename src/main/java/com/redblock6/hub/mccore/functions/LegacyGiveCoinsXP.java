package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import static com.redblock6.hub.Main.pool;

public class LegacyGiveCoinsXP {
    private static final Main plugin = Main.getInstance();
    private static int currentcoins;
    private static int currentexp;

    public static void GivePlayerCoins(Player p, int amount) {
        //get the pool
        int ticks;

        if (amount > 100) {
            ticks = 1;
        } else {
            ticks = 2;
        }
        try {
            Jedis j = pool.getResource();
            currentcoins = Integer.parseInt(j.get(p.getUniqueId() + "Coins"));

            //play the giving coins sound thingy
            new BukkitRunnable() {
                int coinsgiven = 0;

                @Override
                public void run() {
                    if (coinsgiven == amount) {
                        cancel();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 2);
                        j.set(p.getUniqueId() + "Coins", String.valueOf(currentcoins + amount));
                        p.sendMessage(CreateGameMenu.translate("&5&l> &fYou now have &d" + j.get(p.getUniqueId() + "Coins") + " Magic Dust"));
                        j.close();

                        p.sendTitle(CreateGameMenu.translate("&5&l☆ &d0 &5&l☆"), CreateGameMenu.translate(CreateGameMenu.translate("&fYou now have &d" + j.get(p.getUniqueId() + "Coins") + " Magic Dust")), 0, 40, 10);
                        CreateScoreboard.setScoreboard(p, "Normal", false);
                    } else {
                        coinsgiven++;
                        p.sendTitle(CreateGameMenu.translate("&5&l★ &d" + (amount - coinsgiven) + " &5&l★"), CreateGameMenu.translate("&fYou now have &d" + (currentcoins + coinsgiven) + " Magic Dust"), 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, ticks);

            plugin.getServer().getLogger().info("> Added " + amount + " coins for " + p.getUniqueId() + " in redis.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD..."), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 40, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p + " " + amount + " coins with redis.");
        }
    }

    public static void GivePlayerEXP(Player p, int amount) {
        int ticks;

        if (amount > 100) {
            ticks = 1;
        } else {
            ticks = 2;
        }
        try {
            Jedis j = pool.getResource();
            currentexp = Integer.parseInt(j.get(p.getUniqueId() + "Exp"));

            //play the giving coins sound thingy
            new BukkitRunnable() {
                int expgiven = 0;

                @Override
                public void run() {
                    if (expgiven == amount) {
                        cancel();
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 2);
                        j.set(p.getUniqueId() + "Exp", String.valueOf((currentexp + amount)));
                        p.sendMessage(CreateGameMenu.translate("&4&l> &fYou now have &c" + j.get(p.getUniqueId() + "Exp") + "&7/&c" + j.get(p.getUniqueId() + "ExpMax") + " &fexperience."));
                        j.close();

                        p.sendTitle(CreateGameMenu.translate("&4&l⬞ &c0 &4&l⬞"), CreateGameMenu.translate((currentexp + expgiven) + "&7/&c" + j.get(p.getUniqueId() + "ExpMax")), 0, 40, 10);
                        CreateScoreboard.setScoreboard(p, "Normal", false);
                    } else {
                        expgiven++;
                        p.sendTitle(CreateGameMenu.translate("&4&l⬝ &c" + (amount - expgiven) + " &4&l⬝"), CreateGameMenu.translate((currentexp + expgiven) + "&7/&c" + j.get(p.getUniqueId() + "ExpMax")), 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 1);
                        // j.incrBy(p.getUniqueId() + "Exp", 1);
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, ticks);

            plugin.getServer().getLogger().info("> Added " + amount + " exp for " + p.getUniqueId() + " in redis.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD..."), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 40, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p + " " + amount + " exp with redis.");
        }
    }

    public static void GivePlayerBoth(Player p, int amountcoins, int amountexp) {
        int ticks;

        if (amountcoins > 100 || amountexp > 100) {
            ticks = 1;
        } else {
            ticks = 2;
        }
        try {
            Jedis j = pool.getResource();
            currentexp = Integer.parseInt(j.get(p.getUniqueId() + "Exp"));
            currentcoins = Integer.parseInt(j.get(p.getUniqueId() + "Coins"));

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
                        j.set(p.getUniqueId() + "Coins", String.valueOf((currentcoins + amountcoins)));
                        j.set(p.getUniqueId() + "Exp", String.valueOf((currentexp + amountexp)));
                        p.sendMessage(CreateGameMenu.translate("&4&l> &fYou now have &d" + j.get(p.getUniqueId() + "Coins") + " Magic Dust &fand &c" + j.get(p.getUniqueId() + "Exp") + "&7&l/&c" + j.get(p.getUniqueId() + "ExpMax") + " &fexperience."));
                        j.close();
                        CreateScoreboard.setScoreboard(p, "Normal", false);

                        p.sendTitle(CreateGameMenu.translate("&5&l★ &5" + "0" + " &5&l★ &7&l- &4&l⬝ &c" + "0" + " &4&l⬝"), CreateGameMenu.translate("&fYou now have &d" + j.get(p.getUniqueId() + "Coins") + " Magic Dust &fand &c" + j.get(p.getUniqueId() + "Exp") + "&7&l/&c" + j.get(p.getUniqueId() + "ExpMax") + " &fexperience."), 0, 40, 10);
                    } else {
                        p.sendTitle(CreateGameMenu.translate("&5&l★ &5" + (amountcoins - coinsgiven) + " &5&l★ &7&l- &4&l⬝ &c" + (amountexp - expgiven) + " &4&l⬝"), CreateGameMenu.translate("&fYou now have &d" + (coinsgiven + amountcoins) + " Magic Dust &fand &c" + (expgiven + amountexp) + "&7&l/&c" + j.get(p.getUniqueId() + "ExpMax") + " &fexperience."), 0, 20, 0);
                        if (coinsgiven != amountcoins) {
                            coinsgiven++;
                            // j.incrBy(p.getUniqueId() + "Coins", 1);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                        }
                        if (expgiven != amountexp) {
                            expgiven++;
                            // j.incrBy(p.getUniqueId() + "Exp", 1);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 100, 1);
                            if (Integer.parseInt(j.get(p.getUniqueId() + "Exp")) >= (Integer.parseInt(j.get(p.getUniqueId() + "ExpMax")) - 1)) {
                                levelUp(p);
                                coinsgiven -= 50;
                            }
                        }
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 40, ticks);

            plugin.getServer().getLogger().info("> Added " + amountcoins + " Magic Dust and " + amountexp + " exp for " + p.getUniqueId() + " in redis.");
        } catch (Exception e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lWELL THAT'S WEIRD..."), CreateGameMenu.translate("&fFailed to contact RYGB services. Please tell &cRedblock6#6091 &fto fix his code."), 0, 40, 0);
            plugin.getServer().getLogger().info("> Failed to give " + p + " " + amountcoins + " Magic Dust and " + amountexp + " exp with redis.");
        }
    }

    public static void levelUp(Player p) {
        Jedis j = pool.getResource();

        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
        j.set(p.getUniqueId() + "Exp", String.valueOf(Integer.parseInt("0")));
        j.incrBy(p.getUniqueId() + "ExpMax", 100);
        j.incrBy(p.getUniqueId() + "Level", 1);
        j.incrBy(p.getUniqueId() + "Coins", 1);
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