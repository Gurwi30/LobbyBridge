package me.gurwi.lobbybridge.enums;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@SuppressWarnings("unused")
public enum Version {

    V1_7,
    V1_8,
    V1_9,
    V1_10,
    V1_11,
    V1_12,
    V1_13,
    V1_14,
    V1_15,
    V1_16,
    V1_17,
    V1_18,
    V1_19,
    V1_20;

    public static Version getVersion(JavaPlugin plugin) {

        String serverVer = plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
        String[] splittedVersion = serverVer.split("_");

        return Version.valueOf((splittedVersion[0] + "_" + splittedVersion[1]).toUpperCase());

    }

    public static String getRawVersion(JavaPlugin plugin) {
        return plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public boolean isLegacy() {
        return isOlderThan(Version.V1_13);
    }

    public boolean isOlderThan(Version version) {
        return Arrays.asList(Version.values()).indexOf(this) < Arrays.asList(Version.values()).indexOf(version);
    }

    public boolean isNewerThan(Version version) {
        return Arrays.asList(Version.values()).indexOf(this) < Arrays.asList(Version.values()).indexOf(version);
    }

}
