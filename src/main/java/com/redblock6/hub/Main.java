package com.redblock6.hub;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

public class Main extends JavaPlugin {

    private static Main instance;
    public static JedisPool pool;
    public static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        // This always goes at the top, or
        instance = this;

        Register.registerEvents();
        pool = new JedisPool("192.168.1.242", Integer.parseInt("6379"));
        loadConfigs();

        //get protocol lib
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onDisable() {
        //close the thingy wingy thing thing jedis
        pool.destroy();
    }

    public static Main getInstance() { return instance;}

    public void loadConfigs() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
