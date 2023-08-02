package me.gurwi.lobbybridge.commands.base;

import me.gurwi.lobbybridge.config.managers.LangManager;
import me.gurwi.lobbybridge.utils.PermissionChecker;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDExecutionValidator {

    public static boolean validate(SubCommand command, CommandSender sender) {

        String prefix = LangManager.PREFIX.getFormattedString();

        if (command.isPlayerOnly() && !(sender instanceof Player)) {
            sender.sendMessage(prefix + onlyPlayerError());
            return false;
        }

        if (command.getPermission() != null && !PermissionChecker.isOp(sender)) {
            sender.sendMessage(PermissionChecker.getInsufficentPermsError());
            return false;
        }

        return true;

    }

    public static String onlyPlayerError() {
        return "§c§oOnly players can execute this command!";
    }

}
