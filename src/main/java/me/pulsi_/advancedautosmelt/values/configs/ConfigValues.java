package me.pulsi_.advancedautosmelt.values.configs;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.managers.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigValues {

    private static String prefix;

    private static boolean updateCheckerEnabled;

    private static String autoPickupJoinPermission;
    private static List<String> autoPickupWorldBlacklist;
    private static List<String> autoPickupBlockBlacklist;
    private static boolean autoPickupEnabled;
    private static boolean autoPickupEnabledOnJoin;
    private static boolean autoPickupEnabledOnJoinNeedPerm;
    private static boolean processPlayerPickupEvent;
    private static boolean dropItemsOnInventoryFull;
    private static boolean dropNeedCorrectItem;
    private static boolean enableLegacySupport;

    private static String autoSmeltJoinPermission;
    private static List<String> autoSmeltList;
    private static List<String> autoSmeltWorldBlacklist;
    private static boolean autoSmeltEnabled;
    private static boolean autoSmeltEnabledOnJoin;
    private static boolean autoSmeltEnabledOnJoinNeedPerm;

    private static List<String> fortuneWhitelist;
    private static List<String> fortuneWorldBlacklist;
    private static boolean fortuneEnabled;
    private static boolean fortuneWhitelistEnabled;
    private static boolean fortuneWhitelistIsBlacklist;
    private static String fortunePermission;
    private static boolean fortuneRequirePermission;

    private static List<String> customExpList;
    private static boolean customExpEnabled;

    private static boolean inventorySmelterEnabled;
    private static List<String> inventorySmelterList;

    private static boolean ingotToBlockEnabled;
    private static List<String> ingotToBlockList;

    private static String inventoryAlertsTitle;
    private static String inventoryAlertsSound;
    private static String inventoryAlertsActionbar;
    private static List<String> inventoryAlertsMessage;
    private static int inventoryAlertsDelay;
    private static boolean inventoryAlertsEnabled;
    private static boolean inventoryAlertsTitleEnabled;
    private static boolean inventoryAlertsSoundEnabled;
    private static boolean inventoryAlertsActionbarEnabled;
    private static boolean inventoryAlertsMessageEnabled;

    private static String enabledPlaceholder;
    private static String disabledPlaceholder;

    private final AdvancedAutoSmelt plugin;

    public ConfigValues(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void setupValues() {
        FileConfiguration config = new ConfigManager(plugin).getConfig();

        prefix = config.getString("Prefix");

        updateCheckerEnabled = config.getBoolean("Update-Checker");

        autoPickupJoinPermission = config.getString("AutoPickup.Enabled-On-Join.Permission");
        autoPickupWorldBlacklist = config.getStringList("AutoPickup.World-Blacklist");
        autoPickupBlockBlacklist = config.getStringList("AutoPickup.Block-Blacklist");
        autoPickupEnabled = config.getBoolean("AutoPickup.Enabled");
        autoPickupEnabledOnJoin = config.getBoolean("AutoPickup.Enabled-On-Join.Enabled");
        autoPickupEnabledOnJoinNeedPerm = config.getBoolean("AutoPickup.Enabled-On-Join.Is-Required-Permission");
        processPlayerPickupEvent = config.getBoolean("AutoPickup.Process-PlayerPickupEvent");
        dropItemsOnInventoryFull = config.getBoolean("AutoPickup.Drop-Items-On-Inventory-Full");
        dropNeedCorrectItem = config.getBoolean("AutoPickup.Drops-Need-Correct-Item");
        enableLegacySupport = config.getBoolean("AutoPickup.Enable-Legacy-Support");

        autoSmeltJoinPermission = config.getString("AutoSmelt.Enabled-On-Join.Permission");
        autoSmeltList = config.getStringList("AutoSmelt.Smelt-List");
        autoSmeltWorldBlacklist = config.getStringList("AutoSmelt.World-Blacklist");
        autoSmeltEnabled = config.getBoolean("AutoSmelt.Enabled");
        autoSmeltEnabledOnJoinNeedPerm = config.getBoolean("AutoSmelt.Enabled-On-Join.Is-Required-Permission");
        autoSmeltEnabledOnJoin = config.getBoolean("AutoSmelt.Enabled-On-Join.Enabled");

        fortuneWhitelist = config.getStringList("Fortune.Block-Whitelist.Whitelist");
        fortuneWorldBlacklist = config.getStringList("Fortune.World-Blacklist");
        fortuneEnabled = config.getBoolean("Fortune.Enabled");
        fortuneWhitelistEnabled = config.getBoolean("Fortune.Block-Whitelist.Enabled");
        fortuneWhitelistIsBlacklist = config.getBoolean("Fortune.Block-Whitelist.Whitelist-Is-Blacklist");
        fortunePermission = config.getString("Fortune.Is-Required-Permission.Permission");
        fortuneRequirePermission = config.getBoolean("Fortune.Is-Required-Permission.Enabled");

        customExpList = config.getStringList("Custom-Exp.Exp-List");
        customExpEnabled = config.getBoolean("Custom-Exp.Enabled");

        inventorySmelterEnabled = config.getBoolean("Extras.Inventory-Smelter.Enabled");
        inventorySmelterList = config.getStringList("Extras.Inventory-Smelter.List");

        ingotToBlockEnabled = config.getBoolean("Extras.Ingot-To-Block.Enabled");
        ingotToBlockList = config.getStringList("Extras.Ingot-To-Block.List");

        inventoryAlertsTitle = config.getString("Inventory-Full-Alerts.Title.Title");
        inventoryAlertsSound = config.getString("Inventory-Full-Alerts.Sound.Sound");
        inventoryAlertsActionbar = config.getString("Inventory-Full-Alerts.Actionbar.Message");
        inventoryAlertsMessage = config.getStringList("Inventory-Full-Alerts.Message.Message");
        inventoryAlertsEnabled = config.getBoolean("Inventory-Full-Alerts.Enabled");
        inventoryAlertsTitleEnabled = config.getBoolean("Inventory-Full-Alerts.Title.Enabled");
        inventoryAlertsSoundEnabled = config.getBoolean("Inventory-Full-Alerts.Sound.Enabled");
        inventoryAlertsActionbarEnabled = config.getBoolean("Inventory-Full-Alerts.Actionbar.Enabled");
        inventoryAlertsMessageEnabled = config.getBoolean("Inventory-Full-Alerts.Message.Enabled");

        inventoryAlertsDelay = config.getInt("Inventory-Full-Alerts.Alert-Delay");

        enabledPlaceholder = config.getString("Placeholders.Enabled");
        disabledPlaceholder = config.getString("Placeholders.Disabled");
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isUpdateCheckerEnabled() {
        return updateCheckerEnabled;
    }

    public String getAutoPickupJoinPermission() {
        return autoPickupJoinPermission;
    }

    public List<String> getAutoPickupWorldBlacklist() {
        return autoPickupWorldBlacklist;
    }

    public List<String> getAutoPickupBlockBlacklist() {
        return autoPickupBlockBlacklist;
    }

    public boolean isAutoPickupEnabledOnJoin() {
        return autoPickupEnabledOnJoin;
    }

    public boolean isAutoPickupEnabledOnJoinNeedPerm() {
        return autoPickupEnabledOnJoinNeedPerm;
    }

    public boolean isAutoPickupEnabled() {
        return autoPickupEnabled;
    }

    public boolean isProcessPlayerPickupEvent() {
        return processPlayerPickupEvent;
    }

    public boolean isDropItemsOnInventoryFull() {
        return dropItemsOnInventoryFull;
    }

    public boolean isDropNeedCorrectItem() {
        return dropNeedCorrectItem;
    }

    public boolean isEnableLegacySupport() {
        return enableLegacySupport;
    }

    public String getAutoSmeltJoinPermission() {
        return autoSmeltJoinPermission;
    }

    public List<String> getAutoSmeltList() {
        return autoSmeltList;
    }

    public List<String> getAutoSmeltWorldBlacklist() {
        return autoSmeltWorldBlacklist;
    }

    public boolean isAutoSmeltEnabled() {
        return autoSmeltEnabled;
    }

    public boolean isAutoSmeltEnabledOnJoinNeedPerm() {
        return autoSmeltEnabledOnJoinNeedPerm;
    }

    public boolean isAutoSmeltEnabledOnJoin() {
        return autoSmeltEnabledOnJoin;
    }

    public List<String> getFortuneWhitelist() {
        return fortuneWhitelist;
    }

    public List<String> getFortuneWorldBlacklist() {
        return fortuneWorldBlacklist;
    }

    public boolean isFortuneEnabled() {
        return fortuneEnabled;
    }

    public boolean isFortuneWhitelistEnabled() {
        return fortuneWhitelistEnabled;
    }

    public boolean isFortuneWhitelistIsBlacklist() {
        return fortuneWhitelistIsBlacklist;
    }

    public String getFortunePermission() {
        return fortunePermission;
    }

    public boolean isFortuneRequirePermission() {
        return fortuneRequirePermission;
    }

    public List<String> getCustomExpList() {
        return customExpList;
    }

    public boolean isIsCustomExpEnabled() {
        return customExpEnabled;
    }

    public boolean isInventorySmelterEnabled() {
        return inventorySmelterEnabled;
    }

    public List<String> getInventorySmelterList() {
        return inventorySmelterList;
    }

    public List<String> getIngotToBlockList() {
        return ingotToBlockList;
    }

    public boolean isIngotToBlockEnabled() {
        return ingotToBlockEnabled;
    }

    public String getInventoryAlertsTitle() {
        return inventoryAlertsTitle;
    }

    public String getInventoryAlertsSound() {
        return inventoryAlertsSound;
    }

    public String getInventoryAlertsActionbar() {
        return inventoryAlertsActionbar;
    }

    public List<String> getInventoryAlertsMessage() {
        return inventoryAlertsMessage;
    }

    public int getInventoryAlertsDelay() {
        return inventoryAlertsDelay;
    }

    public boolean isInventoryAlertsEnabled() {
        return inventoryAlertsEnabled;
    }

    public boolean isInventoryAlertsTitleEnabled() {
        return inventoryAlertsTitleEnabled;
    }

    public boolean isInventoryAlertsSoundEnabled() {
        return inventoryAlertsSoundEnabled;
    }

    public boolean isInventoryAlertsActionbarEnabled() {
        return inventoryAlertsActionbarEnabled;
    }

    public boolean isInventoryAlertsMessageEnabled() {
        return inventoryAlertsMessageEnabled;
    }

    public String getEnabledPlaceholder() {
        if (enabledPlaceholder == null) return "&2Enabled";
        return enabledPlaceholder;
    }

    public String getDisabledPlaceholder() {
        if (disabledPlaceholder == null) return "&cDisabled";
        return disabledPlaceholder;
    }
}