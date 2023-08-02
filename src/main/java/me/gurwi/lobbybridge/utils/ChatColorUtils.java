package me.gurwi.lobbybridge.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ChatColorUtils {

    public static List<String> getFormattedStringList(List<String> stringList) {
        List<String> formattedLore = new ArrayList<>();

        stringList.forEach(s -> formattedLore.add(getFormattedString(s)));
        return formattedLore;
    }

    public static String getFormattedString(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
