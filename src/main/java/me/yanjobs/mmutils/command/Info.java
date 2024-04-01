package me.yanjobs.mmutils.command;

import me.yanjobs.mmutils.utils.chat.Message;
import net.weavemc.loader.api.command.Command;
import org.jetbrains.annotations.NotNull;

public class Info extends Command {
    public Info() {
        super("mmhelp");
    }

    @Override
    public void handle(@NotNull String[] args) {
        Message.sendMessage("Toggle: /mmtoggle", Message.LEVEL.Log);
        Message.sendMessage("Creator: imyanice, GitHub: Yan-Jobs", Message.LEVEL.Log);
    }
}
