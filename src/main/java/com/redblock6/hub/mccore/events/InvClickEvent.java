package com.redblock6.hub.mccore.events;

import com.redblock6.hub.mccore.functions.CreateGameMenu;
import com.redblock6.hub.mccore.functions.ServerConnector;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvClickEvent implements Listener {
    @EventHandler
    public void invClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ClickType click = e.getClick();
        Inventory open = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();

        if (e.getView().getTitle().equals("Game Menu")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP"))) {
                CreateGameMenu.newInventory(p, "KitPvP");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK"))) {
                CreateGameMenu.newInventory(p, "GameMenu");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP-1"))) {
                ServerConnector.sendServer(p, "KITPVP-1");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP-2"))) {
                ServerConnector.sendServer(p, "KITPVP-2");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP-3"))) {
                ServerConnector.sendServer(p, "KITPVP-3");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP-4"))) {
                ServerConnector.sendServer(p, "KITPVP-4");
                e.setCancelled(true);
            }

        }
    }

    public String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
