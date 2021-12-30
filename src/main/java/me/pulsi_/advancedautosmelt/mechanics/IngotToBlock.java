package me.pulsi_.advancedautosmelt.mechanics;

import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IngotToBlock {

    public static void ingotToBlock(Player p) {
        if (!Values.getConfig().isIngotToBlockEnabled() || p.getInventory().firstEmpty() < 0) return;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR) continue;
            for (String line : Values.getConfig().getIngotToBlockList()) {
                if (!line.contains(";")) continue;

                String itemName = line.split(";")[0];
                if (!item.getType().toString().equals(itemName)) continue;

                int amount = item.getAmount();
                if (amount < 9) continue;

                int newItemAmount = amount / 9;
                String newItemName = line.split(";")[1];

                ItemStack newItem;
                try {
                    newItem = new ItemStack(Material.valueOf(newItemName), newItemAmount);
                } catch (IllegalArgumentException ex) {
                    AASLogger.warn("Unknown ItemStack type: " + ex.getMessage());
                    return;
                }

                p.getInventory().addItem(newItem);
                item.setAmount(item.getAmount() - (9 * newItemAmount));
            }
        }
    }
}