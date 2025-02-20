package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.managers.AASConfigs;
import me.pulsi_.advancedautosmelt.managers.AASData;
import me.pulsi_.advancedautosmelt.placeholders.Placeholders;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    private static AdvancedAutoSmelt INSTANCE;

    private boolean placeholderApiHooked, worldGuardHooked, prisonEnchantsHooked;

    private AASConfigs configs;
    private AASData dataManager;

    private String serverVersion;
    private int serverVersionInt;

    private static Economy vaultEconomy = null;

    private int tries = 0;

    @Override
    public void onEnable() {
        INSTANCE = this;

        serverVersion = getServer().getVersion();
        String version = serverVersion.substring(serverVersion.lastIndexOf("MC:"), serverVersion.length() - 1);

        int number = -1;
        try {
            number = Integer.parseInt(version.split("\\.")[1]);
        } catch (NumberFormatException e) {
            AASLogger.error("Failed to identify server version, contact the developer if the issue persist!");
        }
        serverVersionInt = number;

        configs = new AASConfigs(this);
        dataManager = new AASData(this);
        dataManager.setupPlugin();

        if (ConfigValues.isAutoSellEnabled() && ConfigValues.isAutoSellUseVaultEconomy()) {
            if (!setupVault()) {
                if (tries == 3) AASLogger.warn("Could not setup Vault economy because it has not been found.");
                else {
                    Bukkit.getScheduler().runTaskLater(this, this::onEnable, 2);
                    AASLogger.warn("Could not setup Vault economy for autosell because it has not been found, trying again... (" + (tries + 1) + " try)");
                    tries++;
                    return;
                }
            }
        }

        PluginManager plManager = getServer().getPluginManager();
        if (plManager.getPlugin("PlaceholderAPI") != null) {
            placeholderApiHooked = true;
            new Placeholders().register();
            AASLogger.info("Hooked into PlaceholderAPI!");
        }

        if (plManager.getPlugin("WorldGuard") != null) {
            worldGuardHooked = true;
            AASLogger.info("Hooked into WorldGuard!");
        }
    }

    @Override
    public void onDisable() {
        dataManager.shutDown();
    }

    public static AdvancedAutoSmelt INSTANCE() {
        return INSTANCE;
    }

    private boolean setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;

        vaultEconomy = rsp.getProvider();
        return true;
    }

    public boolean isPlaceholderApiHooked() {
        return placeholderApiHooked;
    }

    public boolean isWorldGuardHooked() {
        return worldGuardHooked;
    }

    public boolean isPrisonEnchantsHooked() {
        return prisonEnchantsHooked;
    }

    public AASConfigs getConfigs() {
        return configs;
    }

    public AASData getDataManager() {
        return dataManager;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public int getServerVersionInt() {
        return serverVersionInt;
    }

    public static Economy getVaultEconomy() {
        return vaultEconomy;
    }
}