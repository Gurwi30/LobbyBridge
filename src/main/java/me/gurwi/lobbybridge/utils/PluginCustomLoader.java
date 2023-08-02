package me.gurwi.lobbybridge.utils;


import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import me.gurwi.lobbybridge.LobbyBridge;
import me.gurwi.lobbybridge.cache.PlayersManager;
import me.gurwi.lobbybridge.commands.LBMainCommand;
import me.gurwi.lobbybridge.config.ConfigLoader;
import me.gurwi.lobbybridge.database.LBridgeDatabase;
import me.gurwi.lobbybridge.database.SQLConnectionProvider;
import me.gurwi.lobbybridge.listeners.BlockPlace;
import me.gurwi.lobbybridge.listeners.ListenersManager;
import me.gurwi.lobbybridge.nms.BlockBreakAnimation;
import me.gurwi.lobbybridge.objects.LobbyPlayer;
import me.gurwi.lobbybridge.utils.customlogger.CustomLogger;
import me.gurwi.lobbybridge.utils.customlogger.LoggerTag;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class PluginCustomLoader {

    @Getter(lazy = true)
    private static final PluginCustomLoader instance = new PluginCustomLoader();
    private final LobbyBridge plugin = LobbyBridge.getInstance();

    private ConfigLoader configLoader;

    // MYSQL DATABASE

    private LBridgeDatabase database;
    private SQLConnectionProvider connectionProvider;
    private final ExecutorService databaseExecutor = Executors.newFixedThreadPool(2);

    // METHODS

    private void loadConfig() {
        CustomLogger.log(LoggerTag.LOAD_TAG, "Loading Config...");
        configLoader = new ConfigLoader(plugin);

        configLoader.loadConfigs();
    }

    private void loadData() throws SQLException {
        CustomLogger.log(LoggerTag.LOAD_TAG, "Loading Data...");

        connectionProvider = new SQLConnectionProvider(plugin.getConfig(), plugin.getLogger());
        database = new LBridgeDatabase(connectionProvider, databaseExecutor);

        Bukkit.getOnlinePlayers().forEach(DataUtils::loadPlayerData);

    }

    private void loadListeners() {
        CustomLogger.log(LoggerTag.LOAD_TAG, "Loading Listeners...");

        new ListenersManager(plugin);
    }

    private void loadCommands() {
        CustomLogger.log(LoggerTag.LOAD_TAG, "Loading Commands...");

        plugin.getCommand("lobbybridge").setExecutor(new LBMainCommand());
    }

    private void loadTabCompleters() {

    }

    // MAIN METHODS

    public void loadPlugin() {

        loadConfig();
        try {
            loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        loadListeners();
        loadCommands();
        loadTabCompleters();

    }

    public void disablePlugin() {

        if (connectionProvider != null)connectionProvider.disable();

        BlockPlace.getPlacedBlocks().forEach((entityId, block) -> {
            if (!LobbyBlockUtils.lobbyBlockExits(XMaterial.matchXMaterial(block.getType()))) return;
            BlockBreakAnimation.breakBlock(entityId, block, -1);
            block.setType(Material.AIR);
        });

    }

}
