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

        /* //blank
        String blank3 = ChatColor.translateAlternateColorCodes('&', "&4&l&c&m&9&l&b&m");
        Score blankline3 = o.getScore(blank3);
        blankline3.setScore(4);

        //claim blocks
        String claim = ChatColor.translateAlternateColorCodes('&', "&3&lClaim Blocks");
        Score claimline = o.getScore(claim);
        claimline.setScore(6);

        //blank
        String blank45 = ChatColor.translateAlternateColorCodes('&', "&4&l&c&m");
        Score blankline45 = o.getScore(blank45);
        blankline45.setScore(7);

        //player count
        Team playercount = b.registerNewTeam("playercount");
        playercount.addEntry(ChatColor.AQUA + "" + ChatColor.GRAY);
        playercount.setPrefix(ChatColor.translateAlternateColorCodes('&', "&b" + Bukkit.getOnlinePlayers().size() + "&7/50"));
        o.getScore(ChatColor.AQUA + "" + ChatColor.GRAY).setScore(8);

        //players
        String playerslineb = ChatColor.translateAlternateColorCodes('&', "&3&lPlayers");
        Score playersline = o.getScore(playerslineb);
        playersline.setScore(9); */

        //top line
        String line2 = ChatColor.translateAlternateColorCodes('&', "&4&l&7&m---------------------");
        Score line2line = o.getScore(line2);
        line2line.setScore(5);

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
        }
    }

}