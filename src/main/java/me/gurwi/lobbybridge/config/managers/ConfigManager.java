package me.gurwi.lobbybridge.config.managers;

import me.gurwi.lobbybridge.LobbyBridge;
import org.bukkit.configuration.Configuration;

import java.util.List;

@SuppressWarnings("unused")
public enum ConfigManager {

    DEFAULT_BLOCK("default-block"),
    GIVE_SLOT("slot");

    // CONSTRUCTOR

    public final String path;

    ConfigManager(String path) {
        this.path = path;
    }

    ///

    private final Configuration config = LobbyBridge.getInstance().getConfig();

    // METHODS

    public List<String> getStringList() {
        return config.getStringList(this.path);
    }

    public String getString() {
        return config.getString(this.path);
    }

    public Boolean getBoolean() {
        return config.getBoolean(this.path);
    }

    public Integer getInt() {
        return config.getInt(this.path);
    }

    public Double getDouble() {
        return config.getDouble(this.path);
    }

    public Long getLong() {
        return config.getLong(this.path);
    }

}
