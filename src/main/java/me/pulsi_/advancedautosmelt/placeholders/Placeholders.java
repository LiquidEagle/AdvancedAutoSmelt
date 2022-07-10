package me.pulsi_.advancedautosmelt.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.AASApi;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "Pulsi_";
    }

    @Override
    public String getIdentifier() {
        return "advancedautosmelt";
    }

    @Override
    public String getVersion() {
        return AdvancedAutoSmelt.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) return "Player not online";

        switch (identifier) {
            case "autopickup":
                if (AASApi.isAutoPickupEnabled(p)) return Values.getConfig().getEnabledPlaceholder();
                return Values.getConfig().getDisabledPlaceholder();

            case "autosmelt":
                if (AASApi.isAutoSmeltEnabled(p)) return Values.getConfig().getEnabledPlaceholder();
                return Values.getConfig().getDisabledPlaceholder();

            case "inventoryalerts":
                if (AASApi.isInventoryAlertsEnabled(p)) return Values.getConfig().getEnabledPlaceholder();
                return Values.getConfig().getDisabledPlaceholder();
        }
        return null;
    }
}