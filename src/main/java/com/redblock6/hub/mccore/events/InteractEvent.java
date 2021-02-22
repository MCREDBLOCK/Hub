package com.redblock6.hub.mccore.events;

import com.redblock6.hub.mccore.functions.CreateGameMenu;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityDismountEvent;

public class InteractEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player p = event.getPlayer();

        try {
            if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                NBTItem nbti = new NBTItem(p.getInventory().getItemInMainHand());
                if (nbti.getString("item").equals("gameMenu")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l> &fYou opened the &cGame Menu"));
                    CreateGameMenu.newInventory(p, "GameMenu");
                }
            }
        } catch (NullPointerException e) {
            if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getClickedBlock() != null) {
                    if (event.getClickedBlock().getType().equals(Material.SPRUCE_SLAB)) {
                        Arrow arrow = Bukkit.getServer().getWorld("hub").spawnArrow(event.getClickedBlock().getLocation(), p.getLocation().getDirection(), 0, 0);
                        arrow.addPassenger(p);
                    }
                }
            }
        }
    }
}
