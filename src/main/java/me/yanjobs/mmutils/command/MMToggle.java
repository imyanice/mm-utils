package me.yanjobs.mmutils.command;

import me.yanjobs.mmutils.MMUtils;
import me.yanjobs.mmutils.utils.chat.Message;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MMToggle {
    public static final String name = "mmtogggle";

    public static void handle(@NotNull String[] args) {
        try {
            boolean isEnabled = Boolean.parseBoolean(MMUtils.getConfig().getProperty("enabled"));
            MMUtils.getConfig().setProperty("enabled", String.valueOf(!isEnabled));
            Message.sendMessage("Successfully turned " + (isEnabled ? "off" : "on") + " MMUtils!", Message.LEVEL.Log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
