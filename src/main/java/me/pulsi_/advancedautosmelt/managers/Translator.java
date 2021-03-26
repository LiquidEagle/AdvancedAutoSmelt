package me.pulsi_.advancedautosmelt.managers;

import net.md_5.bungee.api.ChatColor;

public class Translator {

    public static String Colors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}