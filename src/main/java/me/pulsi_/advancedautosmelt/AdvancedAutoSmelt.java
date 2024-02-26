package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.managers.AASConfigs;
import me.pulsi_.advancedautosmelt.managers.AASData;
import me.pulsi_.advancedautosmelt.placeholders.Placeholders;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    private static AdvancedAutoSmelt INSTANCE;

    private boolean placeholderApiHooked = false;

    private AASConfigs configs;
    private AASData dataManager;

    private String serverVersion;
    private int serverVersionInt;

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

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderApiHooked = true;
            new Placeholders().register();
            AASLogger.info("Hooked into PlaceholderAPI!");
        }
    }

    @Override
    public void onDisable() {
        dataManager.shutDown();
    }

    public static AdvancedAutoSmelt INSTANCE() {
        return INSTANCE;
    }

    public boolean isPlaceholderApiHooked() {
        return placeholderApiHooked;
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
}