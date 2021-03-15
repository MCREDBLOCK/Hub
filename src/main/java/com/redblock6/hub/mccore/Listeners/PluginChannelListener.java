package com.redblock6.hub.mccore.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PluginChannelListener implements PluginMessageListener {
    private static HashMap<Player, Object> obj = new HashMap<Player, Object>();

    @Override
    public synchronized void onPluginMessageReceived(@NotNull String s, @NotNull Player player, @NotNull byte[] bytes) {

    }

    public synchronized Object get(Player p, boolean integer) {  // here you can add parameters (e.g. String table, String column, ...)
        return null;
    }

}
