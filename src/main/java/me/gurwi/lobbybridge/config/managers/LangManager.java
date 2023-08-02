package me.gurwi.lobbybridge.config.managers;

import me.gurwi.lobbybridge.utils.PluginCustomLoader;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public enum LangManager {

    PREFIX("Prefix"),
    NO_PERMS("No-Perms");

    // CONSTRUCTOR

    public final String path;

    LangManager(String path) {
        this.path = path;
    }

    ///

    private final Configuration langConfig = PluginCustomLoader.getInstance().getConfigLoader().getLangConfig().getCustomConfig();

    // METHODS

    public List<String> getStringList() {
        return langConfig.getStringList(this.path);
    }

    public String getFormattedString() {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(langConfig.getString(this.path)));
    }

    public String getString() {
        return langConfig.getString(this.path);
    }

}
