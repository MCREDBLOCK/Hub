package com.redblock6.hub;

import com.redblock6.hub.mccore.commands.GameMenuCommand;
import com.redblock6.hub.mccore.commands.Gamemode;
import com.redblock6.hub.mccore.commands.TutorialCommaned;
import com.redblock6.hub.mccore.events.*;
import com.redblock6.hub.mccore.functions.Tutorial;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import redis.clients.jedis.JedisPool;

public class Register {

    private static final Main pl = Main.getInstance();
    private static JedisPool jedisPool;

    public static void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        // Register events
        pm.registerEvents(new JoinLeaveEvent(), pl);
        pm.registerEvents(new InteractEvent(), pl);
        pm.registerEvents(new DeathDamageEvent(), pl);
        pm.registerEvents(new JumpEvent(), pl);
        pm.registerEvents(new InvClickEvent(), pl);
        pm.registerEvents(new MoveEvent(), pl);
        pm.registerEvents(new Regions(), pl);

        // Register commands
        pl.getCommand("gamemenu").setExecutor(new GameMenuCommand());
        pl.getCommand("gmc").setExecutor(new Gamemode());
        pl.getCommand("gma").setExecutor(new Gamemode());
        pl.getCommand("gms").setExecutor(new Gamemode());
        pl.getCommand("gmsp").setExecutor(new Gamemode());
        pl.getCommand("kitpvp").setExecutor(new GameMenuCommand());
        pl.getCommand("pkrun").setExecutor(new GameMenuCommand());
        pl.getCommand("oitq").setExecutor(new GameMenuCommand());
        pl.getCommand("deathrun").setExecutor(new GameMenuCommand());
        pl.getCommand("tutorial").setExecutor(new TutorialCommaned());
    }

    public static void registerRedis() {
        jedisPool = new JedisPool("127.0.0.1", Integer.parseInt("6379"));
    }

    public static void unRegisterRedis() {
        jedisPool.getResource().close();
    }
}
