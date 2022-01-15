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
import me.pulsi_.advancedautosmelt.listeners.PlayerJoinListener;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import me.pulsi_.advancedautosmelt.values.configs.ConfigValues;
import me.pulsi_.advancedautosmelt.values.configs.MessagesValues;

public class DataManager {

    public static void setupPlugin(AdvancedAutoSmelt plugin) {
        long startTime = System.currentTimeMillis();
        long time;

        ChatUtils.log("");
        ChatUtils.log("  &8[&a&lAdvanced&9&lAuto&c&lSmelt&8] &3v" + plugin.getDescription().getVersion());
        ChatUtils.log("  &fEnabling plugin...");

        time = System.currentTimeMillis();
        loadConfigs(plugin, time);

        time = System.currentTimeMillis();
        registerCommands(plugin, time);

        time = System.currentTimeMillis();
        registerEvents(plugin, time);

        ChatUtils.log("  &2Done! &a(" + (System.currentTimeMillis() - startTime) + " total ms)");
        ChatUtils.log("");

        new bStats(plugin, 11014);
    }

    public static void shutDown() {
        ChatUtils.log("");
        ChatUtils.log("  &8[&a&lAdvanced&9&lAuto&c&lSmelt&8] &cDisabling plugin...");
        ChatUtils.log("");
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
        ChatUtils.log("  &fLoaded config files! &a(" + (System.currentTimeMillis() - time) + "ms)");
    }

    private static void registerCommands(AdvancedAutoSmelt plugin, long time) {
        plugin.getCommand("autopickup").setExecutor(new AutoPickupCmd());
        plugin.getCommand("autosmelt").setExecutor(new AutoSmeltCmd());
        plugin.getCommand("inventoryalerts").setExecutor(new InventoryAlertsCmd());
        plugin.getCommand("advancedautosmelt").setExecutor(new MainCmd(plugin));
        ChatUtils.log("  &fRegistered commands! &a(" + (System.currentTimeMillis() - time) + "ms)");
    }

    private static void registerEvents(AdvancedAutoSmelt plugin, long time) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockBreakListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GuiListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new UpdateChecker(plugin), plugin);
        ChatUtils.log("  &fRegistered events! &a(" + (System.currentTimeMillis() - time) + "ms)");
    }
}