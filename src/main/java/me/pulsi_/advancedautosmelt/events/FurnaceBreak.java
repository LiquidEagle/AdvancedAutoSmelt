package me.pulsi_.advancedautosmelt.events;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class FurnaceBreak implements Listener {

    private final boolean useLegacySupp;
    public FurnaceBreak(AdvancedAutoSmelt plugin) {
        this.useLegacySupp = plugin.isUseLegacySupp();
    }

    private final ItemStack furnace = new ItemStack(Material.FURNACE, 1);

    public void removeDrops(BlockBreakEvent e) {
        if (useLegacySupp) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    @EventHandler
    public void furnaceBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

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