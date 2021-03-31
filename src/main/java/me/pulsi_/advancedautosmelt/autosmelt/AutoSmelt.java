package me.pulsi_.advancedautosmelt.autosmelt;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class AutoSmelt implements Listener {

    private boolean isEFS;
    private final boolean isSmeltGold;
    private final boolean isSmeltIron;
    private final boolean isSmeltStone;
    private final boolean isAutoSmeltDCM;
    private final boolean isAutoPickupEnabled;
    private List<String> autoSmeltDisabledWorlds;
    private List<String> autoPickupDisabledWorlds;

    public AutoSmelt(AdvancedAutoSmelt plugin) {
        this.isEFS = plugin.isEFS();
        this.isAutoSmeltDCM = plugin.isDCM();
        this.isAutoPickupEnabled = plugin.isAutoPickupEnabled();
        this.isSmeltGold = plugin.isSmeltGold();
        this.isSmeltIron = plugin.isSmeltIron();
        this.isSmeltStone = plugin.isSmeltStone();
        this.autoSmeltDisabledWorlds = plugin.getAutoSmeltDisabledWorlds();
        this.autoPickupDisabledWorlds = plugin.getAutoPickupDisabledWorlds();
    }

    private final ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private final ItemStack stone = new ItemStack(Material.STONE);
    private final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoPickupOFF;

    public void smelt(Player p, Material block, ItemStack smelt, ItemStack notSmelt, BlockBreakEvent e) {

        boolean cantPickup = autoPickupOFF.contains(p.getName());
        boolean cantSmelt = autoSmeltOFF.contains(p.getName());

        if (!cantPickup && !cantSmelt) {
            if (!(e.getBlock().getType() == block)) return;
            for (String noPickupWorlds : autoPickupDisabledWorlds)
                if (!noPickupWorlds.contains(p.getWorld().getName())) {
                    if (!p.getInventory().addItem(smelt).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), smelt);
                    }
                } else {
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);
                }

        } else if (cantPickup && cantSmelt) {
            return;

        } else if (cantPickup) {
            if (!(e.getBlock().getType() == block)) return;
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);

        } else if (cantSmelt) {
            if (!(e.getBlock().getType() == block)) return;
            for (String noPickupWorlds : autoPickupDisabledWorlds)
                if (!noPickupWorlds.contains(p.getWorld().getName())) {
                    if (!p.getInventory().addItem(notSmelt).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), notSmelt);
                    }
                } else {
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);
                }
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
                for (String noPickupWorlds : autoPickupDisabledWorlds)
                    if (!noPickupWorlds.contains(p.getWorld().getName())) {
                        if (!p.getInventory().addItem(notSmelt).isEmpty()) {
                            p.getWorld().dropItem(p.getLocation(), notSmelt);
                        }
                    } else {
                        e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);
                    }
            }
        }
    }

    @EventHandler
    public void autoSmeltGold(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (isEFS) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.gold"))) return;
        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }

        for (String noSmeltWorlds : autoSmeltDisabledWorlds)
            if (noSmeltWorlds.contains(p.getWorld().getName())) return;

        if (isSmeltGold) {
            if (isAutoPickupEnabled) {
                smelt(p, Material.GOLD_ORE, goldIngot, goldOre, e);
                e.getBlock().setType(Material.AIR);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.GOLD_ORE, goldIngot, e);
            }
            e.getBlock().setType(Material.AIR);
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.GOLD_ORE, goldOre, e);
                e.getBlock().setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void autoSmeltIron(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (isEFS) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.iron"))) return;
        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }

        for (String noSmeltWorlds : autoSmeltDisabledWorlds)
            if (noSmeltWorlds.contains(p.getWorld().getName())) return;

        if (isSmeltIron) {
            if (isAutoPickupEnabled) {
                smelt(p, Material.IRON_ORE, ironIngot, ironOre, e);
                e.getBlock().setType(Material.AIR);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.IRON_ORE, ironIngot, e);
            }
            e.getBlock().setType(Material.AIR);
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.IRON_ORE, ironOre, e);
                e.getBlock().setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void autoSmeltStone(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (isEFS) return;

        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }

        for (String noSmeltWorlds : autoSmeltDisabledWorlds)
            if (noSmeltWorlds.contains(p.getWorld().getName())) return;

        if (isSmeltStone) {
            if (!(p.hasPermission("advancedautosmelt.smelt.stone"))) return;
            if (isAutoPickupEnabled) {
                smelt(p, Material.STONE, stone, cobblestone, e);
                e.getBlock().setType(Material.AIR);
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.STONE, stone, e);
            }
            e.getBlock().setType(Material.AIR);
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.STONE, cobblestone, e);
                e.getBlock().setType(Material.AIR);
            }
        }
    }
}