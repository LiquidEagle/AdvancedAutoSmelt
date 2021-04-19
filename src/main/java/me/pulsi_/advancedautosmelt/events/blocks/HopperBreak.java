package me.pulsi_.advancedautosmelt.events.blocks;

import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.managers.DataManager;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class HopperBreak {

    private final List<String> worldsBlackList;
    private final boolean useLegacySupp;
    private final boolean isInvFullDrop;
    private final boolean isAutoPickup;
    private final Set<String> autoPickupOFF;

    public HopperBreak(DataManager dm) {
        useLegacySupp = dm.isUseLegacySupp();
        worldsBlackList = dm.getWorldsBlackList();
        isInvFullDrop = dm.isDropsItemsInvFull();
        isAutoPickup = dm.isAutoPickupEnabled();
        autoPickupOFF = Commands.autoPickupOFF;
    }

    private final ItemStack hopper = new ItemStack(Material.HOPPER, 1);

    public void dropsItems(Player p, ItemStack i) {
        if (!p.getInventory().addItem(i).isEmpty()) {
            if (isInvFullDrop) {
                p.getWorld().dropItem(p.getLocation(), i);
            }
        }
    }

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
        if (!isAutoPickup) return;
        if (autoPickupOFF.contains(p.getName())) return;
        if (e.getBlock().getState() instanceof Hopper) {
            Hopper hopperBlock = ((Hopper) e.getBlock().getState());
            for (ItemStack itemsInTheHopper : hopperBlock.getInventory().getContents()) {
                if (itemsInTheHopper != null) {
                    dropsItems(p, itemsInTheHopper);
                    hopperBlock.getInventory().clear();
                }
            }
            p.getInventory().addItem(hopper);
            removeDrops(e);
        }
    }
}