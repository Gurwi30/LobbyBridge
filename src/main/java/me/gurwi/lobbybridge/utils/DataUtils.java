package me.gurwi.lobbybridge.utils;

import me.gurwi.lobbybridge.cache.PlayersManager;
import me.gurwi.lobbybridge.config.managers.ConfigManager;
import me.gurwi.lobbybridge.config.managers.LangManager;
import me.gurwi.lobbybridge.config.managers.LobbyBlocksManager;
import me.gurwi.lobbybridge.database.LBridgeDatabase;
import me.gurwi.lobbybridge.enums.BreakAnimation;
import me.gurwi.lobbybridge.objects.LobbyBlock;
import me.gurwi.lobbybridge.objects.LobbyPlayer;
import me.gurwi.lobbybridge.utils.customlogger.CustomLogger;
import me.gurwi.lobbybridge.utils.customlogger.LoggerTag;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class DataUtils {

    private static final LobbyBlocksManager blocksManager = PluginCustomLoader.getInstance().getConfigLoader().getLobbyBlocksManager();
    private static final LBridgeDatabase database = PluginCustomLoader.getInstance().getDatabase();
    private static final PlayersManager playersManager = PlayersManager.getInstance();

    @SuppressWarnings("all")
    public static void loadPlayerData(Player player) {

        database.getLobbyPlayer(player.getUniqueId()).whenComplete((pseudoPlayer, throwable) -> {

            if (throwable == null) return;
            throwable.printStackTrace();
            player.sendMessage(LangManager.PREFIX.getFormattedString() + "§cAn error occured while loading your LobbyBridge data!");

        }).thenAccept(pseudoPlayer -> {

            AtomicInteger placedBlocks = new AtomicInteger(0);
            LobbyBlock lobbyBlock = LobbyBlockUtils.getDefaultBlock();
            BreakAnimation breakAnimation = BreakAnimation.NONE;

            if (pseudoPlayer != null) {

                placedBlocks.set(pseudoPlayer.getPlacedBlocks());
                breakAnimation = pseudoPlayer.getBreakAnimation();

                if (LobbyBlockUtils.lobbyBlockExits(pseudoPlayer.getBlockMaterial())) {
                    lobbyBlock = LobbyBlockUtils.getLobbyBlock(pseudoPlayer.getBlockMaterial());
                }

            }

            PlayersManager.getInstance().getPlayersMap().put(player.getUniqueId(), new LobbyPlayer(player.getUniqueId(), placedBlocks, lobbyBlock, breakAnimation));

            if (!blocksManager.getBlocksMap().containsKey(ConfigManager.DEFAULT_BLOCK.getString())) {
                CustomLogger.log(LoggerTag.CONFIG_ERROR_TAG, "Couldn't give block on join to §4" + player.getName() + " §cbecause the default block is not present in the block.yml!");
                return;
            }

            int slot = ConfigManager.GIVE_SLOT.getInt();

            if (!lobbyBlock.getPermission().isEmpty() && !player.hasPermission(lobbyBlock.getPermission())) return;

            if (slot >= 0) {
                player.getInventory().setItem(slot, lobbyBlock.getItemStack());
            } else {
                player.getInventory().addItem(lobbyBlock.getItemStack());
            }

        });

    }

    @SuppressWarnings("all")
    public static void updatePlayerData(Player player) {

        database.getLobbyPlayer(player.getUniqueId()).whenComplete((pseudoPlayer, throwable) -> {

            if (throwable == null) return;
            throwable.printStackTrace();
            CustomLogger.log(LoggerTag.DB_ERROR_TAG, "§cAn error occured while updating §4" + player.getName() + " §cdata!");

        }).thenAccept(pseudoPlayer -> {

            LobbyPlayer lobbyPlayer = playersManager.getPlayersMap().get(player.getUniqueId());

            if (pseudoPlayer == null) {
                database.addPlayer(lobbyPlayer).whenComplete((player1, throwable) -> {

                    if (throwable == null) return;
                    throwable.printStackTrace();
                    CustomLogger.log(LoggerTag.DB_ERROR_TAG, "§cAn error occured while updating §4" + player.getName() + " §cdata!");

                }).thenAccept(player1 -> playersManager.getPlayersMap().remove(player.getUniqueId()));

                return;
            }

            database.updatePlayer(lobbyPlayer).whenComplete((lobbyPlayer1, throwable) -> {

                if (throwable == null) return;
                throwable.printStackTrace();
                CustomLogger.log(LoggerTag.DB_ERROR_TAG, "§cAn error occured while updating §4" + player.getName() + " §cdata!");

            }).thenAccept(lobbyPlayer1 -> playersManager.getPlayersMap().remove(player.getUniqueId()));

        });

    }

}
