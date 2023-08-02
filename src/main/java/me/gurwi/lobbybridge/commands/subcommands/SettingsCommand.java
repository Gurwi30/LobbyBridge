package me.gurwi.lobbybridge.commands.subcommands;

import me.gurwi.lobbybridge.commands.base.SubCommand;
import me.gurwi.lobbybridge.guis.SettingGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand extends SubCommand {

    public SettingsCommand() {
        super("settings", "Open block settings", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        SettingGUI.open(player);

    }

}
