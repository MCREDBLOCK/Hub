package com.redblock6.hub.mccore.commands;

import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.events.JoinLeaveEvent;
import com.redblock6.hub.mccore.functions.CreateGameMenu;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class StopCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
            if (command.getName().equalsIgnoreCase("stop")) {
                if (!sender.hasPermission("redblock.stopthedangserver")) {
                    sender.sendMessage(CreateGameMenu.translate("&4&l> &fYou don't have permission to do this."));

                    return true;
                }

                JoinLeaveEvent.npcArrayList.forEach(NPC::despawn);
                JoinLeaveEvent.npcArrayList.forEach(NPC::destroy);
                for (NPC npc : JoinLeaveEvent.npcArrayList) {
                    CitizensAPI.getNPCRegistry().deregister(npc);
                }

                JoinLeaveEvent.npcArrayList.clear();
                JoinLeaveEvent.playerTutorialNPC.clear();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.shutdown();
                    }
                }.runTaskLater(Main.getInstance(), 5);
            }
        return false;
    }
}
