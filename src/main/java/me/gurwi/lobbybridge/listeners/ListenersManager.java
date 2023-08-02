package me.gurwi.lobbybridge.listeners;

import me.gurwi.lobbybridge.LobbyBridge;
import me.gurwi.lobbybridge.listeners.data.PlayerDataUpdate;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenersManager {

    private final LobbyBridge plugin;

    public ListenersManager(LobbyBridge plugin) {
        this.plugin = plugin;

        register(new PlayerDataUpdate());

        register(new PlayerJoin());
        register(new InventoryInteraction());
        register(new BlockPlace());
        register(new FallingBlockChecker());
    }

    private void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

}
