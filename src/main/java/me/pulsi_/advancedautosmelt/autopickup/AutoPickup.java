package me.pulsi_.advancedautosmelt.autopickup;

import com.sk89q.worldguard.WorldGuard;
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

    private WorldGuard worldGuard = WorldGuard.getInstance();
    private final boolean isEFS;
    private final boolean isAutoSmeltDCM;
    private final boolean isAutoPickupEnabled;
    private final boolean isAutoPickupBlacklist;
    private final boolean useLegacySupp;
    private final List<String> blackList;
    private final List<String> autoPickupDisabledWorlds;

    public AutoPickup(AdvancedAutoSmelt plugin) {
        this.isAutoSmeltDCM = plugin.isDCM();
        this.isAutoPickupEnabled = plugin.isAutoPickupEnabled();
        this.isAutoPickupBlacklist = plugin.isAutoPickupBlacklist();
        this.blackList = plugin.getBlackList();
        this.autoPickupDisabledWorlds = plugin.getAutoPickupDisabledWorlds();
        this.useLegacySupp = plugin.isUseLegacySupp();
        this.isEFS = plugin.isEFS();
    }

    Set<String> autoPickupOFF = Commands.autoPickupOFF;

    public void removeDrops(BlockBreakEvent e) {
        if (useLegacySupp) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    @EventHandler
    public void autoPickup(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String worlds : autoPickupDisabledWorlds)
            if (worlds.contains(p.getWorld().getName())) return;

        if (useLegacySupp) {
            if (!(e.getBlock().getType().name().endsWith("LEAVES"))) {

                ItemStack item = p.getInventory().getItemInHand();
                Material itemType = p.getInventory().getItemInHand().getType();

                if (itemType.getMaxDurability() == 0) return;

                short durability = item.getDurability();
                int durabilityLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
                boolean shouldDamage = ((Math.random()) < (1 / (durabilityLevel + 1)));
                if (shouldDamage)
                    item.setDurability((short) (durability + 1));

                p.setItemInHand(item.getDurability() > item.getType().getMaxDurability() ? null : item);
            }
        }

        if (isEFS) return;
        if (isAutoSmeltDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
        if (autoPickupOFF.contains(p.getName())) return;
        if (!p.hasPermission("advancedautosmelt.autopickup")) return;
        if (!isAutoPickupEnabled) return;
        if (e.getBlock().getType() == (Material.IRON_ORE) ||
                e.getBlock().getType() == (Material.GOLD_ORE) ||
                e.getBlock().getType() ==(Material.STONE) ||
                e.getBlock().getType() == (Material.CHEST) ||
                e.getBlock().getType() == (Material.FURNACE) ||
                e.getBlock().getType() == (Material.ENDER_CHEST)) return;

        if (isAutoPickupBlacklist) {
            for (String blacklist : blackList)
                if (blacklist.contains(e.getBlock().getType().toString())) return;

            for (ItemStack drops : e.getBlock().getDrops()) {
                if (!p.getInventory().addItem(drops).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), drops);
                }
                removeDrops(e);
            }
        } else {
            for (ItemStack drops : e.getBlock().getDrops()) {
                if (!p.getInventory().addItem(drops).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), drops);
                }
                removeDrops(e);
            }
        }
    }
}