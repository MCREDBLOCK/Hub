package com.redblock6.hub;

import com.redblock6.hub.mccore.functions.CreateGameMenu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

public class Main extends JavaPlugin {

    private static Main instance;
    public static JedisPool pool;

    @Override
    public void onEnable() {
        // This always goes at the top, or
        instance = this;

        Register.registerEvents();

        //do the thingy thingy with the jedis thingy ;)
        try {
            pool = new JedisPool("172.0.0.1", 6379);
        } catch (Exception e) {
            Bukkit.getLogger().info(CreateGameMenu.translate("&4&l> &fFailed to connect to redis, you know what to do."));
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //close the thingy wingy thing thing jedis
        pool.close();
    }
    public static Main getInstance() { return instance;}
}
