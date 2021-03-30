package com.redblock6.hub.mccore.events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.functions.CreateGameMenu;
import com.redblock6.hub.mccore.functions.MySQLSetterGetter;
import com.redblock6.hub.mccore.functions.Parkour;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

import static com.redblock6.hub.Main.pool;

public class Regions implements Listener {
    private final Main plugin = Main.getInstance();
    public static HashMap<Player, Hologram> statuses = new HashMap<>();
    private static final MySQLSetterGetter mysql = new MySQLSetterGetter();

    private static Hologram holo;

    @EventHandler
    public void tutorialRegionEnter(RegionEnteredEvent e) {
        if (e.getPlayer() == null) {
            return;
        }

        Player p = e.getPlayer();

        if (mysql.getTutorial(p.getUniqueId()).equals("Incomplete") && e.getRegionName().equalsIgnoreCase("tutorial")) {
            Location loc = new Location(Bukkit.getWorld("Hub"), (1384 + 0.5), 71, (-58 + 0.5));

            holo = HologramsAPI.createHologram(plugin, loc);
            statuses.put(p, holo);
            VisibilityManager visibilityManager = holo.getVisibilityManager();

            visibilityManager.showTo(p);
            visibilityManager.setVisibleByDefault(false);

            holo.appendTextLine(CreateGameMenu.translate("&4&l✖ &fTutorial Incomplete"));

            ItemStack item = new ItemStack(Material.RED_WOOL, 1);
            holo.appendItemLine(item);

            new BukkitRunnable(){
                final Location loc = new Location(Bukkit.getWorld("Hub"), (1384 + 0.5), 76, (-58 + 0.5));

                @Override
                public void run() {
                    holo.teleport(loc);
                }
            }.runTaskLater(plugin, 5);
        }
    }

    @EventHandler
    public void tutorialRegionLeave(RegionLeftEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        if (statuses.get(e.getPlayer()) == null) {
            return;
        }

        Player p = e.getPlayer();
        if (mysql.getTutorial(p.getUniqueId()).equals("Incomplete") && e.getRegionName().equalsIgnoreCase("tutorial")) {
            Location loc = new Location(Bukkit.getWorld("Hub"), (1384 + 0.5), 71, (-58 + 0.5));
            Hologram newholo = statuses.get(p);
            newholo.teleport(loc);

            new BukkitRunnable() {
                @Override
                public void run() {
                    newholo.delete();
                }
            }.runTaskLater(plugin, 5);
        }
    }

    @EventHandler
    public void regionLeave(RegionLeftEvent e) {
        Player p = e.getPlayer();
        Parkour park = Parkour.getParkourStatus(p);
        String regionname = e.getRegionName();

        if (regionname.equalsIgnoreCase("parkour")) {
            if (park.inParkour()) {
                if (!(e.getPlayer().getLocation().getY() < 1)) {
                    park.exitParkour();
                }
            }
        }
    }

    @EventHandler
    public void regionEnter(RegionEnteredEvent e) {
        Player p = e.getPlayer();
        Parkour park = Parkour.getParkourStatus(p);
        String regionname = e.getRegionName();

        if (regionname.equalsIgnoreCase("parkourend")) {
            if (park.inParkour()) {
                Location loc2 = new Location(plugin.getServer().getWorld("Hub"), 1379, 74, -42);
                e.getPlayer().teleport(loc2);
            }
        }
    }
}
