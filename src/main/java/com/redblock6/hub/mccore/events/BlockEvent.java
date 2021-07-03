package com.redblock6.hub.mccore.events;

import com.redblock6.hub.mccore.commands.BuilderModeCommand;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import redis.clients.jedis.Jedis;

import static com.redblock6.hub.Main.pool;
import static com.redblock6.hub.mccore.functions.CreateGameMenu.translate;

public class BlockEvent implements Listener {
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("redblock.builder")) {
            Jedis j = pool.getResource();
            if (j.get(p.getName() + "IBW").contains("FALSE") && !BuilderModeCommand.playersInBuildMode.contains(p.getUniqueId())) {
                p.sendMessage(translate("&4&l> &fYou need to be in build mode to build/break blocks! Run &c/build &fto enter build mode."));
                p.sendMessage(translate("&7To ignore these warnings run /ignorebuildwarnings"));
                e.setCancelled(true);
            } else if (j.get(p.getName() + "IBW").contains("TRUE") && !BuilderModeCommand.playersInBuildMode.contains(p.getUniqueId())) {
                e.setCancelled(true);
            }
            j.close();
        } else {
            if (BuilderModeCommand.playersInBuildMode.contains(p.getUniqueId())) {
                p.sendMessage(translate("&4&l> &cSYSTEM &fhas disabled your build mode."));
                BuilderModeCommand.playersInBuildMode.remove(p.getUniqueId());
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("redblock.builder")) {
            Jedis j = pool.getResource();
            if (j.get(p.getName() + "IBW").contains("FALSE") && !BuilderModeCommand.playersInBuildMode.contains(p.getUniqueId())) {
                p.sendMessage(translate("&4&l> &fYou need to be in build mode to build/break blocks! Run &c/build &fto enter build mode."));
                p.sendMessage(translate("&7To ignore these warnings run /ignorebuildwarnings"));
                e.setCancelled(true);
            } else if (j.get(p.getName() + "IBW").contains("TRUE") && !BuilderModeCommand.playersInBuildMode.contains(p.getUniqueId())) {
                e.setCancelled(true);
            }
            j.close();
        } else {
            if (BuilderModeCommand.playersInBuildMode.contains(p.getUniqueId())) {
                p.sendMessage(translate("&4&l> &cSYSTEM &fhas disabled your build mode."));
                BuilderModeCommand.playersInBuildMode.remove(p.getUniqueId());
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
            }
            e.setCancelled(true);
        }
    }
}
