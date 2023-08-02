package me.gurwi.lobbybridge.database;

import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;

@Getter
public class DatabaseTable {

    protected final SQLConnectionProvider connectionProvider;
    protected final ExecutorService databaseExecutor;

    public DatabaseTable(SQLConnectionProvider connectionProvider, ExecutorService databaseExecutor, String... createTableQueries) throws SQLException {
        this.connectionProvider = connectionProvider;
        this.databaseExecutor = databaseExecutor;

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            for (String createTableQuery : createTableQueries) {
                statement.addBatch(createTableQuery);
            }

            statement.executeBatch();
        }
    }

    public Connection getConnection() throws SQLException {
        return connectionProvider.getConnection();
    }

}
