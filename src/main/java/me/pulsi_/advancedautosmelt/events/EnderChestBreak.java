package me.pulsi_.advancedautosmelt.events;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnderChestBreak implements Listener {

    private final List<String> worldsBlackList;
    private final boolean useLegacySupp;
    private final boolean smeltEnderChest;
    public EnderChestBreak(AdvancedAutoSmelt plugin) {
        this.useLegacySupp = plugin.isUseLegacySupp();
        this.smeltEnderChest = plugin.isSmeltEnderChest();
        this.worldsBlackList = plugin.getWorldsBlackList();
    }

    private final ItemStack enderChest = new ItemStack(Material.ENDER_CHEST, 1);

    public void removeDrops(BlockBreakEvent e) {
        if (useLegacySupp) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void enderChestBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.ENDER_CHEST)) return;
        if (smeltEnderChest) {
            if (!p.getInventory().addItem(enderChest).isEmpty()) {
                p.getWorld().dropItem(p.getLocation(), enderChest);
            }
        } else {
            for (ItemStack drops : e.getBlock().getDrops()) {
                if (!p.getInventory().addItem(drops).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), drops);
                }
            }
        }
        removeDrops(e);
    }
}