package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AASChat {

    private final static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String prefix = "&8[&a&lA&9&lA&c&lS&8]", longPrefix = "&a&lAdvanced&9&lAuto&c&lSmelt";

    public static String color(String message) {
        AdvancedAutoSmelt pl = AdvancedAutoSmelt.INSTANCE();
        if (pl != null && pl.getServerVersionInt() >= 16) {
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}