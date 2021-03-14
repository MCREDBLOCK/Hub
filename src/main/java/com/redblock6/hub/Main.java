package com.redblock6.hub;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.redblock6.hub.mccore.events.JoinLeaveEvent;
import de.slikey.effectlib.EffectManager;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;

public class Main extends JavaPlugin {

    private static Main instance;
    public static JedisPool pool;
    public EffectManager em = new EffectManager(this);

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
