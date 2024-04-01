package me.yanjobs.mmutils.command;

import me.yanjobs.mmutils.MMUtils;
import me.yanjobs.mmutils.utils.chat.Message;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MMToggle extends Command {
    public MMToggle() {
        super("mmtoggle");
    }

    @Override
    public void handle(@NotNull String[] args) {
        try {
            boolean isEnabled = Boolean.parseBoolean(MMUtils.getConfig().getProperty("enabled"));
            MMUtils.getConfig().setProperty("enabled", String.valueOf(!isEnabled));
            Message.sendMessage("Successfully turned " + (isEnabled ? "off" : "on") + " MMUtils!", Message.LEVEL.Log);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
