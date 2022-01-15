package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.mechanics.AutoPickup;
import me.pulsi_.advancedautosmelt.mechanics.IngotToBlock;
import me.pulsi_.advancedautosmelt.mechanics.InventoryAlerts;
import me.pulsi_.advancedautosmelt.mechanics.InventorySmelter;
import me.pulsi_.advancedautosmelt.utils.AASApi;
import me.pulsi_.advancedautosmelt.utils.Methods;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        Player p = e.getPlayer();
        Block block = e.getBlock();

        if (AASApi.canAutoPickup(p, block)) {
            int expAmount = e.getExpToDrop();
            e.setExpToDrop(0);
            p.giveExp(expAmount);
        }

        Methods.giveCustomExp(p, block);
        AutoPickup.processAutoPickup(e);
        IngotToBlock.ingotToBlock(p);
        InventorySmelter.smeltInventory(p);
        InventoryAlerts.inventoryAlerts(p);
    }
}