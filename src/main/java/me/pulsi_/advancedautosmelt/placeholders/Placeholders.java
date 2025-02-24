package me.pulsi_.advancedautosmelt.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.coreSystem.AutoSell;
import me.pulsi_.advancedautosmelt.players.AASPlayer;
import me.pulsi_.advancedautosmelt.players.PlayerRegistry;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
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
        return AdvancedAutoSmelt.INSTANCE().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (p == null) return "Player not online";

        AASPlayer player = PlayerRegistry.getPlayer(p);
        switch (identifier) {
            case "autopickup":
                if (player != null && player.isAutoPickupEnabled()) return ConfigValues.getEnabledPlaceholder();
                return ConfigValues.getDisabledPlaceholder();

            case "autosmelt":
                if (player != null && player.isAutoSmeltEnabled()) return ConfigValues.getEnabledPlaceholder();
                return ConfigValues.getDisabledPlaceholder();

            case "autosell":
                if (player != null && player.isAutoSellEnabled()) return ConfigValues.getEnabledPlaceholder();
                return ConfigValues.getDisabledPlaceholder();

            case "inventoryalerts":
                if (player != null && player.isInventoryAlertsEnabled()) return ConfigValues.getEnabledPlaceholder();
                return ConfigValues.getDisabledPlaceholder();

            case "autosell_multiplier":
                return AutoSell.getAutoSellMultiplier(p) + "";
        }
        return null;
    }
}