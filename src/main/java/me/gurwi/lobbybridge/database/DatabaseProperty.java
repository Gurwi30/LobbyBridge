package me.gurwi.lobbybridge.database;

import lombok.Getter;
import org.bukkit.configuration.Configuration;

@Getter
public class DatabaseProperty {
    private static final String KEY = "DataBase.MySql.";
    private final String host;
    private final String database;
    private final String username;
    private final String password;
    private final String port;
    private final boolean ssl;
    private final int poolSize;
    private final String poolName;

    public DatabaseProperty(Configuration config, int poolSize, String poolName) {
        this.host = getString(config, "storageHost");
        this.database = getString(config, "storageDatabase");
        this.username = getString(config, "storageUser");
        this.password = getString(config, "storagePassword");
        this.port = getString(config, "storagePort");
        this.ssl = getBool(config, "useSSL");
        this.poolSize = poolSize;
        this.poolName = poolName;
    }

    private static String getString(Configuration config, String value) {
        return config.getString(KEY + value);
    }
    private static boolean getBool(Configuration config, String value) {
        return config.getBoolean(KEY + value);
    }
}
