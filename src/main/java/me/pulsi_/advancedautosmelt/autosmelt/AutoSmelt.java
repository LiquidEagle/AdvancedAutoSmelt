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

import java.util.Set;

public class AutoSmelt implements Listener {

    private final boolean isSmeltGold;
    private final boolean isSmeltIron;
    private final boolean isSmeltStone;
    private final boolean isAutoSmeltDCM;
    private final boolean isAutoPickupEnabled;

    public AutoSmelt(AdvancedAutoSmelt plugin) {
        this.isAutoSmeltDCM = plugin.isDCM();
        this.isAutoPickupEnabled = plugin.isAutoPickupEnabled();
        this.isSmeltGold = plugin.isSmeltGold();
        this.isSmeltIron = plugin.isSmeltIron();
        this.isSmeltStone = plugin.isSmeltStone();
    }

    private final ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private final ItemStack stone = new ItemStack(Material.STONE);
    private final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoPickupOFF;

    public void smelt(Player p, Material block, ItemStack smelt, ItemStack notSmelt, Material air, BlockBreakEvent e) {

        boolean cantPickup = autoPickupOFF.contains(p.getName());
        boolean cantSmelt = autoSmeltOFF.contains(p.getName());

        if (!cantPickup && !cantSmelt) {
            if (!(e.getBlock().getType() == block)) return;
            if (!p.getInventory().addItem(smelt).isEmpty()) {
                p.getWorld().dropItem(p.getLocation(), smelt);
            }
            e.getBlock().setType(air);

        } else if (cantPickup && cantSmelt) {
            return;

        } else if (cantPickup) {
            if (!(e.getBlock().getType() == block)) return;
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);
            e.getBlock().setType(air);

        } else if (cantSmelt) {
            if (!(e.getBlock().getType() == block)) return;
            if (!p.getInventory().addItem(notSmelt).isEmpty()) {
                p.getWorld().dropItem(p.getLocation(), notSmelt);
            }
            e.getBlock().setType(air);
        }
    }

    public void smeltNoPickup(Player p, Material block, Material air, ItemStack i, BlockBreakEvent e) {
        if (!Commands.autoSmeltOFF.contains(p.getName())) {
            if (e.getBlock().getType() == block) {
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), i);
            }
            e.getBlock().setType(air);
        }
    }

    public void pickNoSmelt(Player p, Material block, Material air, ItemStack i, BlockBreakEvent e) {
        if (!autoPickupOFF.contains(p.getName())) {
            if (e.getBlock().getType() == block) {
                if (!p.getInventory().addItem(i).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), i);
                }
                e.getBlock().setType(air);
            }
        }
    }

    @EventHandler
    public void autoSmeltGold(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (isSmeltGold) {
            if (!(p.hasPermission("advancedautosmelt.smelt.gold"))) return;
            if (isAutoPickupEnabled) {
                smelt(p, Material.GOLD_ORE, goldIngot, goldOre, Material.AIR, e);
            } else {
                if (!isSmeltGold) return;
                smeltNoPickup(p, Material.GOLD_ORE, Material.AIR, goldIngot, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.GOLD_ORE, Material.AIR, goldOre, e);
            }
        }
    }

    @EventHandler
    public void autoSmeltIron(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (isSmeltIron) {
            if (!(p.hasPermission("advancedautosmelt.smelt.iron"))) return;
            if (isAutoPickupEnabled) {
                smelt(p, Material.IRON_ORE, ironIngot, ironOre, Material.AIR, e);
            } else {
                if (!isSmeltIron) return;
                smeltNoPickup(p, Material.IRON_ORE, Material.AIR, ironIngot, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.IRON_ORE, Material.AIR, ironOre, e);
            }
        }
    }

    @EventHandler
    public void autoSmeltStone(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (isAutoSmeltDCM) { if (p.getGameMode().equals(GameMode.CREATIVE)) return; }
        if (isSmeltStone) {
            if (!(p.hasPermission("advancedautosmelt.smelt.stone"))) return;
            if (isAutoPickupEnabled) {
                smelt(p, Material.STONE, stone, cobblestone, Material.AIR, e);
            } else {
                if (!isSmeltStone) return;
                smeltNoPickup(p, Material.STONE, Material.AIR, stone, e);
            }
        } else {
            if (isAutoPickupEnabled) {
                pickNoSmelt(p, Material.STONE, Material.AIR, cobblestone, e);
            }
        }
    }
}