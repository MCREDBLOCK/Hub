package com.redblock6.hub.mccore.functions;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class Fireworks {
    public static Firework spawnFirework1(Location loc) {
        Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();

        meta.addEffect(builder.flicker(false).withColor(Color.YELLOW).build());
        meta.addEffect(builder.withFade(Color.YELLOW).build());
        meta.addEffect(builder.with(FireworkEffect.Type.BALL_LARGE).build());
        meta.setPower(0);
        fw.setFireworkMeta(meta);

        return fw;
    }

    public static Firework spawnFirework2(Location loc) {
        Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();

        meta.addEffect(builder.flicker(false).withColor(Color.BLUE).build());
        meta.addEffect(builder.withFade(Color.AQUA).build());
        meta.addEffect(builder.with(FireworkEffect.Type.BALL_LARGE).build());
        meta.setPower(0);
        fw.setFireworkMeta(meta);

        return fw;
    }
}
