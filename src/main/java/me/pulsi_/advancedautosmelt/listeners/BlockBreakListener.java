package me.pulsi_.advancedautosmelt.listeners;

import dev.rosewood.rosestacker.lib.rosegarden.RosePlugin;
import dev.rosewood.rosestacker.manager.StackManager;
import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.mechanics.AutoPickup;
import me.pulsi_.advancedautosmelt.mechanics.IngotToBlock;
import me.pulsi_.advancedautosmelt.mechanics.InventoryAlerts;
import me.pulsi_.advancedautosmelt.mechanics.InventorySmelter;
import me.pulsi_.advancedautosmelt.utils.AASApi;
import me.pulsi_.advancedautosmelt.utils.Methods;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class BlockBreakListener implements Listener {

    private final HashMap<Player, Integer> expAmount = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak2(BlockBreakEvent e) {
        expAmount.put(e.getPlayer(), e.getExpToDrop());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (e.isCancelled() || p.getGameMode().equals(GameMode.CREATIVE)) return;

        if (AdvancedAutoSmelt.isRoseStackerHooked()) {
            RosePlugin rosePlugin = RosePlugin.getPlugin(RosePlugin.class);
            StackManager stackManager = rosePlugin.getManager(StackManager.class);
            if (stackManager.isBlockStacked(e.getBlock())) return;
        }

        int exp = expAmount.get(p);
        Block block = e.getBlock();
        if (AASApi.canAutoPickup(p, block)) {
            e.setExpToDrop(0);
            p.giveExp(exp);
        } else block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(exp);

        Methods.giveCustomExp(p, block);
        AutoPickup.processAutoPickup(e);
        IngotToBlock.ingotToBlock(p);
        InventorySmelter.smeltInventory(p);
        InventoryAlerts.inventoryAlerts(p);
    }
}