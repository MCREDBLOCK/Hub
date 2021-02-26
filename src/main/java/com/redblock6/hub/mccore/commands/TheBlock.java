package com.redblock6.hub.mccore.commands;

import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.functions.GiveCoinsXP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class TheBlock implements Listener, CommandExecutor {
    private static final Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("getblock")) {
            sender.sendMessage("Attemting to give you 100 coins and exp...");
            GiveCoinsXP.GivePlayerBoth((Player) sender, 100, 100);

        }
        return false;
    }

/*
    public ItemStack getItem(Player p) {
        Inventory inv = p.getInventory();

        String player = p.getDisplayName();
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lGAME MENU"));
        item.setItemMeta(meta);

        //get the players inv & give them the gamemenu
        NBTItem nbti = new NBTItem(item);
        nbti.setString("item", "gameMenu");
        nbti.applyNBT(item);
        inv.setItem(4, item);

        return item;
    } */
}
