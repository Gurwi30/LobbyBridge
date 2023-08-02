package me.gurwi.lobbybridge.glib;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

@SuppressWarnings({"unused", "ConstantConditions", "ResultOfMethodCallIgnored"})
public class ResourceConfig {

    private final JavaPlugin plugin;
    private final String directory;
    private final Boolean replace;

    @Getter
    private File customConfigFile;

    @Getter
    private FileConfiguration customConfig;


    ///

    /**
     * Loads the custom config
     */
    public void loadConfig() {
        customConfigFile = new File(plugin.getDataFolder() + File.separator + directory.replace("/", File.separator));

        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            plugin.saveResource(directory, replace);
        }

        customConfig = new YamlConfiguration();

        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        saveConfig();
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
     * @param replace Replace the current file or not
     * @param <T> Type
     */
    public<T extends JavaPlugin> ResourceConfig(T plugin, String directory, Boolean replace) {
        this.plugin = plugin;
        this.directory = directory;
        this.replace = replace;

        loadConfig();
    }

}
