package me.pulsi_.advancedautosmelt.values;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigValues {

    private static boolean updateCheckerEnabled;

    private static String autoPickupJoinPermission;
    private static List<String> autoPickupWorldBlacklist;
    private static List<String> autoPickupBlockBlacklist;
    private static boolean autoPickupEnabled;
    private static boolean autoPickupEnabledOnJoin;
    private static boolean autoPickupEnabledOnJoinNeedPerm;
    private static boolean processPlayerPickupEvent;
    private static boolean dropItemsOnInventoryFull;
    private static boolean checkForItemInHand;

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

    private static boolean customExpEnabled;
    private static List<String> customExpList;
    private static List<String> customExpWorldBlacklist;

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

    public static void setupValues() {
        FileConfiguration config = AdvancedAutoSmelt.INSTANCE().getConfigs().getConfig("config.yml");

        updateCheckerEnabled = config.getBoolean("Update-Checker");

        autoPickupJoinPermission = config.getString("AutoPickup.Enable-On-Join.Permission");
        autoPickupWorldBlacklist = config.getStringList("AutoPickup.World-Blacklist");
        autoPickupBlockBlacklist = config.getStringList("AutoPickup.Block-Blacklist");
        autoPickupEnabled = config.getBoolean("AutoPickup.Enabled");
        autoPickupEnabledOnJoin = config.getBoolean("AutoPickup.Enable-On-Join.Enabled");
        autoPickupEnabledOnJoinNeedPerm = config.getBoolean("AutoPickup.Enabled-On-Join.Is-Required-Permission");
        processPlayerPickupEvent = config.getBoolean("AutoPickup.Process-PlayerPickupEvent");
        dropItemsOnInventoryFull = config.getBoolean("AutoPickup.Drop-Items-On-Inventory-Full");
        checkForItemInHand = config.getBoolean("AutoPickup.Check-Fot-Item-In-Hand");

        autoSmeltJoinPermission = config.getString("AutoSmelt.Enable-On-Join.Permission");
        autoSmeltList = config.getStringList("AutoSmelt.Smelt-List");
        autoSmeltWorldBlacklist = config.getStringList("AutoSmelt.World-Blacklist");
        autoSmeltEnabled = config.getBoolean("AutoSmelt.Enabled");
        autoSmeltEnabledOnJoinNeedPerm = config.getBoolean("AutoSmelt.Enabled-On-Join.Is-Required-Permission");
        autoSmeltEnabledOnJoin = config.getBoolean("AutoSmelt.Enable-On-Join.Enabled");

        fortuneWhitelist = config.getStringList("Fortune.Block-Whitelist.Whitelist");
        fortuneWorldBlacklist = config.getStringList("Fortune.World-Blacklist");
        fortuneEnabled = config.getBoolean("Fortune.Enabled");
        fortuneWhitelistEnabled = config.getBoolean("Fortune.Block-Whitelist.Enabled");
        fortuneWhitelistIsBlacklist = config.getBoolean("Fortune.Block-Whitelist.Whitelist-Is-Blacklist");
        fortunePermission = config.getString("Fortune.Is-Required-Permission.Permission");
        fortuneRequirePermission = config.getBoolean("Fortune.Is-Required-Permission.Enabled");

        customExpEnabled = config.getBoolean("Custom-Exp.Enabled");
        customExpList = config.getStringList("Custom-Exp.Exp-List");
        customExpWorldBlacklist = config.getStringList("Custom-Exp.World-Blacklist");

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

    public static boolean isUpdateCheckerEnabled() {
        return updateCheckerEnabled;
    }

    public static String getAutoPickupJoinPermission() {
        return autoPickupJoinPermission;
    }

    public static List<String> getAutoPickupWorldBlacklist() {
        return autoPickupWorldBlacklist;
    }

    public static List<String> getAutoPickupBlockBlacklist() {
        return autoPickupBlockBlacklist;
    }

    public static boolean isAutoPickupEnabledOnJoin() {
        return autoPickupEnabledOnJoin;
    }

    public static boolean isAutoPickupEnabledOnJoinNeedPerm() {
        return autoPickupEnabledOnJoinNeedPerm;
    }

    public static boolean isAutoPickupEnabled() {
        return autoPickupEnabled;
    }

    public static boolean isProcessPlayerPickupEvent() {
        return processPlayerPickupEvent;
    }

    public static boolean isDropItemsOnInventoryFull() {
        return dropItemsOnInventoryFull;
    }

    public static boolean isCheckForItemInHand() {
        return checkForItemInHand;
    }

    public static String getAutoSmeltJoinPermission() {
        return autoSmeltJoinPermission;
    }

    public static List<String> getAutoSmeltList() {
        return autoSmeltList;
    }

    public static List<String> getAutoSmeltWorldBlacklist() {
        return autoSmeltWorldBlacklist;
    }

    public static boolean isAutoSmeltEnabled() {
        return autoSmeltEnabled;
    }

    public static boolean isAutoSmeltEnabledOnJoinNeedPerm() {
        return autoSmeltEnabledOnJoinNeedPerm;
    }

    public static boolean isAutoSmeltEnabledOnJoin() {
        return autoSmeltEnabledOnJoin;
    }

    public static List<String> getFortuneWhitelist() {
        return fortuneWhitelist;
    }

    public static List<String> getFortuneWorldBlacklist() {
        return fortuneWorldBlacklist;
    }

    public static boolean isFortuneEnabled() {
        return fortuneEnabled;
    }

    public static boolean isFortuneWhitelistEnabled() {
        return fortuneWhitelistEnabled;
    }

    public static boolean isFortuneWhitelistIsBlacklist() {
        return fortuneWhitelistIsBlacklist;
    }

    public static String getFortunePermission() {
        return fortunePermission;
    }

    public static boolean isFortuneRequirePermission() {
        return fortuneRequirePermission;
    }

    public static boolean isIsCustomExpEnabled() {
        return customExpEnabled;
    }

    public static List<String> getCustomExpList() {
        return customExpList;
    }

    public static List<String> getCustomExpWorldBlacklist() {
        return customExpWorldBlacklist;
    }

    public static boolean isInventorySmelterEnabled() {
        return inventorySmelterEnabled;
    }

    public static List<String> getInventorySmelterList() {
        return inventorySmelterList;
    }

    public static List<String> getIngotToBlockList() {
        return ingotToBlockList;
    }

    public static boolean isIngotToBlockEnabled() {
        return ingotToBlockEnabled;
    }

    public static String getInventoryAlertsTitle() {
        return inventoryAlertsTitle;
    }

    public static String getInventoryAlertsSound() {
        return inventoryAlertsSound;
    }

    public static String getInventoryAlertsActionbar() {
        return inventoryAlertsActionbar;
    }

    public static List<String> getInventoryAlertsMessage() {
        return inventoryAlertsMessage;
    }

    public static int getInventoryAlertsDelay() {
        return inventoryAlertsDelay;
    }

    public static boolean isInventoryAlertsEnabled() {
        return inventoryAlertsEnabled;
    }

    public static boolean isInventoryAlertsTitleEnabled() {
        return inventoryAlertsTitleEnabled;
    }

    public static boolean isInventoryAlertsSoundEnabled() {
        return inventoryAlertsSoundEnabled;
    }

    public static boolean isInventoryAlertsActionbarEnabled() {
        return inventoryAlertsActionbarEnabled;
    }

    public static boolean isInventoryAlertsMessageEnabled() {
        return inventoryAlertsMessageEnabled;
    }

    public static String getEnabledPlaceholder() {
        if (enabledPlaceholder == null) return "&2Enabled";
        return enabledPlaceholder;
    }

    public static String getDisabledPlaceholder() {
        if (disabledPlaceholder == null) return "&cDisabled";
        return disabledPlaceholder;
    }
}