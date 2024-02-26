package me.pulsi_.advancedautosmelt.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
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

        switch (identifier) {
            case "autopickup":
                if (true) return ConfigValues.getEnabledPlaceholder();
                return ConfigValues.getDisabledPlaceholder();

            case "autosmelt":
                if (true) return ConfigValues.getEnabledPlaceholder();
                return ConfigValues.getDisabledPlaceholder();

            case "inventoryalerts":
                if (true) return ConfigValues.getEnabledPlaceholder();
                return ConfigValues.getDisabledPlaceholder();
        }
        return null;
    }
}