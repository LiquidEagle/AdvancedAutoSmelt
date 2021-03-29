package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;

import java.util.List;

public class ConfigValues {

    private AdvancedAutoSmelt plugin;
    public ConfigValues(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    private String version;
    private String noPerm;
    private String reload;
    private String unknownCommand;
    private String toggleOn;
    private String toggleOff;

    private List<String> blackList;
    private List<String> whiteList;

    private boolean isDCM;
    private boolean isAutoPickupEnabled;
    private boolean isAutoPickupBlacklist;
    private boolean isSmeltInv;
    private boolean isSmeltGold;
    private boolean isSmeltIron;
    private boolean isSmeltStone;
    private boolean isAutoPickupExp;
    private boolean isGivingGoldExp;
    private boolean isGivingIronExp;
    private boolean isEFS;
    private boolean useWhitelist;

    private int goldExp;
    private int ironExp;

    private ConfigManager messagesConfig;

    public void loadValues() {

        ConfigManager messages = new ConfigManager(plugin, "messages.yml");

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
