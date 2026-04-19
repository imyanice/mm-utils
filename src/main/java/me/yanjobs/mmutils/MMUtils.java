package me.yanjobs.mmutils;

import me.yanjobs.mmutils.events.ChatReceived;
import me.yanjobs.mmutils.events.ChatSent;
import me.yanjobs.mmutils.events.MurdererFinder;
import me.yanjobs.mmutils.utils.config.Config;

import java.io.IOException;

import net.weavemc.api.ModInitializer;
import net.weavemc.api.event.EventBus;

public class MMUtils implements ModInitializer {
    public static boolean isInMMClassic;
    public static final String VERSION = "2.0.0";
    private static Config config;

    @Override
    public void init() {
        System.out.println("MMUtils v" + VERSION + " successfully loaded!");
        EventBus.subscribe(new MurdererFinder());
        EventBus.subscribe(new ChatReceived());
        EventBus.subscribe(new ChatSent());
        try {
            config = new Config();
            config.createConfigFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config getConfig() {
        return config;
    }

    static {
        isInMMClassic = false;
    }
}
