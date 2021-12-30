package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.values.Values;

public class AASLogger {
    public static void error(String error) {
        ChatUtils.log(Values.getConfig().getPrefix() + "&c[ERROR] " + error);
    }

    public static void warn(String warn) {
        ChatUtils.log(Values.getConfig().getPrefix() + "&a[WARN] " + warn);
    }

    public static void info(String info) {
        ChatUtils.log(Values.getConfig().getPrefix() + "&9[INFO] " + info);
    }
}