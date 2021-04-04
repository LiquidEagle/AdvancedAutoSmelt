package me.pulsi_.advancedautosmelt;

import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExp;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickupExpCustom;
import me.pulsi_.advancedautosmelt.autosmelt.AutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.events.BlockBreakSmeltInv;
import me.pulsi_.advancedautosmelt.events.ChestBreak;
import me.pulsi_.advancedautosmelt.events.FortuneSupport;
import me.pulsi_.advancedautosmelt.managers.Translator;
import me.pulsi_.advancedautosmelt.managers.UpdateChecker;
import me.pulsi_.advancedautosmelt.autopickup.AutoPickup;
import me.pulsi_.advancedautosmelt.commands.TabCompletion;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class AdvancedAutoSmelt extends JavaPlugin {

    private String noPerm;
    private String reload;
    private String version;
    private String toggleOn;
    private String toggleOff;
    private String unknownCommand;

    private List<String> blackList;
    private List<String> whiteList;
    private List<String> autoSmeltDisabledWorlds;
    private List<String> autoPickupDisabledWorlds;
    private List<String> fortuneDisabledWorlds;

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

    private int goldExp;
    private int ironExp;

    @Override
    public void onEnable() {

        version = this.getDescription().getVersion();
        noPerm = this.getConfig().getString("no-permission-message");
        reload = this.getConfig().getString("reload-message");
        unknownCommand = this.getConfig().getString("unknown-command");
        toggleOn = this.getConfig().getString("toggled-on-message");
        toggleOff = this.getConfig().getString("toggled-off-message");
        goldExp = this.getConfig().getInt("AutoSmelt.gold-exp");
        ironExp = this.getConfig().getInt("AutoSmelt.iron-exp");
        isDCM = this.getConfig().getBoolean("AutoSmelt.disable-creative-mode");
        isAutoPickupEnabled = this.getConfig().getBoolean("AutoPickup.enable-autopickup");
        isAutoPickupBlacklist = this.getConfig().getBoolean("AutoPickup.use-blacklist");
        blackList = this.getConfig().getStringList("AutoPickup.blacklist");
        isSmeltInv = this.getConfig().getBoolean("AutoSmelt.smelt-ores-in-inventory");
        isSmeltGold = this.getConfig().getBoolean("AutoSmelt.smelt-gold");
        isSmeltIron = this.getConfig().getBoolean("AutoSmelt.smelt-iron");
        isSmeltStone = this.getConfig().getBoolean("AutoSmelt.smelt-stone");
        isAutoPickupExp = this.getConfig().getBoolean("AutoPickup.autopickup-experience");
        isGivingGoldExp = this.getConfig().getBoolean("AutoSmelt.give-exp-gold");
        isGivingIronExp = this.getConfig().getBoolean("AutoSmelt.give-exp-iron");
        isEFS = this.getConfig().getBoolean("Fortune.enable-fortune-support");
        useWhitelist = this.getConfig().getBoolean("Fortune.use-whitelist");
        whiteList = this.getConfig().getStringList("Fortune.whitelist");
        isSmeltStoneInv = this.getConfig().getBoolean("AutoSmelt.inv-smelt.cobblestone");
        isSmeltGoldInv = this.getConfig().getBoolean("AutoSmelt.inv-smelt.gold-ore");
        isSmeltIronInv = this.getConfig().getBoolean("AutoSmelt.inv-smelt.iron-ore");
        autoSmeltDisabledWorlds = this.getConfig().getStringList("AutoSmelt.disabled-worlds");
        autoPickupDisabledWorlds = this.getConfig().getStringList("AutoPickup.disabled-worlds");
        fortuneDisabledWorlds = this.getConfig().getStringList("Fortune.disabled-worlds");
        useLegacySupp = this.getConfig().getBoolean("enable-legacy-support");

        saveDefaultConfig();

        getCommand("advancedautosmelt").setExecutor(new Commands(this));
        getCommand("advancedautosmelt").setTabCompleter(new TabCompletion());

        getServer().getPluginManager().registerEvents(new AutoPickup(this), this);
        getServer().getPluginManager().registerEvents(new FortuneSupport(this), this);
        getServer().getPluginManager().registerEvents(new ChestBreak(this), this);
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
        getServer().getConsoleSender().sendMessage(Translator.c("&2Enabling Plugin! &bv%v%").replace("%v%", this.getVersion()));
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

    public List<String> getBlackList() {
        return blackList;
    }

    public List<String> getAutoSmeltDisabledWorlds() {
        return autoSmeltDisabledWorlds;
    }

    public List<String> getAutoPickupDisabledWorlds() {
        return autoPickupDisabledWorlds;
    }

    public List<String> getFortuneDisabledWorlds() {
        return fortuneDisabledWorlds;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

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
}