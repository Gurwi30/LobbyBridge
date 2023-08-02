package me.gurwi.lobbybridge.nms;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class BlockBreakAnimation {

    public static void breakBlock(int entityId, Block block, int step) {

        if (step > 9) step = 9;

        try {

            Object blockPosition = ReflectionUtils.getNMSClass("BlockPosition").getConstructor(int.class, int.class, int.class).newInstance(block.getX(), block.getY(), block.getZ());
            Object blockBreakAnimation = ReflectionUtils.getNMSClass("PacketPlayOutBlockBreakAnimation")
                    .getConstructor(int.class, ReflectionUtils.getNMSClass("BlockPosition"), int.class).newInstance(entityId, blockPosition, step);


            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> ReflectionUtils.sendPacket(onlinePlayer, blockBreakAnimation));

        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @SneakyThrows
    public static int getNextEntityId() {

        Class<?> nmsEntityClass = ReflectionUtils.getNMSClass("Entity");
        Field entityIdField = nmsEntityClass.getDeclaredField("entityCount");
        entityIdField.setAccessible(true);

        int currentId = entityIdField.getInt(nmsEntityClass);
        int nextId = currentId + 1;

        entityIdField.set(nmsEntityClass, nextId);

        return nextId;
    }

}
