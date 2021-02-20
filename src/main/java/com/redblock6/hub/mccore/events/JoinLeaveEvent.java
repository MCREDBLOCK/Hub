package com.redblock6.hub.mccore.events;

import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.functions.Bar;
import com.redblock6.hub.mccore.functions.CreateGameMenu;
import com.redblock6.hub.mccore.functions.CreateScoreboard;
import com.redblock6.hub.mccore.functions.Parkour;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.WarpEffect;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinLeaveEvent implements Listener {
    private static final Main plugin = Main.getInstance();

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        //clear player inventory
        Inventory inv = p.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null) {
                inv.getItem(i).setAmount(0);
            }
        }
        //p.setArrowsInBody(5);

        //set their food bar
        p.setFoodLevel(20);

        // flight
        p.setAllowFlight(true);
        p.setFlying(false);

        //testing bro lololoo
        /*
        World spawningworld = p.getWorld();
        Sheep sheep = (Sheep) spawningworld.spawnEntity(p.getLocation(), EntityType.SHEEP);
        sheep.setCustomName(ChatColor.translateAlternateColorCodes('&', "&4&liuhawsoiludhdfiuehiruo"));
        sheep.setColor(DyeColor.RED);
        p.addPassenger(sheep);

        World spawningworld = p.getWorld();
        ArmorStand hologram = (ArmorStand) spawningworld.spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setInvulnerable(false);
        hologram.setBasePlate(false);
        hologram.setArms(false);
        hologram.setCustomNameVisible(true);
        hologram.setCustomName(ChatColor.translateAlternateColorCodes('&', "&4&lICE IS VERY VERY POG"));
        p.addPassenger(hologram); */

        //testing demo
        /*
        EffectManager em = new EffectManager(plugin);
        Effect effect = new WarpEffect(em);
        effect.setEntity(p);
        effect.start();

        new BukkitRunnable() {
            @Override
            public void run() {
                em.dispose();
            }
        }.runTaskLaterAsynchronously(plugin, 200); */

        //create the gamemenu itme
        String player = p.getDisplayName();
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lGAME MENU"));

        //get the players inv & give them the gamemenu
        NBTItem nbti = new NBTItem(item);
        nbti.setString("item", "gameMenu");
        nbti.applyNBT(item);
        inv.setItem(4, item);
        e.setJoinMessage(null);

        //create a scoreboard for the player
        CreateScoreboard.setScoreboard(p, "Normal", true);

        //get a world and teleport the player to it
        Location loc = new Location(plugin.getServer().getWorld("Hub"), plugin.getServer().getWorld("Hub").getSpawnLocation().getX(), plugin.getServer().getWorld("Hub").getSpawnLocation().getY(), plugin.getServer().getWorld("Hub").getSpawnLocation().getZ());
        p.teleport(loc);

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

        //check if the player has joined before
        if (!p.hasPlayedBefore()) {
            String achline = CreateGameMenu.translate("&2&m---------------------------------");
            String completed = CreateGameMenu.translate("&2&lACHEIVEMENT COMPLETED &a&lOUR ADVENTURE BEGINS");
            String coinplus = CreateGameMenu.translate("&6&l+ &e100 COINS");
            String xpplus = CreateGameMenu.translate("&2&l+ &a50 XP");
            String tutorial = CreateGameMenu.translate("&fUse the &aTutorial NPC&f, or");
            String tutorial2 = CreateGameMenu.translate("&fclick the &aGame Menu &fto get started");

            //send the messages
            p.sendMessage(achline);
            p.sendMessage(completed);
            p.sendMessage(coinplus);
            p.sendMessage(xpplus);
            p.sendMessage(blank);
            p.sendMessage(tutorial);
            p.sendMessage(tutorial2);
            p.sendMessage(achline);

            //play a couple of different sounds :o
            Parkour.otherSound(p);
            final int[] coinsgiven = {0};
            new BukkitRunnable() {
                @Override
                public void run() {
                    while (coinsgiven[0] != 100) {
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 1);
                        coinsgiven[0]++;
                    }
                }
            }.runTaskLaterAsynchronously(plugin, 40);

            //send the welcome message later
            new BukkitRunnable() {
                @Override
                public void run() {
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
                    //play a sound
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
                }
            }.runTaskLaterAsynchronously(plugin, 120);

        } else {
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
            //play a sound
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 100, 0);
        }


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }
}
