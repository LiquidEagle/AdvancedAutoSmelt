package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.commands.TabCompletion;
import me.pulsi_.advancedautosmelt.externals.bStats;
import me.pulsi_.advancedautosmelt.listeners.AutoPickup;
import me.pulsi_.advancedautosmelt.externals.UpdateChecker;
import me.pulsi_.advancedautosmelt.listeners.InventoryAlerts;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;

public class DataManager {

    private final AdvancedAutoSmelt plugin;
    public DataManager(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new AutoPickup(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new UpdateChecker(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InventoryAlerts(plugin), plugin);
        new bStats(plugin, 11014);
    }

    public void setupCommands() {
        plugin.getCommand("advancedautosmelt").setExecutor(new Commands(plugin));
        plugin.getCommand("advancedautosmelt").setTabCompleter(new TabCompletion());
    }

    public void startupMessage() {
        ChatUtils.consoleMessage("");
        ChatUtils.consoleMessage("&d             &a             _        &c_____                _ _   ");
        ChatUtils.consoleMessage("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  ");
        ChatUtils.consoleMessage("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ ");
        ChatUtils.consoleMessage("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|");
        ChatUtils.consoleMessage("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ ");
        ChatUtils.consoleMessage("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|");
        ChatUtils.consoleMessage("");
        ChatUtils.consoleMessage("&2Enabling Plugin! &bv" + plugin.getDescription().getVersion());
        ChatUtils.consoleMessage("&fAuthor: Pulsi_");
        ChatUtils.consoleMessage("");
    }

    public void shutdownMessage() {
        ChatUtils.consoleMessage("");
        ChatUtils.consoleMessage("&d             &a             _        &c_____                _ _   ");
        ChatUtils.consoleMessage("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  ");
        ChatUtils.consoleMessage("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ ");
        ChatUtils.consoleMessage("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|");
        ChatUtils.consoleMessage("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ ");
        ChatUtils.consoleMessage("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|");
        ChatUtils.consoleMessage("");
        ChatUtils.consoleMessage("&cDisabling Plugin!");
        ChatUtils.consoleMessage("");
    }
}