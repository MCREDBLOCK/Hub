package com.redblock6.hub.mccore.events;

import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.functions.CreateScoreboard;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JoinLeaveEvent implements Listener {
    private static final Main plugin = Main.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        //create the gamemenu itme
        String player = p.getDisplayName();
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lGAME MENU"));

        //get the players inv & give them the gamemenu
        Inventory inv = p.getInventory();
        inv.setItem(4, item);
        e.setJoinMessage(null);

        //create a scoreboard for the player
        CreateScoreboard.setScoreboard(p, "Normal", true);

        //get a world and teleport the player to it
        Location loc = new Location(plugin.getServer().getWorld("Hub"), plugin.getServer().getWorld("Hub").getSpawnLocation().getX(), plugin.getServer().getWorld("Hub").getSpawnLocation().getY(), plugin.getServer().getWorld("Hub").getSpawnLocation().getZ());
        p.teleport(loc);

        //play a sound
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);

        //send the player a title
        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4&lHUB"), ChatColor.translateAlternateColorCodes('&', "&fmc.redblock6.com"), 5, 20, 5);

        //set message strings
        String line = ChatColor.translateAlternateColorCodes('&', "&4&m---------------------------------");
        String welcome = ChatColor.translateAlternateColorCodes('&', "&fWelcome to &4&lMCREDBLOCK &c&lJAVA");
        String blank = "";
        String name = ChatColor.translateAlternateColorCodes('&', "&4&lNAME &c" + player);
        String store = ChatColor.translateAlternateColorCodes('&', "&4&lSOTRE &chttps://store.redblock6.com");
        String forums = ChatColor.translateAlternateColorCodes('&', "&4&lFORUMS &chttps://forums.redblock6.com");
        String discord = ChatColor.translateAlternateColorCodes('&', "&4&lDISCORD &chttps://discord.com/invite/wcdMgBBhWy");
        String ip = ChatColor.translateAlternateColorCodes('&', "&4&lmc.redblock6.com");

        //send the messages
        p.sendMessage(line);
        p.sendMessage(welcome);
        p.sendMessage(blank);
        p.sendMessage(name);
        p.sendMessage(store);
        p.sendMessage(forums);
        p.sendMessage(discord);
        p.sendMessage(blank);
        p.sendMessage(ip);
        p.sendMessage(line);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }
}
