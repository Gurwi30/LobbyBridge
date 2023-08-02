package me.gurwi.lobbybridge.guis;

import com.cryptomorin.xseries.XMaterial;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.gurwi.lobbybridge.enums.BreakAnimation;
import me.gurwi.lobbybridge.utils.LobbyBlockUtils;
import me.gurwi.lobbybridge.utils.customheads.CustomHeads;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BreakEffectsGUI {

    public static void open(Player player) {

        PaginatedGui gui = Gui.paginated()
                .title(Component.text("§8§l» §f§lLobby Bridge Settings"))
                .rows(3)
                .create();

        ///

        gui.getFiller().fillBorder(ItemBuilder.from(Material.AIR).asGuiItem());
        gui.setItem(11, ItemBuilder.from(Material.AIR).asGuiItem());

        ///

        GuiItem blocksMenu = ItemBuilder.from(CustomHeads.LEFT_ARROW.getItemStack("§fBlocks Page")).asGuiItem(event -> SettingGUI.open(player));

        assert XMaterial.BARRIER.parseItem() != null;
        GuiItem disableAnimation = ItemBuilder.from(XMaterial.BARRIER.parseItem()).name(Component.text("§4None")).asGuiItem(event -> LobbyBlockUtils.changePlayerAnimation(player, BreakAnimation.NONE));

        assert XMaterial.GHAST_TEAR.parseItem() != null;
        GuiItem fallAnimation = ItemBuilder.from(XMaterial.GHAST_TEAR.parseItem()).name(Component.text("§7Falling Blocks")).asGuiItem(event -> LobbyBlockUtils.changePlayerAnimation(player, BreakAnimation.FALL));

        assert XMaterial.TNT.parseMaterial() != null;
        GuiItem explodeAnimation = ItemBuilder.from(XMaterial.TNT.parseMaterial()).name(Component.text("§cExploding Blocks")).asGuiItem(event -> LobbyBlockUtils.changePlayerAnimation(player, BreakAnimation.EXPLODE));

        ///

        gui.setItem(10, blocksMenu);

        gui.addItem(disableAnimation);
        gui.addItem(fallAnimation);
        gui.addItem(explodeAnimation);

        ///

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);

    }

}
