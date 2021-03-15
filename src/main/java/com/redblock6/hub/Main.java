package com.redblock6.hub;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.redblock6.hub.mccore.events.JoinLeaveEvent;
import com.redblock6.hub.mccore.functions.Holograms;
import de.slikey.effectlib.EffectManager;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main extends JavaPlugin {

    private static Main instance;
    public static JedisPool pool;
    public EffectManager em = new EffectManager(this);;

    @Override
    public void onEnable() {
        // This always goes at the top, or else die
        instance = this;

        Register.registerEvents();

        pool = new JedisPool("192.168.1.242", Integer.parseInt("6379"));
        loadConfigs();

        //set this hub's status to online
        Jedis j = pool.getResource();
        j.set("HUB-" + this.getConfig().get("hub-identifier") + "Status", "ONLINE");
        j.close();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Holograms.removeGameHolograms();

        new BukkitRunnable() {
            @Override
            public void run() {
                Location loc = new Location(getServer().getWorld("Hub"), (1371 + 0.5), (79 + 0.3), (-88 + 0.5));
                Holograms.createGameHologram(loc, "KITPVP");
                loc = new Location(getServer().getWorld("Hub"), (1374 + 0.5), (79 + 0.3), (-89 + 0.5));
                Holograms.createGameHologram(loc, "DR");
                loc = new Location(getServer().getWorld("Hub"), (1377 + 0.5), (79 + 0.3), (-90 + 0.5));
                Holograms.createGameHologram(loc, "OITQ");
                loc = new Location(getServer().getWorld("Hub"), (1381 + 0.5), (79 + 0.3), (-90 + 0.5));
                Holograms.createGameHologram(loc, "PKR");
            }
        }.runTaskTimer(this, 20, 3600);

        new BukkitRunnable() {
            @Override
            public void run() {
                Holograms.removeGameHolograms();
            }
        }.runTaskTimer(this, 10, 3600);
    }

    @Override
    public void onDisable() {
        //set this hub's status to offline
        Jedis j = pool.getResource();
        j.set("HUB-" + this.getConfig().get("hub-identifier") + "Status", "OFFLINE");
        j.close();

        //destroy the jedis pool
        pool.destroy();

        em.dispose();

        JoinLeaveEvent.holograms.forEach(Hologram::delete);
        JoinLeaveEvent.holograms.clear();

        JoinLeaveEvent.playerHologram.clear();
        JoinLeaveEvent.playerTutorialNPC.clear();

        JoinLeaveEvent.playerEffect.clear();

    }

    public static Main getInstance() { return instance;}

    public void loadConfigs() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
