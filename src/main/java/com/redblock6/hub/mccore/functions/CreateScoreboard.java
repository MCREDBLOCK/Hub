package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import redis.clients.jedis.Jedis;

import static com.redblock6.hub.Main.pool;

public class CreateScoreboard {

    public static Objective o;
    private static final Main plugin = Main.getInstance();

    public static Scoreboard normal(Player p) {
        //get the pool
        Jedis j = pool.getResource();

        //create scoreboard
        Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();

        String s1 = ChatColor.translateAlternateColorCodes('&', "&4&lHUB-" + plugin.getConfig().getInt("hub-identifier"));
        Objective o = b.registerNewObjective("HUB", "dummy", s1);
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(s1);

        //bottom line
        String line = ChatColor.translateAlternateColorCodes('&', "&7&m---------------------");
        Score line1 = o.getScore(line);
        line1.setScore(0);

        //ip
        String ip = ChatColor.translateAlternateColorCodes('&', "&4&lmc.redblock6.com");
        Score ipline = o.getScore(ip);
        ipline.setScore(1);

        //blank
        String blank2 = CreateGameMenu.translate("&4&l&c&m");
        Score blankline2 = o.getScore(blank2);
        blankline2.setScore(2);

        //coins
        Team coins = b.registerNewTeam("c" + p.getName());
        coins.addEntry(ChatColor.YELLOW + "" + ChatColor.YELLOW);
        String coinsline = ChatColor.translateAlternateColorCodes('&', "&5&lMAGIC DUST &d" + j.get(p.getUniqueId() + "Coins"));
        coins.setPrefix(coinsline);
        o.getScore(ChatColor.YELLOW + "" + ChatColor.YELLOW).setScore(3);

        //blank
        String blank = "";
        Score blankline = o.getScore(blank);
        blankline.setScore(4);

        //exp
        Team exp = b.registerNewTeam("e" + p.getName());
        exp.addEntry(ChatColor.RED + "" + ChatColor.GRAY);
        String expline = ChatColor.translateAlternateColorCodes('&', "&4╚═ &c" + j.get(p.getUniqueId() + "Exp") + "&7/&c" + j.get(p.getUniqueId() + "ExpMax"));
        exp.setPrefix(expline);
        o.getScore(ChatColor.RED + "" + ChatColor.GRAY).setScore(5);

        //level
        Team level = b.registerNewTeam("l" + p.getName());
        level.addEntry(ChatColor.DARK_RED + "" + ChatColor.RED);
        String levelline = ChatColor.translateAlternateColorCodes('&', "&4&lLEVEL &c" + j.get(p.getUniqueId() + "Level"));
        level.setPrefix(levelline);
        o.getScore(ChatColor.DARK_RED + "" + ChatColor.RED).setScore(6);

        //top line
        String line2 = ChatColor.translateAlternateColorCodes('&', "&4&l&7&m---------------------");
        Score line2line = o.getScore(line2);
        line2line.setScore(7);

        CreateScoreboard.o = o;
        j.close();
        return b;
    }

    public static Scoreboard parkour() {
        //create scoreboard
        Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();

        String s1 = ChatColor.translateAlternateColorCodes('&', "&6&lPARKOUR");
        Objective o = b.registerNewObjective("PK", "dummy", s1);
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(s1);

        //bottom line
        String line = ChatColor.translateAlternateColorCodes('&', "&7&m---------------------");
        Score line1 = o.getScore(line);
        line1.setScore(0);

        //ip
        String ip = ChatColor.translateAlternateColorCodes('&', "&6&lmc.redblock6.com");
        Score ipline = o.getScore(ip);
        ipline.setScore(1);

        //blank
        String blank = "";
        Score blankline = o.getScore(blank);
        blankline.setScore(2);

        //exp
        String exp = ChatColor.translateAlternateColorCodes('&', "&6&lTIME");
        Score expline = o.getScore(exp);
        expline.setScore(4);

        //level
        Team aclaimblocks = b.registerNewTeam("time");
        aclaimblocks.addEntry(ChatColor.YELLOW + "" + ChatColor.YELLOW);
        String aclaimblocksline = ChatColor.translateAlternateColorCodes('&', "&e");
        aclaimblocks.setPrefix(aclaimblocksline);
        o.getScore(ChatColor.YELLOW + "" + ChatColor.YELLOW).setScore(3);

        //blank
        String blank2 = CreateGameMenu.translate("&4&l&c&m");
        Score blankline2 = o.getScore(blank2);
        blankline2.setScore(5);

        //actual players playing with you
        Team aotherplayers = b.registerNewTeam("playingwithyou");
        aotherplayers.addEntry(ChatColor.YELLOW + "" + ChatColor.WHITE);
        String aotherplayersline = ChatColor.translateAlternateColorCodes('&', "&e" + Parkour.getOtherPlayers() + " &fother players");
        aotherplayers.setPrefix(aotherplayersline);
        o.getScore(ChatColor.YELLOW + "" + ChatColor.WHITE).setScore(6);

        //other players playing with you
        String otherplayers = ChatColor.translateAlternateColorCodes('&', "&6&lYOU'RE PLAYING &e&lPK &6&lWITH");
        Score otherplayersline = o.getScore(otherplayers);
        otherplayersline.setScore(7);

        //top line
        String line2 = ChatColor.translateAlternateColorCodes('&', "&4&l&7&m---------------------");
        Score line2line = o.getScore(line2);
        line2line.setScore(8);

        CreateScoreboard.o = o;
        return b;
    }


    public static void setScoreboard(Player p, String type, Boolean setscoreboard) {
        if (type.equals("Normal")) {
            if (setscoreboard.equals(false)) {
                Parkour park = Parkour.getParkourStatus(p);
                if (!park.inParkour()) {
                    //get the pool
                    Jedis j = pool.getResource();

                    Scoreboard b = p.getScoreboard();
                    String s1 = ChatColor.translateAlternateColorCodes('&', "&4&lHUB-" + plugin.getConfig().getInt("hub-identifier"));

                    b.getTeam("c" + p.getName()).setPrefix(ChatColor.translateAlternateColorCodes('&', "&5&lMAGIC DUST &d" + j.get(p.getUniqueId() + "Coins")));
                    o.getScore(ChatColor.YELLOW + "" + ChatColor.YELLOW).setScore(3);

                    b.getTeam("e" + p.getName()).setPrefix(ChatColor.translateAlternateColorCodes('&', "&4╚═ &c" + j.get(p.getUniqueId() + "Exp") + "&7/&c" + j.get(p.getUniqueId() + "ExpMax")));
                    o.getScore(ChatColor.RED + "" + ChatColor.GRAY).setScore(5);

                    b.getTeam("l" + p.getName()).setPrefix(ChatColor.translateAlternateColorCodes('&', "&4&lLEVEL &c" + j.get(p.getUniqueId() + "Level")));
                    o.getScore(ChatColor.DARK_RED + "" + ChatColor.RED).setScore(6);

                    j.close();
                }
            } else if (setscoreboard.equals(true)) {
                p.setScoreboard(new CreateScoreboard().normal(p));
            }
        } else if (type.equals("Parkour")) {
            if (setscoreboard.equals(false)) {
                Parkour park = Parkour.getParkourStatus(p);

                Scoreboard b = p.getScoreboard();
                String s1 = CreateGameMenu.translate("&6&lPARKOUR");
                b.getTeam("time").setPrefix(ChatColor.translateAlternateColorCodes('&', "&e" + park.getTime()));
                o.getScore(ChatColor.YELLOW + "" + ChatColor.YELLOW).setScore(4);
                b.getTeam("playingwithyou").setPrefix(ChatColor.translateAlternateColorCodes('&', "&e" + (Parkour.getOtherPlayers().size() - 1) + " &fother players"));
                o.getScore(ChatColor.YELLOW + "" + ChatColor.WHITE).setScore(6);
            } else if (setscoreboard.equals(true)) {
                p.setScoreboard(new CreateScoreboard().parkour());
            }
        }
    }

}