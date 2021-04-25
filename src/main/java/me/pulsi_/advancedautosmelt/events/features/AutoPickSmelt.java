package me.pulsi_.advancedautosmelt.events.features;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
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

import java.util.Set;

public class AutoPickSmelt implements Listener {

    private final MethodUtils methodUtils;
    private final AdvancedAutoSmelt plugin;
    public AutoPickSmelt(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;
        this.methodUtils = new MethodUtils(plugin);
    }

    private final ItemStack goldIngot = new ItemStack(Material.GOLD_INGOT);
    private final ItemStack goldOre = new ItemStack(Material.GOLD_ORE);
    private final ItemStack ironIngot = new ItemStack(Material.IRON_INGOT);
    private final ItemStack ironOre = new ItemStack(Material.IRON_ORE);
    private final ItemStack stone = new ItemStack(Material.STONE);
    private final ItemStack cobblestone = new ItemStack(Material.COBBLESTONE);

    private final Set<String> autoPickupOFF = Commands.autoPickupOFF;
    private final Set<String> autoSmeltOFF = Commands.autoSmeltOFF;

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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoPickup(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        if (config.getBoolean("Enable-Legacy-Support")) {
            if (!(e.getBlock().getType().name().endsWith("LEAVES"))) {

                ItemStack item = p.getInventory().getItemInHand();
                Material itemType = p.getInventory().getItemInHand().getType();

                if (itemType.getMaxDurability() != 0) {

                    short durability = item.getDurability();
                    int durabilityLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
                    boolean shouldDamage = ((Math.random()) < (1 / (durabilityLevel + 1)));
                    if (shouldDamage)
                        item.setDurability((short) (durability + 1));

                    p.setItemInHand(item.getDurability() > item.getType().getMaxDurability() ? null : item);
                }
            }
        }

        if (e.isCancelled()) return;
        if (config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        if (!p.hasPermission("advancedautosmelt.autopickup")) return;
        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.equalsIgnoreCase(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(e.getBlock().getType().toString())) return;
        if (e.getBlock().getType() == (Material.IRON_ORE) ||
                e.getBlock().getType() == (Material.GOLD_ORE) ||
                e.getBlock().getType() == (Material.STONE) ||
                e.getBlock().getType() == (Material.CHEST) ||
                e.getBlock().getType() == (Material.FURNACE) ||
                e.getBlock().getType() == (Material.ENDER_CHEST)) return;
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltGold(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(Material.GOLD_ORE.toString())) return;
        if (!(e.getBlock().getType() == Material.GOLD_ORE)) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }

        methodUtils.autoPickSmeltGold(p, config, goldIngot, goldOre, autoPickupOFF, autoSmeltOFF, e);
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltIron(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(Material.IRON_ORE.toString())) return;
        if (!(e.getBlock().getType() == Material.IRON_ORE)) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }

        methodUtils.autoPickSmeltIron(p, config, ironIngot, ironOre, autoPickupOFF, autoSmeltOFF, e);
        removeDrops(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void autoSmeltStone(BlockBreakEvent e) {

        FileConfiguration config = plugin.getConfiguration();

        Player p = e.getPlayer();

        if (e.isCancelled()) return;
        if (config.getBoolean("Fortune.Enable-Fortune-Support")) return;
        for (String disabledWorlds : config.getStringList("Disabled-Worlds"))
            if (disabledWorlds.contains(p.getWorld().getName())) return;
        for (String blockBlacklist : config.getStringList("Disabled-Blocks"))
            if (blockBlacklist.contains(Material.STONE.toString())) return;
        if (!(e.getBlock().getType() == Material.STONE)) return;
        if (!(p.hasPermission("advancedautosmelt.smelt.stone"))) return;
        if (config.getBoolean("AutoSmelt.Disable-Creative-Mode")) {
            if (p.getGameMode().equals(GameMode.CREATIVE)) return;
        }

        methodUtils.autoPickSmeltStone(p, config, stone, cobblestone, autoPickupOFF, autoSmeltOFF, e);
        removeDrops(e);
    }
}