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

    private AdvancedAutoSmelt plugin;
    public AutoSmelt(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    private ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    private ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private ItemStack stone = new ItemStack(Material.STONE);
    private ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);
    private Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private Set<String> autoSmeltOFF = Commands.autoPickupOFF;

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

        if (plugin.getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

        }
        if (plugin.getConfig().getBoolean("AutoSmelt.smelt-gold")) {
            if (!(p.hasPermission("advancedautosmelt.smelt.gold"))) return;
            if (plugin.getConfig().getBoolean("AutoPickup.enable-autopickup")) {

                smelt(p, Material.GOLD_ORE, goldIngot, goldOre, Material.AIR, e);

            } else {

                if (plugin.getConfig().getBoolean("AutoSmelt.smelt-gold")) {

                    smeltNoPickup(p, Material.GOLD_ORE, Material.AIR, goldIngot, e);
                }
            }

        } else {

            if (plugin.getConfig().getBoolean("AutoPickup.enable-autopickup")) {

                pickNoSmelt(p, Material.GOLD_ORE, Material.AIR, goldOre, e);
            }
        }
    }

    @EventHandler
    public void autoSmeltIron(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (plugin.getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

        }
        if (plugin.getConfig().getBoolean("AutoSmelt.smelt-iron")) {
            if (!(p.hasPermission("advancedautosmelt.smelt.iron"))) return;
            if (plugin.getConfig().getBoolean("AutoPickup.enable-autopickup")) {

                smelt(p, Material.IRON_ORE, ironIngot, ironOre, Material.AIR, e);

            } else {

                if (plugin.getConfig().getBoolean("AutoSmelt.smelt-iron")) {

                    smeltNoPickup(p, Material.IRON_ORE, Material.AIR, ironIngot, e);
                }
            }

        } else {

            if (plugin.getConfig().getBoolean("AutoPickup.enable-autopickup")) {

                pickNoSmelt(p, Material.IRON_ORE, Material.AIR, ironOre, e);
            }
        }
    }

    @EventHandler
    public void autoSmeltStone(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (plugin.getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

        }
        if (plugin.getConfig().getBoolean("AutoSmelt.smelt-stone")) {
            if (!(p.hasPermission("advancedautosmelt.smelt.stone"))) return;
            if (plugin.getConfig().getBoolean("AutoPickup.enable-autopickup")) {

                smelt(p, Material.STONE, stone, cobblestone, Material.AIR, e);

            } else {

                if (plugin.getConfig().getBoolean("AutoSmelt.smelt-stone")) {

                    smeltNoPickup(p, Material.STONE, Material.AIR, stone, e);
                }
            }

        } else {

            if (plugin.getConfig().getBoolean("AutoPickup.enable-autopickup")) {

                pickNoSmelt(p, Material.STONE, Material.AIR, cobblestone, e);
            }
        }
    }
}