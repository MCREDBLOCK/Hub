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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l> &fYou opened the &cGame Menu"));
                //open the gamemenu
                CreateGameMenu.newInventory((Player) sender, "GameMenu");

            } else {
                sender.sendMessage("&4&l> &fImagine trying to open the gamemenu from console &cbruh");
                return true;
            }
        }
        return false;
    }
}
