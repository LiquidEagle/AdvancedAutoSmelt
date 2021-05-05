package me.pulsi_.advancedautosmelt.events.blocks;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class EnderChestBreak implements Listener {

    private AdvancedAutoSmelt plugin;

    public EnderChestBreak(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    private final ItemStack enderChest = new ItemStack(Material.ENDER_CHEST, 1);

    public void dropsItems(Player p, ItemStack i) {

        FileConfiguration config = plugin.getConfiguration();

        if (!p.getInventory().addItem(i).isEmpty()) {
            if (config.getBoolean("AutoPickup.Inv-Full-Drop-Items")) {
                p.getWorld().dropItem(p.getLocation(), i);
            }
        }
    }

    public void removeDrops(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        if (config.getBoolean("Enable-Legacy-Support")) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void enderChestBreak(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.ENDER_CHEST)) return;
        if (config.getBoolean("AutoSmelt.Smelt-Enderchest")) {
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