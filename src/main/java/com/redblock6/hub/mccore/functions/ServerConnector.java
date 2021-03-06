package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream doutputStream = new DataOutputStream(outputStream);
        p.sendTitle(CreateGameMenu.translate("&2&lSENDING YOU TO " + server), CreateGameMenu.translate("&fWe're sending you to &a" + server.toUpperCase() + " &fas fast as possible."), 5, 20, 5);
        try {
            doutputStream.writeUTF("Connect");
            doutputStream.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
            p.sendTitle(CreateGameMenu.translate("&4&lERROR"), CreateGameMenu.translate("&fWe couldn't transfer you to &c" + server + " &fplease contact &cRedblock6#6091 &fon discord."), 5, 20, 5);
        }
        p.sendPluginMessage(plugin, "Bungeecord", outputStream.toByteArray());
    }

    public static String getPlayerCount(String server) {
        Jedis j = pool.getResource();
        if (j.get(server + "-1Online") != null) {
            server1 = j.get(server + "-1Online");
        } else {
            server1 = String.valueOf(0);
        }
        if (j.get(server + "-2Online") != null) {
            server2 = j.get(server + "-2Online");
        } else {
            server2 = String.valueOf(0);
        }
        if (j.get(server + "-3Online") != null) {
            server3 = j.get(server + "-3Online");
        } else {
            server3 = String.valueOf(0);
        }
        if (j.get(server + "-4Online") != null) {
            server4 = j.get(server + "-4Online");
        } else {
            server4 = String.valueOf(0);
        }
        j.close();

        return String.valueOf((Integer.parseInt(server1) + Integer.parseInt(server2)) + (Integer.parseInt(server3) + Integer.parseInt(server4)));
    }
}
