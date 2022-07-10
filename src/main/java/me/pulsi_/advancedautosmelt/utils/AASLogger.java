package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.values.Values;

public class AASLogger {
    public static void error(String error) {
        AASChat.log(Values.getConfig().getPrefix() + "&c[ERROR] " + error);
    }

    public static void warn(String warn) {
        AASChat.log(Values.getConfig().getPrefix() + "&a[WARN] " + warn);
    }

    public static void info(String info) {
        AASChat.log(Values.getConfig().getPrefix() + "&9[INFO] " + info);
    }
}