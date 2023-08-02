package me.gurwi.lobbybridge.commands.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

@Getter
@RequiredArgsConstructor
public abstract class SubCommand {

    private final String name;
    private final String description;
    private final String permission;
    private final boolean playerOnly;

    public abstract void execute(CommandSender sender, String[] args);


}
