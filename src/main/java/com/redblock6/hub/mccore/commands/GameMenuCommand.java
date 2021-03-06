package com.redblock6.hub.mccore.commands;

import com.redblock6.hub.mccore.functions.CreateGameMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class GameMenuCommand implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemenu")) {
            if (sender instanceof Player) {
                //open the gamemenu
                CreateGameMenu.newInventory((Player) sender, "GameMenu");

            } else {
                sender.sendMessage("&4&l> &fImagine trying to open the gamemenu from console &cbruh");
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("kitpvp")) {
            if (sender instanceof Player) {
                CreateGameMenu.newInventory((Player) sender, "KitPvP");
            } else {
                sender.sendMessage("&4&l> &fImagine trying to open the KitPvP menu from the console");
            }
        } else if (cmd.getName().equalsIgnoreCase("deathrun")) {
            if (sender instanceof Player) {
                CreateGameMenu.newInventory((Player) sender, "DR");
            } else {
                sender.sendMessage("no");
            }
        } else if (cmd.getName().equalsIgnoreCase("oitq")) {
            if (sender instanceof Player) {
                CreateGameMenu.newInventory((Player) sender, "OITQ");
            } else {
                sender.sendMessage("bruh");
            }
        } else if (cmd.getName().equalsIgnoreCase("pkrun")) {
            if (sender instanceof Player) {
                CreateGameMenu.newInventory((Player) sender, "PKR");
            } else {
                sender.sendMessage("uowiquygeouywqgoey");
            }
        }
        return false;
    }
}
