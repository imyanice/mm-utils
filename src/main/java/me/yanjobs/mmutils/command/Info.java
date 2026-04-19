package me.yanjobs.mmutils.command;

import me.yanjobs.mmutils.MMUtils;
import me.yanjobs.mmutils.utils.chat.Message;
import org.jetbrains.annotations.NotNull;

public class Info {
    public static final String name = "mmhelp";

    public static void handle(@NotNull String[] args) {
        Message.sendMessage("", Message.LEVEL.Log);
        Message.sendMessage("Murder Mytery Utils v" + MMUtils.VERSION, Message.LEVEL.Log);
        Message.sendMessage("Toggle: /mmtoggle", Message.LEVEL.Log);
        Message.sendMessage("made by imyanice!", Message.LEVEL.Log);
        Message.sendMessage("", Message.LEVEL.Log);
    }
}
