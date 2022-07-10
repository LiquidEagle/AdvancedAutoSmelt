package me.pulsi_.advancedautosmelt.values;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.values.configs.ConfigValues;
import me.pulsi_.advancedautosmelt.values.configs.MessagesValues;
import org.bukkit.plugin.java.JavaPlugin;

public class Values {

    private static final AdvancedAutoSmelt plugin = JavaPlugin.getPlugin(AdvancedAutoSmelt.class);

    public static ConfigValues getConfig() {
        return new ConfigValues(plugin);
    }

    public static MessagesValues getMessages() {
        return new MessagesValues(plugin);
    }
}