package me.pulsi_.advancedautosmelt.coreSystem;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.utils.AASMessages;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import me.pulsi_.prisonenchants.utils.PELogger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import static me.pulsi_.advancedautosmelt.coreSystem.ExtraFeatures.sellPrices;

public class AutoSell {

    private static final HashMap<UUID, Double> recap = new HashMap<>();

    /**
     * Scan the whole player inventory and sell all the sellable items.
     *
     * @param p The player to sell the inventory items.
     */
    public static double sellInventory(Player p) {
        double sellMoney = 0;
        for (ItemStack drop : p.getInventory().getContents()) { // Check which of the drops is sellable.
            if (!AutoSell.isSellable(drop)) continue;
            sellMoney += getPrice(drop);
            drop.setAmount(0);
        }
        giveMoney(p, sellMoney);
        return sellMoney;
    }

    /**
     * /**
     * Sell the specified list of drops to the player.
     * The drops, if sellable, will be removed from the list and the money
     * will be added to the player balance based on the settings on the config.
     * If a non-sellable list has been specified, this method won't do anything.
     *
     * @param p     The player that will sell the items and receive money.
     * @param drops The drops to sell.
     */
    public static void sellItems(Player p, Collection<ItemStack> drops) {
        double sellMoney = 0;
        for (ItemStack drop : new ArrayList<>(drops)) { // Check which of the drops is sellable.
            if (!AutoSell.isSellable(drop)) continue;
            // Sum the price and remove the drop to not send it into the player's inventory.
            sellMoney += AutoSell.getPrice(drop);
            drops.remove(drop);
        }
        giveMoney(p, sellMoney);
    }

    /**
     * Give the specified amount of money to the player.
     * This method is part of the AutoSell, so once given the
     * money, the count-down for the recap will start. (if enabled)
     *
     * @param p      The player that will receive the money.
     * @param amount The money amount.
     */
    public static void giveMoney(Player p, double amount) {
        String n = p.getName();
        if (ConfigValues.isAutoSellUseVaultEconomy()) {
            Economy vault = AdvancedAutoSmelt.getVaultEconomy();
            if (vault != null) vault.depositPlayer(p, amount);
            else AASLogger.warn("Could not add sell money to player \"" + n + "\" because Vault hasn't been hooked.");
        } else {
            String cmd = ConfigValues.getAutoSellEconomyAddCmd();
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    cmd.replace("%player%", n).replace("%amount%", amount + "")
            );
        }

        if (!ConfigValues.isAutoSellRecapEnabled()) return;

        UUID uuid = p.getUniqueId();
        // Update the money to the new amount if already present.
        if (recap.containsKey(uuid)) recap.put(uuid, recap.get(uuid) + amount);
        else {
            recap.put(uuid, amount);
            // Register the player to the map and start the cooldown.
            Bukkit.getScheduler().runTaskLater(AdvancedAutoSmelt.INSTANCE(), () -> {
                AASMessages.send(
                        p,
                        "AutoSell-Recap-Message",
                        ExtraFeatures.getMoneyReplacer(recap.get(uuid))
                );
                recap.remove(uuid);
            }, ConfigValues.getAutoSellRecapDelay());
        }
    }

    /**
     * Check if the specified item drop is sellable.
     *
     * @param drop The item stack to check.
     * @return true if the item stack can be sold.
     */
    public static boolean isSellable(ItemStack drop) {
        if (drop == null) return false;
        return sellPrices.containsKey(drop.getType());
    }

    /**
     * Get the price of the specified item, taking care of the amount.
     *
     * @param drop The item stack to calculate the price.
     * @return The price
     */
    public static double getPrice(ItemStack drop) {
        if (drop == null) return 0;

        double price = sellPrices.get(drop.getType());
        return price * drop.getAmount();
    }
}
