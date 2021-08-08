package com.redblock6.hub.mccore.bot;

import com.redblock6.hub.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class BotMain extends ListenerAdapter {
    public Main pl;
    public JDA bot;
    public static MessageChannel eventsChannel;

    public BotMain(Main main) {
        this.pl = main;
        initializeBot();
        bot.addEventListener(this);
        eventsChannel = bot.getTextChannelById("864140794999078932");
    }

    public void initializeBot() {
        JDABuilder builder = JDABuilder.createDefault("NzQwMzMzMjkyNDYwNzAzODE0.XynfGw.Sz2oWQLMMCaijTpLeqTQwKBnB-w");
        builder.setActivity(Activity.watching(" for beta testers!"));
        try {
            bot = builder.build();
            bot.awaitReady();

        } catch (LoginException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendMessage(User user, String content) {
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(content))
                .queue();
    }

}
