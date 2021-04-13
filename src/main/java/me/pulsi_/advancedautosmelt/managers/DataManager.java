package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;

import java.util.List;

public class DataManager {

    private String noPerm;
    private String reload;
    private String version;
    private String toggleOn;
    private String toggleOff;
    private String unknownCommand;

    private List<String> blackList;
    private List<String> whiteList;
    private List<String> worldsBlackList;

    private boolean isDCM;
    private boolean isAutoPickupEnabled;
    private boolean isAutoPickupBlacklist;
    private boolean isSmeltInv;
    private boolean isSmeltStoneInv;
    private boolean isSmeltGoldInv;
    private boolean isSmeltIronInv;
    private boolean isSmeltGold;
    private boolean isSmeltIron;
    private boolean isSmeltStone;
    private boolean isAutoPickupExp;
    private boolean isGivingGoldExp;
    private boolean isGivingIronExp;
    private boolean isEFS;
    private boolean useWhitelist;
    private boolean useLegacySupp;
    private boolean smeltEnderChest;

    private int goldExp;
    private int ironExp;

    private AdvancedAutoSmelt plugin;
    public DataManager(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void initializeValues() {
        version = plugin.getDescription().getVersion();
        noPerm = plugin.getConfig().getString("no-permission-message");
        reload = plugin.getConfig().getString("reload-message");
        unknownCommand = plugin.getConfig().getString("unknown-command");
        toggleOn = plugin.getConfig().getString("toggled-on-message");
        toggleOff = plugin.getConfig().getString("toggled-off-message");
        goldExp = plugin.getConfig().getInt("AutoSmelt.gold-exp");
        ironExp = plugin.getConfig().getInt("AutoSmelt.iron-exp");
        isDCM = plugin.getConfig().getBoolean("AutoSmelt.disable-creative-mode");
        isAutoPickupEnabled = plugin.getConfig().getBoolean("AutoPickup.enable-autopickup");
        isAutoPickupBlacklist = plugin.getConfig().getBoolean("AutoPickup.use-blacklist");
        blackList = plugin.getConfig().getStringList("AutoPickup.blacklist");
        isSmeltInv = plugin.getConfig().getBoolean("AutoSmelt.smelt-ores-in-inventory");
        isSmeltGold = plugin.getConfig().getBoolean("AutoSmelt.smelt-gold");
        isSmeltIron = plugin.getConfig().getBoolean("AutoSmelt.smelt-iron");
        isSmeltStone = plugin.getConfig().getBoolean("AutoSmelt.smelt-stone");
        isAutoPickupExp = plugin.getConfig().getBoolean("AutoPickup.autopickup-experience");
        isGivingGoldExp = plugin.getConfig().getBoolean("AutoSmelt.give-exp-gold");
        isGivingIronExp = plugin.getConfig().getBoolean("AutoSmelt.give-exp-iron");
        isEFS = plugin.getConfig().getBoolean("Fortune.enable-fortune-support");
        useWhitelist = plugin.getConfig().getBoolean("Fortune.use-whitelist");
        whiteList = plugin.getConfig().getStringList("Fortune.whitelist");
        isSmeltStoneInv = plugin.getConfig().getBoolean("AutoSmelt.inv-smelt.cobblestone");
        isSmeltGoldInv = plugin.getConfig().getBoolean("AutoSmelt.inv-smelt.gold-ore");
        isSmeltIronInv = plugin.getConfig().getBoolean("AutoSmelt.inv-smelt.iron-ore");
        worldsBlackList = plugin.getConfig().getStringList("Disabled-Worlds");
        useLegacySupp = plugin.getConfig().getBoolean("enable-legacy-support");
        smeltEnderChest = plugin.getConfig().getBoolean("AutoSmelt.smelt-enderchest");
    }

    public void startupMessage() {
        plugin.getServer().getConsoleSender().sendMessage(Translator.c(""));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d             &a             _        &c_____                _ _   "));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  "));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ "));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|"));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ "));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|"));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c(""));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&2Enabling Plugin! &bv%v%").replace("%v%", this.getVersion()));
        if (plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            plugin.getServer().getConsoleSender().sendMessage(Translator.c("&9WorldGuard Hooked Up!"));
        }
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&fAuthor: Pulsi_"));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c(""));
    }

    public void shutdownMessage() {
        plugin.getServer().getConsoleSender().sendMessage(Translator.c(""));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d             &a             _        &c_____                _ _   "));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d     /\\     &a   /\\        | |      &c/ ____|              | | |  "));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d    /  \\    &a  /  \\  _   _| |_ ___&c| (___  _ __ ___   ___| | |_ "));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d   / /\\ \\  &a  / /\\ \\| | | | __/ _ \\&c\\___ \\| '_ ` _ \\ / _ \\ | __|"));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d  / ____ \\  &a/ ____ \\ |_| | || (_) |&c___) | | | | | |  __/ | |_ "));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&d /_/    \\_\\&a/_/    \\_\\__,_|\\__\\___/&c_____/|_| |_| |_|\\___|_|\\__|"));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c(""));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c("&cDisabling Plugin!"));
        plugin.getServer().getConsoleSender().sendMessage(Translator.c(""));
    }

    //Strings
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
    //Strings

    //Ints
    public int getGoldExp() {
        return goldExp;
    }
    public int getIronExp() {
        return ironExp;
    }
    //Ints

    //Lists
    public List<String> getBlackList() {
        return blackList;
    }
    public List<String> getWorldsBlackList() {
        return worldsBlackList;
    }
    public List<String> getWhiteList() {
        return whiteList;
    }
    //Lists

    //Booleans
    public boolean isSmeltInv() {
        return isSmeltInv;
    }
    public boolean isSmeltStoneInv() {
        return isSmeltStoneInv;
    }
    public boolean isSmeltIronInv() {
        return isSmeltIronInv;
    }
    public boolean isSmeltGoldInv() {
        return isSmeltGoldInv;
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
    public boolean isDCM() {
        return isDCM;
    }
    public boolean isAutoPickupEnabled() {
        return isAutoPickupEnabled;
    }
    public boolean isAutoPickupBlacklist() {
        return isAutoPickupBlacklist;
    }
    public boolean isUseLegacySupp() {
        return useLegacySupp;
    }
    public boolean isSmeltEnderChest() {
        return smeltEnderChest;
    }
    //Booleans
}