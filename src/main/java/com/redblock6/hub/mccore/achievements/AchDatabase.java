package com.redblock6.hub.mccore.achievements;

import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;

import static com.redblock6.hub.Main.pool;

public class AchDatabase {
    Player p;
    ArrayList<HAchType> achH = new ArrayList<>();
    ArrayList<KAchType> achK = new ArrayList<>();

    public AchDatabase(Player p) {
        this.p = p;
    }

    public ArrayList<HAchType> getHubAch() {
        Jedis j = pool.getResource();
        for (HAchType ach : HAchType.values()) {
            if (j.get(ach.name() + p.getUniqueId()) != null) {
                achH.add(ach);
            }
        }
        j.close();

        return achH;
    }

    public ArrayList<KAchType> getKitAch() {
        Jedis j = pool.getResource();
        for (KAchType ach : KAchType.values()) {
            if (j.get(ach.name() + p.getUniqueId()) != null) {
                achK.add(ach);
            }
        }
        j.close();

        return achK;
    }

    public void revokeHAch(HAchType ach) {
        Jedis j = pool.getResource();
        j.del(ach.name() + p.getUniqueId());
        j.close();
    }

    public void revokeKAch(KAchType ach) {
        Jedis j = pool.getResource();
        j.del(ach.name() + p.getUniqueId());
        j.close();
    }

    public void addHAch(HAchType ach) {
        Jedis j = pool.getResource();
        j.set(ach.name() + p.getUniqueId(), "HAS");
        j.close();
    }

    public void addKAch(KAchType ach) {
        Jedis j = pool.getResource();
        j.set(ach.name() + p.getUniqueId(), "HAS");
        j.close();
    }
}
