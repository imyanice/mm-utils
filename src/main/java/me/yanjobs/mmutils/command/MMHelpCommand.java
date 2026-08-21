package me.yanjobs.mmutils.command;

import me.yanjobs.mmutils.MMUtils;
import me.yanjobs.mmutils.utils.chat.Message;
import net.weavemc.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class MMHelpCommand extends Command {
    public static final String name = "mmhelp";

    public MMHelpCommand() {
        super(name);
    }

    @Override
    public void execute(@NotNull String[] strings) {
        Message.sendMessage("", Message.LEVEL.Log);
        Message.sendMessage("Murder Mytery Utils v" + MMUtils.VERSION, Message.LEVEL.Log);
        Message.sendMessage("Toggle: /mmtoggle", Message.LEVEL.Log);
        Message.sendMessage("made by yanice https://discord.gg/lilith!", Message.LEVEL.Log);
        Message.sendMessage("", Message.LEVEL.Log);
    }
}
