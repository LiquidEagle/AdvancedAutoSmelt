package me.pulsi_.advancedautosmelt.players;

public class AASPlayer {

    private boolean autoPickupEnabled, autoSmeltEnabled, autoSellEnabled, inventoryAlertsEnabled;

    public boolean isAutoPickupEnabled() {
        return autoPickupEnabled;
    }

    public boolean isAutoSmeltEnabled() {
        return autoSmeltEnabled;
    }

    public boolean isAutoSellEnabled() {
        return autoSellEnabled;
    }

    public boolean isInventoryAlertsEnabled() {
        return inventoryAlertsEnabled;
    }

    public void setAutoPickupEnabled(boolean autoPickupEnabled) {
        this.autoPickupEnabled = autoPickupEnabled;
    }

    public void setAutoSmeltEnabled(boolean autoSmeltEnabled) {
        this.autoSmeltEnabled = autoSmeltEnabled;
    }

    public void setAutoSellEnabled(boolean autoSellEnabled) {
        this.autoSellEnabled = autoSellEnabled;
    }

    public void setInventoryAlertsEnabled(boolean inventoryAlertsEnabled) {
        this.inventoryAlertsEnabled = inventoryAlertsEnabled;
    }
}