package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExp;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExpCustom;
import me.pulsi_.advancedautosmelt.events.blocks.ChestBreak;
import me.pulsi_.advancedautosmelt.events.blocks.EnderChestBreak;
import me.pulsi_.advancedautosmelt.events.blocks.FurnaceBreak;
import me.pulsi_.advancedautosmelt.events.features.AutoPickSmelt;
import me.pulsi_.advancedautosmelt.events.features.BlockBreakSmeltInv;
import me.pulsi_.advancedautosmelt.events.features.InvFullAlert;
import me.pulsi_.advancedautosmelt.events.supports.FortuneSupport;
import me.pulsi_.advancedautosmelt.events.supports.SilkTouchSupport;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;

public class DataManager {

    private AdvancedAutoSmelt plugin;
    public DataManager(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new AutoPickupExp(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AutoPickupExpCustom(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockBreakSmeltInv(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AutoPickSmelt(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new FortuneSupport(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SilkTouchSupport(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new InvFullAlert(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ChestBreak(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new FurnaceBreak(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EnderChestBreak(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new UpdateChecker(plugin, 90587), plugin);
    }

    public void startupMessage() {
        plugin.getServer().getConsoleSender().sendMessage("");
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d             &a             _        &c_____                _ _   "));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  "));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ "));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|"));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ "));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|"));
        plugin.getServer().getConsoleSender().sendMessage("");
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&2Enabling Plugin! &bv%v%").replace("%v%", plugin.getDescription().getVersion()));
        if (plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&9WorldGuard Hooked Up!"));
        }
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&fAuthor: Pulsi_"));
        plugin.getServer().getConsoleSender().sendMessage("");
    }

    public void shutdownMessage() {
        plugin.getServer().getConsoleSender().sendMessage("");
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d             &a             _        &c_____                _ _   "));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  "));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ "));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|"));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ "));
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|"));
        plugin.getServer().getConsoleSender().sendMessage("");
        plugin.getServer().getConsoleSender().sendMessage(ChatUtils.c("&cDisabling Plugin!"));
        plugin.getServer().getConsoleSender().sendMessage("");
    }
}