package me.gurwi.lobbybridge.commands.subcommands;

import me.gurwi.lobbybridge.commands.base.SubCommand;
import me.gurwi.lobbybridge.config.ConfigLoader;
import me.gurwi.lobbybridge.config.managers.LangManager;
import me.gurwi.lobbybridge.utils.PluginCustomLoader;
import me.gurwi.lobbybridge.utils.customlogger.CustomLogger;
import me.gurwi.lobbybridge.utils.customlogger.LoggerTag;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    public ReloadCommand() {
        super("reload", "Reloads plugin configurations", "lobbybridge.reload", false);
    }

    private final ConfigLoader configLoader = PluginCustomLoader.getInstance().getConfigLoader();

    @Override
    public void execute(CommandSender sender, String[] args) {

        String prefix = LangManager.PREFIX.getFormattedString();
        long start = System.currentTimeMillis();

        CustomLogger.log(LoggerTag.INFO_TAG, "Started plugin reload...");
        sender.sendMessage(prefix + "§7Started plugin reload...");

        sender.sendMessage(prefix + "§7Reloading §bLobbyBridge §7configurations...");
        configLoader.reloadConfigs();

        long elapsedTime = System.currentTimeMillis() - start;
        sender.sendMessage(prefix + "§7Reload finished in §b" + elapsedTime + "ms");
        CustomLogger.log(LoggerTag.SUCCESS_TAG, "Reload finished in §f" + elapsedTime + "ms");

    }

}
