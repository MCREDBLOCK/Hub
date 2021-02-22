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
        Parkour park = Parkour.getParkourStatus(p);
        if (p.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.GOLD_BLOCK)) {
            if (!park.inParkour) {
                park.enterParkour();
            }

        } else if (p.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.DIAMOND_BLOCK)) {
            if (park.inParkour()) {
                park.finishParkour();
            }
        }
    }
}
