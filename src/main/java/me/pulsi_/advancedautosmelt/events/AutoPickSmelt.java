package me.pulsi_.advancedautosmelt.events;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class AutoPickSmelt implements Listener {

    private boolean isEFS;
    private final boolean isSmeltGold;
    private final boolean isSmeltIron;
    private final boolean isSmeltStone;
    private final boolean isAutoSmeltDCM;
    private final boolean isAutoPickupEnabled;
    private final boolean useLegacySupp;
    private final List<String> worldsBlackList;
    private final boolean isAutoPickupBlacklist;
    private final List<String> blackList;

    public AutoPickSmelt(AdvancedAutoSmelt plugin) {
        this.isEFS = plugin.isEFS();
        this.isAutoSmeltDCM = plugin.isDCM();
        this.isAutoPickupEnabled = plugin.isAutoPickupEnabled();
        this.isSmeltGold = plugin.isSmeltGold();
        this.isSmeltIron = plugin.isSmeltIron();
        this.isSmeltStone = plugin.isSmeltStone();
        this.worldsBlackList = plugin.getWorldsBlackList();
        this.useLegacySupp = plugin.isUseLegacySupp();
        this.isAutoPickupBlacklist = plugin.isAutoPickupBlacklist();
        this.blackList = plugin.getBlackList();
        this.isEFS = plugin.isEFS();
    }

    private final ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private final ItemStack stone = new ItemStack(Material.STONE);
    private final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoPickupOFF;

    public void removeDrops(BlockBreakEvent e) {
        if (useLegacySupp) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    public void smelt(Player p, ItemStack smelt, ItemStack notSmelt, BlockBreakEvent e) {

        boolean cantPickup = autoPickupOFF.contains(p.getName());
        boolean cantSmelt = autoSmeltOFF.contains(p.getName());

        if (!cantPickup && !cantSmelt) {
            if (!p.getInventory().addItem(smelt).isEmpty()) {
                p.getWorld().dropItem(p.getLocation(), smelt);
            }

        } else if (cantPickup && cantSmelt) {
            return;

        } else if (cantPickup) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);

        } else if (cantSmelt) {
            if (!p.getInventory().addItem(notSmelt).isEmpty()) {
                p.getWorld().dropItem(p.getLocation(), notSmelt);
            }
        } else {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);
        }
    }

    public void smeltNoPickup(Player p, Material block, ItemStack notSmelt, BlockBreakEvent e) {
        if (!Commands.autoSmeltOFF.contains(p.getName())) {
            if (e.getBlock().getType() == block) {
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);
            }
        }
    }

    public void pickNoSmelt(Player p, Material block, ItemStack notSmelt, BlockBreakEvent e) {
        if (!autoPickupOFF.contains(p.getName())) {
            if (e.getBlock().getType() == block) {
                if (!p.getInventory().addItem(notSmelt).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), notSmelt);
                }
            } else {
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoPickup(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.equalsIgnoreCase(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
        if (useLegacySupp) {
            if (!(e.getBlock().getType().name().endsWith("LEAVES"))) {

                ItemStack item = p.getInventory().getItemInHand();
                Material itemType = p.getInventory().getItemInHand().getType();

                if (itemType.getMaxDurability() != 0) {

                    short durability = item.getDurability();
                    int durabilityLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
                    boolean shouldDamage = ((Math.random()) < (1 / (durabilityLevel + 1)));
                    if (shouldDamage)
                        item.setDurability((short) (durability + 1));

                    p.setItemInHand(item.getDurability() > item.getType().getMaxDurability() ? null : item);
                }
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltGold(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;

        if (e.isCancelled()) return;
        if (isEFS) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.gold"))) return;
        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }

        if (isSmeltGold) {
            if (isAutoPickupEnabled) {
                smelt(p, goldIngot, goldOre, e);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.GOLD_ORE, goldIngot, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.GOLD_ORE, goldOre, e);
            }
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltIron(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (isEFS) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.iron"))) return;
        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;

        if (isSmeltIron) {
            if (isAutoPickupEnabled) {
                smelt(p, ironIngot, ironOre, e);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.IRON_ORE, ironIngot, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.IRON_ORE, ironOre, e);
            }
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltStone(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (isEFS) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.stone"))) return;
        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (!(e.getBlock().getType() == Material.STONE)) return;

        if (isSmeltStone) {
            if (isAutoPickupEnabled) {
                smelt(p, stone, cobblestone, e);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.STONE, stone, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.STONE, cobblestone, e);
            }
        }
        removeDrops(e);
    }
}