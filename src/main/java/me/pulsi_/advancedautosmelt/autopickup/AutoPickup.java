package me.pulsi_.advancedautosmelt.autopickup;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class AutoPickup implements Listener {

    private boolean isAutoSmeltDCM;
    private boolean isAutoPickupEnabled;
    private boolean isAutoPickupBlacklist;
    private List<String> blackList;
    public AutoPickup(AdvancedAutoSmelt plugin) {
        this.isAutoSmeltDCM = plugin.isAutoSmeltDCM();
        this.isAutoPickupEnabled = plugin.isAutoPickupEnabled();
        this.isAutoPickupBlacklist = plugin.isAutoPickupBlacklist();
        this.blackList = plugin.getBlackList();
    }

    Set<String> autoPickupOFF = Commands.autoPickupOFF;

    @EventHandler
    public void autoPickup(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!(e.getBlock().getType().name().endsWith("LEAVES"))) {
            ItemStack item = p.getInventory().getItemInHand();
            short durability = item.getDurability();
            int durabilityLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
            boolean shouldDamage = ((Math.random()) < (1 / (durabilityLevel + 1)));
            if (shouldDamage)
                item.setDurability((short) (durability + 1));

            p.setItemInHand(item.getDurability() > item.getType().getMaxDurability() ? null : item);
        }

        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (autoPickupOFF.contains(p.getName())) return;
        if (!p.hasPermission("advancedautosmelt.autopickup")) return;
        if (!isAutoPickupEnabled) return;
        if (e.getBlock().getType().equals(Material.IRON_ORE) ||
                e.getBlock().getType().equals(Material.GOLD_ORE) ||
                e.getBlock().getType().equals(Material.STONE)) return;

        if (isAutoPickupBlacklist) {
            for (String blacklist : blackList)
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
    }
}