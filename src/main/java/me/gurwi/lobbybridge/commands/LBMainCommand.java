package me.gurwi.lobbybridge.commands;

import lombok.Getter;
import me.gurwi.lobbybridge.commands.base.CMDExecutionValidator;
import me.gurwi.lobbybridge.commands.base.SubCommand;
import me.gurwi.lobbybridge.commands.subcommands.ReloadCommand;
import me.gurwi.lobbybridge.commands.subcommands.SettingsCommand;
import me.gurwi.lobbybridge.commands.subcommands.VersionCommand;
import me.gurwi.lobbybridge.config.managers.LangManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class LBMainCommand implements CommandExecutor {

    @Getter
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public void register(SubCommand command, String... aliases) {
        subCommands.put(command.getName(), command);

        for (String commandName : aliases) {
            subCommands.put(commandName, command);
        }
    }

    public LBMainCommand() {
        register(new VersionCommand());
        register(new ReloadCommand());
        register(new SettingsCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        String prefix = LangManager.PREFIX.getFormattedString();

        if (args.length == 0) {
            sender.sendMessage(prefix + "§7Get the list of all available commands by typing §b/lobbybridge help");
            return false;
        }

        String argument = args[0].toLowerCase();
        SubCommand commandToExecute = subCommands.get(argument);

        if (subCommands.get(argument) == null) {
            sender.sendMessage(prefix + "§cCommand not found! §7Get the list of all available commands by typing §b/lobbybridge help");
            return false;
        }

        if (!CMDExecutionValidator.validate(commandToExecute, sender)) return false;

        commandToExecute.execute(sender, args);

        return true;
    }

}
