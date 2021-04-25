package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class MethodUtils {

    private AdvancedAutoSmelt plugin;
    public MethodUtils(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    public void removeDrops(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        if (config.getBoolean("Enable-Legacy-Support")) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    public void dropsItems(Player p, ItemStack i) {

        FileConfiguration config = plugin.getConfiguration();

        if (!p.getInventory().addItem(i).isEmpty()) {
            if (config.getBoolean("AutoPickup.Inv-Full-Drop-Items")) {
                p.getWorld().dropItem(p.getLocation(), i);
            }
        }
    }

    public void smeltNoPickup(ItemStack smelt, BlockBreakEvent e) {
        e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);
    }

    public void pickNoSmelt(Player p, ItemStack notSmelt) {
        dropsItems(p, notSmelt);
    }

    public void fortuneSupportStone(Player p, FileConfiguration config, ItemStack stone, ItemStack cobblestone, BlockBreakEvent e, Set<String> autoPickupOFF, Set<String> autoSmeltOFF) {

        if (config.getBoolean("AutoSmelt.Smelt-Stone")) {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.autopickup")) {
                            if (p.hasPermission("advancedautosmelt.smelt.stone")) {
                                dropsItems(p, stone);
                            } else {
                                if (!autoPickupOFF.contains(p.getName())) {
                                    if (p.hasPermission("advancedautosmelt.autopickup")) {
                                        pickNoSmelt(p, cobblestone);
                                    } else {
                                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                                    }
                                } else {
                                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                                }
                            }
                        } else {
                            if (!autoSmeltOFF.contains(p.getName())) {
                                if (p.hasPermission("advancedautosmelt.smelt.stone")) {
                                    smeltNoPickup(stone, e);
                                } else {
                                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                                }
                            } else {
                                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                            }
                        }
                    } else {
                        if (!autoPickupOFF.contains(p.getName())) {
                            if (p.hasPermission("advancedautosmelt.autopickup")) {
                                pickNoSmelt(p, cobblestone);
                            } else {
                                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                            }
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                        }
                    }
                } else {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.smelt.stone")) {
                            smeltNoPickup(stone, e);
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                        }
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                    }
                }
            } else {
                if (!autoSmeltOFF.contains(p.getName())) {
                    if (p.hasPermission("advancedautosmelt.smelt.stone")) {
                        smeltNoPickup(stone, e);
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                    }
                } else {
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                }
            }
        } else {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    pickNoSmelt(p, cobblestone);
                } else {
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
                }
            } else {
                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), cobblestone);
            }
        }
        removeDrops(e);
    }

    public void fortuneSupportIron(Player p, FileConfiguration config, ItemStack ironIngot, ItemStack ironOre, BlockBreakEvent e, Set<String> autoPickupOFF, Set<String> autoSmeltOFF) {

        if (config.getBoolean("AutoSmelt.Smelt-Iron")) {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.autopickup")) {
                            if (p.hasPermission("advancedautosmelt.smelt.iron")) {
                                dropsItems(p, ironIngot);
                            } else {
                                if (!autoPickupOFF.contains(p.getName())) {
                                    if (p.hasPermission("advancedautosmelt.autopickup")) {
                                        pickNoSmelt(p, ironOre);
                                    } else {
                                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                                    }
                                } else {
                                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                                }
                            }
                        } else {
                            if (!autoSmeltOFF.contains(p.getName())) {
                                if (p.hasPermission("advancedautosmelt.smelt.iron")) {
                                    smeltNoPickup(ironIngot, e);
                                } else {
                                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                                }
                            } else {
                                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                            }
                        }
                    } else {
                        if (!autoPickupOFF.contains(p.getName())) {
                            if (p.hasPermission("advancedautosmelt.autopickup")) {
                                pickNoSmelt(p, ironOre);
                            } else {
                                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                            }
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                        }
                    }
                } else {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.smelt.iron")) {
                            smeltNoPickup(ironIngot, e);
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                        }
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                    }
                }
            } else {
                if (!autoSmeltOFF.contains(p.getName())) {
                    if (p.hasPermission("advancedautosmelt.smelt.iron")) {
                        smeltNoPickup(ironIngot, e);
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                    }
                } else {
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                }
            }
        } else {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    pickNoSmelt(p, ironOre);
                } else {
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
                }
            } else {
                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), ironOre);
            }
        }
        removeDrops(e);
    }


    public void fortuneSupportGold(Player p, FileConfiguration config, ItemStack goldIngot, ItemStack goldOre, BlockBreakEvent e, Set<String> autoPickupOFF, Set<String> autoSmeltOFF) {

        if (config.getBoolean("AutoSmelt.Smelt-Gold")) {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.autopickup")) {
                            if (p.hasPermission("advancedautosmelt.smelt.gold")) {
                                dropsItems(p, goldIngot);
                            } else {
                                if (!autoPickupOFF.contains(p.getName())) {
                                    if (p.hasPermission("advancedautosmelt.autopickup")) {
                                        pickNoSmelt(p, goldOre);
                                    } else {
                                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                                    }
                                } else {
                                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                                }
                            }
                        } else {
                            if (!autoSmeltOFF.contains(p.getName())) {
                                if (p.hasPermission("advancedautosmelt.smelt.gold")) {
                                    smeltNoPickup(goldIngot, e);
                                } else {
                                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                                }
                            } else {
                                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                            }
                        }
                    } else {
                        if (!autoPickupOFF.contains(p.getName())) {
                            if (p.hasPermission("advancedautosmelt.autopickup")) {
                                pickNoSmelt(p, goldOre);
                            } else {
                                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                            }
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                        }
                    }
                } else {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.smelt.gold")) {
                            smeltNoPickup(goldIngot, e);
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                        }
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                    }
                }
            } else {
                if (!autoSmeltOFF.contains(p.getName())) {
                    if (p.hasPermission("advancedautosmelt.smelt.gold")) {
                        smeltNoPickup(goldIngot, e);
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                    }
                } else {
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                }
            }
        } else {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    pickNoSmelt(p, goldOre);
                } else {
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
                }
            } else {
                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), goldOre);
            }
        }
        removeDrops(e);
    }

    public void generalFortuneSupport(Player p, FileConfiguration config, ItemStack drops, Set<String> autoPickupOFF, BlockBreakEvent e) {
        if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
            if (!autoPickupOFF.contains(p.getName())) {
                if (p.hasPermission("advancedautosmelt.autopickup")) {
                    dropsItems(p, drops);
                } else {
                    e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), drops);
                }
            } else {
                e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), drops);
            }
        } else {
            e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), drops);
        }
        removeDrops(e);
    }

    //===========================================================================================================================================
    // AUTOPICKSMELT METHODS
    //===========================================================================================================================================

    public void autoPickSmeltStone(Player p, FileConfiguration config, ItemStack stone, ItemStack cobblestone, Set<String> autoPickupOFF, Set<String> autoSmeltOFF, BlockBreakEvent e) {
        if (config.getBoolean("AutoSmelt.Smelt-Stone")) {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.autopickup")) {
                            if (p.hasPermission("advancedautosmelt.smelt.stone")) {
                                dropsItems(p, stone);
                            } else {
                                if (autoPickupOFF.contains(p.getName())) return;
                                if (!p.hasPermission("advancedautosmelt.autopickup")) return;
                                pickNoSmelt(p, cobblestone);
                            }
                        } else {
                            if (!autoSmeltOFF.contains(p.getName())) return;
                            if (!p.hasPermission("advancedautosmelt.smelt.stone")) return;
                            smeltNoPickup(stone, e);
                        }
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        if (!p.hasPermission("advancedautosmelt.autopickup")) return;
                        pickNoSmelt(p, cobblestone);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    if (!p.hasPermission("advancedautosmelt.smelt.stone")) return;
                    smeltNoPickup(stone, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                if (!p.hasPermission("advancedautosmelt.smelt.stone")) return;
                smeltNoPickup(stone, e);
            }
        } else {
            if (!config.getBoolean("AutoPickup.Enable-Autopickup")) return;
            if (autoPickupOFF.contains(p.getName())) return;
            pickNoSmelt(p, cobblestone);
        }
        removeDrops(e);
    }

    public void autoPickSmeltIron(Player p, FileConfiguration config, ItemStack ironIngot, ItemStack ironOre, Set<String> autoPickupOFF, Set<String> autoSmeltOFF, BlockBreakEvent e) {
        if (config.getBoolean("AutoSmelt.Smelt-Iron")) {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.autopickup")) {
                            if (p.hasPermission("advancedautosmelt.smelt.iron")) {
                                dropsItems(p, ironIngot);
                            } else {
                                if (autoPickupOFF.contains(p.getName())) return;
                                if (!p.hasPermission("advancedautosmelt.autopickup")) return;
                                pickNoSmelt(p, ironOre);
                            }
                        } else {
                            if (!autoSmeltOFF.contains(p.getName())) return;
                            if (!p.hasPermission("advancedautosmelt.smelt.iron")) return;
                            smeltNoPickup(ironIngot, e);
                        }
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        if (!p.hasPermission("advancedautosmelt.autopickup")) return;
                        pickNoSmelt(p, ironOre);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    if (!p.hasPermission("advancedautosmelt.smelt.iron")) return;
                    smeltNoPickup(ironIngot, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                if (!p.hasPermission("advancedautosmelt.smelt.iron")) return;
                smeltNoPickup(ironIngot, e);
            }
        } else {
            if (!config.getBoolean("AutoPickup.Enable-Autopickup")) return;
            if (autoPickupOFF.contains(p.getName())) return;
            pickNoSmelt(p, ironOre);
        }
        removeDrops(e);
    }

    public void autoPickSmeltGold(Player p, FileConfiguration config, ItemStack goldIngot, ItemStack goldOre, Set<String> autoPickupOFF, Set<String> autoSmeltOFF, BlockBreakEvent e) {
        if (config.getBoolean("AutoSmelt.Smelt-Gold")) {
            if (config.getBoolean("AutoPickup.Enable-Autopickup")) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        if (p.hasPermission("advancedautosmelt.autopickup")) {
                            if (p.hasPermission("advancedautosmelt.smelt.gold")) {
                                dropsItems(p, goldIngot);
                            } else {
                                if (autoPickupOFF.contains(p.getName())) return;
                                if (!p.hasPermission("advancedautosmelt.autopickup")) return;
                                pickNoSmelt(p, goldOre);
                            }
                        } else {
                            if (!autoSmeltOFF.contains(p.getName())) return;
                            if (!p.hasPermission("advancedautosmelt.smelt.gold")) return;
                            smeltNoPickup(goldIngot, e);
                        }
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        if (!p.hasPermission("advancedautosmelt.autopickup")) return;
                        pickNoSmelt(p, goldOre);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    if (!p.hasPermission("advancedautosmelt.smelt.gold")) return;
                    smeltNoPickup(goldIngot, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                if (!p.hasPermission("advancedautosmelt.smelt.gold")) return;
                smeltNoPickup(goldIngot, e);
            }
        } else {
            if (!config.getBoolean("AutoPickup.Enable-Autopickup")) return;
            if (autoPickupOFF.contains(p.getName())) return;
            pickNoSmelt(p, goldOre);
        }
        removeDrops(e);
    }
}
