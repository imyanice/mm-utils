package me.yanjobs.mmutils.events;

import me.yanjobs.mmutils.MMUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.weavemc.loader.api.event.ChatReceivedEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import tv.twitch.chat.Chat;

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
