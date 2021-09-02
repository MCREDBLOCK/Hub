package com.redblock6.hub;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.redblock6.hub.mccore.bot.BotMain;
import com.redblock6.hub.mccore.commands.WarnReboot;
import com.redblock6.hub.mccore.events.JoinLeaveEvent;
import com.redblock6.hub.mccore.functions.CreateGameMenu;
import com.redblock6.hub.mccore.functions.Holograms;
import de.slikey.effectlib.EffectManager;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends JavaPlugin {

    private static Main instance;
    public static JedisPool pool;
    private Connection connection;
    // private static HikariDataSource ds;
    public String host, database, username, password, global_table, hub_table, kitpvp_table, oitq_table;
    public int port;
    public EffectManager em = new EffectManager(this);
    public static BotMain bot;
    public static Guild rygb;

    /*
    public void hikariSetup() {
        host = "192.168.1.223";
        port = 3306;
        username = "mc";
        database = "mc_user";
        global_table = "GLOBAL";
        hub_table = "HUB";
        kitpvp_table = "KITPVP";
        oitq_table = "OITQ";
        password = "minecraft";
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.addDataSourceProperty("autoReconnect", true);
        ds = new HikariDataSource(config);
    }

     */

    public void mysqlSetup() {
        host = "192.168.1.223";
        port = 3306;
        username = "mc";
        database = "mc_user";
        global_table = "GLOBAL";
        hub_table = "HUB";
        kitpvp_table = "KITPVP";
        oitq_table = "OITQ";
        password = "minecraft";

        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password));
            Bukkit.getConsoleSender().sendMessage(CreateGameMenu.translate("&2&l> &fConnected to &aMySQL &fusing user: &a" + this.username));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onEnable() {
        // This always goes at the top, or else die
        instance = this;

        Register.registerEvents();

        pool = new JedisPool("192.168.1.222", Integer.parseInt("6379"));
        loadConfigs();
        bot = new BotMain(this);
        rygb = getBot().bot.getGuildById("614942507452596240");
        mysqlSetup();

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
        }.runTaskTimer(this, 20, 600);

        new BukkitRunnable() {
            @Override
            public void run() {
                Holograms.removeGameHolograms();
            }
        }.runTaskTimer(this, 10, 600);

        new BukkitRunnable() {
            @Override
            public void run() {
                WarnReboot.startCountdown(120);
            }
        }.runTaskLater(this, 20);
        
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
    public static BotMain getBot() {
        return bot;
    }

    public void loadConfigs() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
