package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.CmdRegisterer;
import me.pulsi_.advancedautosmelt.commands.baseCmds.*;
import me.pulsi_.advancedautosmelt.coreSystem.AdvancedAutoSmeltDropSystem;
import me.pulsi_.advancedautosmelt.coreSystem.ExtraFeatures;
import me.pulsi_.advancedautosmelt.external.UpdateChecker;
import me.pulsi_.advancedautosmelt.external.bStats;
import me.pulsi_.advancedautosmelt.listeners.ServerListener;
import me.pulsi_.advancedautosmelt.listeners.blockBreakListener.*;
import me.pulsi_.advancedautosmelt.utils.AASChat;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.utils.AASMessages;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import org.bukkit.plugin.PluginManager;

public class AASData {
    private final AdvancedAutoSmelt plugin;

    public AASData(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void setupPlugin() {
        long startTime = System.currentTimeMillis();

        AASLogger.log("");
        AASLogger.log("  " + AASChat.longPrefix + " &3v" + plugin.getDescription().getVersion());
        AASLogger.log("  &bEnabling plugin...");

        loadConfigs();
        reloadPlugin();
        registerCommands();
        registerEvents();

        AASLogger.log("  &2Done! &8(&9" + (System.currentTimeMillis() - startTime) + " total ms&8)");
        AASLogger.log("");

        new bStats(plugin);
    }

    public void shutDown() {
        AASLogger.log("");
        AASLogger.log("  " + AASChat.longPrefix + " &cDisabling plugin...");
        AASLogger.log("");
    }

    public boolean reloadPlugin() {
        try {
            ConfigValues.setupValues();
            AASMessages.loadMessages();

            AdvancedAutoSmeltDropSystem.loadDropSystem();
            ExtraFeatures.loadExtraFeatures();
        } catch (Exception e) {
            AASLogger.warn(e, "Something went wrong while trying to reload the plugin.");
            return false;
        }
        return true;
    }

    private void loadConfigs() {
        long time = System.currentTimeMillis();

        plugin.getConfigs().setupConfigFiles();
        ConfigValues.setupValues();
        AASMessages.loadMessages();

        AASLogger.log("  &aLoaded config files! &8(&9" + (System.currentTimeMillis() - time) + "ms&8)");
    }

    private void registerCommands() {
        long time = System.currentTimeMillis();

        plugin.getCommand("advancedautosmelt").setExecutor(new MainCmd());
        plugin.getCommand("advancedautosmelt").setTabCompleter(new MainCmd());

        plugin.getCommand("autopickup").setExecutor(new AutoPickupCmd());
        plugin.getCommand("autosmelt").setExecutor(new AutoSmeltCmd());
        plugin.getCommand("autosell").setExecutor(new AutoSellCmd());
        plugin.getCommand("inventoryalerts").setExecutor(new InventoryAlertsCmd());

        if (ConfigValues.isAutoSellRegisterSellAllCmd()) plugin.getCommand("sellall").setExecutor(new SellAllCmd());

        CmdRegisterer.registerCmds();

        AASLogger.log("  &aRegistered commands! &8(&9" + (System.currentTimeMillis() - time) + "ms&8)");
    }

    private void registerEvents() {
        long time = System.currentTimeMillis();

        PluginManager pManager = plugin.getServer().getPluginManager();
        pManager.registerEvents(new ServerListener(), plugin);
        pManager.registerEvents(new UpdateChecker(plugin), plugin);

        switch (ConfigValues.getBlockBreakListenerPriority()) {
            case "HIGHEST":
                pManager.registerEvents(new BlockBreakListenerHighest(), plugin);
                break;

            case "HIGH":
                pManager.registerEvents(new BlockBreakListenerHigh(), plugin);
                break;

            case "LOW":
                pManager.registerEvents(new BlockBreakListenerLow(), plugin);
                break;

            case "LOWEST":
                pManager.registerEvents(new BlockBreakListenerLowest(), plugin);
                break;

            default:
                pManager.registerEvents(new BlockBreakListenerNormal(), plugin);
        }

        AASLogger.log("  &aRegistered events! &8(&9" + (System.currentTimeMillis() - time) + "ms&8)");
    }
}