package me.gurwi.lobbybridge.commands.subcommands;

import me.gurwi.lobbybridge.LobbyBridge;
import me.gurwi.lobbybridge.commands.base.SubCommand;
import org.bukkit.command.CommandSender;

public class VersionCommand extends SubCommand {

    public VersionCommand() {
        super("version", "Shows more informations about the plugin", null, false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        sender.sendMessage("");
        sender.sendMessage("    §7FadedMask " + LobbyBridge.getInstance().getDescription().getVersion());
        sender.sendMessage("    §7By @Gurwi30 (§fgithub.com/Gurwi30§7)");
        sender.sendMessage("");

    }

}
