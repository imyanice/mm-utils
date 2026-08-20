package me.yanjobs.mmutils;

import me.yanjobs.mmutils.command.MMHelpCommand;
import me.yanjobs.mmutils.command.MMToggleCommand;
import me.yanjobs.mmutils.events.ChatReceived;
import me.yanjobs.mmutils.events.MurdererFinder;
import me.yanjobs.mmutils.utils.config.Config;
import net.weavemc.api.ModInitializer;
import net.weavemc.api.command.CommandBus;
import net.weavemc.api.event.EventBus;
import net.weavemc.api.event.StartGameEvent;

import java.io.IOException;

public class MMUtils implements ModInitializer {
    public static boolean isInMMClassic;
    public static final String VERSION = "2.0.0";
    private static Config config;

    @Override
    public void init() {
        EventBus.subscribe(StartGameEvent.Post.class, (event) -> {
            System.out.println("MMUtils v" + VERSION + " successfully loaded!");
            EventBus.subscribe(new MurdererFinder());
            EventBus.subscribe(new ChatReceived());
            CommandBus.register(
                    new MMHelpCommand(),
                    new MMToggleCommand()
            );
            try {
                config = new Config();
                config.createConfigFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Config getConfig() {
        return config;
    }

    static {
        isInMMClassic = false;
    }
}
