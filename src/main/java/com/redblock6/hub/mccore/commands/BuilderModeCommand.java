package com.redblock6.hub.mccore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.UUID;

import static com.redblock6.hub.Main.pool;
import static com.redblock6.hub.mccore.functions.CreateGameMenu.translate;

public class BuilderModeCommand implements Listener, CommandExecutor {
    public static ArrayList<UUID> playersInBuildMode = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("build")) {
            Player p = ((Player) sender).getPlayer();
            if (p.hasPermission("redblock.builder")) {
                if (!playersInBuildMode.contains(p.getUniqueId())) {
                    for (Player lp : Bukkit.getOnlinePlayers()) {
                        if (lp.hasPermission("redblock.admin")) {
                            lp.sendMessage(translate("&4&l> &c" + p.getDisplayName() +  " &fentered build mode!"));
                        }
                    }
                    p.sendMessage(translate("&2&l> &fEntered build mode. To exit, run &a/build"));
                    p.sendMessage(translate("&7You can:"));
                    p.sendMessage(translate("&7- Bypass no kit warnings"));
                    p.sendMessage(translate("&7- Break blocks"));
                    p.sendMessage(translate("&7- Place blocks"));
                    playersInBuildMode.add(p.getUniqueId());
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 2);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 2);
                } else {
                    for (Player lp : Bukkit.getOnlinePlayers()) {
                        if (lp.hasPermission("redblock.admin")) {
                            lp.sendMessage(translate("&4&l> &c" + p.getDisplayName() +  " &fexited build mode!"));
                        }
                    }
                    p.sendMessage(translate("&4&l> &fExited build mode. To enter build mode, run &c/build"));
                    p.sendMessage(translate("&7You no longer can:"));
                    p.sendMessage(translate("&7- Bypass no kit warnings"));
                    p.sendMessage(translate("&7- Break blocks"));
                    p.sendMessage(translate("&7- Place blocks"));
                    playersInBuildMode.remove(p.getUniqueId());
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                }
            }
        } else if (command.getName().equals("ignorebuildwarnings")) {
            Player p = ((Player) sender).getPlayer();
            if (p.hasPermission("redblock.builder")) {
                Jedis j = pool.getResource();
                if (j.get(p.getName() + "IBW") == null || j.get(p.getName() + "IBW").contains("FALSE")) {
                    p.sendMessage(translate("&4&l> &fBuild warnings have been disabled for &c" + p.getName()));
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 2);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 2);
                    j.set(p.getName() + "IBW", "TRUE");
                } else if (j.get(p.getName() + "IBW").contains("TRUE")) {
                    p.sendMessage(translate("&2&l> &fBuild warnings have been enabled for &a" + p.getName()));
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 2);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 2);
                    j.set(p.getName() + "IBW", "FALSE");
                }
                j.close();
            }
        }
        return false;
    }
}
