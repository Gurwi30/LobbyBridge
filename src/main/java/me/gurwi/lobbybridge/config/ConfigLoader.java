package me.gurwi.lobbybridge.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.gurwi.lobbybridge.LobbyBridge;
import me.gurwi.lobbybridge.config.managers.LobbyBlocksManager;
import me.gurwi.lobbybridge.glib.ResourceConfig;

@Getter
@RequiredArgsConstructor
public class ConfigLoader {

    private final LobbyBridge plugin;

    private ResourceConfig langConfig;
    private ResourceConfig blocksConfig;

    private LobbyBlocksManager lobbyBlocksManager;

    public void loadConfigs() {

        plugin.saveDefaultConfig();
        langConfig = new ResourceConfig(plugin, "lang.yml", false);
        blocksConfig = new ResourceConfig(plugin, "blocks.yml", false);

        lobbyBlocksManager = new LobbyBlocksManager(blocksConfig);
        lobbyBlocksManager.load();
    }

    public void reloadConfigs() {

        plugin.reloadConfig();
        langConfig.reloadConfig();
        blocksConfig.reloadConfig();

        lobbyBlocksManager.load();
    }

}
