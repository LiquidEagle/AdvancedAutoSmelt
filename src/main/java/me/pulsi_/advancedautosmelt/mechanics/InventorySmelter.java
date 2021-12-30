package me.pulsi_.advancedautosmelt.mechanics;

import me.pulsi_.advancedautosmelt.utils.AASLogger;
import me.pulsi_.advancedautosmelt.values.Values;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySmelter {

    public static void smeltInventory(Player p) {
        if (!Values.getConfig().isInventorySmelterEnabled()) return;

        Inventory inv = p.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null || item.getType() == Material.AIR) continue;
            for (String line : Values.getConfig().getInventorySmelterList()) {
                if (!line.contains(";")) continue;

                String itemName = line.split(";")[0];
                if (!item.getType().toString().equals(itemName)) continue;

                String newItem = line.split(";")[1];
                try {
                    item.setType(Material.valueOf(newItem));
                } catch (IllegalArgumentException e) {
                    AASLogger.warn("Unknown ItemStack input for \"" + newItem + "\": " + e.getMessage());
                }
            }
        }
    }
}