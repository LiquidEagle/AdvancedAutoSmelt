package me.pulsi_.advancedautosmelt.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String c(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}