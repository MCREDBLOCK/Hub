package com.redblock6.hub;

import com.redblock6.hub.mccore.commands.TheBlock;
import com.redblock6.hub.mccore.events.JoinLeaveEvent;
import com.redblock6.hub.mccore.commands.GameMenuCommand;
import com.redblock6.hub.mccore.commands.Gamemode;
import com.redblock6.hub.mccore.events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Register {

    private static Main pl = Main.getInstance();

    public static void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        // Register events
        pm.registerEvents(new JoinLeaveEvent(), pl);
        pm.registerEvents(new InteractEvent(), pl);
        pm.registerEvents(new DeathDamageEvent(), pl);
        pm.registerEvents(new JumpEvent(), pl);
        pm.registerEvents(new InvClickEvent(), pl);
        pm.registerEvents(new MoveEvent(), pl);

        // Register commands
        pl.getCommand("gamemenu").setExecutor(new GameMenuCommand());
        pl.getCommand("gmc").setExecutor(new Gamemode());
        pl.getCommand("gma").setExecutor(new Gamemode());
        pl.getCommand("gms").setExecutor(new Gamemode());
        pl.getCommand("gmsp").setExecutor(new Gamemode());
        // pl.getCommand("getblock").setExecutor(new TheBlock());
    }
}
