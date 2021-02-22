package com.redblock6.hub;

import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

public class Main extends JavaPlugin {

    private static Main instance;
    // public static JedisPool pool;

    @Override
    public void onEnable() {
        // This always goes at the top, or
        instance = this;

        Register.registerEvents();

        //do the thingy thingy with the jedis thingy ;)
        // pool = new JedisPool("192.168.1.242", 6379);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //close the thingy wingy thing thing jedis
        // pool.close();
    }
    public static Main getInstance() { return instance;}
}
