package com.redblock6.hub.mccore.events;

import com.redblock6.hub.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class DeathDamageEvent implements Listener {
    private static final Main plugin = Main.getInstance();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();

        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            Location loc = new Location(plugin.getServer().getWorld("Hub"),
                    plugin.getServer().getWorld("Hub").getSpawnLocation().getX(),
                    plugin.getServer().getWorld("Hub").getSpawnLocation().getY(),
                    plugin.getServer().getWorld("Hub").getSpawnLocation().getZ(),
                    (float) -179.9, (float) -1.5);
            p.teleport(loc);
            e.setCancelled(true);
        } else if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().getLocation().getY() < 0) {
            Location loc = new Location(plugin.getServer().getWorld("Hub"),
                    plugin.getServer().getWorld("Hub").getSpawnLocation().getX(),
                    plugin.getServer().getWorld("Hub").getSpawnLocation().getY(),
                    plugin.getServer().getWorld("Hub").getSpawnLocation().getZ(),
                    (float) -179.9, (float) -1.5);
            e.getPlayer().teleport(loc);
        }
    }
}
