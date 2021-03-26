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

public class AutoSmeltGold implements Listener {

    ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    Map autoSmeltOFF = Commands.toggleAutoSmelt;
    Map autoPickupOFF = Commands.toggleAutoPickup;

    @EventHandler
    public void autoSmeltGold(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

        if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_gold")) {

            if (!(p.hasPermission("advancedautosmelt.smelt.gold"))) return;

                    if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {

                        if (!autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.GOLD_ORE) {
                                if (!p.getInventory().addItem(goldIngot).isEmpty()) {
                                    p.getWorld().dropItem(p.getLocation(), goldIngot);
                                }
                                e.getBlock().setType(Material.AIR);
                            }

                        } else if (autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                            return;

                        } else if (autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.GOLD_ORE) {
                                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(goldIngot));
                                e.getBlock().setType(Material.AIR);
                            }

                        } else if (!autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.GOLD_ORE) {
                                if (!p.getInventory().addItem(goldOre).isEmpty()) {
                                    p.getWorld().dropItem(p.getLocation(), goldOre);
                                }
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    } else {
                        if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_gold")) {
                            if (!autoSmeltOFF.containsKey(p.getName())) {
                                if (e.getBlock().getType() == Material.GOLD_ORE) {
                                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(goldIngot));
                                    e.getBlock().setType(Material.AIR);
                                }
                            }
                        }
                    }
                } else {
                    if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {
                        if (!autoPickupOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.GOLD_ORE) {
                                if (!p.getInventory().addItem(goldOre).isEmpty()) {
                                    p.getWorld().dropItem(p.getLocation(), goldOre);
                                }
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    }
                }
            } else {

            if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_gold")) {

                if (!(p.hasPermission("advancedautosmelt.smelt.gold"))) return;

                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {

                    if (!autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.GOLD_ORE) {
                            if (!p.getInventory().addItem(goldIngot).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), goldIngot);
                            }
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        return;

                    } else if (autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.GOLD_ORE) {
                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(goldIngot));
                            e.getBlock().getDrops().clear();
                        }

                    } else if (!autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.GOLD_ORE) {
                            if (!p.getInventory().addItem(goldOre).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), goldOre);
                            }
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                } else {
                    if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_gold")) {
                        if (!autoSmeltOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.GOLD_ORE) {
                                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(goldIngot));
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    }
                }
            } else {
                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {
                    if (!autoPickupOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.GOLD_ORE) {
                            if (!p.getInventory().addItem(goldOre).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), goldOre);
                            }
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}