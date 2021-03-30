package me.pulsi_.advancedautosmelt.managers;

import org.bukkit.ChatColor;

public class Translator {

    public static String c(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}