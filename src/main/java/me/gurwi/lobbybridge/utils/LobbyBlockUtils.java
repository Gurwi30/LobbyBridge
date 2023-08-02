package me.gurwi.lobbybridge.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import me.gurwi.lobbybridge.cache.PlayersManager;
import me.gurwi.lobbybridge.config.managers.ConfigManager;
import me.gurwi.lobbybridge.config.managers.LobbyBlocksManager;
import me.gurwi.lobbybridge.enums.BreakAnimation;
import me.gurwi.lobbybridge.enums.NBTTag;
import me.gurwi.lobbybridge.objects.LobbyBlock;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LobbyBlockUtils {

    private static final LobbyBlocksManager lobbyBlocksManager = PluginCustomLoader.getInstance().getConfigLoader().getLobbyBlocksManager();
    private static final PlayersManager playersManager = PlayersManager.getInstance();

    @Getter
    private static final LobbyBlock defaultBlock = lobbyBlocksManager.getBlocksMap().get(ConfigManager.DEFAULT_BLOCK.getString());

    public static boolean isLobbyBlock(ItemStack itemStack) {

        if (itemStack == null || XMaterial.matchXMaterial(itemStack) == XMaterial.AIR) return false;
        if (!lobbyBlockExits(XMaterial.matchXMaterial(itemStack))) return false;

        NBTItem nbti = new NBTItem(itemStack);
        return nbti.hasTag(NBTTag.BLOCK_TAG.getTag());

    }

    public static boolean lobbyBlockExits(XMaterial material) {
        return lobbyBlocksManager.getBlocksMap().containsKey(material.name());
    }

    public static LobbyBlock getLobbyBlock(XMaterial material) {
        return lobbyBlocksManager.getBlocksMap().get(material.name());
    }

    public static void changePlayerBlock(Player player, LobbyBlock lobbyBlock) {

        ItemStack itemStack = lobbyBlock.getItemStack();
        itemStack.setAmount(64);

        player.getInventory().setItem(ConfigManager.GIVE_SLOT.getInt(), itemStack);
        PlayersManager.getInstance().getPlayersMap().get(player.getUniqueId()).setSelectedBlock(lobbyBlock);
    }

    public static void changePlayerAnimation(Player player, BreakAnimation breakAnimation) {
        PlayersManager.getInstance().getPlayersMap().get(player.getUniqueId()).setBreakAnimation(breakAnimation);
    }

    public static LobbyBlock getPlayerCurrentBlock(Player player) {
        return playersManager.getPlayersMap().get(player.getUniqueId()).getSelectedBlock();
    }

}
