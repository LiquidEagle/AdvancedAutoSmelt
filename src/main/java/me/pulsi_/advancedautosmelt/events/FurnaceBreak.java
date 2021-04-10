package me.pulsi_.advancedautosmelt.events;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class FurnaceBreak implements Listener {

    private final List<String> worldsBlackList;
    private final boolean useLegacySupp;
    public FurnaceBreak(AdvancedAutoSmelt plugin) {
        this.useLegacySupp = plugin.isUseLegacySupp();
        this.worldsBlackList = plugin.getWorldsBlackList();
    }

    private final ItemStack furnace = new ItemStack(Material.FURNACE, 1);

    public void removeDrops(BlockBreakEvent e) {
        if (useLegacySupp) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void furnaceBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.FURNACE)) return;
        p.getInventory().addItem(furnace);
        if (e.getBlock().getState() instanceof Furnace) {
            Furnace furnaceBlock = ((Furnace) e.getBlock().getState());
            for (ItemStack itemsInTheFurnace : furnaceBlock.getInventory().getContents()) {
                if (itemsInTheFurnace != null) {
                    if (!p.getInventory().addItem(itemsInTheFurnace).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), itemsInTheFurnace);
                    }
                    itemsInTheFurnace.setAmount(0);
                }
            }
            removeDrops(e);
        }
    }
}