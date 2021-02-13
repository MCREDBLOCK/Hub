package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class CreateGameMenu implements Listener {
    private Plugin plugin = Main.getPlugin(Main.class);

    public void newGameMenu(Player p) {
        Inventory i = plugin.getServer().createInventory(null, 6, "Game Menu");
        p.openInventory(i);


    }
}
