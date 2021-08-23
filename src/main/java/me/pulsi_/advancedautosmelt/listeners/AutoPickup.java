package me.pulsi_.advancedautosmelt.listeners;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import me.pulsi_.advancedautosmelt.utils.MethodUtils;
import me.pulsi_.advancedautosmelt.utils.SetUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AutoPickup implements Listener {

    private final AdvancedAutoSmelt plugin;
    public AutoPickup(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void pickupExp(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!plugin.config().getBoolean("AutoPickup.Enabled") | !p.hasPermission("advancedautosmelt.autopickup") | MethodUtils.isWorldBlacklist(p) |
                MethodUtils.isCreativeDisabled(p) | SetUtils.AUTOPICKUP_OFF.contains(p.getUniqueId())) return;
        for (String blocks : plugin.config().getStringList("AutoPickup.Custom-Exp.Exp-List")) {
            try {
                Material block = Material.valueOf(blocks.split(";")[0]);
                if (!e.getBlock().getType().equals(block)) {
                    if (e.getExpToDrop() != 0) {
                        p.giveExp(e.getExpToDrop());
                        e.setExpToDrop(0);
                    }
                } else {
                    if (plugin.config().getBoolean("AutoPickup.Custom-Exp.Enabled")) {
                        int exp = Integer.parseInt(blocks.split(";")[1]);
                        p.giveExp(exp);
                        if (e.getExpToDrop() != 0) e.setExpToDrop(0);
                    }
                }
            } catch (IllegalArgumentException ex) {
                ChatUtils.consoleMessage("&8&l<&d&lAdvanced&a&lAuto&c&lSmelt&8&l> &cThere is an invalid value in the Custom-Exp list.");
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void autoPickup(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Material blockType = e.getBlock().getType();
        boolean containsBlock;

        damageItem(e, p);
        if (MethodUtils.canPickup(p) & MethodUtils.canSmelt(p)) {
            try {
                Material block = null;
                ItemStack drops = null;
                for (String s : plugin.config().getStringList("AutoSmelt.Smelt-List")) {
                    block = Material.valueOf(s.split(";")[0]);
                    drops = new ItemStack(Material.valueOf(s.split(";")[1]));
                    if (block.equals(blockType)) break;
                }
                containsBlock = block.equals(blockType);
                multiplyFortune(drops, p, e);
                if (containsBlock) {
                    MethodUtils.dropsItems(p, drops);
                } else {
                    giveDrops(e, p);
                }
            } catch (IllegalArgumentException ignored) {}
            MethodUtils.removeDrops(e);
            return;
        }

        if (MethodUtils.canPickup(p) & !MethodUtils.canSmelt(p)) {
            try {
                giveDrops(e, p);
            } catch (IllegalArgumentException ignored) {}
            MethodUtils.removeDrops(e);
            return;
        }

        if (!MethodUtils.canPickup(p) & MethodUtils.canSmelt(p)) {
            try {
                Material block = null;
                ItemStack drops = null;
                for (String s : plugin.config().getStringList("AutoSmelt.Smelt-List")) {
                    block = Material.valueOf(s.split(";")[0]);
                    drops = new ItemStack(Material.valueOf(s.split(";")[1]));
                    if (block.equals(blockType)) break;
                }
                containsBlock = block.equals(blockType);
                multiplyFortune(drops, p, e);
                if (containsBlock) {
                    p.getWorld().dropItem(e.getBlock().getLocation(), drops).setPickupDelay(0);
                } else {
                    for (ItemStack i : e.getBlock().getDrops(p.getItemInHand()))
                        p.getWorld().dropItem(e.getBlock().getLocation(), i).setPickupDelay(0);
                }
            } catch (IllegalArgumentException ignored) {}
            MethodUtils.removeDrops(e);
            return;
        }

        for (ItemStack i : e.getBlock().getDrops(p.getItemInHand())) {
            multiplyFortune(i, p, e);
            p.getWorld().dropItem(e.getBlock().getLocation(), i);
        }
        MethodUtils.removeDrops(e);
    }

    private void giveDrops(BlockBreakEvent e, Player p) {
        for (ItemStack drops : e.getBlock().getDrops(p.getItemInHand())) {
            multiplyFortune(drops, p, e);
            MethodUtils.dropsItems(p, drops);
        }
    }
    
    private void damageItem(BlockBreakEvent e, Player p) {
        if (!plugin.config().getBoolean("Enable-Legacy-Support") || !e.getBlock().getType().name().endsWith("LEAVES")) return;
        ItemStack item = p.getInventory().getItemInHand();
        if (item.getType().getMaxDurability() != 0) {
            int durabilityLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
            boolean shouldDamage = ((Math.random()) < (1 / (durabilityLevel + 1)));
            if (shouldDamage) item.setDurability((short) (item.getDurability() + 1));
            p.setItemInHand(item.getDurability() > item.getType().getMaxDurability() ? null : item);
        }
    }

    private void multiplyFortune(ItemStack drop, Player p, BlockBreakEvent e) {
        if (plugin.config().getBoolean("Fortune.Enable-Fortune-Support") & p.hasPermission("advancedautosmelt.fortune")
                & p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            int randomAmount = new Random().nextInt(fortuneLevel) + 1;
            if (plugin.config().getBoolean("Fortune.Whitelist.Enabled")) {
                if (plugin.config().getStringList("Fortune.Whitelist.Block-List").contains(e.getBlock().getType().toString()))
                    drop.setAmount(randomAmount);
            } else {
                drop.setAmount(randomAmount);
            }
        }
    }
}