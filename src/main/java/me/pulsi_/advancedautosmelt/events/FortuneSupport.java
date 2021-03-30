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

    private AdvancedAutoSmelt plugin;
    private boolean isEFS;
    private boolean useWhitelist;
    private boolean isDCM;
    private boolean isSmeltGold;
    private boolean isSmeltIron;
    private boolean isSmeltStone;
    private boolean isAutoPickupEnabled;
    private List<String> whitelist;

    public FortuneSupport(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
        this.isEFS = plugin.isEFS();
        this.useWhitelist = plugin.useWhitelist();
        this.isDCM = plugin.isDCM();
        this.isSmeltGold = plugin.isSmeltGold();
        this.isSmeltIron = plugin.isSmeltIron();
        this.isSmeltStone = plugin.isSmeltStone();
        this.isAutoPickupEnabled = plugin.isAutoPickupEnabled();
        this.whitelist = plugin.getWhiteList();
    }

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoSmeltOFF;

    private final ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private final ItemStack stone = new ItemStack(Material.STONE);
    private final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);

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
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), notSmelt);

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

    public void smeltNoPickup(Player p, Material block, Material air, ItemStack smelt, BlockBreakEvent e) {
        if (!Commands.autoSmeltOFF.contains(p.getName())) {
            if (e.getBlock().getType() == block) {
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), smelt);
            }
            e.getBlock().setType(air);
        }
    }

    public void pickNoSmelt(Player p, Material block, Material air, ItemStack noSmelt, BlockBreakEvent e) {
        if (!autoPickupOFF.contains(p.getName())) {
            if (e.getBlock().getType() == block) {
                if (!p.getInventory().addItem(noSmelt).isEmpty()) {
                    p.getWorld().dropItem(p.getLocation(), noSmelt);
                }
                e.getBlock().setType(air);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreakFortune(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isEFS) return;

        if (e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.IRON_ORE || e.getBlock().getType() == Material.GOLD_ORE) return;

        if (useWhitelist) {
                if (whitelist.contains(e.getBlock().getType().toString())) {

                    if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return;
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int randomDrop = r.nextInt(fortuneLevel);
                    for (ItemStack drops : e.getBlock().getDrops()) {
                        int dropsAmount = drops.getAmount() * randomDrop - 1;

                        drops.setAmount(dropsAmount);

                        if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
                        if (!autoPickupOFF.contains(p.getName())) {
                            if (!p.getInventory().addItem(drops).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), drops);
                            }
                        } else {
                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), drops);
                        }
                    }
                } else {

                    for (ItemStack drops : e.getBlock().getDrops()) {
                        if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
                        if (!autoPickupOFF.contains(p.getName())) {
                            if (!p.getInventory().addItem(drops).isEmpty()) {
                                p.getWorld().dropItem(p.getLocation(), drops);
                            }
                        } else {
                            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), drops);
                        }
                        e.getBlock().setType(Material.AIR);
                    }
                }

        } else {

            if (e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.IRON_ORE || e.getBlock().getType() == Material.GOLD_ORE) return;

            if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return;
            int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

            Random r = new Random();
            int randomDrop = r.nextInt(fortuneLevel);
            for (ItemStack drops : e.getBlock().getDrops()) {
                int dropsAmount = drops.getAmount() * randomDrop;

                drops.setAmount(dropsAmount);

                if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!p.getInventory().addItem(drops).isEmpty()) {
                        p.getWorld().dropItem(p.getLocation(), drops);
                    }
                } else {
                    e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), drops);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onStoneBreakFortune(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isEFS) return;

        System.out.println(e.getBlock().getType() == Material.STONE);
        if (!(e.getBlock().getType() == Material.STONE)) return;

        System.out.println(useWhitelist);
        if (useWhitelist) {
            System.out.println(whitelist.contains(Material.STONE.toString()));
            if (whitelist.contains(Material.STONE.toString())) {

                if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return;
                int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                Random r = new Random();
                int randomDrop = r.nextInt(fortuneLevel);

                stone.setAmount(randomDrop);
                cobblestone.setAmount(randomDrop);

                if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
                if (isSmeltStone) {
                    if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
                    if (isAutoPickupEnabled) {
                        smelt(p, Material.STONE, stone, cobblestone, Material.AIR, e);
                    } else {
                        if (!isSmeltStone) return;
                        smeltNoPickup(p, Material.STONE, Material.AIR, stone, e);
                    }
                } else {
                    if (!isAutoPickupEnabled) return;
                    pickNoSmelt(p, Material.STONE, Material.AIR, cobblestone, e);
                }
            }

        } else {

            if (!(e.getBlock().getType() == Material.STONE)) return;
            if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return;
            int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

            Random r = new Random();
            int randomDrop = r.nextInt(fortuneLevel);

            stone.setAmount(randomDrop);
            cobblestone.setAmount(randomDrop);

            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
            if (isSmeltStone) {
                if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
                if (isAutoPickupEnabled) {
                    smelt(p, Material.STONE, stone, cobblestone, Material.AIR, e);
                } else {
                    if (!isSmeltStone) return;
                    smeltNoPickup(p, Material.STONE, Material.AIR, stone, e);
                }
            } else {
                if (!isAutoPickupEnabled) return;
                pickNoSmelt(p, Material.STONE, Material.AIR, cobblestone, e);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onIronOreBreakFortune(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isEFS) return;

        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;

        if (useWhitelist) {
            if (whitelist.contains(Material.IRON_ORE.toString())) {

                if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return;
                int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                Random r = new Random();
                int randomDrop = r.nextInt(fortuneLevel);

                ironIngot.setAmount(randomDrop);
                ironOre.setAmount(randomDrop);

                if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
                if (isSmeltIron) {
                    if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
                    if (isAutoPickupEnabled) {
                        smelt(p, Material.IRON_ORE, ironIngot, ironOre, Material.AIR, e);
                    } else {
                        if (!isSmeltIron) return;
                        smeltNoPickup(p, Material.IRON_ORE, Material.AIR, ironIngot, e);
                    }
                } else {
                    if (!isAutoPickupEnabled) return;
                    pickNoSmelt(p, Material.IRON_ORE, Material.AIR, ironOre, e);
                }
            }

        } else {

            if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
            if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return;
            int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

            Random r = new Random();
            int randomDrop = r.nextInt(fortuneLevel);

            ironIngot.setAmount(randomDrop);
            ironOre.setAmount(randomDrop);

            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
            if (isSmeltIron) {
                if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
                if (isAutoPickupEnabled) {
                    smelt(p, Material.IRON_ORE, ironIngot, ironOre, Material.AIR, e);
                } else {
                    if (!isSmeltIron) return;
                    smeltNoPickup(p, Material.IRON_ORE, Material.AIR, ironIngot, e);
                }
            } else {
                if (!isAutoPickupEnabled) return;
                pickNoSmelt(p, Material.IRON_ORE, Material.AIR, ironOre, e);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGoldOreBreakFortune(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (!isEFS) return;

        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;

        if (useWhitelist) {
            if (whitelist.contains(Material.GOLD_ORE.toString())) {

                if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return;
                int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                Random r = new Random();
                int randomDrop = r.nextInt(fortuneLevel);

                goldIngot.setAmount(randomDrop);
                goldOre.setAmount(randomDrop);

                if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
                if (isSmeltGold) {
                    if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
                    if (isAutoPickupEnabled) {
                        smelt(p, Material.GOLD_ORE, goldIngot, goldOre, Material.AIR, e);
                    } else {
                        if (!isSmeltGold) return;
                        smeltNoPickup(p, Material.GOLD_ORE, Material.AIR, goldIngot, e);
                    }
                } else {
                    if (!isAutoPickupEnabled) return;
                    pickNoSmelt(p, Material.GOLD_ORE, Material.AIR, goldOre, e);
                }
            }

        } else {

            if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
            if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) return;
            int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

            Random r = new Random();
            int randomDrop = r.nextInt(fortuneLevel);

            goldIngot.setAmount(randomDrop);
            goldOre.setAmount(randomDrop);

            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}
            if (isSmeltGold) {
                if (!(p.hasPermission("advancedautosmelt.fortune"))) return;
                if (isAutoPickupEnabled) {
                    smelt(p, Material.GOLD_ORE, goldIngot, goldOre, Material.AIR, e);
                } else {
                    if (!isSmeltGold) return;
                    smeltNoPickup(p, Material.GOLD_ORE, Material.AIR, goldIngot, e);
                }
            } else {
                if (!isAutoPickupEnabled) return;
                pickNoSmelt(p, Material.GOLD_ORE, Material.AIR, goldOre, e);
            }
        }
    }
}