package me.yanjobs.mmutils.events;

import me.yanjobs.mmutils.MMUtils;
import net.weavemc.loader.api.event.ChatReceivedEvent;
import net.weavemc.loader.api.event.SubscribeEvent;

public class ChatReceived {

    @SubscribeEvent
    public void onChatMessage(ChatReceivedEvent event) {
        if (event.getMessage().getUnformattedText().equals("Teaming with the Murderer is not allowed!")) {
            MMUtils.isInMMClassic = true;
        } if (event.getMessage().getUnformattedText().equals("Teaming with the Detective/Innocents is not allowed!")) {
            MMUtils.isInMMClassic = true;
        }
    }
}
