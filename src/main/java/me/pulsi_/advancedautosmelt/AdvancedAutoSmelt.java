package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.managers.ConfigManager;
import me.pulsi_.advancedautosmelt.managers.DataManager;
import me.pulsi_.advancedautosmelt.managers.Metrics;
import me.pulsi_.advancedautosmelt.commands.TabCompletion;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    DataManager dm = new DataManager(this);
    ConfigManager cm = new ConfigManager(this);

    @Override
    public void onEnable() {

        //Create Configs
        cm.createConfigs();

        //Commands
        getCommand("advancedautosmelt").setExecutor(new Commands(this));
        getCommand("advancedautosmelt").setTabCompleter(new TabCompletion());

        //Register Events
        dm.registerEvents();

        //Metrics
        new Metrics(this, 11014);

        //Startup Message
        dm.startupMessage();
    }

    @Override
    public void onDisable() {

        //Shutdown Message
        dm.shutdownMessage();
    }
    public FileConfiguration getConfiguration() {
        return cm.getConfig();
    }
    public FileConfiguration getMessages() {
        return cm.getMessages();
    }
    public void reloadConfigs() {
        cm.reloadConfigs();
    }
}