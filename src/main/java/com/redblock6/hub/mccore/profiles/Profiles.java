package com.redblock6.hub.mccore.profiles;

import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.achievements.AchDatabase;
import com.redblock6.hub.mccore.achievements.AchLibrary;
import com.redblock6.hub.mccore.achievements.HAchType;
import com.redblock6.hub.mccore.functions.MySQLSetterGetter;
import com.redblock6.hub.mccore.functions.ServerConnector;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import static com.redblock6.hub.mccore.functions.CreateGameMenu.createGuiItem;
import static com.redblock6.hub.mccore.functions.CreateGameMenu.translate;

public class Profiles implements Listener {
    private static final Main plugin = Main.getInstance();
    private static final MySQLSetterGetter mysql = new MySQLSetterGetter();

    public static void profileMenu(Player p) {
        Inventory i = plugin.getServer().createInventory(null, 27, "Your Profile");
        AchDatabase database = new AchDatabase(p);

        ItemStack playerskull = new ItemStack(Material.SKULL_ITEM,1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) playerskull.getItemMeta();

        meta.setOwningPlayer(p);

        playerskull.setItemMeta(meta);
        String prefixColor = "&7";
        String secondaryColor = "&f";
        String name = p.getDisplayName();
        if (name.startsWith("&2")) {
            prefixColor = "&2";
            secondaryColor = "&a";
        } else if (name.startsWith("&b")) {
            prefixColor = "&b";
        } else if (name.contains("&c&lYOU&f&lTUBE")) {
            prefixColor = "&c";
        } else if (name.contains("&c&lADMIN")) {
            prefixColor = "&c";
        } else if (name.contains("&9&lHELPER")) {
            prefixColor = "&9";
            secondaryColor = "&b";
        } else if (name.contains("&4&lOWNER")) {
            prefixColor = "&4";
            secondaryColor = "&c";
        }

        i.setItem(10, createGuiItem(playerskull, ChatColor.translateAlternateColorCodes('&', name), translate(prefixColor + "&m-----------------------"), translate(prefixColor + "&lLEVEL " + mysql.getLevel(p.getUniqueId())), translate(prefixColor + "╚═ " + secondaryColor + mysql.getEXP(p.getUniqueId()) + "&7/" + secondaryColor + mysql.getEXPMax(p.getUniqueId())), "", translate("&5&lMAGIC DUST &d" + mysql.getDust(p.getUniqueId())) ,  translate(prefixColor + "&m-----------------------")));
        if (database.getHubAch().contains(HAchType.Link_Your_Account_With_Core)) {
            i.setItem(14, createGuiItem(Material.EMERALD_BLOCK, ChatColor.translateAlternateColorCodes('&', "&2&lACCOUNT LINKED"), translate("&2&m-----------------------"), translate("&fYour account is linked to discord"), "",  translate("&2&lCLICK TO UNLINK"), translate("&2&m-----------------------")));
        } else if (!database.getHubAch().contains(HAchType.Link_Your_Account_With_Core)) {
            i.setItem(14, createGuiItem(Material.REDSTONE_BLOCK, ChatColor.translateAlternateColorCodes('&', "&4&lACCOUNT NOT LINKED"), translate("&4&m-----------------------"), translate("&fYour account is not linked to discord"), "",  translate("&4&lCLICK TO LINK"), translate("&4&m-----------------------")));
        }
        i.setItem(13, createGuiItem(Material.GOLD_INGOT, ChatColor.translateAlternateColorCodes('&', "&6&lACHIEVEMENTS"), translate("&6&m-----------------------"), translate("&fView your Achievements"), "",  translate("&6&lCLICK TO VIEW"), translate("&6&m-----------------------")));
        // i.setItem(15, createGuiItem(Material.WOODEN_SWORD, ChatColor.translateAlternateColorCodes('&', "&4&lMOB WARS"), translate("&4&m-----------------------"), translate("&fTry to kill all mobs. If you do so, your team"), translate("&fwins the game"), "", translate("&c%{players.mw}% &4&lPLAYERS"), translate("&4&m-----------------------")));
        i.setItem(22, createGuiItem(Material.BARRIER, ChatColor.translateAlternateColorCodes('&', "&4&lCLOSE"), (String) null));
        
        p.openInventory(i);
    }

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();

        if (e.getView().getTitle().equals("Your Profile")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }
            if (item.getItemMeta().getDisplayName().equals(translate("&2&lACCOUNT LINKED"))) {
                //p.sendTitle(translate("&4&lRUN &c/unlink &4&lIN #bot-commands"), translate("&fOr DM Core &c/unlink"), 10, 1000000000, 10);
                //p.closeInventory();
                ServerConnector.sendServer(p, "COREHUB-1");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(translate("&4&lACCOUNT NOT LINKED"))) {
                ServerConnector.sendServer(p, "COREHUB-1");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(translate("&6&lACHIEVEMENTS"))) {
                AchLibrary.achSelector(p);
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(translate("&4&lCLOSE"))) {
                p.closeInventory();
            }
        }
    }
}
