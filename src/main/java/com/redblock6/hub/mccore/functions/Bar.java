package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {

    private static int taskID = -1;
    private static Main plugin = Main.getInstance();
    private static BossBar bar;

    public Bar(Main plugin) {
        Bar.plugin = plugin;
    }

    public void addPlayer(Player p) {
        bar.addPlayer(p);
    }

    public BossBar getBar() {
        return bar;
    }

    public void createBossBar() {
        bar = Bukkit.createBossBar(ChatColor.DARK_RED + "" + ChatColor.BOLD + "MCREDBLOCK" + ChatColor.RED + " " + ChatColor.BOLD + "JAVA", BarColor.RED, BarStyle.SOLID);
        bar.setVisible(true);
        // cast();
    }

    /*
    public static void cast() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int count = -1;
            double progress = 1.0;
            double time = 1.0 / (10 * 10);

            @Override
            public void run() {
                bar.setProgress(progress);

                switch(count) {
                    case -1:
                        break;
                    case 0:
                        bar.setTitle(ChatColor.translateAlternateColorCodes('&', "&4&lMCREDBLOCK &c&lJAVA"));
                        count++;
                    case 1:
                        bar.setTitle(ChatColor.translateAlternateColorCodes( '&', "&4&lJOIN THE DISCORD! &chttps://discord.com/invite/wcdMgBBhWy"));
                        count++;
                    case 2:
                    default:
                        bar.setTitle(ChatColor.translateAlternateColorCodes('&', "&4&lCONNECT WITH &cmc.redblock6.com"));
                        count = -1;
                        break;
                }
                progress = progress - time;
                if (progress <= 0) {
                    count++;
                    progress = 1.0;
                }
            }
        }, 0, 0);
     */
    }
