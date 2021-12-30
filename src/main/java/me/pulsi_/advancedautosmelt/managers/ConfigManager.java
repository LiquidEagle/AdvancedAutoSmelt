package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final AdvancedAutoSmelt plugin;
    private static File messagesFile, configFile;
    private static FileConfiguration messages, config;

    public ConfigManager(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void createConfigs() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }

        config = new YamlConfiguration();
        messages = new YamlConfiguration();

        try {
            config.load(configFile);
            messages.load(messagesFile);
            config = YamlConfiguration.loadConfiguration(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            AASLogger.error(e.getMessage());
        }
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void reloadConfigs() {
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}