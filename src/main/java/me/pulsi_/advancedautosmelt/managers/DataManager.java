package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.AutoPickupCmd;
import me.pulsi_.advancedautosmelt.commands.AutoSmeltCmd;
import me.pulsi_.advancedautosmelt.commands.InventoryAlertsCmd;
import me.pulsi_.advancedautosmelt.commands.MainCmd;
import me.pulsi_.advancedautosmelt.external.UpdateChecker;
import me.pulsi_.advancedautosmelt.external.bStats;
import me.pulsi_.advancedautosmelt.listeners.BlockBreakListener;
import me.pulsi_.advancedautosmelt.listeners.GuiListener;
import me.pulsi_.advancedautosmelt.listeners.PickupListener;
import me.pulsi_.advancedautosmelt.listeners.PlayerJoinListener;
import me.pulsi_.advancedautosmelt.utils.AASChat;
import me.pulsi_.advancedautosmelt.utils.MapUtils;
import me.pulsi_.advancedautosmelt.values.configs.ConfigValues;
import me.pulsi_.advancedautosmelt.values.configs.MessagesValues;

public class DataManager {

    public static void setupPlugin(AdvancedAutoSmelt plugin) {
        long startTime = System.currentTimeMillis();
        long time;

        AASChat.log("");
        AASChat.log("  &8[&a&lAdvanced&9&lAuto&c&lSmelt&8] &3v" + plugin.getDescription().getVersion());
        AASChat.log("  &fEnabling plugin...");
        new MapUtils();

        time = System.currentTimeMillis();
        loadConfigs(plugin, time);

        time = System.currentTimeMillis();
        registerCommands(plugin, time);

        time = System.currentTimeMillis();
        registerEvents(plugin, time);

        AASChat.log("  &2Done! &a(" + (System.currentTimeMillis() - startTime) + " total ms)");
        AASChat.log("");

        new bStats(plugin, 11014);
    }

    public static void shutDown() {
        AASChat.log("");
        AASChat.log("  &8[&a&lAdvanced&9&lAuto&c&lSmelt&8] &cDisabling plugin...");
        AASChat.log("");
    }

    public static void reloadPlugin(AdvancedAutoSmelt plugin) {
        new ConfigManager(plugin).reloadConfigs();
        new ConfigValues(plugin).setupValues();
        new MessagesValues(plugin).setupValues();
    }

    private static void loadConfigs(AdvancedAutoSmelt plugin, long time) {
        new ConfigManager(plugin).createConfigs();
        new ConfigValues(plugin).setupValues();
        new MessagesValues(plugin).setupValues();
        AASChat.log("  &fLoaded config files! &a(" + (System.currentTimeMillis() - time) + "ms)");
    }

    private static void registerCommands(AdvancedAutoSmelt plugin, long time) {
        plugin.getCommand("autopickup").setExecutor(new AutoPickupCmd());
        plugin.getCommand("autosmelt").setExecutor(new AutoSmeltCmd());
        plugin.getCommand("inventoryalerts").setExecutor(new InventoryAlertsCmd());
        plugin.getCommand("advancedautosmelt").setExecutor(new MainCmd(plugin));
        plugin.getCommand("advancedautosmelt").setTabCompleter(new MainCmd(plugin));
        AASChat.log("  &fRegistered commands! &a(" + (System.currentTimeMillis() - time) + "ms)");
    }

    private static void registerEvents(AdvancedAutoSmelt plugin, long time) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockBreakListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GuiListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PickupListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new UpdateChecker(plugin), plugin);
        AASChat.log("  &fRegistered events! &a(" + (System.currentTimeMillis() - time) + "ms)");
    }
}