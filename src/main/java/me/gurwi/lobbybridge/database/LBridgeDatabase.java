package me.gurwi.lobbybridge.database;

import com.cryptomorin.xseries.XMaterial;
import me.gurwi.lobbybridge.enums.BreakAnimation;
import me.gurwi.lobbybridge.objects.LobbyPlayer;
import me.gurwi.lobbybridge.objects.PseudoPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class LBridgeDatabase extends DatabaseTable {

    protected final static String LOBBYBRIDGE_TABLE = "lobbybridge_data";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + LOBBYBRIDGE_TABLE + " (playerUUID VARCHAR(36), placedBlocks INTEGER, block LONGTEXT, breakAnimation LONGTEXT)";

    public LBridgeDatabase(SQLConnectionProvider connectionProvider, ExecutorService databaseExecutor) throws SQLException {
        super(connectionProvider, databaseExecutor, CREATE_TABLE);
    }

    // METHODS

    public CompletableFuture<LobbyPlayer> addPlayer(LobbyPlayer player) {

        return CompletableFuture.supplyAsync(() -> {

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO " + LOBBYBRIDGE_TABLE + " (playerUUID, placedBlocks, block, breakAnimation) VALUES (?, ?, ?, ?)")) {

                statement.setString(1, player.getUniqueId().toString());
                statement.setInt(2, 0);
                statement.setString(3, XMaterial.matchXMaterial(player.getSelectedBlock().getItemStack().getType()).name());
                statement.setString(4, BreakAnimation.NONE.name());

                statement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return player;
        }, databaseExecutor);

    }

    public CompletableFuture<LobbyPlayer> updatePlayer(LobbyPlayer updatedPlayer) {

        return CompletableFuture.supplyAsync(() -> {

            try (Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE " + LOBBYBRIDGE_TABLE + " SET placedBlocks = ?, block = ?, breakAnimation = ? WHERE playerUUID = ?")) {

                statement.setInt(1, updatedPlayer.getPlacedBlocks().get());
                statement.setString(2, XMaterial.matchXMaterial(updatedPlayer.getSelectedBlock().getItemStack()).name());
                statement.setString(3, updatedPlayer.getBreakAnimation().name());
                statement.setString(4, updatedPlayer.getUniqueId().toString());

                statement.executeUpdate();

                return updatedPlayer;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }, databaseExecutor);

    }

    public CompletableFuture<PseudoPlayer> getLobbyPlayer(UUID playerUniqueId) {

        return CompletableFuture.supplyAsync(() -> {

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + LOBBYBRIDGE_TABLE + " WHERE playerUUID = ?")) {

                statement.setString(1, playerUniqueId.toString());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {

                    int placedBlocks = resultSet.getInt(2);
                    XMaterial material = XMaterial.matchXMaterial(resultSet.getString(3)).get();
                    BreakAnimation breakAnimation = BreakAnimation.valueOf(resultSet.getString(4));

                    return new PseudoPlayer(playerUniqueId, placedBlocks, material, breakAnimation);
                }

                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }, databaseExecutor);

    }

}
