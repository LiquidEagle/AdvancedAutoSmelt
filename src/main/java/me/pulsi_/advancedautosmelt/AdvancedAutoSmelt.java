package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExp;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExpCustom;
import me.pulsi_.advancedautosmelt.events.blocks.ChestBreak;
import me.pulsi_.advancedautosmelt.events.blocks.EnderChestBreak;
import me.pulsi_.advancedautosmelt.events.blocks.FurnaceBreak;
import me.pulsi_.advancedautosmelt.events.features.AutoPickSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.events.features.BlockBreakSmeltInv;
import me.pulsi_.advancedautosmelt.events.features.InvFullAlert;
import me.pulsi_.advancedautosmelt.events.supports.FortuneSupport;
import me.pulsi_.advancedautosmelt.events.supports.SilkTouchSupport;
import me.pulsi_.advancedautosmelt.managers.DataManager;
import me.pulsi_.advancedautosmelt.managers.Metrics;
import me.pulsi_.advancedautosmelt.managers.UpdateChecker;
import me.pulsi_.advancedautosmelt.commands.TabCompletion;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    DataManager dm = new DataManager(this);

    @Override
    public void onEnable() {

        //Values
        dm.initializeValues();
        //Create Config
        saveDefaultConfig();

        //Commands
        getCommand("advancedautosmelt").setExecutor(new Commands(this, dm));
        getCommand("advancedautosmelt").setTabCompleter(new TabCompletion());
        //AutoPickup
        getServer().getPluginManager().registerEvents(new AutoPickupExp(dm), this);
        getServer().getPluginManager().registerEvents(new AutoPickupExpCustom(dm), this);
        //AutoSmelt
        getServer().getPluginManager().registerEvents(new BlockBreakSmeltInv(dm), this);
        //Events / Supports
        getServer().getPluginManager().registerEvents(new AutoPickSmelt(dm), this);
        getServer().getPluginManager().registerEvents(new FortuneSupport(dm), this);
        getServer().getPluginManager().registerEvents(new SilkTouchSupport(dm), this);
        getServer().getPluginManager().registerEvents(new InvFullAlert(dm), this);
        getServer().getPluginManager().registerEvents(new ChestBreak(dm), this);
        getServer().getPluginManager().registerEvents(new FurnaceBreak(dm), this);
        getServer().getPluginManager().registerEvents(new EnderChestBreak(dm), this);
        //Update Checker
        getServer().getPluginManager().registerEvents(new UpdateChecker(this, 90587), this);
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
}