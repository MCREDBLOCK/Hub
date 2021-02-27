package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Locale;

public class ServerConnector {
    private static final Main plugin = Main.getInstance();

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
}
