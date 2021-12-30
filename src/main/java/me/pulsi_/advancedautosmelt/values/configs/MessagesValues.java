package me.pulsi_.advancedautosmelt.values.configs;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

public class MessagesValues {

    private static String noPermission;
    private static String pluginReloaded;
    private static String autoPickupDisabled;
    private static String autoPickupActivated;
    private static String autoPickupDeactivated;
    private static String autoSmeltDisabled;
    private static String autoSmeltActivated;
    private static String autoSmeltDeactivated;
    private static String notPlayer;

    private final AdvancedAutoSmelt plugin;

    public MessagesValues(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void setupValues() {
        FileConfiguration messages = new ConfigManager(plugin).getMessages();

        noPermission = messages.getString("No-Permission");
        pluginReloaded = messages.getString("Plugin-Reloaded");
        autoPickupDisabled = messages.getString("AutoPickup-Disabled");
        autoPickupActivated = messages.getString("AutoPickup-Activated");
        autoPickupDeactivated = messages.getString("AutoPickup-Deactivated");
        autoSmeltDisabled = messages.getString("AutoSmelt-Disabled");
        autoSmeltActivated = messages.getString("AutoSmelt-Activated");
        autoSmeltDeactivated = messages.getString("AutoSmelt-Deactivated");
        notPlayer = messages.getString("Not-Player");
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getPluginReloaded() {
        return pluginReloaded;
    }

    public String getAutoPickupDisabled() {
        return autoPickupDisabled;
    }

    public String getAutoPickupActivated() {
        return autoPickupActivated;
    }

    public String getAutoPickupDeactivated() {
        return autoPickupDeactivated;
    }

    public String getAutoSmeltActivated() {
        return autoSmeltActivated;
    }

    public String getAutoSmeltDeactivated() {
        return autoSmeltDeactivated;
    }

    public String getAutoSmeltDisabled() {
        return autoSmeltDisabled;
    }

    public String getNotPlayer() {
        return notPlayer;
    }
}