package me.gurwi.lobbybridge.listeners;

import me.gurwi.lobbybridge.utils.LobbyBlockUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryInteraction implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        ItemStack clickedItem = event.getCurrentItem();
        if (LobbyBlockUtils.isLobbyBlock(clickedItem)) event.setCancelled(true);

    }

    @EventHandler
    public void onCreativeInventoryClick(InventoryCreativeEvent event) {

        if (!event.getClick().isCreativeAction()) return;
        ItemStack clickedItem = event.getCurrentItem();
        if (LobbyBlockUtils.isLobbyBlock(clickedItem)) event.setCancelled(true);

    }

}
