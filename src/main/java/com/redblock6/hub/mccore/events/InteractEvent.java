package com.redblock6.hub.mccore.events;

import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.functions.CreateGameMenu;
import com.redblock6.hub.mccore.functions.CreateScoreboard;
import com.redblock6.hub.mccore.functions.Parkour;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.spigotmc.event.entity.EntityDismountEvent;
import redis.clients.jedis.Jedis;

import static com.redblock6.hub.Main.pool;

public class InteractEvent implements Listener {

    private static final Main plugin = Main.getInstance();

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player p = event.getPlayer();
        try {
            if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                NBTItem nbti = new NBTItem(p.getInventory().getItemInMainHand());
                if (nbti.getString("item").equals("gameMenu")) {
                    if (!(p.getOpenInventory().getType() == InventoryType.CHEST)) {
                        CreateGameMenu.newInventory(p, "GameMenu");
                    }
                } else if (nbti.getString("item").equals("exit")) {
                    Parkour park = Parkour.getParkourStatus(p);

                    park.exitParkour();
                    Location loc = new Location(plugin.getServer().getWorld("Hub"),
                            plugin.getServer().getWorld("Hub").getSpawnLocation().getX(),
                            plugin.getServer().getWorld("Hub").getSpawnLocation().getY(),
                            plugin.getServer().getWorld("Hub").getSpawnLocation().getZ(),
                            (float) -179.9, (float) -1.5);
                    event.getPlayer().teleport(loc);
                } else if (nbti.getString("item").equals("start")) {
                    Parkour park = Parkour.getParkourStatus(p);

                    park.resetTime();
                    Location loc2 = new Location(plugin.getServer().getWorld("Hub"), 1379, 74, -42);
                    p.teleport(loc2);
                }
            }
        } catch (NullPointerException e) {
            if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getClickedBlock() != null) {
                    event.getClickedBlock().getType();
                }
            }
        }
    }
}
