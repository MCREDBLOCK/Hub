package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerConnector {
    private static final Main plugin = Main.getInstance();

    public static void sendServer(Player p, String server) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             DataOutputStream doutputStream = new DataOutputStream(outputStream)) {
            doutputStream.writeUTF("Connect");
            doutputStream.writeUTF(server);
            p.sendPluginMessage(plugin, "BungeeCord", outputStream.toByteArray());
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
            // p.sendTitle(CreateGameMenu.translate("&2&lSENDING YOU TO " + server), CreateGameMenu.translate("&fWe're sending you to &a" + server.toUpperCase() + " &fas fast as possible."), 5, 80, 5);
        } catch (IOException e) {
            e.printStackTrace();
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
            p.sendTitle(CreateGameMenu.translate("&4&lERROR"), CreateGameMenu.translate("&fWe couldn't transfer you to &c" + server + " &fplease contact &cRedblock6#6091 &fon discord."), 5, 80, 5);
        }
    }

    public static String getPlayerCount(String server, int id) {
        String server1 = PlaceholderAPI.setPlaceholders(null, "%bungee_" + server + "-" + id + "%");

        return String.valueOf(Integer.parseInt(server1));
    }

    public static String getPlayerCount(String server) {
        // Jedis j = pool.getResource();
        // if (j.get(server + "-1Count") != null) {
            // server1 = j.get(server + "-1Count");
        String server1 = PlaceholderAPI.setPlaceholders(null, "%bungee_" + server + "-1%");
        // } else {
            //server1 = String.valueOf(0);
        // }
        // if (j.get(server + "-2Count") != null) {
            // server2 = j.get(server + "-2Count");
        String server2 = PlaceholderAPI.setPlaceholders(null, "%bungee_" + server + "-2%");
        // } else {
            //server2 = String.valueOf(0);
        // }
        // if (j.get(server + "-3Count") != null) {
            // server3 = j.get(server + "-3Count");
        String server3 = PlaceholderAPI.setPlaceholders(null, "%bungee_" + server + "-3%");
        // } else {
            //server3 = String.valueOf(0);
        // }
        // if (j.get(server + "-4Count") != null) {
            // server4 = j.get(server + "-4Count");
        String server4 = PlaceholderAPI.setPlaceholders(null, "%bungee_" + server + "-4%");
        // } else {
            //server4 = String.valueOf(0);
        // }
        // j.close();

        return String.valueOf((Integer.parseInt(server1) + Integer.parseInt(server2)) + (Integer.parseInt(server3) + Integer.parseInt(server4)));
    }
}
