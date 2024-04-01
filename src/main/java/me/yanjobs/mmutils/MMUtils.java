package me.yanjobs.mmutils;

import me.yanjobs.mmutils.command.Info;
import me.yanjobs.mmutils.command.MMToggle;
import me.yanjobs.mmutils.events.ChatReceived;
import me.yanjobs.mmutils.events.MurdererFinder;
import me.yanjobs.mmutils.utils.config.Config;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.command.CommandBus;
import net.weavemc.loader.api.event.EventBus;

import java.io.IOException;

public class MMUtils implements ModInitializer {
    public static boolean isInMMClassic;
    private static Config config;
    @Override
    public void preInit() {
        System.out.println("MMUtils successfully loaded!");
        EventBus.subscribe(new MurdererFinder());
        EventBus.subscribe(new ChatReceived());
        CommandBus.register(new MMToggle());
        CommandBus.register(new Info());
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