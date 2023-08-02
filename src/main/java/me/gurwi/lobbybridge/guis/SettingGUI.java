package me.gurwi.lobbybridge.guis;

import com.cryptomorin.xseries.XMaterial;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.gurwi.lobbybridge.cache.PlayersManager;
import me.gurwi.lobbybridge.config.managers.LobbyBlocksManager;
import me.gurwi.lobbybridge.utils.LobbyBlockUtils;
import me.gurwi.lobbybridge.utils.PluginCustomLoader;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class SettingGUI {

    private static final LobbyBlocksManager blocksManager = PluginCustomLoader.getInstance().getConfigLoader().getLobbyBlocksManager();

    public static void open(Player player) {

        PaginatedGui gui = Gui.paginated()
                .title(Component.text("§8§l» §f§lLobby Bridge Settings"))
                .rows(3)
                .create();

        ///

        gui.getFiller().fillBorder(ItemBuilder.from(Material.AIR).asGuiItem());
        gui.setItem(11, ItemBuilder.from(Material.AIR).asGuiItem());

        ///

        assert XMaterial.BLAZE_POWDER.parseItem() != null;
        GuiItem animationsMenu = ItemBuilder.from(XMaterial.BLAZE_POWDER.parseItem()).name(Component.text("§eAnimations Page")).asGuiItem(event -> BreakEffectsGUI.open(player));

        ///

        gui.setItem(10, animationsMenu);

        blocksManager.getBlocksMap().forEach((s, lobbyBlock) -> {
            ItemBuilder blockItem = ItemBuilder.from(lobbyBlock.getItemStack().clone()).setAmount(1);
            if (lobbyBlock.equals(LobbyBlockUtils.getPlayerCurrentBlock(player))) blockItem.glow(true);

            gui.addItem(blockItem.asGuiItem(event -> {

                for (ItemStack i : gui.getInventory().getContents()) {
                    if (i == null) continue;
                    i.removeEnchantment(Enchantment.LURE);
                }

                LobbyBlockUtils.changePlayerBlock(player, lobbyBlock);

                ItemMeta meta = event.getCurrentItem().getItemMeta();
                meta.addEnchant(Enchantment.LURE, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                event.getCurrentItem().setItemMeta(meta);

            }));

        });

        ///

        player.sendMessage(String.valueOf(gui.getNextPageNum()));

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);

    }

}
