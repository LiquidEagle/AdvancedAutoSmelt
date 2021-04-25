package me.pulsi_.advancedautosmelt.events.blocks;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnderChestBreak implements Listener {

    private FileConfiguration config;
    private List<String> worldsBlackList;
    private boolean isInvFullDrop;
    private boolean useLegacySupp;
    private boolean smeltEnderChest;
    public EnderChestBreak(AdvancedAutoSmelt plugin) {
        this.config = plugin.getConfiguration();
        this.useLegacySupp = config.getBoolean("Enable-Legacy-Support");
        this.smeltEnderChest = config.getBoolean("AutoSmelt.Smelt-Enderchest");
        this.worldsBlackList = config.getStringList("Disabled-Worlds");
        this.isInvFullDrop = config.getBoolean("AutoPickup.Inv-Full-Drop-Items");
    }

    private final ItemStack enderChest = new ItemStack(Material.ENDER_CHEST, 1);

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
    public void enderChestBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.ENDER_CHEST)) return;
        if (smeltEnderChest) {
            dropsItems(p, enderChest);
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