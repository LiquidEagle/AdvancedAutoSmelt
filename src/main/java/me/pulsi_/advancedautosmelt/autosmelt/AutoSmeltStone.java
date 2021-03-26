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

public class AutoSmeltStone implements Listener {

    ItemStack stone = new ItemStack(Material.STONE);
    ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);
    Map autoSmeltOFF = Commands.toggleAutoSmelt;
    Map autoPickupOFF = Commands.toggleAutoPickup;

    @EventHandler
    public void autoSmeltIron(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.disable-creative-mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;

        if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_stone")) {

            if (!(p.hasPermission("advancedautosmelt.smelt.stone"))) return;

                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {

                    if (!autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.STONE) {
                            if (!p.getInventory().addItem(stone).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), stone);
                            }
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        return;

                    } else if (autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.STONE) {
                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(stone));
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (!autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.STONE) {
                            if (!p.getInventory().addItem(cobblestone).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), cobblestone);
                            }
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                } else {
                    if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_stone")) {
                        if (!autoSmeltOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.STONE) {
                                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(stone));
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    }
                }
            } else {
                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {
                    if (!autoPickupOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.STONE) {
                            if (!p.getInventory().addItem(cobblestone).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), cobblestone);
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        } else {

            if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_stone")) {

                if (!(p.hasPermission("advancedautosmelt.smelt.stone"))) return;

                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {

                    if (!autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.STONE) {
                            if (!p.getInventory().addItem(stone).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), stone);
                            }
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        return;

                    } else if (autoPickupOFF.containsKey(p.getName()) && !autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.STONE) {
                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(stone));
                            e.getBlock().setType(Material.AIR);
                        }

                    } else if (!autoPickupOFF.containsKey(p.getName()) && autoSmeltOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.STONE) {
                            if (!p.getInventory().addItem(cobblestone).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), cobblestone);
                            }
                            e.getBlock().setType(Material.AIR);
                        }
                    }
                } else {
                    if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoSmelt.smelt_stone")) {
                        if (!autoSmeltOFF.containsKey(p.getName())) {
                            if (e.getBlock().getType() == Material.STONE) {
                                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(stone));
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    }
                }
            } else {
                if (AdvancedAutoSmelt.getInstance().getConfig().getBoolean("AutoPickup.enable_autopickup")) {
                    if (!autoPickupOFF.containsKey(p.getName())) {
                        if (e.getBlock().getType() == Material.STONE) {
                            if (!p.getInventory().addItem(cobblestone).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), cobblestone);
                                e.getBlock().setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
    }
}