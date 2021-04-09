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
import java.util.Random;
import java.util.Set;

public class FortuneSupport implements Listener {

    private final boolean isEFS;
    private final boolean useWhitelist;
    private final boolean isDCM;
    private final boolean isSmeltGold;
    private final boolean isSmeltIron;
    private final boolean isSmeltStone;
    private final boolean isAutoPickupEnabled;
    private final boolean useLegacySupp;
    private final List<String> whitelist;
    private final List<String> disabledWorlds;

    public FortuneSupport(AdvancedAutoSmelt plugin) {
        this.isEFS = plugin.isEFS();
        this.useWhitelist = plugin.useWhitelist();
        this.isDCM = plugin.isDCM();
        this.isSmeltGold = plugin.isSmeltGold();
        this.isSmeltIron = plugin.isSmeltIron();
        this.isSmeltStone = plugin.isSmeltStone();
        this.isAutoPickupEnabled = plugin.isAutoPickupEnabled();
        this.whitelist = plugin.getWhiteList();
        this.disabledWorlds = plugin.getFortuneDisabledWorlds();
        this.useLegacySupp = plugin.isUseLegacySupp();
    }

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoSmeltOFF;

    private final ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private final ItemStack stone = new ItemStack(Material.STONE);
    private final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);

    public void removeDrops(BlockBreakEvent e) {
        if (useLegacySupp) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    public void smelt(Player p, Material block, ItemStack smelt, ItemStack notSmelt, BlockBreakEvent e) {

        boolean cantPickup = autoPickupOFF.contains(p.getName());
        boolean cantSmelt = autoSmeltOFF.contains(p.getName());

        if (!cantPickup && !cantSmelt) {
            if (!(e.getBlock().getType() == block)) return;
            if (!p.getInventory().addItem(smelt).isEmpty()) {
                p.getWorld().dropItem(p.getLocation(), smelt);
            }

        } else if (cantPickup && cantSmelt) {
            return;

        } else if (cantPickup) {
            if (!(e.getBlock().getType() == block)) return;
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);

        } else if (cantSmelt) {
            if (!(e.getBlock().getType() == block)) return;
            if (!p.getInventory().addItem(notSmelt).isEmpty()) {
                p.getWorld().dropItem(p.getLocation(), notSmelt);
            }
        }
    }

    public void smeltNoPickup(Player p, Material block, ItemStack smelt, BlockBreakEvent e) {
        if (!Commands.autoSmeltOFF.contains(p.getName())) {
            if (e.getBlock().getType() == block) {
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);
            }
        }
    }

    public void pickNoSmelt(Player p, Material block, ItemStack noSmelt, BlockBreakEvent e) {
        if (!autoPickupOFF.contains(p.getName())) {
            if (e.getBlock().getType() == block) {
                if (!p.getInventory().addItem(noSmelt).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), noSmelt);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreakFortune(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isEFS) return;
        if (e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.IRON_ORE
                || e.getBlock().getType() == Material.GOLD_ORE || e.getBlock().getType() == Material.CHEST
                || e.getBlock().getType() == Material.FURNACE || e.getBlock().getType() == Material.ENDER_CHEST) return;
        if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
        if (isDCM) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }

        for (String worlds : disabledWorlds)
            if (worlds.contains(p.getWorld().getName())) return;

        if (useWhitelist) {
            if (whitelist.contains(e.getBlock().getType().toString())) {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                    for (ItemStack drops : e.getBlock().getDrops()) {
                        drops.setAmount(multiply);

                        if (isAutoPickupEnabled) {
                            if (!autoPickupOFF.contains(p.getName())) {
                                if (!p.getInventory().addItem(drops).isEmpty()) {
                                    p.getWorld().dropItem(p.getLocation(), drops);
                                }
                            } else {
                                e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                            }
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                        }
                    }

                } else {

                    for (ItemStack drops : e.getBlock().getDrops()) {

                        if (isAutoPickupEnabled) {
                            if (!autoPickupOFF.contains(p.getName())) {
                                if (!p.getInventory().addItem(drops).isEmpty()) {
                                    p.getWorld().dropItem(p.getLocation(), drops);
                                }
                            } else {
                                e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                            }
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                        }
                    }
                }
            } else {
                for (ItemStack drops : e.getBlock().getDrops()) {

                    if (isAutoPickupEnabled) {
                        if (!autoPickupOFF.contains(p.getName())) {
                            if (!p.getInventory().addItem(drops).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), drops);
                            }
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                        }
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                    }
                }
            }

        } else {

            if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                Random r = new Random();
                int min = 1;
                int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                for (ItemStack drops : e.getBlock().getDrops()) {
                    drops.setAmount(multiply);

                    if (isAutoPickupEnabled) {
                        if (!autoPickupOFF.contains(p.getName())) {
                            if (!p.getInventory().addItem(drops).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), drops);
                            }
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                        }
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                    }
                }
            } else {
                for (ItemStack drops : e.getBlock().getDrops()) {

                    if (isAutoPickupEnabled) {
                        if (!autoPickupOFF.contains(p.getName())) {
                            if (!p.getInventory().addItem(drops).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), drops);
                            }
                        } else {
                            e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                        }
                    } else {
                        e.getBlock().getLocation().getWorld().dropItem(p.getLocation(), drops);
                    }
                }
            }
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onStoneBreakFortune(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isEFS) return;
        if (!(e.getBlock().getType() == Material.STONE)) return;
        if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
        if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

        for (String worlds : disabledWorlds)
            if (worlds.contains(p.getWorld().getName())) return;

        if (useWhitelist) {
            if (whitelist.contains(Material.STONE.toString())) {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                    stone.setAmount(multiply);
                    cobblestone.setAmount(multiply);

                } else {
                    stone.setAmount(1);
                    cobblestone.setAmount(1);
                }
            }

        } else {

            if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                Random r = new Random();
                int min = 1;
                int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                stone.setAmount(multiply);
                cobblestone.setAmount(multiply);
            } else {
                stone.setAmount(1);
                cobblestone.setAmount(1);
            }

        }
        if (isSmeltStone) {
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        smelt(p, Material.STONE, stone, cobblestone, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickNoSmelt(p, Material.STONE, cobblestone, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    smeltNoPickup(p, Material.STONE, stone, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.STONE, stone, e);
            }
        } else {
            if (!isAutoPickupEnabled) return;
            if (autoPickupOFF.contains(p.getName())) return;
            pickNoSmelt(p, Material.STONE, cobblestone, e);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onIronOreBreakFortune(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isEFS) return;
        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
        if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
        if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

        for (String worlds : disabledWorlds)
            if (worlds.contains(p.getWorld().getName())) return;

        if (useWhitelist) {
            if (whitelist.contains(Material.IRON_ORE.toString())) {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int randomDrop = r.nextInt((fortuneLevel - min) + 1) + min;

                    ironIngot.setAmount(randomDrop);
                    ironOre.setAmount(randomDrop);

                } else {
                    ironIngot.setAmount(1);
                    ironOre.setAmount(1);
                }
            }

        } else {

            if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                Random r = new Random();
                int min = 1;
                int randomDrop = r.nextInt((fortuneLevel - min) + 1) + min;
                ironIngot.setAmount(randomDrop);
                ironOre.setAmount(randomDrop);

            } else {
                ironIngot.setAmount(1);
                ironOre.setAmount(1);
            }

        }
        if (isSmeltIron) {
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        smelt(p, Material.IRON_ORE, ironIngot, ironOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickNoSmelt(p, Material.IRON_ORE, ironOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    smeltNoPickup(p, Material.IRON_ORE, ironIngot, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.IRON_ORE, ironIngot, e);
            }
        } else {
            if (!isAutoPickupEnabled) return;
            if (autoPickupOFF.contains(p.getName())) return;
            pickNoSmelt(p, Material.IRON_ORE, ironOre, e);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onGoldOreBreakFortune(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isEFS) return;
        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
        if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
        if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

        for (String worlds : disabledWorlds)
            if (worlds.contains(p.getWorld().getName())) return;

        if (useWhitelist) {
            if (whitelist.contains(Material.GOLD_ORE.toString())) {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    goldIngot.setAmount(multiply);
                    goldOre.setAmount(multiply);
                } else {
                    goldIngot.setAmount(1);
                    goldOre.setAmount(1);
                }
            }

        } else {

            if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                Random r = new Random();
                int min = 1;
                int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                goldIngot.setAmount(multiply);
                goldOre.setAmount(multiply);
            } else {
                goldIngot.setAmount(1);
                goldOre.setAmount(1);
            }

        }
        if (isSmeltGold) {
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        smelt(p, Material.GOLD_ORE, goldIngot, goldOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickNoSmelt(p, Material.GOLD_ORE, goldOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    smeltNoPickup(p, Material.GOLD_ORE, goldIngot, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                smeltNoPickup(p, Material.GOLD_ORE, goldIngot, e);
            }
        } else {
            if (!isAutoPickupEnabled) return;
            if (autoPickupOFF.contains(p.getName())) return;
            pickNoSmelt(p, Material.GOLD_ORE, goldOre, e);
        }
        removeDrops(e);
    }
}