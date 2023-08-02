package me.gurwi.lobbybridge;

import lombok.Getter;
import me.gurwi.lobbybridge.utils.PluginCustomLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyBridge extends JavaPlugin {

    @Getter
    private static LobbyBridge instance;

    @Override
    public void onEnable() {
        instance = this;
        PluginCustomLoader.getInstance().loadPlugin();

    }

    @Override
    public void onDisable() {
        PluginCustomLoader.getInstance().disablePlugin();

    }
}
