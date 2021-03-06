package com.redblock6.hub;

import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main extends JavaPlugin {

    private static Main instance;
    public static JedisPool pool;
    /*
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection maincollection;
     */

    @Override
    public void onEnable() {
        // This always goes at the top, or
        instance = this;

        Register.registerEvents();
        pool = new JedisPool("192.168.1.242", Integer.parseInt("6379"));
        loadConfigs();

        //mongo db
        /* try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Bukkit.getServer().getLogger().info("> Failed to connect to MongoDB");
        } finally {
            Bukkit.getServer().getLogger().info("> Connected to MongoDB, getting databases and stuff");
            database = mongoClient.getDB("MC_USERS");
            maincollection = database.getCollection("STATS");
        } */

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

        //close mongodb
        // mongoClient.close();
    }

    public static Main getInstance() { return instance;}

    public void loadConfigs() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
