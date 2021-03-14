package com.redblock6.hub;

import com.redblock6.hub.mccore.commands.GameMenuCommand;
import com.redblock6.hub.mccore.commands.Gamemode;
import com.redblock6.hub.mccore.commands.StopCommand;
import com.redblock6.hub.mccore.commands.TutorialCommaned;
import com.redblock6.hub.mccore.events.*;
import com.redblock6.hub.mccore.functions.Tutorial;
import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import redis.clients.jedis.JedisPool;

public class Register {

    private static final Main pl = Main.getInstance();
    private static JedisPool jedisPool;
    public static EffectManager em = new EffectManager(pl);

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
        pl.getCommand("stop").setExecutor(new StopCommand());
    }

    public static EffectManager getEffectManager() {
        return em;
    }
}
