package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static com.redblock6.hub.Main.pool;

public class ServerConnector {
    private static final Main plugin = Main.getInstance();
    private static String server1;
    private static String server2;
    private static String server3;
    private static String server4;

    public static void sendServer(Player p, String server) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             DataOutputStream doutputStream = new DataOutputStream(outputStream)) {
            doutputStream.writeUTF("Connect");
            doutputStream.writeUTF(server);
            p.sendPluginMessage(plugin, "BungeeCord", outputStream.toByteArray());
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 1);
            p.sendTitle(CreateGameMenu.translate("&2&lSENDING YOU TO " + server), CreateGameMenu.translate("&fWe're sending you to &a" + server.toUpperCase() + " &fas fast as possible."), 5, 80, 5);
        } catch (IOException e) {
            e.printStackTrace();
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
            p.sendTitle(CreateGameMenu.translate("&4&lERROR"), CreateGameMenu.translate("&fWe couldn't transfer you to &c" + server + " &fplease contact &cRedblock6#6091 &fon discord."), 5, 80, 5);
        }
    }

    public static String getPlayerCount(String server) {
        Jedis j = pool.getResource();
        if (j.get(server + "-1Count") != null) {
            server1 = j.get(server + "-1Count");
        } else {
            server1 = String.valueOf(0);
        }
        if (j.get(server + "-2Count") != null) {
            server2 = j.get(server + "-2Count");
        } else {
            server2 = String.valueOf(0);
        }
        if (j.get(server + "-3Count") != null) {
            server3 = j.get(server + "-3Count");
        } else {
            server3 = String.valueOf(0);
        }
        if (j.get(server + "-4Count") != null) {
            server4 = j.get(server + "-4Count");
        } else {
            server4 = String.valueOf(0);
        }
        j.close();

        return String.valueOf((Integer.parseInt(server1) + Integer.parseInt(server2)) + (Integer.parseInt(server3) + Integer.parseInt(server4)));
    }
}
