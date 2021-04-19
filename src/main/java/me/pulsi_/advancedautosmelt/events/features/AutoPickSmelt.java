package me.pulsi_.advancedautosmelt.events.features;

import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.managers.DataManager;
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
    private final boolean isInvFullDrop;
    private final List<String> worldsBlackList;
    private final boolean isAutoPickupBlacklist;
    private final List<String> blackList;

    public AutoPickSmelt(DataManager dm) {
        isEFS = dm.isEFS();
        isAutoSmeltDCM = dm.isDCM();
        isAutoPickupEnabled = dm.isAutoPickupEnabled();
        isSmeltGold = dm.isSmeltGold();
        isSmeltIron = dm.isSmeltIron();
        isSmeltStone = dm.isSmeltStone();
        worldsBlackList = dm.getWorldsBlackList();
        useLegacySupp = dm.isUseLegacySupp();
        isAutoPickupBlacklist = dm.isAutoPickupBlacklist();
        blackList = dm.getBlackList();
        isEFS = dm.isEFS();
        isInvFullDrop = dm.isDropsItemsInvFull();
    }

    private final ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private final ItemStack stone = new ItemStack(Material.STONE);
    private final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoSmeltOFF;

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

    public void smelt(Player p, ItemStack smelt, ItemStack notSmelt, BlockBreakEvent e) {

        boolean cantPickup = autoPickupOFF.contains(p.getName());
        boolean cantSmelt = autoSmeltOFF.contains(p.getName());

        if (!cantPickup && !cantSmelt) {
            dropsItems(p, smelt);

        } else if (cantPickup && cantSmelt) {
            return;

        } else if (cantPickup) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);

        } else if (cantSmelt) {
            dropsItems(p, notSmelt);
        } else {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);
        }
    }

    public void smeltNoPickup(Player p, ItemStack notSmelt, BlockBreakEvent e) {
        if (!Commands.autoSmeltOFF.contains(p.getName())) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);
        }
    }

    public void pickNoSmelt(Player p, ItemStack notSmelt, BlockBreakEvent e) {
        if (!autoPickupOFF.contains(p.getName())) {
            dropsItems(p, notSmelt);
        } else {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoPickup(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.hasPermission("advancedautosmelt.autopickup")) return;
        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.equalsIgnoreCase(p.getWorld().getName())) return;

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
                dropsItems(p, drops);
                removeDrops(e);
            }
        } else {
            for (ItemStack drops : e.getBlock().getDrops()) {
                dropsItems(p, drops);
                removeDrops(e);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltGold(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.gold"))) return;
        if (isEFS) return;
        if (isAutoSmeltDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
        if (isSmeltGold) {
            if (isAutoPickupEnabled) {
                smelt(p, goldIngot, goldOre, e);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, goldIngot, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, goldOre, e);
            }
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltIron(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.iron"))) return;
        if (isEFS) return;
        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (isSmeltIron) {
            if (isAutoPickupEnabled) {
                smelt(p, ironIngot, ironOre, e);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, ironIngot, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, ironOre, e);
            }
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltStone(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.STONE)) return;
        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.stone"))) return;
        if (isEFS) return;
        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (isSmeltStone) {
            if (isAutoPickupEnabled) {
                smelt(p, stone, cobblestone, e);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, stone, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, cobblestone, e);
            }
        }
        removeDrops(e);
    }
}