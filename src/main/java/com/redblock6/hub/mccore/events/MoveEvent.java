package com.redblock6.hub.mccore.events;

import com.redblock6.hub.mccore.functions.Parkour;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.getLocation().subtract(0, 1, 0).equals(Material.GOLD_BLOCK)) {
            Parkour.enterParkour(p);
        } else if (p.getLocation().subtract(0, 1, 0).equals(Material.DIAMOND_BLOCK)) {
            Parkour.exitParkour(p);
        }
    }
}
