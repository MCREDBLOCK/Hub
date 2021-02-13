package com.redblock6.hub.mccore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event) {
        Action action = event.getAction();
        Player p = event.getPlayer();
    }
}
