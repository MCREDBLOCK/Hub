package com.redblock6.hub.mccore.functions.NMS;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.redblock6.hub.Main;
import com.redblock6.hub.mccore.functions.CreateGameMenu;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class NPC implements Listener {
    /*
    public NPCLocation(String[] Text, Location loc, boolean npc) {
        if (!npc) {
            this.text = Text;
            this.loc = loc;
            create();
        }
    } */

    private static final com.redblock6.hub.Main plugin = Main.getInstance();

    public static EntityPlayer createPlayerNpc(World world, Location location, String skin, Player p, String npcname) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        net.minecraft.server.v1_16_R3.WorldServer nmsWorld = ((CraftWorld)Bukkit.getWorld(world.getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), npcname); // Change "playername" to the name the NPC should have, max 16 characters.
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld)); // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        npc.getBukkitEntity().setCustomName(CreateGameMenu.translate("&7&lNPC &fStats"));
        npc.getBukkitEntity().setCustomNameVisible(false);

        String[] name = getSkin(p, skin);
        gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));

        return npc;
    }

    private static String[] getSkin(Player p, String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);

            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");

            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();

            return new String[] {texture, signature};
        } catch (Exception e) {
            GameProfile profile = ((CraftPlayer) p).getProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.getValue();
            String signature = property.getSignature();
            return new String[] {texture, signature};
        }
    }

    public static void removeNpcPacket(Entity npc, Player p) {
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
    }

    public static EntityPlayer addNpcPacket(EntityPlayer npc, Player p, boolean showName) {
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;

        //adds the player data for the client to use when spawning a player
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));

        //spawns the npc
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

        //fix head rotation
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));

        // Show the Second Skin Layer
        DataWatcher watcher = npc.getDataWatcher();
        watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 255);

        if (!showName) {
            ScoreboardTeam team = new ScoreboardTeam(((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), p.getName());

            team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);

            ArrayList<String> playerToAdd = new ArrayList<>();

            playerToAdd.add(p.getName()); //Add the fake player so this player will not have a nametag

            connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
            connection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
            connection.sendPacket(new PacketPlayOutScoreboardTeam(team, playerToAdd, 3));
        }

        return npc;
    }

    /*
    public static EntityArmorStand addHologramPacket(World world, Location loc, String line, Player p) {
        WorldServer nmsWorld = ((CraftWorld)Bukkit.getWorld(world.getName())).getHandle();

        EntityArmorStand hologram = new EntityArmorStand(nmsWorld, loc.getX(), loc.getY(), loc.getZ());
        hologram.setInvisible(true);
        hologram.setInvulnerable(true);
        hologram.setNoGravity(true);
        hologram.setCustomName(IChatBaseComponent.ChatSerializer.a(line));
        hologram.setCustomNameVisible(true);
        hologram.setBasePlate(false);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(hologram);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        return hologram;
    }

    private void create(World world, Location loc) {
        for (String text : this.text) {
            EntityArmorStand entity = new EntityArmorStand(((CraftWorld) this.loc.getWorld()).getHandle(),this.loc.getX(), this.loc.getY(),this.loc.getZ());
        }
    } */

            /*
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(String.class);

        WrappedDataWatcher water = new WrappedDataWatcher();
        water.setEntity((org.bukkit.entity.Entity) stand);

        water.setObject(2, serializer, line);
        hologram.getWatchableCollectionModifier().write(0, water.getWatchableObjects());

        Optional<?> opt = Optional
                .of(WrappedChatComponent
                        .fromChatMessage(line)[0].getHandle());
        water.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);

        try {
            protocolManager.sendServerPacket(p, hologram);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet" + hologram, e);
        } */
}
