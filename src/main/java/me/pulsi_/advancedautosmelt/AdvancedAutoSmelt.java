package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExp;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExpCustom;
import me.pulsi_.advancedautosmelt.autosmelt.AutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.events.BlockBreakSmeltInv;
import me.pulsi_.advancedautosmelt.events.FortuneSupport;
import me.pulsi_.advancedautosmelt.managers.ConfigManager;
import me.pulsi_.advancedautosmelt.managers.Translator;
import me.pulsi_.advancedautosmelt.managers.UpdateChecker;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickup;
import me.pulsi_.advancedautosmelt.commands.TabCompletion;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AdvancedAutoSmelt extends JavaPlugin {

    ConfigManager messagesConfig;

    public void loadValues() {

        ConfigManager messages = new ConfigManager(this, "messages.yml");

        this.version = plugin.getDescription().getVersion();
        this.noPerm = messages.getConfig().getString("no-permission-message");
        this.reload = messages.getConfig().getString("reload-message");
        this.unknownCommand = messages.getConfig().getString("unknown-command");
        this.toggleOn = messages.getConfig().getString("toggled-on-message");
        this.toggleOff = messages.getConfig().getString("toggled-off-message");
        this.goldExp = plugin.getConfig().getInt("AutoSmelt.gold-exp");
        this.ironExp = plugin.getConfig().getInt("AutoSmelt.iron-exp");
        this.isDCM = plugin.getConfig().getBoolean("AutoSmelt.disable-creative-mode");
        this.isAutoPickupEnabled = plugin.getConfig().getBoolean("AutoPickup.enable-autopickup");
        this.isAutoPickupBlacklist = plugin.getConfig().getBoolean("AutoPickup.use-blacklist");
        this.blackList = plugin.getConfig().getStringList("AutoPickup.blacklist");
        this.isSmeltInv = plugin.getConfig().getBoolean("AutoSmelt.smelt-ores-in-inventory");
        this.isSmeltGold = plugin.getConfig().getBoolean("AutoSmelt.smelt-gold");
        this.isSmeltIron = plugin.getConfig().getBoolean("AutoSmelt.smelt-iron");
        this.isSmeltStone = plugin.getConfig().getBoolean("AutoSmelt.smelt-stone");
        this.isAutoPickupExp = plugin.getConfig().getBoolean("AutoPickup.autopickup-experience");
        this.isGivingGoldExp = plugin.getConfig().getBoolean("AutoSmelt.give-exp-gold");
        this.isGivingIronExp = plugin.getConfig().getBoolean("AutoSmelt.give-exp-iron");
        this.isEFS = plugin.getConfig().getBoolean("Fortune.enable-fortune-support");
        this.useWhitelist = plugin.getConfig().getBoolean("Fortune.use-whitelist");
        this.whiteList = plugin.getConfig().getStringList("Fortune.whitelist");

    }

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.messagesConfig = new ConfigManager(this, "messages.yml");

        getCommand("advancedautosmelt").setExecutor(new Commands(this));
        getCommand("advancedautosmelt").setTabCompleter(new TabCompletion());

        getServer().getPluginManager().registerEvents(new AutoPickup(this), this);
        getServer().getPluginManager().registerEvents(new FortuneSupport(this), this);
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
        getServer().getConsoleSender().sendMessage(Translator.c("&2Enabling Plugin! &bv%v%").replace("%v%", version));
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

    public ConfigManager getMessagesConfig() {
        return messagesConfig;
    }

    public static String getNoPerm() {
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

    public boolean isDCM() {
        return isDCM;
    }

    public boolean isAutoPickupEnabled() {
        return isAutoPickupEnabled;
    }

    public boolean isAutoPickupBlacklist() {
        return isAutoPickupBlacklist;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public boolean isSmeltInv() {
        return isSmeltInv;
    }

    public boolean isSmeltGold() {
        return isSmeltGold;
    }

    public boolean isSmeltIron() {
        return isSmeltIron;
    }

    public boolean isSmeltStone() {
        return isSmeltStone;
    }

    public boolean isAutoPickupExp() {
        return isAutoPickupExp;
    }

    public boolean isGivingGoldExp() {
        return isGivingGoldExp;
    }

    public boolean isGivingIronExp() {
        return isGivingIronExp;
    }

    public boolean isEFS() {
        return isEFS;
    }

    public boolean useWhitelist() {
        return useWhitelist;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }
}