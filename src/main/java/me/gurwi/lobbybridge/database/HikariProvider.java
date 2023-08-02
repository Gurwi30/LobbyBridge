package me.gurwi.lobbybridge.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

@Getter
public class HikariProvider {
    private final HikariDataSource dataSource;

    public HikariProvider(DatabaseProperty property) {
        dataSource = openConnection(property);
    }

    private HikariDataSource openConnection(DatabaseProperty property) throws IllegalStateException {
        HikariConfig config = getHikariConfig(property);
        config.setPoolName(property.getPoolName());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("allowMultiQueries", true);
        config.addDataSourceProperty("allowMultiQueries", true);
        config.setMaximumPoolSize(property.getPoolSize());

        return new HikariDataSource(config);
    }

    @NotNull
    private static HikariConfig getHikariConfig(DatabaseProperty property) {
        Properties properties = new Properties();

        properties.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        properties.setProperty("jdbcUrl", String.format("jdbc:mysql://%s:%s/%s", property.getHost(), property.getPort(), property.getDatabase()));
        properties.setProperty("dataSource.serverName", property.getHost());
        properties.setProperty("dataSource.user", property.getUsername());
        properties.setProperty("dataSource.password", property.getPassword());
        properties.setProperty("dataSource.databaseName", property.getDatabase());
        properties.setProperty("dataSource.portNumber", property.getPort());
        properties.setProperty("dataSource.useSSL", String.valueOf(property.isSsl()));

        return new HikariConfig(properties);
    }

    public void disable() {
        dataSource.close();
    }
}
