package com.redblock6.hub;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        // This always goes at the top, or
        instance = this;

        Register.registerEvents();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static Main getInstance() { return instance;}
}
