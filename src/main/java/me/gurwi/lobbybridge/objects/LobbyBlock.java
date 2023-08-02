package me.gurwi.lobbybridge.objects;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.gurwi.lobbybridge.utils.LobbyBlockUtils;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public class LobbyBlock {

    private final ItemStack itemStack;
    private final int breakDelay;
    private final String permission;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LobbyBlock)) return false;

        LobbyBlock comparedBlock = (LobbyBlock) obj;
        return XMaterial.matchXMaterial(itemStack).equals(XMaterial.matchXMaterial(comparedBlock.getItemStack()));

    }
}
