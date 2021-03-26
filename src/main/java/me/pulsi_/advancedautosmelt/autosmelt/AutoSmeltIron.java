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

import java.util.Map;

public class AutoSmeltIron implements Listener {

    ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    Map autoSmeltOFF = Commands.toggleAutoSmelt;
    Map autoPickupOFF = Commands.toggleAutoPickup;

    @EventHandler
    public void autoSmeltIron(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

            if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_iron")) {
                if (!(p.hasPermission("advancedautosmelt.smelt.iron"))) return;

                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {

                    if (!autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.IRON_ORE) {
                            if (!p.getInventory().addItem(ironIngot).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), ironIngot);
                            }
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        return;

                    } else if (autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.IRON_ORE) {
                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(ironIngot));
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (!autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.IRON_ORE) {
                            if (!p.getInventory().addItem(ironOre).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), ironOre);
                            }
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                } else {
                    if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_iron")) {
                        if (!autoSmeltOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.IRON_ORE) {
                                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(ironIngot));
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    }
                }
            } else {
                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {
                    if (!autoPickupOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.IRON_ORE) {
                            if (!p.getInventory().addItem(ironOre).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), ironOre);
                            }
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        } else {

            if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_iron")) {

                if (!(p.hasPermission("advancedautosmelt.smelt.iron"))) return;

                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {

                    if (!autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.IRON_ORE) {
                            if (!p.getInventory().addItem(ironIngot).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), ironIngot);
                            }
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        return;

                    } else if (autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.IRON_ORE) {
                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(ironIngot));
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (!autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.IRON_ORE) {
                            if (!p.getInventory().addItem(ironOre).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), ironOre);
                            }
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                } else {
                    if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_iron")) {
                        if (!autoSmeltOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.IRON_ORE) {
                                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(ironIngot));
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    }
                }
            } else {
                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {
                    if (!autoPickupOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.IRON_ORE) {
                            if (!p.getInventory().addItem(ironOre).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), ironOre);
                            }
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}