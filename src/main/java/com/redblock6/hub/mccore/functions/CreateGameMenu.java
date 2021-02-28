package com.redblock6.hub.mccore.functions;

import com.redblock6.hub.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import redis.clients.jedis.Jedis;

import java.util.Arrays;

import static com.redblock6.hub.Main.pool;

public class CreateGameMenu implements Listener {
    private static Plugin plugin = Main.getPlugin(Main.class);

    public void newGameMenu(Player p) {
        Inventory i = plugin.getServer().createInventory(null, 6, "Game Menu");
        p.openInventory(i);
	}

    protected static ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }


    /*
    public static ItemStack getGameMenuItem(final Material material, final String name) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        NBTItem nbti = new NBTItem(item);
        nbti.setString("item", "gameMenu");
        nbti.applyNBT(item);
        }

        */
		
    public static void newInventory(Player p, String type) {
        if (type.equals("GameMenu")) {
            Inventory i = plugin.getServer().createInventory(null, 27, "Game Menu");

            i.setItem(10, createGuiItem(Material.IRON_SWORD, ChatColor.translateAlternateColorCodes('&', "&4&lKITPVP"), translate("&4&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&c%{players.kitpvp}% &4&lPLAYERS"), translate("&4&m-----------------------")));
            i.setItem(12, createGuiItem(Material.BOW, ChatColor.translateAlternateColorCodes('&', "&4&lONE IN THE QUIVER"), translate("&4&m-----------------------"), translate("&fFight against other"), translate("&fplayers. There are 4 kits,"), translate("&ffighter, archer, default, jumper, and tank."), translate("&fThe first person to get"), translate("&fto &4&l20 kills &fwins!"), "", translate("&c%{players.oitq1}% &4&lPLAYERS"), translate("&4&m-----------------------")));
            i.setItem(11, createGuiItem(Material.LEATHER_BOOTS, ChatColor.translateAlternateColorCodes('&', "&4&lDEATH RUN"), translate("&4&m-----------------------"), translate("&fTry to avoid traps"), translate("&fset off by a randomly"), translate("&fselected death."), "", translate("&c%{_dr}% &4&lPLAYERS"), translate("&4&m-----------------------")));
            i.setItem(15, createGuiItem(Material.WOODEN_SWORD, ChatColor.translateAlternateColorCodes('&', "&4&lMOB WARS"), translate("&4&m-----------------------"), translate("&fTry to kill all mobs. If you do so, your team"), translate("&fwins the game"), "", translate("&c%{players.mw}% &4&lPLAYERS"), translate("&4&m-----------------------")));
            i.setItem(22, createGuiItem(Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&4&lCLOSE"), (String) null));
            i.setItem(14, createGuiItem(Material.GOLDEN_BOOTS, ChatColor.translateAlternateColorCodes('&', "&4&lPARKOUR RUN"), translate("&4&m-----------------------"), translate("&fTry to be the first to the end."), translate("&fIf you don't get to the end &cin time&f,"), translate("&fyou lose! Only &c3 players &fcan win!"), "", translate("&c%{players.pk1}% &4&lPLAYERS"), translate("&4&m-----------------------")));

            p.openInventory(i);
            //p.setArrowsInBody(0);
        } else if (type.equals("KitPvP")) {
            Jedis j = pool.getResource();
            Inventory i = plugin.getServer().createInventory(null, 27, "Select a game");

            if (j.get("KITPVP-1").equals("OFFLINE")) {
                i.setItem(10, createGuiItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&4&lKITPVP-1"), translate("&4&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&4&lOFFLINE"), translate("&4&m-----------------------")));
            } else {
                i.setItem(10, createGuiItem(Material.IRON_SWORD, ChatColor.translateAlternateColorCodes('&', "&2&lKITPVP-1"), translate("&2&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&a" + j.get("KITPVP-1Online") + "&2&lPLAYERS"), translate("&2&m-----------------------")));
            }
            if (j.get("KITPVP-2Online") != null) {
                if (j.get("KITPVP-2Online").equals("OFFLINE")) {
                    i.setItem(11, createGuiItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&4&lKITPVP-2"), translate("&4&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&c%{players.kitpvp}% &4&lPLAYERS"), translate("&4&m-----------------------")));
                } else {
                    i.setItem(11, createGuiItem(Material.IRON_SWORD, ChatColor.translateAlternateColorCodes('&', "&2&lKITPVP-2"), translate("&2&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&a" + j.get("KITPVP-2Online") + "&2&lPLAYERS"), translate("&2&m-----------------------")));
                }
            }
            if (j.get("KITPVP-3Online") != null) {
                if (j.get("KITPVP-3Online").equals("OFFLINE")) {
                    i.setItem(12, createGuiItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&4&lKITPVP-3"), translate("&4&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&4&lOFFLINE"), translate("&4&m-----------------------")));
                } else {
                    i.setItem(12, createGuiItem(Material.IRON_SWORD, ChatColor.translateAlternateColorCodes('&', "&2&lKITPVP-3"), translate("&2&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&a" + j.get("KITPVP-3Online") + "&2&lPLAYERS"), translate("&2&m-----------------------")));
                }
            }
            if (j.get("KITPVP-4Online") != null) {
                if (j.get("KITPVP-4Online").equals("OFFLINE")) {
                    i.setItem(13, createGuiItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&4&lKITPVP-4"), translate("&4&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&4&lOFFLINE"), translate("&4&m-----------------------")));
                } else {
                    i.setItem(13, createGuiItem(Material.IRON_SWORD, ChatColor.translateAlternateColorCodes('&', "&2&lKITPVP-4"), translate("&2&m-----------------------"), translate("&fFight against other"), translate("&fplayers with unique"), translate("&fkits and abilities."), "", translate("&a" + j.get("KITPVP-4Online") + " &2&lPLAYERS"), translate("&2&m-----------------------")));
                }
            }
            i.setItem(22, createGuiItem(Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&4&lCLOSE"), (String) null));
            i.setItem(21, createGuiItem(Material.ARROW, ChatColor.translateAlternateColorCodes('&', "&4&lBACK"), (String) null));

            p.openInventory(i);
        }
    }

    public static String translate(String msg) {
        String newmsg = ChatColor.translateAlternateColorCodes('&', msg);

        return newmsg;
    }
}
