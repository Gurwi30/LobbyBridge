package me.gurwi.lobbybridge.glib;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings({"unused", "ResultOfMethodCallIgnored"})
public class CustomConfig {

    private final JavaPlugin plugin;
    private final String directory;

    @Getter
    private File customConfigFile;

    @Getter
    private FileConfiguration customConfig;


    ///

    /**
     * Loads the custom config
     */
    @SneakyThrows
    public void loadConfig() {

        customConfigFile = new File(plugin.getDataFolder(), directory);
        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
        if (!customConfigFile.exists()) customConfigFile.createNewFile();

        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

    }

    /**
     * Reloads the custom config
     */
    public void reloadConfig() {
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save the config
     */
    public void saveConfig() {

        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // CONSTRUCTOR

    /**
     * @param plugin Plugin
     * @param directory Directory where to load the config
     * @param <T> Type
     */
    public<T extends JavaPlugin> CustomConfig(T plugin, String directory) {
        this.plugin = plugin;
        this.directory = directory;
    }

}
