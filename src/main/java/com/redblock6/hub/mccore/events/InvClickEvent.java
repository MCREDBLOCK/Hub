package com.redblock6.hub.mccore.events;

import com.redblock6.hub.mccore.functions.CreateGameMenu;
import com.redblock6.hub.mccore.functions.ServerConnector;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InvClickEvent implements Listener {
    @EventHandler
    public void invClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();

        if (e.getView().getTitle().equals("Game Menu")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP"))) {
                CreateGameMenu.newInventory(p, "KitPvP");
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK"))) {
                CreateGameMenu.newInventory(p, "GameMenu");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lDEATH RUN"))) {
                CreateGameMenu.newInventory(p, "DR");
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lONE IN THE QUIVER"))) {
                CreateGameMenu.newInventory(p, "OITQ");
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lPARKOUR RUN"))) {
                CreateGameMenu.newInventory(p, "PKR");
            }
        } else if (e.getView().getTitle().equals("Select a KITPVP game")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&2&lKITPVP-1"))) {
                ServerConnector.sendServer(p, "KITPVP-1");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP-1"))) {
                CreateGameMenu.newInventory(p, "KitPvP");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lKITPVP-2"))) {
                ServerConnector.sendServer(p, "KITPVP-2");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP-2"))) {
                CreateGameMenu.newInventory(p, "KitPvP");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lKITPVP-3"))) {
                ServerConnector.sendServer(p, "KITPVP-3");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP-3"))) {
                CreateGameMenu.newInventory(p, "KitPvP");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lKITPVP-4"))) {
                ServerConnector.sendServer(p, "KITPVP-4");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lKITPVP-4"))) {
                CreateGameMenu.newInventory(p, "KitPvP");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK"))) {
                CreateGameMenu.newInventory(p, "GameMenu");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equals("Select a DR game")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&2&lDR-1"))) {
                ServerConnector.sendServer(p, "DR-1");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lDR-1"))) {
                CreateGameMenu.newInventory(p, "DR");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lDR-2"))) {
                ServerConnector.sendServer(p, "DR-2");
                e.setCancelled(true);
            }else if (item.getItemMeta().getDisplayName().equals(format("&4&lDR-2"))) {
                CreateGameMenu.newInventory(p, "DR");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lDR-3"))) {
                ServerConnector.sendServer(p, "DR-3");
                e.setCancelled(true);
            }else if (item.getItemMeta().getDisplayName().equals(format("&4&lDR-3"))) {
                CreateGameMenu.newInventory(p, "DR");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lDR-4"))) {
                ServerConnector.sendServer(p, "DR-4");
                e.setCancelled(true);
            }else if (item.getItemMeta().getDisplayName().equals(format("&4&lDR-4"))) {
                CreateGameMenu.newInventory(p, "DR");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK"))) {
                CreateGameMenu.newInventory(p, "GameMenu");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equals("Select a OITQ game")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&2&lOITQ-1"))) {
                ServerConnector.sendServer(p, "OITQ-1");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lOITQ-1"))) {
                CreateGameMenu.newInventory(p, "OITQ");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lOITQ-2"))) {
                ServerConnector.sendServer(p, "OITQ-2");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lOITQ-2"))) {
                CreateGameMenu.newInventory(p, "OITQ");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lOITQ-3"))) {
                ServerConnector.sendServer(p, "OITQ-3");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lOITQ-3"))) {
                CreateGameMenu.newInventory(p, "OITQ");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lOITQ-4"))) {
                ServerConnector.sendServer(p, "OITQ-4");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lOITQ-4"))) {
                CreateGameMenu.newInventory(p, "OITQ");
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK"))) {
                CreateGameMenu.newInventory(p, "GameMenu");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equals("Select a PKR game")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&4&lPKR-1"))) {
                ServerConnector.sendServer(p, "PKR-1");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lPKR-2"))) {
                ServerConnector.sendServer(p, "PKR-2");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lPKR-3"))) {
                ServerConnector.sendServer(p, "PKR-3");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lPKR-4"))) {
                ServerConnector.sendServer(p, "PKR-4");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lBACK"))) {
                CreateGameMenu.newInventory(p, "GameMenu");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            }
        } else if (e.getView().getTitle().equals("Select a HUB")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(format("&4&lHUB-1"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline!"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lHUB-1"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 1);
                p.closeInventory();
                ServerConnector.sendServer(p, "HUB-1");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lHUB-2"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline!"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lHUB-2"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 1);
                p.closeInventory();
                ServerConnector.sendServer(p, "HUB-2");
                e.setCancelled(true);
            } if (item.getItemMeta().getDisplayName().equals(format("&4&lHUB-3"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline!"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lHUB-3"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 1);
                p.closeInventory();
                ServerConnector.sendServer(p, "HUB-3");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lHUB-4"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
                p.sendMessage(CreateGameMenu.translate("&4&l> &fThis server is offline!"));
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&2&lHUB-4"))) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 1);
                p.closeInventory();
                ServerConnector.sendServer(p, "HUB-4");
                e.setCancelled(true);
            } else if (item.getItemMeta().getDisplayName().equals(format("&4&lCLOSE"))) {
                p.closeInventory();
                e.setCancelled(true);
            }
        }
    }

    public String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
