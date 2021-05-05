package me.pulsi_.advancedautosmelt.events.supports;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
import me.pulsi_.advancedautosmelt.utils.MethodUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.Set;

public class FortuneSupport implements Listener {

    private AdvancedAutoSmelt plugin;
    public FortuneSupport(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
    }

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoSmeltOFF;

    private final ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT, 1);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE, 1);
    private final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT, 1);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE, 1);
    private final ItemStack stone = new ItemStack(Material.STONE, 1);
    private final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE, 1);

    public void dropsItems(Player p, ItemStack i) {

        FileConfiguration config = plugin.getConfiguration();

        if (!p.getInventory().addItem(i).isEmpty()) {
            if (config.getBoolean("AutoPickup.Inv-Full-Drop-Items")) {
                p.getWorld().dropItem(p.getLocation(), i);
            }
        }
    }

    public void removeDrops(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        if (config.getBoolean("Enable-Legacy-Support")) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreakFortune(BlockBreakEvent e) {

        MethodUtils methodUtils = new MethodUtils(plugin);
        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(e.getBlock().getType().toString())) return;

        if (e.isCancelled()) return;
        if (!config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        if (e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.IRON_ORE
                || e.getBlock().getType() == Material.GOLD_ORE || e.getBlock().getType() == Material.CHEST
                || e.getBlock().getType() == Material.FURNACE || e.getBlock().getType() == Material.ENDER_CHEST) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (p.hasPermission("advancedautosmelt.fortune")) {
            if (config.getBoolean("Fortune.Use-Whitelist")) {
                    if (!config.getStringList("Fortune.Whitelist").contains(e.getBlock().getType().toString())) {
                        for (ItemStack drops : e.getBlock().getDrops()) {
                            methodUtils.generalFortuneSupport(p, config, drops, autoPickupOFF, e);
                        }
                    } else {

                        if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                            int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                            Random r = new Random();
                            int min = 1;
                            int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                            for (ItemStack drops : e.getBlock().getDrops()) {
                                drops.setAmount(multiply);
                                methodUtils.generalFortuneSupport(p, config, drops, autoPickupOFF, e);
                            }

                        } else {
                            for (ItemStack drops : e.getBlock().getDrops()) {
                                methodUtils.generalFortuneSupport(p, config, drops, autoPickupOFF, e);
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
                        methodUtils.generalFortuneSupport(p, config, drops, autoPickupOFF, e);
                    }

                } else {
                    for (ItemStack drops : e.getBlock().getDrops()) {
                        methodUtils.generalFortuneSupport(p, config, drops, autoPickupOFF, e);
                    }
                }
            }

        } else {
            if (e.isCancelled()) return;
            if (!p.hasPermission("advancedautosmelt.autopickup")) return;
            for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
                if (disabledWorlds.equalsIgnoreCase(p.getWorld().getName())) return;
            for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
                if (blockBlacklist.contains(e.getBlock().getType().toString())) return;
            if (e.getBlock().getType() == (Material.IRON_ORE) || e.getBlock().getType() == (Material.GOLD_ORE) ||
                    e.getBlock().getType() == (Material.STONE) || e.getBlock().getType() == (Material.CHEST) ||
                    e.getBlock().getType() == (Material.FURNACE) || e.getBlock().getType() == (Material.ENDER_CHEST)) return;
            if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
                if (p.getGameMode().equals(GameMode.CREATIVE)) return;
            }
            if (autoPickupOFF.contains(p.getName())) return;
            if (!config.getBoolean("AutoPickup.Enable-Autopickup")) return;

            for (ItemStack drops : e.getBlock().getDrops()) {
                dropsItems(p, drops);
                removeDrops(e);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onStoneBreakFortune(BlockBreakEvent e) {

        MethodUtils methodUtils = new MethodUtils(plugin);
        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(Material.STONE.toString())) return;

        if (e.isCancelled()) return;
        if (!config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        if (!(e.getBlock().getType() == Material.STONE)) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (p.hasPermission("advancedautosmelt.fortune")) {
            if (config.getBoolean("Fortune.Use-Whitelist")) {
                    if (!config.getStringList("Fortune.Whitelist").contains(Material.STONE.toString())) {

                        stone.setAmount(1);
                        cobblestone.setAmount(1);
                        methodUtils.autoPickSmeltStone(p, config, stone, cobblestone, autoPickupOFF, autoSmeltOFF, e);

                    } else {

                        if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                            int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                            Random r = new Random();
                            int min = 1;
                            int randomDrop = r.nextInt((fortuneLevel - min) + 1) + min;
                            stone.setAmount(randomDrop);
                            cobblestone.setAmount(randomDrop);
                        } else {
                            stone.setAmount(1);
                            cobblestone.setAmount(1);
                        }
                        methodUtils.fortuneSupportStone(p, config, stone, cobblestone, e, autoPickupOFF, autoSmeltOFF);
                    }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                    Random r = new Random();
                    int min = 1;
                    int randomDrop = r.nextInt((fortuneLevel - min) + 1) + min;
                    stone.setAmount(randomDrop);
                    cobblestone.setAmount(randomDrop);
                } else {
                    stone.setAmount(1);
                    cobblestone.setAmount(1);
                }
                methodUtils.fortuneSupportStone(p, config, stone, cobblestone, e, autoPickupOFF, autoSmeltOFF);
            }

        } else {
            methodUtils.autoPickSmeltStone(p, config, stone, cobblestone, autoPickupOFF, autoSmeltOFF, e);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onIronOreBreakFortune(BlockBreakEvent e) {

        MethodUtils methodUtils = new MethodUtils(plugin);
        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(Material.IRON_ORE.toString())) return;

        if (e.isCancelled()) return;
        if (!config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (p.hasPermission("advancedautosmelt.fortune")) {
            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (!config.getStringList("Fortune.Whitelist").contains(Material.IRON_ORE.toString())) {
                    ironIngot.setAmount(1);
                    ironOre.setAmount(1);
                    methodUtils.autoPickSmeltIron(p, config, ironIngot, ironOre, autoPickupOFF, autoSmeltOFF, e);
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
                    methodUtils.fortuneSupportIron(p, config, ironIngot, ironOre, e, autoPickupOFF, autoSmeltOFF);
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
                methodUtils.fortuneSupportIron(p, config, ironIngot, ironOre, e, autoPickupOFF, autoSmeltOFF);
            }

        } else {
            methodUtils.autoPickSmeltIron(p, config, ironIngot, ironOre, autoPickupOFF, autoSmeltOFF, e);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onGoldOreBreakFortune(BlockBreakEvent e) {

        MethodUtils methodUtils = new MethodUtils(plugin);
        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;

        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(Material.GOLD_ORE.toString())) return;

        if (e.isCancelled()) return;
        if (!config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (p.hasPermission("advancedautosmelt.fortune")) {
            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (!config.getStringList("Fortune.Whitelist").contains(Material.GOLD_ORE.toString())) {
                    goldIngot.setAmount(1);
                    goldOre.setAmount(1);
                    methodUtils.autoPickSmeltGold(p, config, goldIngot, goldOre, autoPickupOFF, autoSmeltOFF, e);
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
                    methodUtils.fortuneSupportGold(p, config, goldIngot, goldOre, e, autoPickupOFF, autoSmeltOFF);
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
                methodUtils.fortuneSupportGold(p, config, goldIngot, goldOre, e, autoPickupOFF, autoSmeltOFF);
            }
        } else {

            if (e.isCancelled()) return;
            if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
            for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
                if (disabledWorlds.contains(p.getWorld().getName())) return;
            for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
                if (blockBlacklist.contains(e.getBlock().getType().toString())) return;
            if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
                if (p.getGameMode().equals(GameMode.CREATIVE)) return;
            }
            methodUtils.autoPickSmeltGold(p, config, goldIngot, goldOre, autoPickupOFF, autoSmeltOFF, e);
        }
    }
}