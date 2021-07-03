package com.redblock6.hub.mccore.functions;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AchievementLibrary {

    public static void openMenu(Player p) {
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 100, 1);
        p.sendTitle(CreateGameMenu.translate("&4&lCOMING SOON"), ChatColor.WHITE + "This feature is coming soon!", 5, 10, 5);
    }
}
