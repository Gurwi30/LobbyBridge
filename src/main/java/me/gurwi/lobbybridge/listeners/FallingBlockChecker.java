package me.gurwi.lobbybridge.listeners;

import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class FallingBlockChecker implements Listener {

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent event) {

        if (!(event.getEntity() instanceof FallingBlock)) return;
        FallingBlock fallingBlock = (FallingBlock) event.getEntity();
        if (!(fallingBlock.getCustomName() != null || !fallingBlock.getCustomName().equals("LobbyBridge_Block"))) return;
        event.setCancelled(true);

    }

}
