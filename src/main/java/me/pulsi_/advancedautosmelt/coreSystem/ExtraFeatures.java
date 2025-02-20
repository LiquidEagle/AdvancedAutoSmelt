package me.pulsi_.advancedautosmelt.coreSystem;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.AASFormatter;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Class to hold stored data of different feature modules and utility methods.
 */
public class ExtraFeatures {

    protected static final HashMap<Material, Double> sellPrices = new HashMap<>();
    protected static final HashMap<Material, Pair> expMap = new HashMap<>();
    protected static final HashMap<Material, Material> inventoryIngotToBlockMap = new HashMap<>(), inventorySmelterMap = new HashMap<>();
    protected static final Set<UUID> inventoryAlertsCooldown = new HashSet<>();

    /**
     * Check if the specified player has the inventory full.
     *
     * @param p The player to check.
     * @return true if full, false otherwise.
     */
    public static boolean isInventoryFull(Player p) {
        return p.getInventory().firstEmpty() == -1;
    }

    /**
     * Return the specified string with the money identifier formatted.
     *
     * @param amount     The money amount.
     * @return The string formatted.
     */
    public static List<String> getMoneyReplacer(double amount) {
        List<String> values = new ArrayList<>();
        values.add("%amount%$" + AASFormatter.formatCommas(amount));
        values.add("%amount_long%$" + (long) amount);
        values.add("%amount_formatted%$" + AASFormatter.formatPrecise(amount));
        values.add("%amount_formatted_long%$" + AASFormatter.formatLong(amount));
        return values;
    }

    /**
     * Method to call before initializing the events to cache settings such as maps for ingot-to-block or smelt features.
     */
    public static void loadExtraFeatures() {
        if (ConfigValues.isIngotToBlockEnabled()) {
            inventoryIngotToBlockMap.clear();
            for (String line : ConfigValues.getIngotToBlockList()) {
                if (!line.contains(";")) {
                    AASLogger.warn("The ingot to block list format must contains a \";\" separator and 2 materials, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                String[] split = line.split(";");
                String ingotMaterialName = split[0], blockMaterialName = split[1];

                Material ingotMaterial, blockMaterial;
                try {
                    ingotMaterial = Material.valueOf(ingotMaterialName);
                    blockMaterial = Material.valueOf(blockMaterialName);
                } catch (IllegalArgumentException e) {
                    AASLogger.warn("The ingot to block list contains an invalid material, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                inventoryIngotToBlockMap.put(ingotMaterial, blockMaterial);
            }
        }

        if (ConfigValues.isInventorySmelterEnabled()) {
            inventorySmelterMap.clear();
            for (String line : ConfigValues.getInventorySmelterList()) {
                if (!line.contains(";")) {
                    AASLogger.warn("The inventory smelter list format must contains a \";\" separator and 2 materials, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                String[] split = line.split(";");
                String prevMaterialName = split[0], newMaterialName = split[1];

                Material prevMaterial, newMaterial;
                try {
                    prevMaterial = Material.valueOf(prevMaterialName);
                    newMaterial = Material.valueOf(newMaterialName);
                } catch (IllegalArgumentException e) {
                    AASLogger.warn("The inventory smelter list contains an invalid material, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                inventorySmelterMap.put(prevMaterial, newMaterial);
            }
        }

        if (ConfigValues.isAutoSellEnabled()) {
            sellPrices.clear();

            FileConfiguration sellPricesConfig = AdvancedAutoSmelt.INSTANCE().getConfigs().getConfig("sell_prices.yml");
            for (String id : sellPricesConfig.getKeys(false)) {
                Material material;
                try {
                    material = Material.valueOf(id);
                } catch (IllegalArgumentException e) {
                    AASLogger.warn("The material id \"" + id + "\" in the file \"sell_prices.yml\" is not a valid material. (Skipping it)");
                    continue;
                }

                double price = sellPricesConfig.getDouble(id);
                if (price <= 0) {
                    AASLogger.warn("The price of the material \"" + id + "\" in the file \"sell_prices.yml\" must be higher than 0. (Skipping it)");
                    continue;
                }

                sellPrices.put(material, price);
            }
        }

        if (ConfigValues.isIsCustomExpEnabled()) {
            expMap.clear();
            for (String line : ConfigValues.getCustomExpList()) {
                if (!line.contains(";")) {
                    AASLogger.warn("The custom exp list format must contains a \";\" separator, 1 material and 1 number, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                String[] split = line.split(";");
                String blockMaterialName = split[0], expAmountString = split[1];

                boolean needAutoSmelt = false;
                if (expAmountString.contains("[NEED_AUTOSMELT]")) {
                    expAmountString = expAmountString.replace("[NEED_AUTOSMELT]", "");
                    needAutoSmelt = true;
                }

                Material blockMaterial;
                int expAmount;
                try {
                    blockMaterial = Material.valueOf(blockMaterialName);
                    expAmount = Integer.parseInt(expAmountString);
                } catch (NumberFormatException e) {
                    AASLogger.warn("The custom exp list contains an invalid number, skipping line... (Wrong line: " + line + ")");
                    continue;
                } catch (IllegalArgumentException e) {
                    AASLogger.warn("The custom exp list contains an invalid material, skipping line... (Wrong line: " + line + ")");
                    continue;
                }

                Pair pair = new Pair();
                pair.k = expAmount;
                pair.v = needAutoSmelt;
                expMap.put(blockMaterial, pair);
            }
        }
    }

    public static class Pair {
        Object k, v;
    }
}