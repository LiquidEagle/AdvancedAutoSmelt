package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConfigManager {

    private final AdvancedAutoSmelt plugin;
    private File messagesFile, configFile;
    private FileConfiguration messages, config;

    public ConfigManager(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void createConfigs() {
        messagesFile = new File(plugin.getDataFolder(), "Messages.yml");
        configFile = new File(plugin.getDataFolder(), "Config.yml");

        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdir();
            plugin.saveResource("Messages.yml", false);
        }
        if (!configFile.exists()) {
            configFile.getParentFile().mkdir();
            plugin.saveResource("Config.yml", false);
        }

        messages = new YamlConfiguration();
        config = new YamlConfiguration();

        try {
            messages.load(messagesFile);
            config.load(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
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