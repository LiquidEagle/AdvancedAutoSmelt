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

import java.util.Map;

public class AutoPickup implements Listener {

    Map autoPickupOFF = Commands.toggleAutoPickup;

    @EventHandler
    public void autoPickup(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

            if (autoPickupOFF.containsKey(p.getName())) return;
            if (!p.hasPermission("advancedautosmelt.autopickup")) return;
            if (!AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) return;
            if (e.getBlock().getType().equals(Material.IRON_ORE) ||
                    e.getBlock().getType().equals(Material.GOLD_ORE) ||
                    e.getBlock().getType().equals(Material.STONE)) return;
            for (ItemStack drops : e.getBlock().getDrops()) {
                if (!p.getInventory().addItem(drops).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), drops);
                }
                e.getBlock().setType(Material.AIR);
            }
        } else {

            if (autoPickupOFF.containsKey(p.getName())) return;
            if (!p.hasPermission("advancedautosmelt.autopickup")) return;
            if (!AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) return;
            if (e.getBlock().getType().equals(Material.IRON_ORE) ||
                    e.getBlock().getType().equals(Material.GOLD_ORE) ||
                    e.getBlock().getType().equals(Material.STONE)) return;
            for (ItemStack drops : e.getBlock().getDrops()) {
                if (!p.getInventory().addItem(drops).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), drops);
                }
                e.getBlock().setType(Material.AIR);
            }
        }
    }
}