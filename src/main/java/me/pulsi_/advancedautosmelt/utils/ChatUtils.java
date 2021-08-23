package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatUtils {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static void consoleMessage(String message) {
        JavaPlugin.getPlugin(AdvancedAutoSmelt.class).getServer().getConsoleSender().sendMessage(color(message));
    }
}