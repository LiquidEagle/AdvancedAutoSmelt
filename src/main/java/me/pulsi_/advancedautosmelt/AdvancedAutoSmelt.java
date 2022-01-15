package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.managers.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    private static AdvancedAutoSmelt instance;

    @Override
    public void onEnable() {
        instance = this;
        DataManager.setupPlugin(this);
    }

    @Override
    public void onDisable() {
        instance = this;
        DataManager.shutDown();
    }

    public static AdvancedAutoSmelt getInstance() {
        return instance;
    }
}