package me.pulsi_.advancedautosmelt.events.supports;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import me.pulsi_.advancedautosmelt.utils.ChatUtils;
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

import java.util.List;
import java.util.Random;
import java.util.Set;

public class SilkTouchSupport implements Listener {

    private FileConfiguration config;
    private boolean isDCM;
    private boolean isAutoPickupEnabled;
    private boolean useLegacySupp;
    private final boolean isInvFullDrop;
    private List<String> whitelist;
    private List<String> disabledWorlds;

    public SilkTouchSupport(AdvancedAutoSmelt plugin) {
        this.config = plugin.getConfiguration();
        this.isDCM = config.getBoolean("AutoSmelt.Disable-Creative-Mode");
        this.isAutoPickupEnabled = config.getBoolean("AutoPickup.Enable-Autopickup");
        this.whitelist = config.getStringList("Fortune.Whitelist");
        this.disabledWorlds = config.getStringList("Disabled-Worlds");
        this.useLegacySupp = config.getBoolean("Enable-Legacy-Support");
        this.isInvFullDrop = config.getBoolean("AutoPickup.Inv-Full-Drop-Items");
    }

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoSmeltOFF;

    //Ores
    private final ItemStack stone = new ItemStack(Material.STONE);
    private final ItemStack coalOre = new ItemStack(Material.COAL_ORE);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private final ItemStack redstoneOre = new ItemStack(Material.REDSTONE_ORE);
    private final ItemStack lapisOre = new ItemStack(Material.LAPIS_ORE);
    private final ItemStack diamondOre = new ItemStack(Material.DIAMOND_ORE);
    private final ItemStack emeraldOre = new ItemStack(Material.EMERALD_ORE);
    //Blocks
    private final ItemStack iceBlock = new ItemStack(Material.ICE);
    private final ItemStack snowBlock = new ItemStack(Material.SNOW_BLOCK);
    private final ItemStack glowstoneBlock = new ItemStack(Material.GLOWSTONE);
    private final ItemStack clayBlock = new ItemStack(Material.CLAY);
    private final ItemStack enderchestBlock = new ItemStack(Material.ENDER_CHEST);
    private final ItemStack bookshelvesBlock = new ItemStack(Material.BOOKSHELF);

    public void dropsItems(BlockBreakEvent e, Player p, ItemStack i) {
        if (!p.getInventory().addItem(i).isEmpty()) {
            if (isInvFullDrop) {
                p.getWorld().dropItem(p.getLocation(), i);
            } else {
                removeDrops(e);
            }
        }
    }

    public void removeDrops(BlockBreakEvent e) {
        if (useLegacySupp) {
            e.getBlock().setType(Material.AIR);
        } else {
            e.setDropItems(false);
        }
    }

    public void silkTouch(Player p, ItemStack i, BlockBreakEvent e) {

        boolean cantPickup = autoPickupOFF.contains(p.getName());
        boolean cantSmelt = autoSmeltOFF.contains(p.getName());

        if (!cantPickup && !cantSmelt) {
            dropsItems(e, p, i);

        } else if (cantPickup && cantSmelt) {
            return;

        } else if (cantPickup) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), i);

        } else if (cantSmelt) {
            dropsItems(e, p, i);
        }
    }

    public void noPickup(Player p, ItemStack i, BlockBreakEvent e) {
        if (!Commands.autoSmeltOFF.contains(p.getName())) {
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), i);
        }
    }

    public void pickup(Player p, ItemStack i, BlockBreakEvent e) {
        if (!autoPickupOFF.contains(p.getName())) {
            dropsItems(e, p, i);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void stoneBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.STONE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.STONE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        stone.setAmount(multiply);

                    } else {
                        stone.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    stone.setAmount(multiply);
                } else {
                    stone.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, stone, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, stone, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, stone, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, stone, e);
            }
        } else {
            p.getInventory().addItem(stone);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void coalBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.COAL_ORE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.COAL_ORE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        coalOre.setAmount(multiply);

                    } else {
                        coalOre.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    coalOre.setAmount(multiply);
                } else {
                    coalOre.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, coalOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, coalOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, coalOre, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, coalOre, e);
            }
        } else {
            p.getInventory().addItem(coalOre);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void ironBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.IRON_ORE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        ironOre.setAmount(multiply);

                    } else {
                        ironOre.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    ironOre.setAmount(multiply);
                } else {
                    ironOre.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, ironOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, ironOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, ironOre, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, ironOre, e);
            }
        } else {
            p.getInventory().addItem(ironOre);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void goldBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.GOLD_ORE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        goldOre.setAmount(multiply);

                    } else {
                        goldOre.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    goldOre.setAmount(multiply);
                } else {
                    goldOre.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, goldOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, goldOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, goldOre, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, goldOre, e);
            }
        } else {
            p.getInventory().addItem(goldOre);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void redstoneBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.REDSTONE_ORE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.REDSTONE_ORE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        redstoneOre.setAmount(multiply);

                    } else {
                        redstoneOre.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    redstoneOre.setAmount(multiply);
                } else {
                    redstoneOre.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, redstoneOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, redstoneOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, redstoneOre, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, redstoneOre, e);
            }
        } else {
            p.getInventory().addItem(redstoneOre);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void lapisBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.LAPIS_ORE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.LAPIS_ORE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        lapisOre.setAmount(multiply);

                    } else {
                        lapisOre.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    lapisOre.setAmount(multiply);
                } else {
                    lapisOre.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, lapisOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, lapisOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, lapisOre, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, lapisOre, e);
            }
        } else {
            p.getInventory().addItem(lapisOre);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void diamondBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.DIAMOND_ORE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.DIAMOND_ORE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        diamondOre.setAmount(multiply);

                    } else {
                        diamondOre.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    diamondOre.setAmount(multiply);
                } else {
                    diamondOre.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, diamondOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, diamondOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, diamondOre, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, diamondOre, e);
            }
        } else {
            p.getInventory().addItem(diamondOre);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void emeraldBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.EMERALD_ORE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.EMERALD_ORE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        emeraldOre.setAmount(multiply);

                    } else {
                        emeraldOre.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    emeraldOre.setAmount(multiply);
                } else {
                    emeraldOre.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, emeraldOre, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, emeraldOre, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, emeraldOre, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, emeraldOre, e);
            }
        } else {
            p.getInventory().addItem(emeraldOre);
        }
        removeDrops(e);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------
    // FROM HERE, START THE BLOCK SILK TOUCH SUPPORT
    //-----------------------------------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------------------------------

    @EventHandler(priority = EventPriority.HIGH)
    public void iceBlock(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.ICE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.ICE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        iceBlock.setAmount(multiply);

                    } else {
                        iceBlock.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    iceBlock.setAmount(multiply);
                } else {
                    iceBlock.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, iceBlock, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, iceBlock, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, iceBlock, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, iceBlock, e);
            }
        } else {
            p.getInventory().addItem(iceBlock);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void snowBlock(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.SNOW_BLOCK)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.SNOW_BLOCK.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        snowBlock.setAmount(multiply);

                    } else {
                        snowBlock.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    snowBlock.setAmount(multiply);
                } else {
                    snowBlock.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, snowBlock, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, snowBlock, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, snowBlock, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, snowBlock, e);
            }
        } else {
            p.getInventory().addItem(snowBlock);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void glowstoneBlock(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.GLOWSTONE)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.GLOWSTONE.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        glowstoneBlock.setAmount(multiply);

                    } else {
                        glowstoneBlock.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    glowstoneBlock.setAmount(multiply);
                } else {
                    glowstoneBlock.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, glowstoneBlock, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, glowstoneBlock, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, glowstoneBlock, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, glowstoneBlock, e);
            }
        } else {
            p.getInventory().addItem(glowstoneBlock);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void clayBlock(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.CLAY_BALL)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.CLAY_BALL.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        clayBlock.setAmount(multiply);

                    } else {
                        clayBlock.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    clayBlock.setAmount(multiply);
                } else {
                    clayBlock.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, clayBlock, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, clayBlock, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, clayBlock, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, clayBlock, e);
            }
        } else {
            p.getInventory().addItem(clayBlock);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void enderchestBlock(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.ENDER_CHEST)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.ENDER_CHEST.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        enderchestBlock.setAmount(multiply);

                    } else {
                        enderchestBlock.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    enderchestBlock.setAmount(multiply);
                } else {
                    enderchestBlock.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, enderchestBlock, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, enderchestBlock, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, enderchestBlock, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, enderchestBlock, e);
            }
        } else {
            p.getInventory().addItem(enderchestBlock);
        }
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void bookshelvesBlock(BlockBreakEvent e) {

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (!p.getInventory().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if (!(e.getBlock().getType() == Material.BOOKSHELF)) return;
        for (String disabledWorlds : disabledWorlds)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (config.getBoolean("Custom-Pickaxe.Works-only-with-custom-pickaxe")) {
            if (!p.getInventory().getItemInHand().hasItemMeta()) return;
            if (!(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtils.c(config.getString("Custom-Pickaxe.Pickaxe.Display-Name"))))) return;
        }

        if (config.getBoolean("Fortune.Fortune-Support-Silktouch")) {
            if (isDCM) {if (p.getGameMode().equals(GameMode.CREATIVE)) return;}

            if (config.getBoolean("Fortune.Use-Whitelist")) {
                if (whitelist.contains(Material.BOOKSHELF.toString())) {

                    if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                        int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                        Random r = new Random();
                        int min = 1;
                        int multiply = r.nextInt((fortuneLevel - min) + 1) + min;
                        bookshelvesBlock.setAmount(multiply);

                    } else {
                        bookshelvesBlock.setAmount(1);
                    }
                }

            } else {

                if (p.getInventory().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortuneLevel = p.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

                    Random r = new Random();
                    int min = 1;
                    int multiply = r.nextInt((fortuneLevel - min) + 1) + min;

                    bookshelvesBlock.setAmount(multiply);
                } else {
                    bookshelvesBlock.setAmount(1);
                }

            }
            if (isAutoPickupEnabled) {
                if (!autoPickupOFF.contains(p.getName())) {
                    if (!autoSmeltOFF.contains(p.getName())) {
                        silkTouch(p, bookshelvesBlock, e);
                    } else {
                        if (autoPickupOFF.contains(p.getName())) return;
                        pickup(p, bookshelvesBlock, e);
                    }
                } else {
                    if (autoSmeltOFF.contains(p.getName())) return;
                    noPickup(p, bookshelvesBlock, e);
                }
            } else {
                if (autoSmeltOFF.contains(p.getName())) return;
                noPickup(p, bookshelvesBlock, e);
            }
        } else {
            p.getInventory().addItem(bookshelvesBlock);
        }
        removeDrops(e);
    }
}