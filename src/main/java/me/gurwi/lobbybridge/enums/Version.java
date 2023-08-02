package me.gurwi.glib;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@SuppressWarnings("unused")
public enum Version {

    V1_7_R1,
    V1_8_R1,
    V1_9_R1,
    V1_10_R1,
    V1_11_R1,
    V1_12_R1,
    V1_13_R1,
    V1_14_R1,
    V1_15_R1,
    V1_16_R1,
    V1_17_R1,
    V1_18_R1,
    V1_19_R1,
    V1_20_R1;

    public static Version getVersion(JavaPlugin plugin) {

        String serverVer = plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
        return Version.valueOf(serverVer);

    }

    public static String getRawVersion(JavaPlugin plugin) {
        return plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public boolean isLegacy() {
        return isOlderThan(Version.V1_13_R1);
    }

    public boolean isOlderThan(Version version) {
        return Arrays.asList(Version.values()).indexOf(this) < Arrays.asList(Version.values()).indexOf(version);
    }

    public boolean isNewerThan(Version version) {
        return Arrays.asList(Version.values()).indexOf(this) < Arrays.asList(Version.values()).indexOf(version);
    }

}
