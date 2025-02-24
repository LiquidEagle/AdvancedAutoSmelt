package me.pulsi_.advancedautosmelt.coreSystem;

import me.pulsi_.advancedautosmelt.utils.AASPermissions;
import me.pulsi_.advancedautosmelt.values.ConfigValues;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.pulsi_.advancedautosmelt.coreSystem.CoreLoader.inventoryIngotToBlockMap;
import static me.pulsi_.advancedautosmelt.coreSystem.CoreLoader.inventorySmelterMap;

public class InventorySmelter {

    /**
     * Method to smelt the ingots to blocks. (This works with every type of objects)
     *
     * @param p The player to retrieve the inventory.
     */
    public static void inventoryIngotToBlock(Player p) {
        if (!ConfigValues.isIngotToBlockEnabled() || !p.hasPermission(AASPermissions.ingotToBlock) || p.getInventory().firstEmpty() < 0)
            return;

        Inventory inv = p.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) continue;

            Material ingotMaterial = item.getType();
            if (!inventoryIngotToBlockMap.containsKey(ingotMaterial)) continue;

            int amount = item.getAmount();
            if (amount < 9) continue;

            int blockAmount = amount / 9, newAmount = amount - (9 * blockAmount);
            inv.addItem(new ItemStack(inventoryIngotToBlockMap.get(ingotMaterial), blockAmount));
            item.setAmount(newAmount);
        }
    }

    /**
     * Method to smelt all the items inside the inventory.
     *
     * @param p The player to retrieve the inventory.
     */
    public static void smeltInventory(Player p) {
        if (!ConfigValues.isInventorySmelterEnabled() || !p.hasPermission(AASPermissions.inventorySmelter)) return;

        Inventory inv = p.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) continue;

            Material prevMaterial = item.getType();
            if (inventorySmelterMap.containsKey(prevMaterial)) item.setType(inventorySmelterMap.get(prevMaterial));
        }
    }
}
