package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.managers.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    @Override
    public void onEnable() {
        DataManager.setupPlugin(this);
    }

    @Override
    public void onDisable() {
        DataManager.shutDown();
    }
}