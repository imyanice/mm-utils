package me.yanjobs.mmutils.events;

import net.weavemc.api.event.SubscribeEvent;
import me.yanjobs.mmutils.command.Info;
import me.yanjobs.mmutils.command.MMToggle;
import net.weavemc.api.ChatSentEvent;

public class ChatSent {
    @SubscribeEvent
    public void handle(ChatSentEvent event) {
        String message = event.getMessage();
        String[] args = message.split(" ");
        if (args[0].toLowerCase().equals("/mmtoggle")) {
            event.setCancelled(true);
            MMToggle.handle(args);
        } else if (args[0].toLowerCase().equals("/mmhelp")) {
            event.setCancelled(true);
            Info.handle(args);
        }
    }
}
