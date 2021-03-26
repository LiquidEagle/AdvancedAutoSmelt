package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExp;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.events.BlockBreakSmeltInv;
import me.pulsi_.advancedautosmelt.managers.ConfigManager;
import me.pulsi_.advancedautosmelt.managers.Translator;
import me.pulsi_.advancedautosmelt.managers.UpdateChecker;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickup;
import me.pulsi_.advancedautosmelt.autosmelt.AutoSmeltGold;
import me.pulsi_.advancedautosmelt.autosmelt.AutoSmeltIron;
import me.pulsi_.advancedautosmelt.autosmelt.AutoSmeltStone;
import me.pulsi_.advancedautosmelt.commands.TabCompletion;
import me.pulsi_.advancedautosmelt.pickupexp.PickupExpGold;
import me.pulsi_.advancedautosmelt.pickupexp.PickupExpIron;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    private static AdvancedAutoSmelt instance;
    public static AdvancedAutoSmelt getInstance() {
        return instance;
    }

    ConfigManager messagesConfig;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        this.messagesConfig = new ConfigManager(this, "messages.yml");

        getCommand("advancedautosmelt").setExecutor(new Commands());
        getCommand("advancedautosmelt").setTabCompleter(new TabCompletion());

        getServer().getPluginManager().registerEvents(new AutoPickup(), this);
        getServer().getPluginManager().registerEvents(new AutoPickupExp(), this);

        getServer().getPluginManager().registerEvents(new BlockBreakSmeltInv(), this);

        getServer().getPluginManager().registerEvents(new PickupExpGold(), this);
        getServer().getPluginManager().registerEvents(new PickupExpIron(), this);

        getServer().getPluginManager().registerEvents(new AutoSmeltIron(), this);
        getServer().getPluginManager().registerEvents(new AutoSmeltGold(), this);
        getServer().getPluginManager().registerEvents(new AutoSmeltStone(), this);

        getServer().getPluginManager().registerEvents(new UpdateChecker(), this);

        getServer().getConsoleSender().sendMessage(Translator.Colors(""));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d             &a             _        &c_____                _ _   "));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  "));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ "));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|"));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ "));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|"));
        getServer().getConsoleSender().sendMessage(Translator.Colors(""));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&2Enabling Plugin! &bv%version%")
                .replace("%version%", this.getDescription().getVersion()));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&fAuthor: Pulsi_"));
        getServer().getConsoleSender().sendMessage(Translator.Colors(""));
    }

    @Override
    public void onDisable() {
        instance = this;

        getServer().getConsoleSender().sendMessage(Translator.Colors(""));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d             &a             _        &c_____                _ _   "));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  "));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ "));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|"));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ "));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|"));
        getServer().getConsoleSender().sendMessage(Translator.Colors(""));
        getServer().getConsoleSender().sendMessage(Translator.Colors("&cDisabling Plugin"));
        getServer().getConsoleSender().sendMessage(Translator.Colors(""));

    }
}