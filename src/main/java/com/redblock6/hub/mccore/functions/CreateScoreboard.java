package com.redblock6.hub.mccore.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class CreateScoreboard {

    public static Objective o;
    public static Scoreboard normal() {
        //create scoreboard
        Scoreboard b = Bukkit.getScoreboardManager().getNewScoreboard();

        String s1 = ChatColor.translateAlternateColorCodes('&', "&4&lHUB");
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
        String blank = "";
        Score blankline = o.getScore(blank);
        blankline.setScore(2);

        //exp
        String exp = ChatColor.translateAlternateColorCodes('&', "&4&lEXP &c0&7/&c1000");
        Score expline = o.getScore(exp);
        expline.setScore(3);

        //level
        String level = ChatColor.translateAlternateColorCodes('&', "&4&lLEVEL &cComming Soon");
        Score levelline = o.getScore(level);
        levelline.setScore(4);

        //top line
        String line2 = ChatColor.translateAlternateColorCodes('&', "&4&l&7&m---------------------");
        Score line2line = o.getScore(line2);
        line2line.setScore(5);

        CreateScoreboard.o = o;
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
                Scoreboard b = p.getScoreboard();
                String s1 = ChatColor.translateAlternateColorCodes('&', "&3&lSMP");

                b.getTeam("playercount").setPrefix(ChatColor.translateAlternateColorCodes('&', "&b" + Bukkit.getOnlinePlayers().size() + "&7/50"));
                o.getScore(ChatColor.AQUA + "" + ChatColor.GRAY).setScore(8);
            } else if (setscoreboard.equals(true)) {
                p.setScoreboard(new CreateScoreboard().normal());
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