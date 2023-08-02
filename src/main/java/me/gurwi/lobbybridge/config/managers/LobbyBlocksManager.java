package me.gurwi.lobbybridge.config.managers;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.gurwi.lobbybridge.enums.NBTTag;
import me.gurwi.lobbybridge.glib.ResourceConfig;
import me.gurwi.lobbybridge.objects.LobbyBlock;
import me.gurwi.lobbybridge.utils.ChatColorUtils;
import me.gurwi.lobbybridge.utils.customlogger.CustomLogger;
import me.gurwi.lobbybridge.utils.customlogger.LoggerTag;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class LobbyBlocksManager {

    private final ResourceConfig resourceConfig;

    @Getter
    private final Map<String, LobbyBlock> blocksMap = new HashMap<>();

    public void load() {

        Configuration config = resourceConfig.getCustomConfig();

        blocksMap.clear();
        config.getRoot().getKeys(false).forEach(materialName -> {

            Optional<XMaterial> material = XMaterial.matchXMaterial(materialName.toUpperCase());

            if (!material.isPresent() || !material.get().isSupported()) {
                CustomLogger.log(LoggerTag.CONFIG_ERROR_TAG, "Invalid material found in " + resourceConfig.getCustomConfigFile().getName() + "! §4" + materialName + " §cis an invalid material.");
                return;
            }

            if (!Objects.requireNonNull(material.get().parseMaterial()).isBlock()) {
                CustomLogger.log(LoggerTag.CONFIG_ERROR_TAG, "Invalid material found in " + resourceConfig.getCustomConfigFile().getName() + "! §4" + materialName + " §chas to be a block.");
                return;
            }

            ItemStack itemStack = material.get().parseItem();
            assert itemStack != null;

            itemStack.setAmount(64);

            if (config.isSet(materialName + ".item.")) {
                ItemMeta meta = itemStack.getItemMeta();

                if (isValuePresent(materialName, "item.display-name")) {
                    meta.setDisplayName(ChatColorUtils.getFormattedString(resourceConfig.getCustomConfig().getString(materialName + ".item." + "display-name")));
                }

                if (isValuePresent(materialName, "item.lore")) {
                    meta.setLore(ChatColorUtils.getFormattedStringList(config.getStringList(materialName + ".item.lore")));
                }

                itemStack.setItemMeta(meta);
            }

            int breakDelay = 3;
            String permission = "";

            if (isValuePresent(materialName, "break-delay")) {
                breakDelay = config.getInt(materialName + ".break-delay");
            }

            if (isValuePresent(materialName, "permission")) {
                permission = config.getString(materialName + ".permission");
            }

            NBTItem nbti = new NBTItem(itemStack, true);
            nbti.setString(NBTTag.BLOCK_TAG.getTag(), materialName.toUpperCase());

            blocksMap.put(material.get().name(), new LobbyBlock(nbti.getItem(), breakDelay, permission));

            CustomLogger.log(LoggerTag.INFO_TAG, "Loaded block §f-> §6" + materialName);
        });

    }

    private boolean isValuePresent(String key, String value) {
        return resourceConfig.getCustomConfig().isSet((key + "." + value));
    }

}
