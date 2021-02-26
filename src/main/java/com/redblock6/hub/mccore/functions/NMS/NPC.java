package com.redblock6.hub.mccore.functions.NMS;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
    public static EntityPlayer createPlayerNpc(World world, Location location, String skin, Player p, String npcname) {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld)Bukkit.getWorld(world.getName())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), npcname); // Change "playername" to the name the NPC should have, max 16 characters.
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld)); // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        npc.setCustomNameVisible(false);

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
            EntityPlayer player = ((CraftPlayer) p).getHandle();
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

    public static EntityPlayer addNpcPacket(EntityPlayer npc, Player p, boolean showName, Location loc) {
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;

        //adds the player data for the client to use when spawning a player
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));

        //spawns the npc
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

        //fix head rotation
        connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc, loc));

        if (showName) {
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

}
