package com.redblock6.hub.mccore.events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.redblock6.hub.mccore.functions.Parkour;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class JumpEvent implements Listener {
    @EventHandler
    public void onFlightToggle(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        Parkour park = Parkour.getParkourStatus(player);

        if (!park.inParkour()) {
            e.setCancelled(true);
            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
                return;
            }

            Location loc = new Location(player.getWorld(),
                    player.getLocation().getX(),
                    player.getLocation().getY() - 1,
                    player.getLocation().getZ(),
                    (float) -179.9, (float) -1.5);

            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 100, 1);
            player.spawnParticle(Particle.ASH, player.getLocation(), 10);

            Vector dir = player.getLocation().getDirection();
            player.setVelocity(dir.multiply(3));
            //player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent e) {
        Player p = e.getPlayer();
        Parkour park = Parkour.getParkourStatus(p);

        if (!park.inParkour()) {
            if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
                return;
            }

            p.setAllowFlight(true);
            // p.setFlying(true);
        }
    }
}