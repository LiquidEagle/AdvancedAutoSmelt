package me.pulsi_.advancedautosmelt.events.blocks;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.commands.Commands;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class ChestBreak implements Listener {

    private FileConfiguration config;
    private List<String> worldsBlackList;
    private boolean useLegacySupp;
    private boolean isInvFullDrop;
    private boolean isAutoPickup;
    private Set<String> autoPickupOFF;

    public ChestBreak(AdvancedAutoSmelt plugin) {
        this.config = plugin.getConfiguration();
        this.useLegacySupp = config.getBoolean("Enable-Legacy-Support");
        this.worldsBlackList = config.getStringList("Disabled-Worlds");
        this.isInvFullDrop = config.getBoolean("AutoPickup.Inv-Full-Drop-Items");
        this.isAutoPickup = config.getBoolean("AutoPickup.Enable-Autopickup");
        this.autoPickupOFF = Commands.autoPickupOFF;
    }

    private final ItemStack chest = new ItemStack(Material.CHEST, 1);

    public void dropsItems(Player p, ItemStack i) {
        if (!p.getInventory().addItem(i).isEmpty()) {
            if (isInvFullDrop) {
                p.getWorld().dropItem(p.getLocation(), i);
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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chestBreak(BlockBreakEvent e) {

        Player p = e.getPlayer();

        for (String disabledWorlds : worldsBlackList)
            if (disabledWorlds.contains(p.getWorld().getName())) return;

        if (e.isCancelled()) return;
        if (!(e.getBlock().getType() == Material.CHEST)) return;
        if (!isAutoPickup) return;
        if (autoPickupOFF.contains(p.getName())) return;
        if (e.getBlock().getState() instanceof Chest || e.getBlock().getState() instanceof DoubleChest) {
            Chest chestBlock = ((Chest) e.getBlock().getState());
            for (ItemStack itemsInTheChest : chestBlock.getInventory().getContents()) {
                if (itemsInTheChest != null) {
                    dropsItems(p, itemsInTheChest);
                    chestBlock.getInventory().clear();
                }
            }
            p.getInventory().addItem(chest);
            removeDrops(e);
        }
    }
}