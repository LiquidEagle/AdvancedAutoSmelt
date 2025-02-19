package me.pulsi_.advancedautosmelt.coreSystem;

import org.bukkit.inventory.ItemStack;

import static me.pulsi_.advancedautosmelt.coreSystem.ExtraFeatures.sellPrices;

public class AutoSell {

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
