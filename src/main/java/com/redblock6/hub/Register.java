package com.redblock6.hub;

import com.redblock6.hub.mccore.events.JoinLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Register {

    private static Main pl = Main.getInstance();

    public static void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        // Register events
        pm.registerEvents(new JoinLeaveEvent(), pl);
    }
}
