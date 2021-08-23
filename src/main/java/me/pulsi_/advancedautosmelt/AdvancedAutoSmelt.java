package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.managers.ConfigManager;
import me.pulsi_.advancedautosmelt.managers.DataManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    DataManager dataManager;
    ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        configManager.createConfigs();

        this.dataManager = new DataManager(this);
        dataManager.setupCommands();
        dataManager.registerEvents();
        dataManager.startupMessage();
    }

    @Override
    public void onDisable() {
        dataManager.shutdownMessage();
    }
    public FileConfiguration config() {
        return configManager.getConfig();
    }
    public FileConfiguration messages() {
        return configManager.getMessages();
    }
    public void reloadConfigs() {
        configManager.reloadConfigs();
    }
}