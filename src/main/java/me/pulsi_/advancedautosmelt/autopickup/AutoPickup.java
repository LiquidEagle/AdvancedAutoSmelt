package me.pulsi_.advancedautosmelt.autopickup;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class AutoPickup implements Listener {

    private AdvancedAutoSmelt plugin;

    public AutoPickup(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    Set<String> autoPickupOFF = Commands.autoPickupOFF;

    @EventHandler
    public void autoPickup(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (plugin.getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

            if (autoPickupOFF.contains(p.getName())) return;
            if (!p.hasPermission("advancedautosmelt.autopickup")) return;
            if (!plugin.getConfig().getBoolean("AutoPickup.enable-autopickup")) return;
            if (e.getBlock().getType().equals(Material.IRON_ORE) ||
                    e.getBlock().getType().equals(Material.GOLD_ORE) ||
                    e.getBlock().getType().equals(Material.STONE)) return;

            if (plugin.getConfig().getBoolean("AutoPickup.use-blacklist")) {
                for (String blacklist : plugin.getConfig().getStringList("AutoPickup.blacklist"))
                if (blacklist.contains(e.getBlock().getType().toString())) return;

                for (ItemStack drops : e.getBlock().getDrops()) {
                    if (!p.getInventory().addItem(drops).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), drops);
                    }
                    e.getBlock().setType(Material.AIR);
                }
            } else {
                for (ItemStack drops : e.getBlock().getDrops()) {
                    if (!p.getInventory().addItem(drops).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), drops);
                    }
                    e.getBlock().setType(Material.AIR);
                }
            }

        } else {

            if (autoPickupOFF.contains(p.getName())) return;
            if (!p.hasPermission("advancedautosmelt.autopickup")) return;
            if (!plugin.getConfig().getBoolean("AutoPickup.enable-autopickup")) return;
            if (e.getBlock().getType().equals(Material.IRON_ORE) ||
                    e.getBlock().getType().equals(Material.GOLD_ORE) ||
                    e.getBlock().getType().equals(Material.STONE)) return;

            if (plugin.getConfig().getBoolean("AutoPickup.use-blacklist")) {
                for (String blacklist : plugin.getConfig().getStringList("AutoPickup.blacklist"))
                    if (blacklist.contains(e.getBlock().getType().toString())) return;

                for (ItemStack drops : e.getBlock().getDrops()) {
                    if (!p.getInventory().addItem(drops).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), drops);
                        e.getBlock().setType(Material.AIR);
                    }
                }
            } else {
                for (ItemStack drops : e.getBlock().getDrops()) {
                    if (!p.getInventory().addItem(drops).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), drops);
                    }
                    e.getBlock().setType(Material.AIR);
                }
            }
        }
    }
}