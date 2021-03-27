package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExp;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExpCustom;
import me.pulsi_.advancedautosmelt.autosmelt.AutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.events.BlockBreakSmeltInv;
import me.pulsi_.advancedautosmelt.managers.ConfigManager;
import me.pulsi_.advancedautosmelt.managers.Translator;
import me.pulsi_.advancedautosmelt.managers.UpdateChecker;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickup;
import me.pulsi_.advancedautosmelt.commands.TabCompletion;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedAutoSmelt extends JavaPlugin {

    ConfigManager messagesConfig;
    private String version;
    private String noPerm;
    private String reload;
    private String unknownCommand;
    private String toggleOn;
    private String toggleOff;
    private int goldExp;
    private int ironExp;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.messagesConfig = new ConfigManager(this, "messages.yml");

        getCommand("advancedautosmelt").setExecutor(new Commands(this));
        getCommand("advancedautosmelt").setTabCompleter(new TabCompletion());

        ConfigManager messages = new ConfigManager(this, "messages.yml");
        this.version = this.getDescription().getVersion();
        this.noPerm = messages.getConfig().getString("no_permission_message");
        this.reload = messages.getConfig().getString("reload_message");
        this.unknownCommand = messages.getConfig().getString("unknown_command");
        this.toggleOn = messages.getConfig().getString("toggled_on_message");
        this.toggleOff = messages.getConfig().getString("toggled_off_message");
        this.goldExp = this.getConfig().getInt("AutoSmelt.gold_exp");
        this.ironExp = this.getConfig().getInt("AutoSmelt.iron_exp");


        getServer().getPluginManager().registerEvents(new AutoPickup(this), this);
        getServer().getPluginManager().registerEvents(new AutoPickupExp(this), this);
        getServer().getPluginManager().registerEvents(new AutoSmelt(this), this);
        getServer().getPluginManager().registerEvents(new BlockBreakSmeltInv(this), this);
        getServer().getPluginManager().registerEvents(new AutoPickupExpCustom(this), this);
        getServer().getPluginManager().registerEvents(new UpdateChecker(this, 90587), this);
        
        getServer().getConsoleSender().sendMessage(Translator.c(""));
        getServer().getConsoleSender().sendMessage(Translator.c("&d             &a             _        &c_____                _ _   "));
        getServer().getConsoleSender().sendMessage(Translator.c("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  "));
        getServer().getConsoleSender().sendMessage(Translator.c("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ "));
        getServer().getConsoleSender().sendMessage(Translator.c("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|"));
        getServer().getConsoleSender().sendMessage(Translator.c("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ "));
        getServer().getConsoleSender().sendMessage(Translator.c("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|"));
        getServer().getConsoleSender().sendMessage(Translator.c(""));
        getServer().getConsoleSender().sendMessage(Translator.c("&2Enabling Plugin! &bv%v%")
                .replace("%v%", this.getDescription().getVersion()));
        getServer().getConsoleSender().sendMessage(Translator.c("&fAuthor: Pulsi_"));
        getServer().getConsoleSender().sendMessage(Translator.c(""));
    }

    @Override
    public void onDisable() {

        getServer().getConsoleSender().sendMessage(Translator.c(""));
        getServer().getConsoleSender().sendMessage(Translator.c("&d             &a             _        &c_____                _ _   "));
        getServer().getConsoleSender().sendMessage(Translator.c("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  "));
        getServer().getConsoleSender().sendMessage(Translator.c("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ "));
        getServer().getConsoleSender().sendMessage(Translator.c("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|"));
        getServer().getConsoleSender().sendMessage(Translator.c("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ "));
        getServer().getConsoleSender().sendMessage(Translator.c("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|"));
        getServer().getConsoleSender().sendMessage(Translator.c(""));
        getServer().getConsoleSender().sendMessage(Translator.c("&cDisabling Plugin"));
        getServer().getConsoleSender().sendMessage(Translator.c(""));

    }

    public String getNoPerm() {
        return noPerm;
    }

    public String getReload() {
        return reload;
    }

    public String getToggleOff() {
        return toggleOff;
    }

    public String getToggleOn() {
        return toggleOn;
    }

    public String getUnknownCommand() {
        return unknownCommand;
    }

    public String getVersion() {
        return version;
    }

    public int getGoldExp() {
        return goldExp;
    }

    public int getIronExp() {
        return ironExp;
    }
}