package me.gurwi.lobbybridge.config.managers;

import com.cryptomorin.xseries.XMaterial;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@RequiredArgsConstructor
public class BlocksConfigReader {

    private final ResourceConfig resourceConfig;
    private final Map<String, LobbyBlock> blockMap = new HashMap<>();

    public void load() {

        Configuration config = resourceConfig.getCustomConfig();

        blockMap.clear();
        config.getRoot().getKeys(false).forEach(materialName -> {

            Optional<XMaterial> material = XMaterial.matchXMaterial(materialName.toUpperCase());

            if (!material.isPresent() || !material.get().isSupported()) {
                CustomLogger.log(LoggerTag.CONFIG_ERROR_TAG, "Invalid material found in " + resourceConfig.getCustomConfigFile().getName() + "! §4" + materialName + " §cis an invalid material.");
                return;
            }

            if (!material.get().parseMaterial().isBlock()) {
                CustomLogger.log(LoggerTag.CONFIG_ERROR_TAG, "Invalid material found in " + resourceConfig.getCustomConfigFile().getName() + "! §4" + materialName + " §chas to be a block.");
                return;
            }

            ItemStack itemStack = material.get().parseItem();

            if (config.isSet(materialName + ".item.")) {
                assert itemStack != null;
                ItemMeta meta = itemStack.getItemMeta();

                if (isItemValuePresent(materialName, "display-name")) {
                    meta.setDisplayName(ChatColorUtils.getFormattedString(resourceConfig.getCustomConfig().getString(materialName + ".item." + "display-name")));
                }

                if (isItemValuePresent(materialName, "lore")) {
                    meta.setLore(ChatColorUtils.getFormattedStringList(config.getStringList(materialName + ".item.lore")));
                }

                itemStack.setItemMeta(meta);
            }

            

        });

    }

    private boolean isItemValuePresent(String key, String value) {
        return resourceConfig.getCustomConfig().isSet((key + ".item." + value));
    }

}
