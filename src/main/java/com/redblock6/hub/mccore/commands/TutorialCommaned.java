package com.redblock6.hub.mccore.commands;

import com.redblock6.hub.mccore.functions.TourGuide;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class TutorialCommaned implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("tutorial")) {
                TourGuide.startTutorial((Player) sender);
            }
        } else {
            sender.sendMessage("no");
            return true;
        }
        return false;
    }
}
