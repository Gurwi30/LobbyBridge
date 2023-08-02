package me.gurwi.lobbybridge.nms;

import me.gurwi.lobbybridge.LobbyBridge;
import me.gurwi.lobbybridge.enums.Version;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {

    private static final LobbyBridge plugin = LobbyBridge.getInstance();

    public static Class<?> getNMSClass(String className) {

        String serverVersion = Version.getRawVersion(plugin);
        try {
            return Class.forName("net.minecraft.server." + serverVersion + "." + className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendPacket(Player player, Object packet) {

        try {

            Object playerHandle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = playerHandle.getClass().getField("playerConnection").get(playerHandle);

            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

}
