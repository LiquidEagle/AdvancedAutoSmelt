package me.pulsi_.advancedautosmelt.events;

import me.pulsi_.advancedautosmelt.managers.DataManager;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HopperBreak {

    private final List<String> worldsBlackList;
    private final boolean useLegacySupp;
    public HopperBreak(DataManager dm) {
        this.useLegacySupp = dm.isUseLegacySupp();
        this.worldsBlackList = dm.getWorldsBlackList();
    }

    private final ItemStack hopper = new ItemStack(Material.HOPPER, 1);

    public void removeDrops(BlockBreakEvent e) {
        if (useLegacySupp) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void hopperBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.FURNACE)) return;
        p.getInventory().addItem(hopper);
        if (e.getBlock().getState() instanceof Hopper) {
            Hopper hopperBlock = ((Hopper) e.getBlock().getState());
            for (ItemStack itemsInTheHopper : hopperBlock.getInventory().getContents()) {
                if (itemsInTheHopper != null) {
                    if (!p.getInventory().addItem(itemsInTheHopper).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), itemsInTheHopper);
                    }
                    itemsInTheHopper.setAmount(0);
                }
            }
            removeDrops(e);
        }
    }
}