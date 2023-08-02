package me.gurwi.lobbybridge.database;

import lombok.Getter;
import org.bukkit.configuration.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@Getter
public class SQLConnectionProvider {
    private static final String POOL_NAME = "LobbyBridge-Hikari-Pool";

    private final DatabaseProperty property;
    private final HikariProvider hikariProvider;
    private final Logger logger;
    private static final int THREADS = 3;

    public SQLConnectionProvider(DatabaseProperty property, Logger logger) {
        this.property = property;
        this.logger = logger;
        hikariProvider = new HikariProvider(property);
    }

    public SQLConnectionProvider(Configuration configuration , Logger logger) {
        this(new DatabaseProperty(configuration, THREADS, POOL_NAME), logger);
    }

    public Connection getConnection() throws SQLException {
        return hikariProvider.getDataSource().getConnection();
    }

    public void disable() {
        hikariProvider.disable();
    }
}
