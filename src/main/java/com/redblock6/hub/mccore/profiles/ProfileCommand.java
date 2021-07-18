package com.redblock6.hub.mccore.profiles;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class ProfileCommand implements Listener, CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("profile")) {
            Profiles.profileMenu(((Player) sender).getPlayer());
        }
        return false;
    }
}
