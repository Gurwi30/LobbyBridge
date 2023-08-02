package me.gurwi.lobbybridge.listeners.data;

import me.gurwi.lobbybridge.utils.DataUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDataUpdate implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        DataUtils.updatePlayerData(player);

    }

}
