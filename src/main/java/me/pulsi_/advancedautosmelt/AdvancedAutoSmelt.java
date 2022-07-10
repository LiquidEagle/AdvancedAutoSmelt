package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.managers.DataManager;
import me.pulsi_.advancedautosmelt.placeholders.Placeholders;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    private static AdvancedAutoSmelt instance;
    private static boolean roseStackerHooked = false, placeholderApiHooked = false;

    @Override
    public void onEnable() {
        instance = this;
        DataManager.setupPlugin(this);

        if (getServer().getPluginManager().getPlugin("RoseStacker") != null) {
            roseStackerHooked = true;
            AASLogger.info("Hooked into RoseStacker!");
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderApiHooked = true;
            new Placeholders().register();
            AASLogger.info("Hooked into PlaceholderAPI!");
        }
    }

    @Override
    public void onDisable() {
        instance = this;
        DataManager.shutDown();
    }

    public static AdvancedAutoSmelt getInstance() {
        return instance;
    }

    public static boolean isRoseStackerHooked() {
        return roseStackerHooked;
    }

    public static boolean isPlaceholderApiHooked() {
        return placeholderApiHooked;
    }
}