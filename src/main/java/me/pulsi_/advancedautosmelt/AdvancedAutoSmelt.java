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

import java.util.List;

public final class AdvancedAutoSmelt extends JavaPlugin {

    ConfigManager messagesConfig;

    private String version;
    private String noPerm;
    private String reload;
    private String unknownCommand;
    private String toggleOn;
    private String toggleOff;

    private List<String> blackList;

    private boolean isAutoSmeltDCM;
    private boolean isAutoPickupEnabled;
    private boolean isAutoPickupBlacklist;
    private boolean isSmeltInv;
    private boolean isSmeltGold;
    private boolean isSmeltIron;
    private boolean isSmeltStone;
    private boolean isAutoPickupExp;
    private boolean isGivingGoldExp;
    private boolean isGivingIronExp;

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
        this.noPerm = messages.getConfig().getString("no-permission-message");
        this.reload = messages.getConfig().getString("reload-message");
        this.unknownCommand = messages.getConfig().getString("unknown-command");
        this.toggleOn = messages.getConfig().getString("toggled-on-message");
        this.toggleOff = messages.getConfig().getString("toggled-off-message");
        this.goldExp = this.getConfig().getInt("AutoSmelt.gold-exp");
        this.ironExp = this.getConfig().getInt("AutoSmelt.iron-exp");
        this.isAutoSmeltDCM = this.getConfig().getBoolean("AutoSmelt.disable-creative-mode");
        this.isAutoPickupEnabled = this.getConfig().getBoolean("AutoPickup.enable-autopickup");
        this.isAutoPickupBlacklist = this.getConfig().getBoolean("AutoPickup.use-blacklist");
        this.blackList = this.getConfig().getStringList("AutoPickup.blacklist");
        this.isSmeltInv = this.getConfig().getBoolean("AutoSmelt.smelt-ores-in-inventory");
        this.isSmeltGold = this.getConfig().getBoolean("AutoSmelt.smelt-gold");
        this.isSmeltIron = this.getConfig().getBoolean("AutoSmelt.smelt-iron");
        this.isSmeltStone = this.getConfig().getBoolean("AutoSmelt.smelt-stone");
        this.isAutoPickupExp = this.getConfig().getBoolean("AutoPickup.autopickup-experience");
        this.isGivingGoldExp = this.getConfig().getBoolean("AutoSmelt.give-exp-gold");
        this.isGivingIronExp = this.getConfig().getBoolean("AutoSmelt.give-exp-iron");

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

    public boolean isAutoSmeltDCM() { return isAutoSmeltDCM; }

    public boolean isAutoPickupEnabled() { return isAutoPickupEnabled; }

    public boolean isAutoPickupBlacklist() { return isAutoPickupBlacklist; }

    public List<String> getBlackList() { return blackList; }

    public boolean isSmeltInv() { return isSmeltInv; }

    public boolean isSmeltGold() { return isSmeltGold; }

    public boolean isSmeltIron() { return isSmeltIron; }

    public boolean isSmeltStone() { return isSmeltStone; }

    public boolean isAutoPickupExp() { return isAutoPickupExp; }

    public boolean isGivingGoldExp() { return isGivingGoldExp; }

    public boolean isGivingIronExp() { return isGivingIronExp; }

}