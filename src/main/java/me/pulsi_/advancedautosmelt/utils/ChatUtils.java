package me.pulsi_.advancedautosmelt.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ChatUtils {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }
}