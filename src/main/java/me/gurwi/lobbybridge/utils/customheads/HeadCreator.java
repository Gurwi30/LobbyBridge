package me.gurwi.lobbybridge.utils.customheads;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@SuppressWarnings({"unused", "ConstantConditions"})
public class HeadCreator {
    public static ItemStack getCustomHead(String value) {

        ItemStack itemStack = XMaterial.PLAYER_HEAD.parseItem();
        if (value.isEmpty()) return itemStack;

        SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", value));

        try {

            Field field = headMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(headMeta, profile);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        itemStack.setItemMeta(headMeta);

        return itemStack;
    }

}
